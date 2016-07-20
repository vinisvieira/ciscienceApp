package br.com.ciscience.scienceci.presenter.impl;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.dao.local.IUserLocalAPI;
import br.com.ciscience.scienceci.model.dao.local.impl.UserLocalAPI;
import br.com.ciscience.scienceci.model.dao.remote.impl.QuizRemoteAPI;
import br.com.ciscience.scienceci.model.entity.impl.Quiz;
import br.com.ciscience.scienceci.presenter.IQuizPresenter;
import br.com.ciscience.scienceci.util.Constants;
import br.com.ciscience.scienceci.view.fragment.IQuizView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pedrodimoura on 15/06/16.
 */
public class QuizPresenter implements IQuizPresenter {

    private IQuizView mIQuizView;
    private QuizRemoteAPI mQuizRemoteAPI;
    private IUserLocalAPI mIUserLocalAPI;

    private CompositeSubscription mCompositeSubscription;

    public QuizPresenter(IQuizView iQuizView) {
        this.mIQuizView = iQuizView;
        this.mIUserLocalAPI = new UserLocalAPI(this.getFragmentActivity());
        this.mQuizRemoteAPI = QuizRemoteAPI.getInstance();
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void loadAvaiableQuiz() {
        this.mIQuizView.showRefresh();
        this.mIQuizView.hideNetworkError();
        this.mIQuizView.hideEmptyQuiz();
        this.mIQuizView.showRootLayout();
        this.mCompositeSubscription
                .add(
                        this.mQuizRemoteAPI
                                .getAPI()
                                .getQuiz(this.mIUserLocalAPI.getSession().getToken())
                                .subscribeOn(Schedulers.io())
                                .flatMap(quiz -> Observable.from(quiz))
                                .defaultIfEmpty(null)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Quiz>() {
                                    @Override
                                    public void onCompleted() {
                                        hideRefresh();
                                        Log.d(Constants.DEBUG_KEY, "onCompleted");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        hideRootLayout();
                                        hideEmptyQuiz();
                                        hideRefresh();
                                        showNetworkError();
                                        showSnackbarMessage(R.string.error_network_processing, Snackbar.LENGTH_LONG);
                                        Log.d(Constants.DEBUG_KEY, "onError");
                                    }

                                    @Override
                                    public void onNext(Quiz quiz) {
                                        if (quiz == null) {
                                            hideRootLayout();
                                            showEmptyQuiz();
                                        } else {
                                            showQuizOnUI(quiz);
                                        }
                                    }
                                })
                );
    }

    @Override
    public void showQuizOnUI(Quiz quiz) {
        this.mIQuizView.showQuizOnUI(quiz);
    }

    @Override
    public void showRefresh() {
        this.mIQuizView.showRefresh();
    }

    @Override
    public void hideRefresh() {
        this.mIQuizView.hideRefresh();
    }

    @Override
    public void showRootLayout() {
        this.mIQuizView.showRootLayout();
    }

    @Override
    public void hideRootLayout() {
        this.mIQuizView.hideRootLayout();
    }

    @Override
    public void showNetworkError() {
        this.mIQuizView.showNetworkError();
    }

    @Override
    public void hideNetworkError() {
        this.mIQuizView.hideNetworkError();
    }

    @Override
    public void showEmptyQuiz() {
        this.mIQuizView.showEmptyQuiz();
    }

    @Override
    public void hideEmptyQuiz() {
        this.mIQuizView.hideEmptyQuiz();
    }

    @Override
    public void showSnackbarMessage(String message, int duration) {
        this.mIQuizView.showSnackbarMessage(message, duration);
    }

    @Override
    public void showSnackbarMessage(int resId, int duration) {
        this.mIQuizView.showSnackbarMessage(resId, duration);
    }

    @Override
    public void onDestroy() {
        if (this.mCompositeSubscription != null && !this.mCompositeSubscription.isUnsubscribed()) this.mCompositeSubscription.unsubscribe();
    }

    @Override
    public Activity getFragmentActivity() {
        return this.mIQuizView.getFragmentActivity();
    }
}
