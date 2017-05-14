package com.gustavosilvadesousa.etflickr.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Perms implements Serializable {

    @JsonProperty("_content")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
