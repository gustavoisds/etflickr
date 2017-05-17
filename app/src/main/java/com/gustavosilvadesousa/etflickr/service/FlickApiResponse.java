package com.gustavosilvadesousa.etflickr.service;

import java.io.Serializable;

public class FlickApiResponse implements Serializable {
    private String stat;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
