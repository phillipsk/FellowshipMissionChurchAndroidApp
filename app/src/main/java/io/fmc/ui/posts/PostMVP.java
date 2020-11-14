package io.fmc.ui.posts;

import java.util.List;

import io.fmc.data.models.AnnouncementPost;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 17/05/2018.
 */

public interface PostMVP {


    interface View {

        void displayPosts(List<AnnouncementPost> posts);

    }

    interface Presenter {

        void setView(PostMVP.View view);

        void fetchPosts();

    }


    interface Model {

        void fetchPosts(PostModel.OnPostsFetched onPostsFetched);

    }

}
