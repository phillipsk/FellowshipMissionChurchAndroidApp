package io.fmc.ui.posts;

import dagger.Module;
import dagger.Provides;
import io.fmc.ui.users.UserMVP;
import io.fmc.ui.users.UserModel;
import io.fmc.ui.users.createaccount.CreateAccountActivityPresenter;
import io.fmc.ui.users.createaccount.CreateAccountMVP;

/**
 * Created by sundayakinsete on 18/05/2018.
 */


@Module
public class PostModule {


    @Provides
    public PostMVP.Presenter providePostFragmentPresenter(){
        return new PostFragmentPresenter(new PostModel());
    }

    @Provides
    public PostMVP.Model providePostModel(){
        return new PostModel();
    }
}
