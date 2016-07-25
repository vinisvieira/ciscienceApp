package br.com.ciscience.scienceci.view.activity;

import java.util.List;

import br.com.ciscience.scienceci.model.entity.impl.Alternative;
import br.com.ciscience.scienceci.model.entity.impl.Question;

/**
 * Created by pedrodimoura on 25/07/16.
 */
public interface IQuestionView {

    void getQuestions();

    void loadQuestionsOnMemory(List<Question> questions);

    void answerQuestion();

    void setNewScore(Question question);

    void showCorrectAnswer(Question question);

    void callNextQuestion();

    void stopCountDownTimer();

    void showQuestionOnUI(Question question);

    void showAlternativesOnUI(List<Alternative> alternatives);

    void startQuiz(Question question);

}
