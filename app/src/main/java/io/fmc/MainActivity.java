package io.fmc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.fmc.ui.dashboard.DashboardActivity;
import io.fmc.ui.users.login.LoginActivity;
import io.fmc.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager sessionManager = new SessionManager(this);

        if(sessionManager.isLoggedIn()) {
            startActivity(new Intent(this, DashboardActivity.class));
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();


    }
}


