package br.com.ciscience.scienceci.presenter.impl;

import android.util.Log;
import android.widget.Toast;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.dao.local.impl.UserLocalAPI;
import br.com.ciscience.scienceci.model.dao.remote.impl.QuizRemoteAPI;
import br.com.ciscience.scienceci.presenter.IQuizResultPresenter;
import br.com.ciscience.scienceci.util.Constants;
import br.com.ciscience.scienceci.view.activity.IQuizResultView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pedrodimoura on 27/07/16.
 */
public class QuizResultPresenter implements IQuizResultPresenter {

    private IQuizResultView mIQuizResultView;
    private QuizRemoteAPI mQuizRemoteAPI;
    private UserLocalAPI mUserLocalAPI;

    private CompositeSubscription mCompositeSubscription;

    public QuizResultPresenter(IQuizResultView iQuizResultView) {
        this.mIQuizResultView = iQuizResultView;
        this.mQuizRemoteAPI = QuizRemoteAPI.getInstance();
        this.mUserLocalAPI = new UserLocalAPI(this.mIQuizResultView.getActivityContext());
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void sendQuizResult(String quizResultJSON) {
        this.mIQuizResultView.hideButton();
        this.mIQuizResultView.showProgressBar();
        this.mCompositeSubscription
                .add(
                        this.mQuizRemoteAPI
                                .getAPI()
                                .sendQuizResults(quizResultJSON, this.mUserLocalAPI.getSession().getToken())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Void>() {
                                    @Override
                                    public void onCompleted() {
                                        mIQuizResultView.showToastMessage(R.string.success_quiz_result, Toast.LENGTH_LONG);
                                        mIQuizResultView.hideProgressBar();
                                        mIQuizResultView.showButton();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        mIQuizResultView.showToastMessage(e.getMessage(), Toast.LENGTH_LONG);
                                    }

                                    @Override
                                    public void onNext(Void aVoid) {
                                        Log.d(Constants.DEBUG_KEY, "onNext -> " + aVoid);
                                    }
                                })
                );
    }
}
