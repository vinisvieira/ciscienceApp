package br.com.ciscience.scienceci.model.entity.impl;

/**
 * Created by pedrodimoura on 07/07/16.
 */
public class Alternative {

    private Long id;
    private String text;
    private boolean answer;
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

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Alternative [id=" + id + ", text=" + text + ", answer=" + answer + ", status=" + status + "]";
    }

}
