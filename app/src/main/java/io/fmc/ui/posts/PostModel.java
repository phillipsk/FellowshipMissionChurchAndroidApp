package io.fmc.ui.posts;

import java.util.List;

import io.fmc.data.FMCApi;
import io.fmc.data.models.Announcement;

/**
 * Created by sundayakinsete on 18/05/2018.
 */

public class PostModel implements PostMVP.Model {


    public interface OnPostsFetched {

        void onPostItemsFetched(List<Announcement> posts);

        void onPostItemFetched(Announcement post);

        void onError(String message);
    }



    @Override
    public void fetchPosts(OnPostsFetched onPostsFetched) {
        FMCApi.listenToPostChanges(onPostsFetched);
    }

}
