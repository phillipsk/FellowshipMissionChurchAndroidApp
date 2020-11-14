package io.fmc.ui.dashboard;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fmc.R;
import io.fmc.ui.aboutus.AboutUsFragment;
import io.fmc.ui.base.BaseActivity;
import io.fmc.ui.connect.StayConnectedFragment;
import io.fmc.ui.listen.AudiosFragment;
import io.fmc.ui.location.LocationFragment;
import io.fmc.ui.posts.PostsFragment;
import io.fmc.utils.BottomNavigationViewHelper;

//import io.fmc.ui.bible.BibleFragment;

public class DashboardActivity extends BaseActivity implements StayConnectedFragment.OnFragmentInteractionListener {

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

//        setupBaseActionbar(toolBar,"Home",false);
        setupBaseActionbar(toolBar,getString(R.string.app_name_long),false);



        setTupBottomNavigation();

//        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_the_word);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_the_word);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_the_word);
    }

    private void setTupBottomNavigation() {
        switchFragments(R.id.bottom_nav_connected);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switchFragments(item.getItemId());
            return true;
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
//        fragment = new BibleFragment();
        if (menu_id == R.id.bottom_nav_connected) {
            fragment = new AboutUsFragment();
//        }else if (menu_id == R.id.bottom_nav_connect){
//            fragment = new StayConnectedFragment();
        }else if(menu_id == R.id.bottom_nav_the_word) {
            fragment = new PostsFragment();
        }else if(menu_id == R.id.bottom_nav_listen) {
            fragment = new AudiosFragment();
        }else if (menu_id == R.id.bottom_nav_prayer){
            fragment = new AboutUsFragment();
        }else if(menu_id == R.id.bottom_nav_info){
            fragment = new LocationFragment();
        }
        return fragment;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
