package io.fmc.ui.users;

import io.fmc.data.FMCApi;
import io.fmc.data.models.User;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 18/05/2018.
 */

public class UserModel implements UserMVP.Model {

    public interface OnLoginListener {void onComplete(User user);void onError(String message);}

    public interface OnCreateAccountListener {void onComplete(User user);void onError(String message);}

    public interface OnPasswordRecoverListener {void onComplete(String message);void onError(String message);}



    @Override
    public void userLogin(User user, OnLoginListener onLoginListener) {
        FMCApi.authenticateUserWithEmailAndPassword(user,onLoginListener);
    }

    @Override
    public void createAccount(User user, OnCreateAccountListener onCreateAccountListener) {
        FMCApi.createUserAccountWithEmailAndPassword(user,onCreateAccountListener);
    }

    @Override
    public void recoverPassword(String email, OnPasswordRecoverListener onPasswordRecoverListener) {
        FMCApi.recoverUserPassword(email,onPasswordRecoverListener);
    }


}
