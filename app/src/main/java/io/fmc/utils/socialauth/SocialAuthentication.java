package io.fmc.utils.socialauth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import org.json.JSONObject;

import java.util.Arrays;

import io.fmc.BuildConfig;
import io.fmc.R;
import io.fmc.data.models.User;
import io.fmc.di.AppController;
import io.fmc.utils.LogHelper;


/**
 * Created by  Kevin Phillips and Sunday Akinsete on 29/05/2018.
 */

public class SocialAuthentication {

    private Context context;
    GoogleApiClient googleApiClient;


    public SocialAuthentication(Context context) {
        this.context = context;

    }

    public void init(FragmentActivity context){
        FacebookSdk.sdkInitialize(context);
    }

    public enum Type {
        GOOGLE,FACEBOOK,TWITTER
    }




    SocialAuthenticationListener socialAuthenticationListener;

    public interface SocialAuthenticationListener {
        void onAuthenticationComplete(User user);
        void onAuthenticationError(String error);
    }


    /**
     * Start facebook login
     */
    public CallbackManager initFacebookRegistration(Activity context, final SocialAuthenticationListener socialAuthenticationListener) {
        FacebookSdk.sdkInitialize(AppController.getAppContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

        CallbackManager callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(context, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken(),socialAuthenticationListener);
            }

            @Override
            public void onCancel() {
                socialAuthenticationListener.onAuthenticationError("Facebook login cancelled.");
            }

            @Override
            public void onError(FacebookException error) {
                socialAuthenticationListener.onAuthenticationError(error.toString());
            }
        });

        return callbackManager;
    }


    /***
     * Handle facebook token authentication
     * @param accessToken
     * @param socialAuthenticationListener
     */
    private void handleFacebookAccessToken(final AccessToken accessToken, final SocialAuthenticationListener socialAuthenticationListener) {
        final AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    LogHelper.e("object",object);
                    LogHelper.e("response",response);
                    User user = new User();

                    String photo = "https://graph.facebook.com/" + object.getString("id") + "/picture?type=large";


                    user.setName(object.optString("name"));
                    user.setEmail(object.optString("email"));
                    user.setEmail(object.optString("gender"));
                    user.setPhoto(photo);

                    socialAuthenticationListener.onAuthenticationComplete(user);

                    handleLogin(credential,user,socialAuthenticationListener);

                }catch (Exception s){
                    socialAuthenticationListener.onAuthenticationError(s.toString());
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }




    private void handleLogin(final AuthCredential credential, final User user, final SocialAuthenticationListener socialAuthenticationListener) {

        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    socialAuthenticationListener.onAuthenticationComplete(user);
                }else{
                    socialAuthenticationListener.onAuthenticationError(task.getException().getMessage());
                }
            }
        });
    }







    /**
     * Initiate Google Sign In
     *
     * @param context
     */
    public void initLoginWithGoogle(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        };

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .enableAutoManage((FragmentActivity) context, onConnectionFailedListener)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        ((FragmentActivity) context).startActivityForResult(signInIntent, 1100);
    }


    public void handleSignInResult(GoogleSignInResult googleSignInResult,SocialAuthenticationListener socialAuthenticationListener) {
        if (googleSignInResult.isSuccess()) {
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();
            AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

            User user = new User();
            user.setName(account.getDisplayName());
            user.setEmail(account.getEmail());
            user.setPhoto(account.getPhotoUrl().toString());


            socialAuthenticationListener.onAuthenticationComplete(user);

            //handleLogin(authCredential,user,socialAuthenticationListener);

        } else {
            socialAuthenticationListener.onAuthenticationError("Google login failed");
            Log.e("googleSignInResult", googleSignInResult.getStatus().toString());
        }
    }

}
