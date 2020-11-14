package io.fmc.ui.posts;

import java.util.List;

import io.fmc.data.FMCApi;
import io.fmc.data.models.AnnouncementPost;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 18/05/2018.
 */

public class PostModel implements PostMVP.Model {


    public interface OnPostsFetched {

        void onPostItemsFetched(List<AnnouncementPost> posts);

        void onPostItemFetched(AnnouncementPost post);

        void onError(String message);
    }



    @Override
    public void fetchPosts(OnPostsFetched onPostsFetched) {
        FMCApi.listenToPostChanges(onPostsFetched);
    }

}
