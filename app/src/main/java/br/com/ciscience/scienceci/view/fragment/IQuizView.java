package br.com.ciscience.scienceci.view.fragment;

import android.app.Activity;

import br.com.ciscience.scienceci.model.entity.impl.Quiz;

/**
 * Created by pedrodimoura on 07/07/16.
 */
public interface IQuizView {

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

    Activity getFragmentActivity();

}
