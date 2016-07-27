package br.com.ciscience.scienceci.view.activity;

import android.app.Activity;

/**
 * Created by pedrodimoura on 27/07/16.
 */
public interface IQuizResultView {

    void sendQuizResults();

    void showToastMessage(String message, int duration);

    void showToastMessage(int resId, int duration);

    Activity getActivityContext();

}
