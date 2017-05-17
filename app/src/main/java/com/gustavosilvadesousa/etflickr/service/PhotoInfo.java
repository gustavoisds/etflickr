package com.gustavosilvadesousa.etflickr.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gustavosilvadesousa.etflickr.domain.PhotoSimple;

import java.io.Serializable;
import java.util.ArrayList;

public class PhotoInfo implements Serializable {

    private int page;
    private int pages;
    @JsonProperty("perpage")
    private int perPage;
    private int total;
    @JsonProperty("photo")
    private ArrayList<PhotoSimple> photos;

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


    public ArrayList<PhotoSimple> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<PhotoSimple> photos) {
        this.photos = photos;
    }
}
