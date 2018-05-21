package io.fmc.di;

import android.app.Application;

import io.fmc.ui.posts.PostModule;
import io.fmc.ui.users.UserModule;

/**
 * Created by sundayakinsete on 14/04/2018.
 */

public class AppController extends Application{

    private ApplicationComponent component;
    private static AppController sApp;


    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .userModule(new UserModule())
                .postModule(new PostModule())
                .build();

        //FirebaseApp.initializeApp(this);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public static ApplicationComponent getAppComponent() {
        return sApp.component;
    }


    public ApplicationComponent getComponent(){
        return component;
    }
}
