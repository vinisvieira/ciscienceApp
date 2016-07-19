package br.com.ciscience.scienceci.view.fragment.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Student;
import br.com.ciscience.scienceci.presenter.IUserPresenter;
import br.com.ciscience.scienceci.presenter.impl.UserPresenter;
import br.com.ciscience.scienceci.view.activity.impl.MainActivity;
import br.com.ciscience.scienceci.view.fragment.IUserView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment implements IUserView, View.OnClickListener {

    @BindString(R.string.debug_key) String debugKey;

    @BindView(R.id.relativeLayoutLogin) RelativeLayout relativeLayoutLogin;
    @BindView(R.id.relativeLayoutLoginSplash) RelativeLayout relativeLayoutLoginSplash;

    @BindView(R.id.textInputEditTextEmail) TextInputEditText textInputEditTextEmail;
    @BindView(R.id.textInputEditTextPassword) TextInputEditText textInputEditTextPassword;

    private View mView;
    private IUserPresenter mIUserPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(LoginActivityFragment.this, this.mView);
        this.mIUserPresenter = new UserPresenter(LoginActivityFragment.this);
        return this.mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.userLoggedIn();
    }

    @OnClick({R.id.buttonLogin})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                this.login();
                break;
        }
    }

    @Override
    public void userLoggedIn() {
        if (this.getSession() != null) {
            this.finishActivity();
            this.startMainActivity();
        } else {
            this.hideProgressBar();
        }
    }

    @Override
    public Student getSession() {
        return this.mIUserPresenter.getSession();
    }

    @Override
    public void login() {
        Student student = new Student();
        student.setEmail(this.textInputEditTextEmail.getText().toString());
        student.setPassword(this.textInputEditTextPassword.getText().toString());

        if (student.validateFields()) {
            this.mIUserPresenter.login(student);
        } else {
            this.showSnackbarMessage(R.string.error_empty_fields, Snackbar.LENGTH_LONG);
        }

    }

    @Override
    public void showProgressBar() {
        this.relativeLayoutLogin.setVisibility(View.GONE);
        this.relativeLayoutLoginSplash.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        this.relativeLayoutLogin.setVisibility(View.VISIBLE);
        this.relativeLayoutLoginSplash.setVisibility(View.GONE);
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(getFragmentActivity(), MainActivity.class));
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
