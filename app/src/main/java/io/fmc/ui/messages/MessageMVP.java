package io.fmc.ui.messages;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 17/05/2018.
 */

public interface MessageMVP {

    interface View {

    }

    interface Presenter {

        void setView(MessageMVP.View view);

    }


}
