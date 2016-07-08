package br.com.ciscience.scienceci.model.entity.impl;

import java.util.List;

/**
 * Created by pedrodimoura on 07/07/16.
 */
public class Question {

    private Long id;
    private String text;
    private int score;
    private List<Alternative> alternatives;
    private Contest contest;
    private Level level;
    private MyFile myFile;
    private boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Alternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Alternative> alternatives) {
        this.alternatives = alternatives;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public MyFile getMyFile() {
        return myFile;
    }

    public void setMyFile(MyFile myFile) {
        this.myFile = myFile;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", text=" + text + ", score=" + score + ", alternatives=" + alternatives
                + ", contest=" + contest + ", level=" + level + ", myFile=" + myFile + ", status=" + status + "]";
    }

}
