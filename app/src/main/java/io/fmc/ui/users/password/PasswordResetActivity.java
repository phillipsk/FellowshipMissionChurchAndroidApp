package io.fmc.ui.users.password;

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
import io.fmc.ui.users.createaccount.CreateAccountMVP;

public class PasswordResetActivity extends BaseActivity implements PasswordResetMVP.View {

    @BindView(R.id.email) EditText email;

    @Inject
    PasswordResetMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

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

    @OnClick(R.id.btn_reset_password)
    public void createAccount(){
        presenter.resetClicked();
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
    public void showResponse(String message) {
        showMessage(message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
    }

    @Override
    public boolean requiredDetailsFilled() {
        if(TextUtils.isEmpty(email.getText().toString())){
            showMessage("Email required",null);
            return false;
        }
        return true;
    }

    @Override
    public User getUser() {
        User user = new User();
        user.setEmail(email.getText().toString());
        return user;
    }
}
