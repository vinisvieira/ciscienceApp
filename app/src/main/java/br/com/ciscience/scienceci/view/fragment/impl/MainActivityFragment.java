package br.com.ciscience.scienceci.view.fragment.impl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Quiz;
import br.com.ciscience.scienceci.view.fragment.IFragment;
import br.com.ciscience.scienceci.view.fragment.IQuizView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements IFragment, IQuizView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayoutQuiz) SwipeRefreshLayout swipeRefreshLayoutQuiz;

    @BindView(R.id.recyclerViewQuiz) RecyclerView recyclerViewQuiz;

    @BindString(R.string.debug_key) String debugKey;

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(MainActivityFragment.this, this.mView);
        return this.mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpListeners();
    }

    @Override
    public void setUpListeners() {
        this.swipeRefreshLayoutQuiz.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent);
        this.swipeRefreshLayoutQuiz.setOnRefreshListener(this);
        recyclerViewQuiz.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void loadAvaiableQuiz() {

    }

    @Override
    public void showQuizOnUI(Quiz quiz) {

    }

    @Override
    public void showRefresh() {
        this.swipeRefreshLayoutQuiz.setRefreshing(true);
    }

    @Override
    public void hideRefresh() {
        this.swipeRefreshLayoutQuiz.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        this.swipeRefreshLayoutQuiz.post(this::showRefresh);
    }
}
