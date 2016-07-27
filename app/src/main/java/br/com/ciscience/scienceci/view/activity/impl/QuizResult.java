package br.com.ciscience.scienceci.view.activity.impl;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Quiz;
import br.com.ciscience.scienceci.model.entity.impl.QuizStudent;
import br.com.ciscience.scienceci.presenter.IQuizResultPresenter;
import br.com.ciscience.scienceci.presenter.impl.QuizResultPresenter;
import br.com.ciscience.scienceci.util.Constants;
import br.com.ciscience.scienceci.view.activity.IQuizResultView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizResult extends AppCompatActivity implements IQuizResultView {

    @BindView(R.id.textViewPointsEarned) TextView textViewPointsEarned;

    private Quiz mQuiz;
    private int mTotalPoints;

    private IQuizResultPresenter mIQuizResultPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        ButterKnife.bind(QuizResult.this);
        this.mIQuizResultPresenter = new QuizResultPresenter(QuizResult.this);
        getIntentData();
        showPointsOnUI();
        sendQuizResults();
    }

    private void getIntentData() {
        this.mQuiz = new Gson().fromJson(getIntent().getStringExtra(Constants.INTENT_KEY_QUIZ), Quiz.class);
        this.mTotalPoints = getIntent().getIntExtra(Constants.INTENT_TOTAL_POINTS, 0);
    }

    private void showPointsOnUI() {
        this.textViewPointsEarned.setText(String.valueOf(this.mTotalPoints));
    }

    @Override
    public void sendQuizResults() {
        QuizStudent quizStudent = new QuizStudent();
        quizStudent.setQuiz(this.mQuiz);
        quizStudent.setStudent(null);
        quizStudent.setTotalScore(this.mTotalPoints);

        this.mIQuizResultPresenter.sendQuizResult(new Gson().toJson(quizStudent));
    }

    @Override
    public void showToastMessage(String message, int duration) {
        Toast.makeText(getActivityContext(), message, duration).show();
    }

    @Override
    public void showToastMessage(int resId, int duration) {
        Toast.makeText(getActivityContext(), resId, duration).show();
    }

    @Override
    public Activity getActivityContext() {
        return QuizResult.this;
    }
}