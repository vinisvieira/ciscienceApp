package br.com.ciscience.scienceci.model.entity.impl;

import java.util.Date;

/**
 * Created by pedrodimoura on 07/07/16.
 */
public class User {

    private Long id;
    private String name;
    private String cpf;
    private String email;
    private Date birthday;
    private String password;
    private Date userSince;
    private boolean status;
    private String profile;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUserSince() {
        return userSince;
    }

    public void setUserSince(Date userSince) {
        this.userSince = userSince;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", cpf=" + cpf
                + ", email=" + email + ", password=" + password + ", birthday="
                + birthday + ", userSince=" + userSince + ", status=" + status
                + ", profile=" + profile + "]";
    }

}
