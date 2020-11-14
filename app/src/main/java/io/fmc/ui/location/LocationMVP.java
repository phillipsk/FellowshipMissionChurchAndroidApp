package io.fmc.ui.location;

import io.fmc.ui.messages.MessageMVP;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 17/05/2018.
 */

public interface LocationMVP {


    interface View {

    }

    interface Presenter {

        void setView(LocationMVP.View view);

    }

}
