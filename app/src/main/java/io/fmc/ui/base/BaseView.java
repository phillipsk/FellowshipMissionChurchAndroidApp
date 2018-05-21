package io.fmc.ui.base;

import io.fmc.data.models.User;

/**
 * Created by sundayakinsete on 18/05/2018.
 */

public interface BaseView {

    void showError(String message);

    void showViewLoading(String message);

    void hideLoadingView();
}
