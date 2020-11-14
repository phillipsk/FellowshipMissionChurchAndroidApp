package io.fmc.ui.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import io.fmc.R;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 16/05/2018.
 */

public class BaseActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public void setupBaseActionbar(Toolbar toolBar, String title,boolean back){
        if (toolBar != null) {
            setSupportActionBar(toolBar);
            if(back) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            if(!TextUtils.isEmpty(title)){
                getSupportActionBar().setTitle(title);
            }
        }
    }


    public void showLoading(String message){
        progressDialog = ProgressDialog.show(this,null,message,false,false);
    }

    public void hideLaoding(){
        if(progressDialog != null){
            if(progressDialog.isShowing()){
                progressDialog.cancel();
            }
        }
    }

    public void showMessage(String message, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(null);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok,positiveListener);
        builder.setPositiveButton(R.string.cancel,negativeListener);
        builder.create().show();
    }

    public void showMessage(String message, DialogInterface.OnClickListener positiveListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(null);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok,positiveListener);
        builder.create().show();
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
