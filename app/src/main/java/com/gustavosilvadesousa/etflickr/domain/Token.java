package com.gustavosilvadesousa.etflickr.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
    @JsonProperty("_content")
    private String getValue;

    public String getGetValue() {
        return getValue;
    }

    public void setGetValue(String frob) {
        this.getValue = frob;
    }
}
