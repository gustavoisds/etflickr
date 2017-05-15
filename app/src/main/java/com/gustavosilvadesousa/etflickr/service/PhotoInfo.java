package com.gustavosilvadesousa.etflickr.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gustavosilvadesousa.etflickr.domain.Photo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhotoInfo implements Serializable {

    private int page;
    private int pages;
    @JsonProperty("perpage")
    private int perPage;
    private int total;
    @JsonProperty("photo")
    private ArrayList<Photo> photos;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
}
