package io.fmc.di;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.stetho.Stetho;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onesignal.OneSignal;

import org.greenrobot.greendao.database.Database;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.fmc.db.AudioMessage;
import io.fmc.db.AudioMessageDao;
import io.fmc.db.DaoMaster;
import io.fmc.db.DaoSession;
import io.fmc.ui.posts.PostModule;
import io.fmc.ui.users.UserModule;
import kotlin.Pair;

//import com.github.kittinunf.fuel.Fuel;
//import com.github.kittinunf.fuel.android.*;



/**
 * Created by  Kevin Phillips and Sunday Akinsete on 14/04/2018.
 */

public class AppController extends MultiDexApplication {

    private ApplicationComponent component;
    private static AppController sApp;

    private static Context context;

    public static final boolean ENCRYPTED = true;

    //    11/21/2019 Changed to public
    public DaoSession daoSession;
    public SupportMapFragment mapFragment;


    public static final String backendBroadCast = "FMC_BROADCAST_UPDATE";
    public static final String DEFAULT_ADDRESS = "god.works";
    public static final String API_SERVICE = "mp3";

//    private static FellowshipApplication sharedInstance;
    private static AppController sharedInstance;
    private List<? extends Pair<String, ? extends Object>> params;

    public static synchronized AppController getInstance() {
//        ((AppController)getActivity().getApplication()).getComponent().inject(this);

        return sharedInstance;
    }
    Context contextOld;

    public static final String BROADCAST_DOWNLOAD_AUDIO_FAILED = "download_audio_failed";
    public static final String BROADCAST_DOWNLOAD_AUDIO_SUCCESSFUL = "download_audio_successful";
    public static final String BROADCAST_PLAY_MEDIA_AT_POSITION = "play_media_at_position";
    public static final String BROADCAST_PAUSE_MEDIA_AT_POSITION = "pause_media_at_position";

    public List<AudioMessage> audioMessages = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        AppController.context = getApplicationContext();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .userModule(new UserModule())
                .postModule(new PostModule())
                .build();

        //FirebaseApp.initializeApp(this);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        contextOld = this;
        Stetho.initializeWithDefaults(this);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        sharedInstance = this;
        sharedInstance.initBackend(contextOld);

//        ((AppController)getActivity().getApplication()).getComponent().inject(this);


        initDatabase();
        initMapFragment();


//        JUL-20
        fetchAudioMessage();
        try {
            useGraphAPI();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



//      old code above
//    new code below

    public static Context getAppContext() {
        return AppController.context;
    }

    public static ApplicationComponent getAppComponent() {
        return sApp.component;
    }


    public ApplicationComponent getComponent(){
        return component;
    }

//    new code above
//    old code below
    private void initBackend(Context contextOld) {
        this.contextOld = this;
    }


    private String getFullEndpoint(String endpoint){

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(DEFAULT_ADDRESS)
                .appendEncodedPath(API_SERVICE)
                .appendEncodedPath(endpoint);
//        Log(builder.build().toString());
        return builder.build().toString();
    }


    private void initDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"fmcdb"); //The users-db here is the name of our database.
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    private void initMapFragment() {
        mapFragment = SupportMapFragment.newInstance();
    }
    public SupportMapFragment getMapFragment(){
        return mapFragment;
    }




    public void fetchAudioMessage(){

        Fuel.get(getFullEndpoint("api.php"), params).responseString(new Handler<String>() {

            private Request request;
            private Response response;
            private String data;

            @Override
            public void failure(@NotNull Request request, @NotNull Response response, @NotNull FuelError fuelError) {
                Log.e("files_audio",fuelError.toString());
                Gson gson = new Gson();

            }

            @Override
            public void success(@NonNull Request request, @NonNull Response response, String data) {
                this.request = request;
                this.response = response;
                this.data = data;
                //do something when it is successful
//                Log.e("files_audio", data);
                Gson gson = new Gson();
                JSONObject jsonObject = convertStringToObject(data);

                try {
                    if (jsonObject.getBoolean("status")) {
                        JSONArray audioFIleArray = jsonObject.getJSONArray("audio_files");
//                        This type literal is create simply to create the correct parameter for the
//                        gson object mapper method below
                        Type audioType = new TypeToken<List<AudioMessage>>() {}.getType();
                        audioMessages = gson.fromJson(audioFIleArray.toString(), audioType);
                        updateAudioMessages(audioMessages);
                    } else {
                        sendLocalBroadcast(BROADCAST_DOWNLOAD_AUDIO_FAILED, null, 0);
                    }

                } catch (Exception a) {
                    sendLocalBroadcast(BROADCAST_DOWNLOAD_AUDIO_FAILED, null, 0);
                }
            }
        });

    }

    private void updateAudioMessages(List<AudioMessage> audioMessages) {
        AudioMessageDao audioMessageDao = daoSession.getAudioMessageDao();
        audioMessageDao.deleteAll();
        audioMessageDao.saveInTx(audioMessages);
        sendLocalBroadcast(BROADCAST_DOWNLOAD_AUDIO_SUCCESSFUL,null,0);
    }


    private JSONObject convertStringToObject(String data){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject = new JSONObject(data);
        }catch (Exception s){

        }

        return jsonObject;
    }


    public void sendLocalBroadcast(String type, HashMap<String,Object> object, int position){
        Intent intent = new Intent(backendBroadCast);
        intent.putExtra("broadcast_type",type);
        intent.putExtra("object",object);
        intent.putExtra("position",position);
        context.sendBroadcast(intent);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);
    }

    public void useGraphAPI() throws Exception {
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        accessToken = "EAAEamWgsjN4BALXr7dq1MDjywGvYYBhyKQ6qp1xiCy4XTmPoPXWuOuqIJmx05OJYeZCGjkJ2sACqNFJ3ZApwZATfzopowUIsOg6ulTFywxCnkDOX2UZCSLYfRZBKNzFOV8mcQKMZAtk8u91sfcQwBTNIh4fi6IHTsraVZADnBZB7gUf4LLxcxVZBnfdHZAPeVzZCJgwxZCBS04dkvQZDZD"
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/120085814675079/published_posts",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        Log.d("GraphAPI FB debug", String.valueOf(response));
                        Log.d("GraphAPI FB debug", response.toString());
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "picture,created_time,story,id,icon,full_picture,attachments{media,media_type},call_to_action,message");
        parameters.putString("limit", "10");
        request.setParameters(parameters);
        request.executeAsync();

    }




}
