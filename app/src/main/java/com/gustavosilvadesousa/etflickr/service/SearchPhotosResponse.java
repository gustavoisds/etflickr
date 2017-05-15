package com.gustavosilvadesousa.etflickr.service;

public class SearchPhotosResponse {
    private SearchPhotos photos;
    private String stat;

    public SearchPhotos getPhotos() {
        return photos;
    }

    public void setPhotos(SearchPhotos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
