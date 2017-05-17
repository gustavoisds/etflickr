package com.gustavosilvadesousa.etflickr.ui;

import android.view.View;

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.domain.PhotoSimple;

import java.util.List;

public class PhotoGridAdapter extends PhotoAdapter {

    public PhotoGridAdapter(List<PhotoSimple> photos) {
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
