package io.fmc.ui.dashboard;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fmc.R;
import io.fmc.ui.base.BaseActivity;
import io.fmc.ui.posts.PostsFragment;
import io.fmc.ui.theword.AudiosFragment;
import io.fmc.utils.BottomNavigationViewHelper;

public class DashboardActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @BindView(R.id.toolbar) Toolbar toolBar;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();

        setupBaseActionbar(toolBar,"Home",false);

        setTupBottomNavigation();
    }

    private void setTupBottomNavigation() {
        switchFragments(R.id.bottom_nav_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switchFragments(item.getItemId());
                return true;
            }
        });
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

    }


    private void switchFragments(int position){
        Fragment fragment = fragmentManager.findFragmentByTag(String.valueOf(position));
        fragmentTransaction = fragmentManager.beginTransaction();

        for(Fragment eachFragment: fragmentManager.getFragments()){
            fragmentTransaction.hide(eachFragment);
        }

        if(fragment == null){
            Fragment newFrag = getItem(position);
            fragmentTransaction.add(R.id.container, newFrag,String.valueOf(position));
            fragmentTransaction.commit();
        }else{
            fragmentTransaction.show(fragment);
            fragmentTransaction.commit();
        }
    }



    public Fragment getItem(int menu_id) {
        Fragment fragment = null;
        if (menu_id == R.id.bottom_nav_home) {
            fragment = new PostsFragment();
        } else if(menu_id == R.id.bottom_nav_the_word) {
            fragment = new AudiosFragment();
        } else if(menu_id == R.id.bottom_nav_messages) {
            fragment = new PostsFragment();
        }else if(menu_id == R.id.bottom_nav_location){
            fragment = new PostsFragment();
        }
        return fragment;
    }
}
