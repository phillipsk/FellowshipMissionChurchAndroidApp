package io.fmc.ui.users.password;

import io.fmc.data.models.User;
import io.fmc.ui.base.BaseView;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 18/05/2018.
 */

public interface PasswordResetMVP {

    interface View extends BaseView {

        void showResponse(String message);

        boolean requiredDetailsFilled();

        User getUser();

    }

    interface Presenter {

        void setView(PasswordResetMVP.View view);

        void resetClicked();

    }

}
