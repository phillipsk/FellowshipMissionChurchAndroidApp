package io.fmc.ui.messages;

import io.fmc.ui.posts.PostMVP;

/**
 * Created by sundayakinsete on 17/05/2018.
 */

public interface MessageMVP {

    interface View {

    }

    interface Presenter {

        void setView(MessageMVP.View view);

    }


}
