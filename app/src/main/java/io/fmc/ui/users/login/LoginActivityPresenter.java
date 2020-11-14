package io.fmc.ui.users.login;

import javax.inject.Inject;

import io.fmc.data.models.User;
import io.fmc.di.AppController;
import io.fmc.ui.users.UserModel;
import io.fmc.utils.SessionManager;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 14/04/2018.
 */

public class LoginActivityPresenter implements LoginMVP.Presenter {

    @Inject
    SessionManager sessionManager;

    UserModel userModel;
    LoginMVP.View view;

    public LoginActivityPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void setView(LoginMVP.View view) {

        this.view = view;
    }

    @Override
    public void signInClicked() {
        if(view.isLoginDetailValid()){
            view.showViewLoading("Please wait");
            User user = view.getLoginUser();
            userModel.userLogin(user, new UserModel.OnLoginListener() {
                @Override
                public void onComplete(User user) {
                    view.hideLoadingView();
                    view.gotoMainView(user);
                }

                @Override
                public void onError(String message) {
                    view.hideLoadingView();
                    view.showError(message);
                }
            });
        }
    }

    @Override
    public void googleSignInClicked() {

    }

    @Override
    public void facebookSignInClicked() {

    }

    @Override
    public void forgetPasswordClicked() {

    }
}
