package io.fmc.ui.users;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.fmc.ui.users.createaccount.CreateAccountActivityPresenter;
import io.fmc.ui.users.createaccount.CreateAccountMVP;
import io.fmc.ui.users.login.LoginActivityPresenter;
import io.fmc.ui.users.login.LoginMVP;
import io.fmc.ui.users.password.PasswordResetActivityPresenter;
import io.fmc.ui.users.password.PasswordResetMVP;
import io.fmc.utils.socialauth.SocialAuthentication;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 18/05/2018.
 */

@Module
public class UserModule {

    @Provides
    public LoginMVP.Presenter provideLoginActivityPresenter(){
        return new LoginActivityPresenter(new UserModel());
    }

    @Provides
    public PasswordResetMVP.Presenter providePasswordResetActivityPresenter(){
        return new PasswordResetActivityPresenter(new UserModel());
    }


    @Provides
    public CreateAccountMVP.Presenter provideCreateAccountPresenter(){
        return new CreateAccountActivityPresenter(new UserModel());
    }

    @Provides
    public UserMVP.Model provideLoginModel(){
        return new UserModel();
    }


    @Provides
    public SocialAuthentication provideSocialAuthentication(Context context){
        return new SocialAuthentication(context);
    }
}
