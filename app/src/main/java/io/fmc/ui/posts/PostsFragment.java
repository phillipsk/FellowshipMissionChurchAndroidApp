package io.fmc.ui.posts;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fmc.R;
import io.fmc.data.models.Announcement;
import io.fmc.di.AppController;
import io.fmc.ui.base.BaseFragment;
import io.fmc.ui.posts.postdetail.PostDetailActivity;
import io.fmc.ui.videoplayer.PlayerActivity;
import io.fmc.utils.SimpleDividerItemDecoration;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends BaseFragment implements PostMVP.View {


    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    PostAdapter postAdapter;
    List<Announcement> posts = new ArrayList<>();

    @Inject
    PostMVP.Presenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ((AppController)getActivity().getApplication()).getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        ButterKnife.bind(this,view);

        setupRecyclerView();

        presenter.setView(this);

        presenter.fetchPosts();

        return view;
    }

    private void setupRecyclerView() {
        postAdapter =  new PostAdapter(posts);
        postAdapter.setOnItemClickListener(new PostAdapter.PostAdapterListener() {
            @Override
            public void onItemSelected(Announcement post) {
                Intent intent;
                if(post.getContentType() == Announcement.Type.VIDEO) {
                    intent = new Intent(getContext(), PlayerActivity.class);
                    intent.putExtra("video_url", post.getVideo_url());
                }else{
                    intent = new Intent(getContext(), PostDetailActivity.class);
                    intent.putExtra("post", post);

                }
                startActivity(intent);

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setAdapter(postAdapter);
    }

    @Override
    public void displayPosts(List<Announcement> posts) {
        postAdapter.setData(posts);
    }

//commented until post activity enabled
//    @OnClick(R.id.btn_add_post)
//    public void addPost(){
//
//    }
}
