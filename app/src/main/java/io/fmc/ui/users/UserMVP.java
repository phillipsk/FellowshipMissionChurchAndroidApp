package io.fmc.ui.users;

import io.fmc.data.models.User;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 18/05/2018.
 */

public class UserMVP {


    interface Model {
        void userLogin(User user, UserModel.OnLoginListener onLoginListener);

        void createAccount(User user,UserModel.OnCreateAccountListener onCreateAccountListener);

        void recoverPassword(String password,UserModel.OnPasswordRecoverListener onPasswordRecoverListener);
    }
}
