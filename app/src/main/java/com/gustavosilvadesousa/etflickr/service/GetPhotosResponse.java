package com.gustavosilvadesousa.etflickr.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetPhotosResponse extends FlickApiResponse{
    @JsonProperty("photos")
    private PhotoInfo photoInfo;

    public PhotoInfo getPhotoInfo() {
        return photoInfo;
    }

    public void setPhotoInfo(PhotoInfo photoInfo) {
        this.photoInfo = photoInfo;
    }
}
