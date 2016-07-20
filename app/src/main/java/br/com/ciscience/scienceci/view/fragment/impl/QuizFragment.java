package br.com.ciscience.scienceci.view.fragment.impl;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Quiz;
import br.com.ciscience.scienceci.presenter.IQuizPresenter;
import br.com.ciscience.scienceci.presenter.impl.QuizPresenter;
import br.com.ciscience.scienceci.util.Constants;
import br.com.ciscience.scienceci.view.adapter.QuizRecyclerViewAdapter;
import br.com.ciscience.scienceci.view.fragment.IFragment;
import br.com.ciscience.scienceci.view.fragment.IQuizView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class QuizFragment extends Fragment implements IFragment, IQuizView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayoutQuiz) SwipeRefreshLayout swipeRefreshLayoutQuiz;

    @BindView(R.id.recyclerViewQuiz) RecyclerView recyclerViewQuiz;
    @BindView(R.id.fragmentNetworkError) RelativeLayout relativeLayoutNetworkError;
    @BindView(R.id.fragmentEmptyQuiz) RelativeLayout relativeLayoutEmptyQuiz;

    @BindString(R.string.debug_key) String debugKey;

    private View mView;
    private IQuizPresenter mIQuizPresenter;
    private QuizRecyclerViewAdapter mQuizRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment_quiz, container, false);
        ButterKnife.bind(QuizFragment.this, this.mView);
        return this.mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mIQuizPresenter = new QuizPresenter(QuizFragment.this);
        this.setUpListeners();
        this.loadAvaiableQuiz();
    }

    @Override
    public void setUpListeners() {
        this.swipeRefreshLayoutQuiz.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent);
        this.swipeRefreshLayoutQuiz.setOnRefreshListener(this);
        this.recyclerViewQuiz.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        this.mQuizRecyclerViewAdapter = new QuizRecyclerViewAdapter();
        this.recyclerViewQuiz.setAdapter(this.mQuizRecyclerViewAdapter);
    }

    @Override
    public void loadAvaiableQuiz() {
        this.mIQuizPresenter.loadAvaiableQuiz();
    }

    @Override
    public void showQuizOnUI(Quiz quiz) {
        this.mQuizRecyclerViewAdapter.addQuiz(quiz);
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
    public void showRootLayout() {
        this.swipeRefreshLayoutQuiz.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRootLayout() {
        this.swipeRefreshLayoutQuiz.setVisibility(View.GONE);
    }

    @Override
    public void showNetworkError() {
        this.relativeLayoutNetworkError.setVisibility(View.VISIBLE);
        Log.d(Constants.DEBUG_KEY, "showNetworkError");
    }

    @Override
    public void hideNetworkError() {
        this.relativeLayoutNetworkError.setVisibility(View.GONE);
        Log.d(Constants.DEBUG_KEY, "hideNetworkError");
    }

    @Override
    public void showEmptyQuiz() {
        this.relativeLayoutEmptyQuiz.setVisibility(View.VISIBLE);
        Log.d(Constants.DEBUG_KEY, "showEmptyQuiz");
    }

    @Override
    public void hideEmptyQuiz() {
        this.relativeLayoutEmptyQuiz.setVisibility(View.GONE);
        Log.d(Constants.DEBUG_KEY, "hideEmptyQuiz");
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
        this.swipeRefreshLayoutQuiz.post(this::showRefresh);
    }
}
