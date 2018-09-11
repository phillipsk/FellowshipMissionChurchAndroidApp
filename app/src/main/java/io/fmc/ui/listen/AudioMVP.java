package io.fmc.ui.listen;

/**
 * Created by sundayakinsete on 17/05/2018.
 */

public interface AudioMVP {

    interface View {

    }

    interface Presenter {

        void setView(AudioMVP.View view);

    }

}
