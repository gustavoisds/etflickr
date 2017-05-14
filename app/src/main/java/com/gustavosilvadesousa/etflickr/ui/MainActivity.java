package com.gustavosilvadesousa.etflickr.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.service.FlickrService;
import com.gustavosilvadesousa.etflickr.service.FrobApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlickrService flickrService = new FlickrService();

        Call<FrobApiResponse> call = flickrService.init().getFrob();

        call.enqueue(new Callback<FrobApiResponse>() {
            @Override
            public void onResponse(Call<FrobApiResponse> call, Response<FrobApiResponse> response) {

                try {

                    FrobApiResponse a = response.body();
                    String frob = a.getFrob().getContent();

                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<FrobApiResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }


        });

    }

}
