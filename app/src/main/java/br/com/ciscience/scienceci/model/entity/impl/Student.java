package br.com.ciscience.scienceci.model.entity.impl;

import java.util.List;

import br.com.ciscience.scienceci.model.entity.IEntity;

/**
 * Created by pedrodimoura on 07/07/16.
 */
public class Student extends User implements IEntity {

    private List<Quiz> quiz;
    private Long score;
    private String token;

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

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "Student{" +
                "quiz=" + quiz +
                ", score=" + score +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public boolean validateFields() {
        if (getEmail() == null || getEmail().trim().equals("")) {
            return false;
        } else if (getPassword() == null || getPassword().trim().equals("")) {
            return false;
        }
        return true;
    }
}
