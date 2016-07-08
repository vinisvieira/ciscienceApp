package br.com.ciscience.scienceci.model.entity.impl;

/**
 * Created by pedrodimoura on 07/07/16.
 */
public class Contest {

    private Long id;
    private String name;
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Contest [id=" + id + ", name=" + name + ", status=" + status + "]";
    }

}
