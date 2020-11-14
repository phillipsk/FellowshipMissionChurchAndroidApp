package io.fmc.ui.users.createaccount;

import io.fmc.data.models.User;
import io.fmc.ui.base.BaseView;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 18/05/2018.
 */

public interface CreateAccountMVP {

    interface View extends BaseView {

        void gotoMainView(User user);

        boolean requiredDetailsFilled();

        User getUser();

    }

    interface Presenter {

        void setView(CreateAccountMVP.View view);

        void signUpClicked();

    }

}
