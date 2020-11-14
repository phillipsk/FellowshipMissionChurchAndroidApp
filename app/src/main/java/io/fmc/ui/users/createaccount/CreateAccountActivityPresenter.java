package io.fmc.ui.users.createaccount;

import io.fmc.data.models.User;
import io.fmc.ui.users.UserModel;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 18/05/2018.
 */

public class CreateAccountActivityPresenter implements CreateAccountMVP.Presenter{


    UserModel userModel;
    CreateAccountMVP.View view;

    public CreateAccountActivityPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void setView(CreateAccountMVP.View view) {
        this.view = view;
    }

    @Override
    public void signUpClicked() {
        if(view.requiredDetailsFilled()){
            User user = view.getUser();
            view.showViewLoading("Please wait");
            userModel.createAccount(user, new UserModel.OnCreateAccountListener() {
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
}
