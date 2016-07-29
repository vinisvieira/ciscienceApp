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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Alternative;
import br.com.ciscience.scienceci.model.entity.impl.Question;
import br.com.ciscience.scienceci.model.entity.impl.Quiz;
import br.com.ciscience.scienceci.util.Constants;
import br.com.ciscience.scienceci.util.SystemServices;
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
    @BindView(R.id.imageViewQuestion) ImageView imageViewQuestion;
    @BindView(R.id.textViewPointsContent) TextView textViewPointsContent;
    @BindView(R.id.textViewQuestionText) TextView textViewQuestionText;
    @BindView(R.id.recyclerViewQuestionAlternatives) RecyclerView recyclerViewQuestionAlternatives;

    @BindView(R.id.buttonAnswer) Button buttonAnswer;
    @BindView(R.id.buttonNextQuestion) Button buttonNextQuestion;
    @BindView(R.id.buttonFinishQuiz) Button buttonFinishQuiz;

    private Quiz mQuiz;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private CountDownTimer countDownTimer;
    private AlternativeRecyclerViewAdapter mAlternativeRecyclerViewAdapter;

    private int mOnPauseCount = 0;

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.mOnPauseCount > 0) completeQuiz();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mOnPauseCount++;
    }

    @Override
    public void onBackPressed() {
        showToastMessage(R.string.warning_exit, Toast.LENGTH_SHORT);
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
    public void getQuestions() {
        Intent intent = getIntent();
        String questionsJSON = intent.getStringExtra(Constants.INTENT_KEY_QUESTION);
        String quizJSON = intent.getStringExtra(Constants.INTENT_KEY_QUIZ);
        this.questions = new Gson().fromJson(questionsJSON, new TypeToken<List<Question>>(){}.getType());
        this.mQuiz = new Gson().fromJson(quizJSON, Quiz.class);
        SystemServices.changeToolbarTitle(QuestionActivity.this, this.mQuiz.getName());
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
        Intent intent = new Intent(QuestionActivity.this, QuizResult.class);
        intent.putExtra(Constants.INTENT_KEY_QUIZ, new Gson().toJson(this.mQuiz));
        intent.putExtra(Constants.INTENT_TOTAL_POINTS, this.mAlternativeRecyclerViewAdapter.getCumulativePoints());

        setResult(AppCompatActivity.RESULT_OK);
        startActivity(intent);
        finish();
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

        if (question.getMyFile().getName() != null) {
            Picasso
                    .with(QuestionActivity.this)
                    .load(Constants.BASE_URL + "datafile/" + question.getMyFile().getName())
                    .into(imageViewQuestion);
        }

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
                completeQuiz();
                break;
        }
    }
}
