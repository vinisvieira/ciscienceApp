package br.com.ciscience.scienceci.view.fragment.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.view.activity.impl.MainActivity;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment implements View.OnClickListener {

    @BindString(R.string.debug_key) String debugKey;

    @BindView(R.id.relativeLayoutLogin) RelativeLayout relativeLayoutLogin;
    @BindView(R.id.relativeLayoutLoginSplash) RelativeLayout relativeLayoutLoginSplash;

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(LoginActivityFragment.this, this.mView);
        return this.mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.buttonLogin})
    @Override
    public void onClick(View view) {
        getActivity().finish();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
}
