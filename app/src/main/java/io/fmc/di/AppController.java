package io.fmc.di;

import android.app.Application;
import android.content.Context;

import io.fmc.ui.posts.PostModule;
import io.fmc.ui.users.UserModule;

/**
 * Created by sundayakinsete on 14/04/2018.
 */

public class AppController extends Application{

    private ApplicationComponent component;
    private static AppController sApp;

    private static Context context;

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
    }

    public static Context getAppContext() {
        return AppController.context;
    }

    public static ApplicationComponent getAppComponent() {
        return sApp.component;
    }


    public ApplicationComponent getComponent(){
        return component;
    }
}
