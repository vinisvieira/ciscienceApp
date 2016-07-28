package br.com.ciscience.scienceci.presenter.impl;

import android.support.design.widget.Snackbar;
import android.util.Log;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.dao.local.impl.UserLocalAPI;
import br.com.ciscience.scienceci.model.dao.remote.impl.RankingRemoteAPI;
import br.com.ciscience.scienceci.model.entity.impl.Student;
import br.com.ciscience.scienceci.presenter.IRankingPresenter;
import br.com.ciscience.scienceci.util.Constants;
import br.com.ciscience.scienceci.view.fragment.IRankingView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pedrodimoura on 28/07/16.
 */
public class RankingPresenter implements IRankingPresenter {

    private IRankingView mIRankingView;
    private RankingRemoteAPI mRankingRemoteAPI;
    private UserLocalAPI mUserLocalAPI;

    private CompositeSubscription mCompositeSubscription;

    public RankingPresenter(IRankingView iRankingView) {
        this.mIRankingView = iRankingView;
        this.mRankingRemoteAPI = RankingRemoteAPI.getInstance();
        this.mUserLocalAPI = new UserLocalAPI(iRankingView.getFragmentActivity());
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getRanking() {
        this.mIRankingView.showRefresh();
        this.mCompositeSubscription
                .add(
                        this.mRankingRemoteAPI
                                .getAPI()
                                .getRanking(this.mUserLocalAPI.getSession().getToken())
                                .subscribeOn(Schedulers.io())
                                .take(10)
                                .flatMap(student -> Observable.from(student))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Student>() {
                                    @Override
                                    public void onCompleted() {
                                        mIRankingView.hideRefresh();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        mIRankingView.hideRefresh();
                                        Log.e(Constants.DEBUG_KEY, e.getMessage(), e);
                                        mIRankingView.showSnackbarMessage(R.string.error_network_processing, Snackbar.LENGTH_LONG);
                                    }

                                    @Override
                                    public void onNext(Student student) {
                                        mIRankingView.showRankingOnUI(student);
                                    }
                                })
                );
    }
}
