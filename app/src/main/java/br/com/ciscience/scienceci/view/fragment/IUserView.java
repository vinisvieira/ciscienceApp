package br.com.ciscience.scienceci.view.fragment;

import android.app.Activity;

import br.com.ciscience.scienceci.model.entity.impl.Student;

/**
 * Created by pedrodimoura on 19/07/16.
 */
public interface IUserView {

    void userLoggedIn();

    Student getSession();

    void login();

    void recoveryPassword();

    void showProgressBar();

    void hideProgressBar();

    void startMainActivity();

    void finishActivity();

    void showSnackbarMessage(String message, int duration);

    void showSnackbarMessage(int resId, int duration);

    Activity getFragmentActivity();

}
