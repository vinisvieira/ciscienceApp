package br.com.ciscience.scienceci.view.fragment.impl;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Student;
import br.com.ciscience.scienceci.presenter.IUserPresenter;
import br.com.ciscience.scienceci.presenter.impl.UserPresenter;
import br.com.ciscience.scienceci.view.fragment.IFragment;
import br.com.ciscience.scienceci.view.fragment.IUserView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pedrodimoura on 01/09/16.
 */

public class ProfileFragment extends Fragment implements IFragment, IUserView {

    @BindView(R.id.progressBarMyProfile) ProgressBar progressBarMyProfile;

    private IUserPresenter mIUserPresenter;

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(ProfileFragment.this, this.mView);
        this.mIUserPresenter = new UserPresenter(ProfileFragment.this);
        return this.mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setUpListeners() {

    }

    @Override
    public void userLoggedIn() {

    }

    @Override
    public Student getSession() {
        return this.mIUserPresenter.getSession();
    }

    @Override
    public void login() {

    }

    @Override
    public void recoveryPassword() {

    }

    @Override
    public void showProgressBar() {
        this.progressBarMyProfile.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        this.progressBarMyProfile.setVisibility(View.GONE);
    }

    @Override
    public void startMainActivity() {

    }

    @Override
    public void finishActivity() {
        this.getFragmentActivity().finish();
    }

    @Override
    public void showSnackbarMessage(String message, int duration) {
        Snackbar.make(this.mView, message, duration).show();
    }

    @Override
    public void showSnackbarMessage(int resId, int duration) {
        Snackbar.make(this.mView, resId, duration).show();
    }

    @Override
    public Activity getFragmentActivity() {
        return getActivity();
    }
}
