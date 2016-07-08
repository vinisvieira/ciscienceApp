package br.com.ciscience.scienceci.presenter;

import br.com.ciscience.scienceci.model.entity.impl.Quiz;

/**
 * Created by pedrodimoura on 15/06/16.
 */
public interface IQuizPresenter {

    void loadAvaiableQuiz();

    void showQuizOnUI(Quiz quiz);

    void showRefresh();

    void hideRefresh();

}
