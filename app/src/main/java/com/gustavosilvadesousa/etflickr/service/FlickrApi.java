package com.gustavosilvadesousa.etflickr.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {

    @GET("/services/rest?method=flickr.auth.getFullToken")
    Call<TokenResponse> getToken();

    @GET("/services/rest?method=flickr.photos.search")
    Call<SearchPhotosResponse> getPhotos(@Query("user_id")String user);

}
