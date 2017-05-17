package com.gustavosilvadesousa.etflickr.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhotoFull extends AbstractPhoto {

    private Content description;
    private Content title;
    @JsonProperty("dateuploaded")
    private String dateUploaded;

    public Content getDescription() {
        return description;
    }

    public void setDescription(Content description) {
        this.description = description;
    }

    public Content getTitle() {
        return title;
    }

    public void setTitle(Content title) {
        this.title = title;
    }

    public String getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        this.dateUploaded = dateUploaded;
    }
}
