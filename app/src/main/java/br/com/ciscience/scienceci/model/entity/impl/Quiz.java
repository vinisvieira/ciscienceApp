package br.com.ciscience.scienceci.model.entity.impl;

import java.util.Date;
import java.util.List;

/**
 * Created by pedrodimoura on 07/07/16.
 */
public class Quiz {

    private Long id;
    private String name;
    private List<Question> questions;
    private Contest contest;
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", questions=" + questions +
                ", contest=" + contest +
                ", date=" + date +
                '}';
    }
}
