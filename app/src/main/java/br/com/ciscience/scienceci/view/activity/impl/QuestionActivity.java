package br.com.ciscience.scienceci.view.activity.impl;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

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
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionActivity extends AppCompatActivity implements IActivity, IQuestionView {

    @BindView(R.id.questionActivityToolbar)
    Toolbar toolbar;

    @BindView(R.id.textViewTimerContent) TextView textViewTimerContent;
    @BindView(R.id.textViewPointsContent) TextView textViewPointsContent;
    @BindView(R.id.textViewQuestionText) TextView textViewQuestionText;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private CountDownTimer countDownTimer;

    private static final String TIMER_FORMAT = "%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(QuestionActivity.this);
        setSupportActionBar(toolbar);
        getQuestions();
        setActionBarDrawerToggle();
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
        showQuestionOnUI(questions.get(currentQuestionIndex));
    }

    @Override
    public void loadQuestionsOnMemory(List<Question> questions) {

        this.countDownTimer = new CountDownTimer((questions.get(currentQuestionIndex).getLevel().getTime() * 1000), 1000) { // adjust the milli seconds here

            public void onTick(long l) {
                textViewTimerContent.setText(String.format(Locale.ENGLISH, TIMER_FORMAT, TimeUnit.MILLISECONDS.toMinutes(l), TimeUnit.MILLISECONDS.toSeconds(l)));
            }

            public void onFinish() {
                textViewTimerContent.setText(String.format(Locale.ENGLISH, TIMER_FORMAT, TimeUnit.MILLISECONDS.toMinutes(0), TimeUnit.MILLISECONDS.toSeconds(0)));
                Log.d(Constants.DEBUG_KEY, "onFinish()");
            }
        }.start();

    }

    @Override
    public void answerQuestion() {

    }

    @Override
    public void setNewScore(Question question) {

    }

    @Override
    public void showCorrectAnswer(Question question) {

    }

    @Override
    public void callNextQuestion() {

    }

    @Override
    public void stopCountDownTimer() {

    }

    @Override
    public void showQuestionOnUI(Question question) {
        textViewPointsContent.setText(String.valueOf(question.getScore()));
        textViewQuestionText.setText(question.getText());
        showAlternativesOnUI(question.getAlternatives());
        loadQuestionsOnMemory(questions);
    }

    @Override
    public void showAlternativesOnUI(List<Alternative> alternatives) {

    }

    @Override
    public void startQuiz(Question question) {

    }
}
