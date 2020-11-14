package io.fmc.ui.users.login;

import io.fmc.data.models.User;
import io.fmc.ui.base.BaseView;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 14/04/2018.
 */

public interface LoginMVP {

    interface View extends BaseView{

        void gotoMainView(User user);

        boolean isLoginDetailValid();

        User getLoginUser();

    }

    interface Presenter {

        void setView(LoginMVP.View view);

        void signInClicked();

        void googleSignInClicked();

        void facebookSignInClicked();

        void forgetPasswordClicked();

    }


}
