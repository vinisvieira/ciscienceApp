package br.com.ciscience.scienceci.presenter;

import android.app.Activity;

import br.com.ciscience.scienceci.model.entity.impl.Quiz;

/**
 * Created by pedrodimoura on 15/06/16.
 */
public interface IQuizPresenter {

    void loadAvaiableQuiz();

    void showQuizOnUI(Quiz quiz);

    void showRefresh();

    void hideRefresh();

    void showRootLayout();

    void hideRootLayout();

    void showNetworkError();

    void hideNetworkError();

    void showEmptyQuiz();

    void hideEmptyQuiz();

    void showSnackbarMessage(String message, int duration);

    void showSnackbarMessage(int resId, int duration);

    void onDestroy();

    Activity getFragmentActivity();

}
