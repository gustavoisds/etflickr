package com.gustavosilvadesousa.etflickr.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Frob {
    @JsonProperty("_content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String frob) {
        this.content = frob;
    }
}
