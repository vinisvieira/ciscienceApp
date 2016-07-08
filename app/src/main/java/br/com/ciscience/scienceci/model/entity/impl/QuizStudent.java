package br.com.ciscience.scienceci.model.entity.impl;

/**
 * Created by pedrodimoura on 07/07/16.
 */
public class QuizStudent {

    private Long id;
    private Student student;
    private Quiz quiz;
    private int totalScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "QuizStudent{" +
                "id=" + id +
                ", student=" + student +
                ", quiz=" + quiz +
                ", totalScore=" + totalScore +
                '}';
    }
}
