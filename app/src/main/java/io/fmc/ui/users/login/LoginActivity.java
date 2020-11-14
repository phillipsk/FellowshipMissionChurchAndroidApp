package io.fmc.ui.users.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

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
import io.fmc.utils.socialauth.SocialAuthentication;

public class LoginActivity extends BaseActivity implements LoginMVP.View, SocialAuthentication.SocialAuthenticationListener{

    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;

    @Inject
    LoginMVP.Presenter presenter;
    @Inject
    SessionManager sessionManager;

    @Inject
    public SocialAuthentication socialAuthentication;
    public CallbackManager mCallbackManager;
    public TwitterAuthClient twitterAuthClient;
    public SocialAuthentication.Type socialLoginType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        ((AppController)getApplication()).getComponent().inject(this);

        presenter.setView(this);

        socialAuthentication.init(this);
    }


    @OnClick(R.id.btn_facebook_sign)
    public void facebookBtnClicked(){
        socialLoginType = SocialAuthentication.Type.FACEBOOK;
        mCallbackManager = socialAuthentication.initFacebookRegistration(this,this);
    }

    @OnClick(R.id.btn_google)
    public void googleBtnClicked(){
        socialLoginType = SocialAuthentication.Type.GOOGLE;
        socialAuthentication.initLoginWithGoogle(this);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (socialLoginType){
            case FACEBOOK:
                mCallbackManager.onActivityResult(requestCode, resultCode, data);
                break;
            case GOOGLE:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                socialAuthentication.handleSignInResult(result,this);
                break;
            case TWITTER:
                twitterAuthClient.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }




    @Override
    public void onAuthenticationComplete(User user) {
        startActivity(new Intent(this, DashboardActivity.class));
        try {
            useGraphAPI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAuthenticationError(String error) {

    }

    public void useGraphAPI() throws Exception {
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

//        accessToken = "EAAEamWgsjN4BALXr7dq1MDjywGvYYBhyKQ6qp1xiCy4XTmPoPXWuOuqIJmx05OJYeZCGjkJ2sACqNFJ3ZApwZATfzopowUIsOg6ulTFywxCnkDOX2UZCSLYfRZBKNzFOV8mcQKMZAtk8u91sfcQwBTNIh4fi6IHTsraVZADnBZB7gUf4LLxcxVZBnfdHZAPeVzZCJgwxZCBS04dkvQZDZD"
//        EAAEamWgsjN4BALmlv4ZCnl08DTMzyW1EHw4iGO5lZB6K8tyFXRrTqKHj2dGmK2DLmoMXLGROsPWGLi4JoPNyJ8ZCa4bBcC9ZBApoSURMvMesGBsHWBeYpFoTfTGy4b44yRRDZCZCgQi2RmZBNo7zntUnp92UnOSSYy9FxNZA5GZAd8UyUFFMVyh9GlZAgcATBdwp5wekb6ZCaefFgZDZD
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/120085814675079/published_posts",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        Log.d("GraphAPI FB debug", String.valueOf(response));
                        Log.d("GraphAPI FB debug", response.toString());
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "picture,created_time,story,id,icon,full_picture,attachments{media,media_type},call_to_action,message");
        parameters.putString("limit", "10");
        request.setParameters(parameters);
        request.executeAsync();

    }
}
