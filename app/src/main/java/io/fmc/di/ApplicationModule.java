package io.fmc.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.fmc.utils.SessionManager;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 21/02/2018.
 */


@Module
public class ApplicationModule {

    private Application application;


    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return application;
    }


    @Provides
    public SessionManager provideSessionManager(Context context){
        return new SessionManager(context);
    }

}
