package io.fmc.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import io.fmc.data.FMCApi;
import io.fmc.ui.posts.PostModule;
import io.fmc.ui.posts.PostsFragment;
import io.fmc.ui.users.UserModule;
import io.fmc.ui.users.createaccount.CreateAccountActivity;
import io.fmc.ui.users.login.LoginActivity;
import io.fmc.ui.users.login.LoginActivityPresenter;
import io.fmc.ui.users.password.PasswordResetActivity;


/**
 * Created by  Kevin Phillips and Sunday Akinsete on 21/02/2018.
 */


@Singleton
@Component(modules = {ApplicationModule.class, UserModule.class, PostModule.class})
public interface ApplicationComponent {

    void inject(LoginActivity target);

    void inject(LoginActivityPresenter target);

    void inject(CreateAccountActivity target);

    void inject (PasswordResetActivity target);

    void inject (PostsFragment target);

    void inject (FMCApi fmcApi);

    Context context();
//
//    void inject(UserRepoListActivity target);
//
//    void inject(RepoDetailActivity target);

}


