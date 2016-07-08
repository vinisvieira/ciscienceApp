package br.com.ciscience.scienceci.view.fragment;

import br.com.ciscience.scienceci.model.entity.impl.Quiz;

/**
 * Created by pedrodimoura on 07/07/16.
 */
public interface IQuizView {

    void loadAvaiableQuiz();

    void showQuizOnUI(Quiz quiz);

    void showRefresh();

    void hideRefresh();

}
