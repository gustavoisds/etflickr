package com.gustavosilvadesousa.etflickr.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.service.FlickrService;
import com.gustavosilvadesousa.etflickr.service.SearchPhotos;
import com.gustavosilvadesousa.etflickr.service.SearchPhotosResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlickrService flickrService = FlickrService.getInstance();

        Call<SearchPhotosResponse> call = flickrService.getFlickrApi().getPhotos("154797495@N05");

        call.enqueue(new Callback<SearchPhotosResponse>() {
            @Override
            public void onResponse(Call<SearchPhotosResponse> call, Response<SearchPhotosResponse> response) {

                try {
                    SearchPhotosResponse a = response.body();
                    a.getPhotos();
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

}
