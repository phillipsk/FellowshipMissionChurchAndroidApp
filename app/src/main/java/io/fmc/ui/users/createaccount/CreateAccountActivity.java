package io.fmc.ui.users.createaccount;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fmc.R;
import io.fmc.data.models.User;
import io.fmc.di.AppController;
import io.fmc.ui.base.BaseActivity;
import io.fmc.ui.dashboard.DashboardActivity;
import io.fmc.utils.SessionManager;

public class CreateAccountActivity extends BaseActivity implements CreateAccountMVP.View {

    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.re_password) EditText re_password;

    @Inject
    CreateAccountMVP.Presenter presenter;

    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ButterKnife.bind(this);

        ((AppController)getApplication()).getComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.setView(this);
    }

    @OnClick(R.id.btn_back)
    public void goBack(){
        finish();
    }

    @OnClick(R.id.btn_create_account)
    public void createAccount(){
        presenter.signUpClicked();
    }

    @Override
    public void showError(String message) {
        showMessage(message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }

    @Override
    public void showViewLoading(String message) {
        showLoading(message);
    }

    @Override
    public void hideLoadingView() {
        hideLaoding();
    }

    @Override
    public void gotoMainView(User user) {
        sessionManager.setLoginUser(user);
        sessionManager.setUserLogin();
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean requiredDetailsFilled() {
        if(TextUtils.isEmpty(email.getText().toString())){
            showMessage("Email required",null);
            return false;
        }else if(TextUtils.isEmpty(password.getText().toString())){
            showMessage("Password required",null);
            return false;
        }else if(TextUtils.isEmpty(re_password.getText().toString())){
            showMessage("Re-enter password",null);
            return false;
        }else if(!re_password.getText().toString().equals(password.getText().toString())){
            showMessage("Password doesn't match",null);
            return false;
        }
        return true;
    }

    @Override
    public User getUser() {
        User user = new User();
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        return user;
    }
}
