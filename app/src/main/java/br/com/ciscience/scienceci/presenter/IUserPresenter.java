package br.com.ciscience.scienceci.presenter;

import android.app.Activity;

import br.com.ciscience.scienceci.model.entity.impl.Student;

/**
 * Created by pedrodimoura on 19/07/16.
 */
public interface IUserPresenter {

    void login(Student student);

    void recoveryPassword(String email, String recoveryKey);

    void destroySession();

    Student getSession();

    void showProgressBar();

    void hideProgressBar();

    void showSnackbarMessage(String message, int duration);

    void showSnackbarMessage(int resId, int duration);

    void startMainActivity();

    void onDestroy();

    void finishActivity();

    Activity getFragmentActivity();

}
