package br.com.ciscience.scienceci.view.activity.impl;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Alternative;
import br.com.ciscience.scienceci.model.entity.impl.Question;
import br.com.ciscience.scienceci.util.Constants;
import br.com.ciscience.scienceci.view.activity.IActivity;
import br.com.ciscience.scienceci.view.activity.IQuestionView;
import br.com.ciscience.scienceci.view.adapter.AlternativeRecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionActivity extends AppCompatActivity implements IActivity, IQuestionView, View.OnClickListener {

    @BindView(R.id.questionActivityToolbar)
    Toolbar toolbar;

    @BindView(R.id.textViewTimerContent) TextView textViewTimerContent;
    @BindView(R.id.textViewPointsContent) TextView textViewPointsContent;
    @BindView(R.id.textViewQuestionText) TextView textViewQuestionText;
    @BindView(R.id.recyclerViewQuestionAlternatives) RecyclerView recyclerViewQuestionAlternatives;

    @BindView(R.id.buttonAnswer) Button buttonAnswer;
    @BindView(R.id.buttonNextQuestion) Button buttonNextQuestion;
    @BindView(R.id.buttonFinishQuiz) Button buttonFinishQuiz;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private CountDownTimer countDownTimer;
    private AlternativeRecyclerViewAdapter mAlternativeRecyclerViewAdapter;

    private static final String TIMER_FORMAT = "%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(QuestionActivity.this);
        setSupportActionBar(toolbar);
        setRecyclerViewDefault();
        getQuestions();
        setActionBarDrawerToggle();
    }

    public void setRecyclerViewDefault() {
        this.mAlternativeRecyclerViewAdapter = new AlternativeRecyclerViewAdapter(QuestionActivity.this, QuestionActivity.this);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            this.recyclerViewQuestionAlternatives.setLayoutManager(new LinearLayoutManager(QuestionActivity.this, LinearLayoutManager.VERTICAL, false));
        } else {
            this.recyclerViewQuestionAlternatives.setLayoutManager(new GridLayoutManager(QuestionActivity.this, 2));
        }
        this.recyclerViewQuestionAlternatives.setAdapter(this.mAlternativeRecyclerViewAdapter);
    }

    @Override
    public void showFragment(int idFragment) {

    }

    @Override
    public void setActionBarDrawerToggle() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setCheckedItemNavigationView(int currentFragment) {

    }

    @Override
    public void loadDefaultMenu() {

    }

    @Override
    public boolean isCurrentOnDisplay(int currentFragment) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getQuestions() {
        Intent intent = getIntent();
        String questionsJSON = intent.getStringExtra(Constants.INTENT_KEY_QUESTION);
        this.questions = new Gson().fromJson(questionsJSON, new TypeToken<List<Question>>(){}.getType());
        currentQuestionIndex = (this.questions.size() - 1);
        showQuestionOnUI(questions.get(currentQuestionIndex));
    }

    @Override
    public Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    @Override
    public void loadQuestionsOnMemory(List<Question> questions) {

        this.countDownTimer = new CountDownTimer((questions.get(currentQuestionIndex).getLevel().getTime() * 1000), 1000) { // adjust the milli seconds here

            public void onTick(long l) {
                textViewTimerContent.setText(String.format(Locale.ENGLISH, TIMER_FORMAT, TimeUnit.MILLISECONDS.toMinutes(l), TimeUnit.MILLISECONDS.toSeconds(l)));
            }

            public void onFinish() {
                textViewTimerContent.setText(String.format(Locale.ENGLISH, TIMER_FORMAT, TimeUnit.MILLISECONDS.toMinutes(0), TimeUnit.MILLISECONDS.toSeconds(0)));
                showCorrectAnswer();
                showNextQuestionButton();
                currentQuestionIndex--;
            }
        }.start();

    }

    @Override
    public void answerQuestion() {
        if (this.mAlternativeRecyclerViewAdapter.isAlternativeChecked()) {
            this.currentQuestionIndex--;
            this.countDownTimer.cancel();
            this.mAlternativeRecyclerViewAdapter.answerQuestion();
            this.showCorrectAnswer();
            this.showNextQuestionButton();
        } else {
            showToastMessage(R.string.error_no_alternative_selected, Toast.LENGTH_LONG);
        }
    }

    @Override
    public void completeQuiz() {

    }

    @Override
    public void showAnswerButton() {
        this.buttonAnswer.setVisibility(View.VISIBLE);
        this.buttonNextQuestion.setVisibility(View.GONE);
    }

    @Override
    public void showNextQuestionButton() {
        this.buttonAnswer.setVisibility(View.GONE);
        this.buttonNextQuestion.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFinishQuizButton() {
        this.buttonAnswer.setVisibility(View.GONE);
        this.buttonNextQuestion.setVisibility(View.GONE);
        this.buttonFinishQuiz.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCorrectAnswer() {
        this.mAlternativeRecyclerViewAdapter.showCorrectAlternative();
    }

    @Override
    public void callNextQuestion() {
        if (currentQuestionIndex != 0) {
            showQuestionOnUI(questions.get(currentQuestionIndex));
        } else {
            showFinishQuizButton();
        }
    }

    @Override
    public void stopCountDownTimer() {
        if (countDownTimer != null) this.countDownTimer.cancel();
    }

    @Override
    public void showQuestionOnUI(Question question) {
        showAnswerButton();
        textViewPointsContent.setText(String.valueOf(question.getScore()));
        textViewQuestionText.setText(question.getText());
        showAlternativesOnUI(question.getAlternatives());
        loadQuestionsOnMemory(questions);
    }

    @Override
    public void showAlternativesOnUI(List<Alternative> alternatives) {
        this.mAlternativeRecyclerViewAdapter.setAlternatives(alternatives);
    }

    @Override
    public void showToastMessage(String message, int duration) {
        Toast.makeText(QuestionActivity.this, message, duration).show();
    }

    @Override
    public void showToastMessage(int resId, int duration) {
        Toast.makeText(QuestionActivity.this, resId, duration).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopCountDownTimer();
    }

    @OnClick({R.id.buttonAnswer, R.id.buttonNextQuestion, R.id.buttonFinishQuiz})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAnswer:
                answerQuestion();
                break;
            case R.id.buttonNextQuestion:
                callNextQuestion();
                break;
            case R.id.buttonFinishQuiz:
                break;
        }
    }
}
