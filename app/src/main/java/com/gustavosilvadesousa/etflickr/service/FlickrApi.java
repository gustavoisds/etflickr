package com.gustavosilvadesousa.etflickr.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FlickrApi {

    @GET("/services/rest?method=flickr.auth.getFrob")
    Call<FrobApiResponse> getFrob();
}
