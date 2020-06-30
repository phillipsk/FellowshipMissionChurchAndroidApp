package io.fmc.di;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

import org.greenrobot.greendao.database.Database;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.fmc.db.AudioMessage;
import io.fmc.db.AudioMessageDao;
import io.fmc.db.DaoMaster;
import io.fmc.db.DaoSession;
import io.fmc.ui.posts.PostModule;
import io.fmc.ui.users.UserModule;

/**
 * Created by sundayakinsete on 14/04/2018.
 */

public class AppController extends MultiDexApplication {

    private ApplicationComponent component;
    private static AppController sApp;

    private static Context context;

    public static final boolean ENCRYPTED = true;

    //    11/21/2019 Changed to public
    public DaoSession daoSession;

    public static final String backendBroadCast = "FMC_BROADCAST_UPDATE";
    public static final String DEFAULT_ADDRESS = "god.works";
    public static final String API_SERVICE = "mp3";

//    private static FellowshipApplication sharedInstance;
    private static AppController sharedInstance;

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




//    public void fetchAudioMessage(){
//
////        FuelManager.instance.basePath = getFullEndpoint("api.php");
//
//        Fuel.get(getFullEndpoint("api.php"), null).responseString(new Handler<String>() {
////        Fuel.get(getFullEndpoint("api.php")).responseString(new Handler<String>() {
//
//            @Override
//            public void success(String s) {
//                Log.e("files_audio", s);
//                Gson gson = new Gson();
//                JSONObject jsonObject = convertStringToObject(s);
//
//                try {
//                    if (jsonObject.getBoolean("status")) {
//                        JSONArray audioFIleArray = jsonObject.getJSONArray("audio_files");
//                        Type audioType = new TypeToken<List<AudioMessage>>() {
//                        }.getType();
//
//                        audioMessages = gson.fromJson(audioFIleArray.toString(), audioType);
//                        updateAudioMessages(audioMessages);
//                    } else {
//                        sendLocalBroadcast(BROADCAST_DOWNLOAD_AUDIO_FAILED, null, 0);
//                    }
//
//                } catch (Exception a) {
//                    sendLocalBroadcast(BROADCAST_DOWNLOAD_AUDIO_FAILED, null, 0);
//                }
//            }
//
//            @Override
//            public void failure(@NotNull FuelError fuelError) {
//
//            }
//
//        });
//    }

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
}
