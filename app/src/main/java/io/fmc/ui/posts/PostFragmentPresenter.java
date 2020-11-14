package io.fmc.ui.posts;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import io.fmc.data.models.AnnouncementPost;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 17/05/2018.
 */

public class PostFragmentPresenter implements PostMVP.Presenter {


    PostMVP.View view;
    PostMVP.Model postModel;

    public PostFragmentPresenter(PostModel postModel) {
        this.postModel = postModel;
    }

    @Override
    public void setView(PostMVP.View view) {
        this.view = view;
    }

    @Override
    public void fetchPosts() {
        postModel.fetchPosts(new PostModel.OnPostsFetched() {
            @Override
            public void onPostItemsFetched(List<AnnouncementPost> posts) {
                if(view != null){
                    Collections.reverse(posts);
                    view.displayPosts(posts);
                }
            }

            @Override
            public void onPostItemFetched(AnnouncementPost post) {

            }

            @Override
            public void onError(String message) {
                Log.e("databaseError", String.valueOf(message));

            }
        });
    }
}
