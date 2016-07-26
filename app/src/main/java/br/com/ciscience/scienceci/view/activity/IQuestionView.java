package br.com.ciscience.scienceci.view.activity;

import java.util.List;

import br.com.ciscience.scienceci.model.entity.impl.Alternative;
import br.com.ciscience.scienceci.model.entity.impl.Question;

/**
 * Created by pedrodimoura on 25/07/16.
 */
public interface IQuestionView {

    void getQuestions();

    Question getCurrentQuestion();

    void loadQuestionsOnMemory(List<Question> questions);

    void answerQuestion();

    void completeQuiz();

    void showAnswerButton();

    void showNextQuestionButton();

    void showFinishQuizButton();

    void showCorrectAnswer();

    void callNextQuestion();

    void stopCountDownTimer();

    void showQuestionOnUI(Question question);

    void showAlternativesOnUI(List<Alternative> alternatives);

    void showToastMessage(String message, int duration);

    void showToastMessage(int resId, int duration);

}
