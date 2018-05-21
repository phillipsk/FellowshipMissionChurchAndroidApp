package io.fmc.ui.posts.postdetail;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fmc.R;
import io.fmc.data.models.Announcement;
import io.fmc.ui.base.BaseActivity;

public class PostDetailActivity extends BaseActivity {

    Announcement post;
    @BindView(R.id.backdrop) ImageView backdrop;
    @BindView(R.id.content) TextView content;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.title) TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);

        post = (Announcement)getIntent().getExtras().getSerializable("post");

        setupBaseActionbar(toolbar,post.getTitle(),true);

        displayData();
    }

    private void displayData() {

        Picasso.with(getApplicationContext())
                  .load(post.getPhoto())
                  .error(R.mipmap.morning_prayer_definition)
                  .into(backdrop);


      title.setText(post.getTitle());
      content.setText(Html.fromHtml(post.getContent()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
