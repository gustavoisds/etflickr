package com.gustavosilvadesousa.etflickr.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.gustavosilvadesousa.etflickr.R;

public class PhotoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_list_activity);
        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            PhotoListFragment fragment = new PhotoListFragment();
            ft.replace(R.id.contentContainer, fragment);
            ft.commit();
        }
    }

}
