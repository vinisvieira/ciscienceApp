package br.com.ciscience.scienceci.view.fragment.impl;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Student;
import br.com.ciscience.scienceci.presenter.IRankingPresenter;
import br.com.ciscience.scienceci.presenter.impl.RankingPresenter;
import br.com.ciscience.scienceci.view.adapter.RankingRecyclerViewAdapter;
import br.com.ciscience.scienceci.view.fragment.IFragment;
import br.com.ciscience.scienceci.view.fragment.IRankingView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pedrodimoura on 28/07/16.
 */
public class RankingFragment extends Fragment implements IFragment, IRankingView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayoutRanking) SwipeRefreshLayout swipeRefreshLayoutRanking;
    @BindView(R.id.recyclerViewRanking) RecyclerView recyclerViewRanking;

    private View mView;
    private RankingRecyclerViewAdapter mRankingRecyclerViewAdapter;
    private IRankingPresenter mIRankingPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment_ranking, container, false);
        ButterKnife.bind(RankingFragment.this, this.mView);
        this.mIRankingPresenter = new RankingPresenter(RankingFragment.this);
        return this.mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpListeners();
        loadRanking();
    }

    @Override
    public void setUpListeners() {
        this.swipeRefreshLayoutRanking.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent);
        this.swipeRefreshLayoutRanking.setOnRefreshListener(this);
        this.recyclerViewRanking.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        this.mRankingRecyclerViewAdapter = new RankingRecyclerViewAdapter(getFragmentActivity());
        this.recyclerViewRanking.setAdapter(this.mRankingRecyclerViewAdapter);
    }

    @Override
    public void loadRanking() {
        this.mIRankingPresenter.getRanking();
    }

    @Override
    public void showRankingOnUI(Student student) {
        this.mRankingRecyclerViewAdapter.addRanking(student);
    }

    @Override
    public void showRefresh() {
        this.swipeRefreshLayoutRanking.post(() -> this.swipeRefreshLayoutRanking.setRefreshing(true));
    }

    @Override
    public void hideRefresh() {
        this.swipeRefreshLayoutRanking.setRefreshing(false);
    }

    @Override
    public void showRootLayout() {
        this.swipeRefreshLayoutRanking.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRootLayout() {
        this.swipeRefreshLayoutRanking.setVisibility(View.GONE);
    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void hideNetworkError() {

    }

    @Override
    public void showEmptyRanking() {

    }

    @Override
    public void hideEmptyRanking() {

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

    @Override
    public void onRefresh() {

    }
}
