package com.gustavosilvadesousa.etflickr.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.service.FlickrService;
import com.gustavosilvadesousa.etflickr.service.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlickrService flickrService = new FlickrService();

        Call<TokenResponse> call = flickrService.init().getToken();

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                try {

                    TokenResponse a = response.body();
                    a.getUser();

                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }


        });

    }

}
