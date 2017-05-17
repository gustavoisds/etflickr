package com.gustavosilvadesousa.etflickr.ui;

import android.view.View;

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.domain.Photo;

import java.util.List;

public class PhotoGridAdapter extends PhotoAdapter {

    public PhotoGridAdapter(List<Photo> photos) {
        super(photos);
    }

    @Override
    protected PhotoViewHolder getHolder(View view) {
        return new PhotoViewHolder(view);
    }

    @Override
    protected int getLayout() {
        return R.layout.photo_item_grid;
    }


}
