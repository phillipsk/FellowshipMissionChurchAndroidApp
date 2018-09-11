package io.fmc;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.google.android.gms.common.api.Response;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.database.Database;
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
import com.facebook.stetho.Stetho;
import com.onesignal.OneSignal;

public class FellowshipApplication extends Application {

    public static final boolean ENCRYPTED = true;
    private DaoSession daoSession;
    public static final String backendBroadCast = "FMC_BROADCAST_UPDATE";
    public static final String DEFAULT_ADDRESS = "fellowshipmission.church";
    public static final String API_SERVICE = "mp3";

    private static FellowshipApplication sharedInstance;
    public static synchronized FellowshipApplication getInstance() {
        return sharedInstance;
    }
    Context context;

    public static final String BROADCAST_DOWNLOAD_AUDIO_FAILED = "download_audio_failed";
    public static final String BROADCAST_DOWNLOAD_AUDIO_SUCCESSFUL = "download_audio_successful";
    public static final String BROADCAST_PLAY_MEDIA_AT_POSITION = "play_media_at_position";
    public static final String BROADCAST_PAUSE_MEDIA_AT_POSITION = "pause_media_at_position";

    public List<AudioMessage> audioMessages = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        context = this;
        Stetho.initializeWithDefaults(this);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        sharedInstance = this;
        sharedInstance.initBackend(context);


        initDatabase();
    }

    private void initBackend(Context context) {
        this.context = this;
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


    public void fetchAudioMessage(){
        Fuel.get(getFullEndpoint("api.php")).responseString(new Handler<String>() {

            @Override
            public void failure(Request request, com.github.kittinunf.fuel.core.Response response, FuelError fuelError) {

            }

            @Override
            public void success(Request request, com.github.kittinunf.fuel.core.Response response, String s) {
                Log.e("files_audio",s);
                Gson gson = new Gson();
                JSONObject jsonObject = convertStringToObject(s);

                try {
                    if (jsonObject.getBoolean("status")){
                        JSONArray audioFIleArray = jsonObject.getJSONArray("audio_files");
                        Type audioType = new TypeToken<List<AudioMessage>>() {}.getType();

                        audioMessages = gson.fromJson(audioFIleArray.toString(), audioType);
                        updateAudioMessages(audioMessages);
                    }else{
                        sendLocalBroadcast(BROADCAST_DOWNLOAD_AUDIO_FAILED,null,0);
                    }

                }catch (Exception a){
                    sendLocalBroadcast(BROADCAST_DOWNLOAD_AUDIO_FAILED,null,0);
                }
            }

            public void success(DownloadManager.Request request, Response response, String s) {
                Log.e("files_audio",s);
                Gson gson = new Gson();
                JSONObject jsonObject = convertStringToObject(s);

                try {
                    if (jsonObject.getBoolean("status")){
                        JSONArray audioFIleArray = jsonObject.getJSONArray("audio_files");
                        Type audioType = new TypeToken<List<AudioMessage>>() {}.getType();

                        audioMessages = gson.fromJson(audioFIleArray.toString(), audioType);
                        updateAudioMessages(audioMessages);
                    }else{
                        sendLocalBroadcast(BROADCAST_DOWNLOAD_AUDIO_FAILED,null,0);
                    }

                }catch (Exception a){
                    sendLocalBroadcast(BROADCAST_DOWNLOAD_AUDIO_FAILED,null,0);
                }
            }


            public void failure(DownloadManager.Request request, Response response, FuelError fuelError) {

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

}
