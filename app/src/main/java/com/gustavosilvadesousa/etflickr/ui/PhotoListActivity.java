package com.gustavosilvadesousa.etflickr.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.domain.Photo;
import com.gustavosilvadesousa.etflickr.service.FlickrService;
import com.gustavosilvadesousa.etflickr.service.SearchPhotosResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_list_activity);

        FlickrService flickrService = FlickrService.getInstance();

        Call<SearchPhotosResponse> call = flickrService.getFlickrApi().getPhotos("154797495@N05");

        call.enqueue(new Callback<SearchPhotosResponse>() {
            @Override
            public void onResponse(Call<SearchPhotosResponse> call, Response<SearchPhotosResponse> response) {

                try {
                    SearchPhotosResponse a = response.body();
                    onRequestFinish(a.getPhotos().getPhoto());
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<SearchPhotosResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
        });

    }

    private void onRequestFinish(ArrayList<Photo> photos) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        PhotoListFragment fragment = PhotoListFragment.newInstance(photos);
        ft.replace(R.id.contentContainer, fragment);
        ft.commit();
    }


}
