package io.fmc.ui.posts;

import java.util.List;

import io.fmc.data.models.Announcement;

/**
 * Created by sundayakinsete on 17/05/2018.
 */

public interface PostMVP {


    interface View {

        void displayPosts(List<Announcement> posts);

    }

    interface Presenter {

        void setView(PostMVP.View view);

        void fetchPosts();

    }


    interface Model {

        void fetchPosts(PostModel.OnPostsFetched onPostsFetched);

    }

}
