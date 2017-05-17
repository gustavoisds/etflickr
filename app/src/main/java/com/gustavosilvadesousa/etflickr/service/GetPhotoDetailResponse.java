package com.gustavosilvadesousa.etflickr.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gustavosilvadesousa.etflickr.domain.PhotoFull;

import java.io.Serializable;

public class GetPhotoDetailResponse extends FlickApiResponse {
    @JsonProperty("photo")
    private PhotoFull photoFull;


    public PhotoFull getPhotoFull() {
        return photoFull;
    }

    public void setPhotoFull(PhotoFull photoFull) {
        this.photoFull = photoFull;
    }
}
