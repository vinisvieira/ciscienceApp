package br.com.ciscience.scienceci.presenter.impl;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.dao.local.IUserLocalAPI;
import br.com.ciscience.scienceci.model.dao.local.impl.UserLocalAPI;
import br.com.ciscience.scienceci.model.dao.remote.impl.UserRemoteAPI;
import br.com.ciscience.scienceci.model.entity.impl.Student;
import br.com.ciscience.scienceci.presenter.IUserPresenter;
import br.com.ciscience.scienceci.util.Constants;
import br.com.ciscience.scienceci.view.fragment.IUserView;
import okhttp3.MultipartBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pedrodimoura on 19/07/16.
 */
public class UserPresenter implements IUserPresenter {

    private IUserView mIUserView;
    private IUserLocalAPI mIUserLocalAPI;
    private UserRemoteAPI mUserRemoteAPI;

    private CompositeSubscription mCompositeSubscription;

    public UserPresenter(IUserView iUserView) {
        this.mIUserView = iUserView;
        this.mIUserLocalAPI = new UserLocalAPI(this.getFragmentActivity());
        this.mUserRemoteAPI = UserRemoteAPI.getInstance();
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void login(Student student) {
        this.mIUserView.showProgressBar();
        this.mCompositeSubscription
                .add(
                        this.mUserRemoteAPI
                                .getAPI()
                                .login(student.getEmail(), student.getPassword())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Student>() {
                                    @Override
                                    public void onCompleted() {
                                        finishActivity();
                                        startMainActivity();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        hideProgressBar();
                                        Log.e(Constants.DEBUG_KEY, e.getMessage(), e);
                                        showSnackbarMessage(getFragmentActivity().getString(R.string.error_invalid_login), Snackbar.LENGTH_LONG);
                                    }

                                    @Override
                                    public void onNext(Student student) {
                                        mIUserLocalAPI.setSession(student);
                                    }
                                })
                );
    }

    @Override
    public void changeAvatar(MultipartBody.Part file, String token) {
        this.mIUserView.showProgressBar();
        this.mCompositeSubscription
                .add(
                        this.mUserRemoteAPI
                                .getAPI()
                                .changeAvatar(file, token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Void>() {
                                    @Override
                                    public void onCompleted() {
                                        Log.d(Constants.DEBUG_KEY, "onCompleted");
                                        mIUserView.hideProgressBar();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d(Constants.DEBUG_KEY, "onError -> " + e.getMessage());
                                    }

                                    @Override
                                    public void onNext(Void aVoid) {
                                        Log.d(Constants.DEBUG_KEY, "onNext -> " + aVoid);
                                    }
                                })
                );
    }

    @Override
    public void getRemoteSession(String token) {
        this.mIUserView.showProgressBar();
        this.mCompositeSubscription
                .add(
                        this.mUserRemoteAPI
                                .getAPI()
                                .getByToken(token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        mIUserLocalAPI::setSession,
                                        throwable -> {
                                            hideProgressBar();
                                            Log.e(Constants.DEBUG_KEY, throwable.getMessage(), throwable);
                                            showSnackbarMessage(getFragmentActivity().getString(R.string.error_network_processing), Snackbar.LENGTH_LONG);
                                        },
                                        () -> {
                                            finishActivity();
                                            startMainActivity();
                                        }
                                )
                );
    }

    @Override
    public void recoveryPassword(String email, String recoveryKey) {
        this.mIUserView.showProgressBar();
        this.mCompositeSubscription
                .add(
                        this.mUserRemoteAPI
                                .getAPI()
                                .recoveryPassword(email, recoveryKey)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Void>() {
                                    @Override
                                    public void onCompleted() {
                                        hideProgressBar();
                                        mIUserView.showSnackbarMessage(R.string.success_recovery_password, Snackbar.LENGTH_LONG);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        hideProgressBar();
                                        mIUserView.showSnackbarMessage(R.string.error_recovery_password, Snackbar.LENGTH_LONG);
                                    }

                                    @Override
                                    public void onNext(Void aVoid) {

                                    }
                                })
                );
    }

    @Override
    public void destroySession() {
        this.mIUserLocalAPI.destroySession();
    }

    @Override
    public Student getSession() {
        return this.mIUserLocalAPI.getSession();
    }

    @Override
    public void showProgressBar() {
        this.mIUserView.showProgressBar();
    }

    @Override
    public void hideProgressBar() {
        this.mIUserView.hideProgressBar();
    }

    @Override
    public void showSnackbarMessage(String message, int duration) {
        this.mIUserView.showSnackbarMessage(message, duration);
    }

    @Override
    public void showSnackbarMessage(int resId, int duration) {
        this.mIUserView.showSnackbarMessage(resId, duration);
    }

    @Override
    public void startMainActivity() {
        this.mIUserView.startMainActivity();
    }

    @Override
    public void onDestroy() {
        this.mCompositeSubscription.unsubscribe();
    }

    @Override
    public void finishActivity() {
        this.mIUserView.finishActivity();
    }

    @Override
    public Activity getFragmentActivity() {
        return this.mIUserView.getFragmentActivity();
    }
}
