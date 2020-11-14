package io.fmc.ui.posts;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kevin Phillips and Sunday Akinsete on 18/05/2018.
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
