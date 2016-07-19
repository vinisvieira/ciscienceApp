package br.com.ciscience.scienceci.view.fragment;

import android.app.Activity;

/**
 * Created by pedrodimoura on 19/07/16.
 */
public interface IUserView {

    void login();

    void showProgressBar();

    void hideProgressBar();

    void startMainActivity();

    void showSnackbarMessage(String message, int duration);

    void showSnackbarMessage(int resId, int duration);

    Activity getFragmentActivity();

}
