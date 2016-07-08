package br.com.ciscience.scienceci.model.entity.impl;

/**
 * Created by pedrodimoura on 07/07/16.
 */
public class Level {

    private Long id;
    private String name;
    private int time;
    private boolean status;

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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Level [id=" + id + ", name=" + name + ", time=" + time
                + ", status=" + status + "]";
    }

}
