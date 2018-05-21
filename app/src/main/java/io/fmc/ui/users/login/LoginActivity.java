package io.fmc.ui.users.login;

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
import io.fmc.ui.users.createaccount.CreateAccountActivity;
import io.fmc.ui.users.password.PasswordResetActivity;
import io.fmc.utils.SessionManager;

public class LoginActivity extends BaseActivity implements LoginMVP.View{

    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;

    @Inject
    LoginMVP.Presenter presenter;
    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        ((AppController)getApplication()).getComponent().inject(this);

        presenter.setView(this);
    }


    @OnClick(R.id.btn_facebook_sign)
    public void facebookBtnClicked(){

    }

    @OnClick(R.id.btn_google)
    public void googleBtnClicked(){

    }

    @OnClick(R.id.label_create_account)
    public void createAccount(){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.label_forget_password)
    public void resetPassword(){
        Intent intent = new Intent(this, PasswordResetActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_sign_in)
    public void loginClicked(){
        presenter.signInClicked();
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
    public void gotoMainView(User user) {
        sessionManager.setLoginUser(user);
        sessionManager.setUserLogin();
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean isLoginDetailValid() {
        if(TextUtils.isEmpty(email.getText().toString())){
            showMessage("Email required",null);
            return false;
        }else if(TextUtils.isEmpty(password.getText().toString())){
            showMessage("Password required",null);
            return false;
        }
        return true;
    }

    @Override
    public User getLoginUser() {
        User user = new User();
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        return user;
    }

    @Override
    public void showViewLoading(String message) {
        showLoading(message);
    }

    @Override
    public void hideLoadingView() {
        hideLaoding();
    }

}
