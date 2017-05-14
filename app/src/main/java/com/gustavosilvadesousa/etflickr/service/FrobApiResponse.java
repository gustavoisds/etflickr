package com.gustavosilvadesousa.etflickr.service;

import java.io.Serializable;


public class FrobApiResponse implements Serializable {

    private String stat;
    private Frob frob;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public Frob getFrob() {
        return frob;
    }

    public void setFrob(Frob frob) {
        this.frob = frob;
    }
}
