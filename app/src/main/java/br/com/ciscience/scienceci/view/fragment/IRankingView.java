package br.com.ciscience.scienceci.view.fragment;

import android.app.Activity;

import br.com.ciscience.scienceci.model.entity.impl.Student;

/**
 * Created by pedrodimoura on 28/07/16.
 */
public interface IRankingView {

    void loadRanking();

    void showRankingOnUI(Student student);

    void showRefresh();

    void hideRefresh();

    void showRootLayout();

    void hideRootLayout();

    void showNetworkError();

    void hideNetworkError();

    void showEmptyRanking();

    void hideEmptyRanking();

    void showSnackbarMessage(String message, int duration);

    void showSnackbarMessage(int resId, int duration);

    Activity getFragmentActivity();

}
