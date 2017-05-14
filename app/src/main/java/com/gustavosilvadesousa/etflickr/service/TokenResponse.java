package com.gustavosilvadesousa.etflickr.service;

import com.gustavosilvadesousa.etflickr.domain.Perms;
import com.gustavosilvadesousa.etflickr.domain.Token;
import com.gustavosilvadesousa.etflickr.domain.User;

import java.io.Serializable;


public class TokenResponse implements Serializable {

    private Token token;
    private Perms perms;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Perms getPerms() {
        return perms;
    }

    public void setPerms(Perms perms) {
        this.perms = perms;
    }
}
