package io.fmc.ui.posts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fmc.R;
import io.fmc.data.models.Announcement;

/**
 * Created by sundayakinsete on 18/05/2018.
 */

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    PostAdapterListener  postAdapterListener;
    List<Announcement> posts;

    public PostAdapter(List<Announcement> posts) {
        this.posts = posts;
    }

    public interface PostAdapterListener {
        void onItemSelected(Announcement post);
    }

    public void setOnItemClickListener(PostAdapterListener onItemClickListener){
        postAdapterListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_post_item_row,parent,false);
        return new PostAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PostAdapterViewHolder postAdapterViewHolder = (PostAdapterViewHolder)holder;
        postAdapterViewHolder.setData(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setData(List<Announcement> data) {
        this.posts = data;
        notifyDataSetChanged();
    }


    public class PostAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.post_title) TextView post_title;
//        @BindView(R.id.post_date) TextView post_date;
        @BindView(R.id.post_content) TextView post_content;
        @BindView(R.id.favorite) TextView favorite;
        @BindView(R.id.view) TextView view;

        public PostAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }

        public void setData(final Announcement post) {
            post_title.setText(post.getTitle());
            post_content.setText(Html.fromHtml(post.getContent()));
            favorite.setText(String.valueOf(post.getLikes()));

            Log.e("favorite",String.valueOf(post.getLiked()));


            if(postAdapterListener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        postAdapterListener.onItemSelected(post);
                    }
                });
            }
        }
    }

}
