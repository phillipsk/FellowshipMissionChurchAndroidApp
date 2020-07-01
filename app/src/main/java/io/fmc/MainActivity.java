package io.fmc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.fmc.ui.dashboard.DashboardActivity;
import io.fmc.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SessionManager sessionManager = new SessionManager(this);

        //if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(this, DashboardActivity.class));
        /*} else {
//            startActivity(new Intent(this, LoginActivity.class));
            startActivity(new Intent(this, DashboardActivity.class));

        }*/
        finish();

//        String COMPLETE_SERVER_URL = "https://api.scripture.api.bible/v1/bibles";
//        BibleDownloader.run(COMPLETE_SERVER_URL);


    }
}


