package io.fmc.ui.base;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 18/05/2018.
 */

public interface BaseView {

    void showError(String message);

    void showViewLoading(String message);

    void hideLoadingView();
}
