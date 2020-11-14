package io.fmc.ui.users.password;

import io.fmc.data.models.User;
import io.fmc.ui.users.UserModel;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 18/05/2018.
 */

public class PasswordResetActivityPresenter implements PasswordResetMVP.Presenter{


    UserModel userModel;
    PasswordResetMVP.View view;

    public PasswordResetActivityPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void setView(PasswordResetMVP.View view) {
        this.view = view;
    }

    @Override
    public void resetClicked() {
        if(view.requiredDetailsFilled()){
            User user = view.getUser();
            view.showViewLoading("Please wait");
            userModel.recoverPassword(user.getEmail(), new UserModel.OnPasswordRecoverListener() {
                @Override
                public void onComplete(String message) {
                    view.hideLoadingView();
                    view.showResponse(message);
                }

                @Override
                public void onError(String message) {
                    view.hideLoadingView();
                    view.showError(message);
                }
            });
        }
    }
}
