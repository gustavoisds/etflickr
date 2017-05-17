package com.gustavosilvadesousa.etflickr.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.gustavosilvadesousa.etflickr.R;

public class PhotoDetailActivity extends AppCompatActivity {

    public static String PHOTO_ID = "photoId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_detail_activity);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            PhotoDetailFragment fragment = new PhotoDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(PhotoDetailActivity.PHOTO_ID, getIntent().getStringExtra(PHOTO_ID));
            fragment.setArguments(bundle);
            ft.replace(R.id.contentContainer, fragment);
            ft.commit();
        }
    }
}
