package br.com.ciscience.scienceci.model.entity.impl;

import java.util.List;

/**
 * Created by pedrodimoura on 07/07/16.
 */
public class Student extends User {

    private List<Quiz> quiz;
    private Long score;

    public List<Quiz> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<Quiz> quiz) {
        this.quiz = quiz;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student [quiz=" + quiz + ", score=" + score + "]";
    }

}
