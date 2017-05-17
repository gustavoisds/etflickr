package com.gustavosilvadesousa.etflickr.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {

    @GET("/services/rest?method=flickr.auth.getFullToken")
    Call<TokenResponse> getToken();

    @GET("/services/rest?method=flickr.photos.search")
    Call<GetPhotosResponse> getPhotos(@Query("user_id")String user,
                                      @Query("per_page")int perPage,
                                      @Query("page") int page,
                                      @Query("privacy_filter") int privacyFilter);

    @GET("/services/rest?method=flickr.photos.getInfo")
    Call<GetPhotoDetailResponse> getPhotoDetail(@Query("user_id")String user,
                                         @Query("photo_id") String photoId);

}
