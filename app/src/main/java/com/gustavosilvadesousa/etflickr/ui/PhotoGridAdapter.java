package com.gustavosilvadesousa.etflickr.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.domain.Photo;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoGridAdapter extends PhotoAdapter {

    public PhotoGridAdapter(List<Photo> photos) {
        super(photos);
    }
    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item_grid, parent, false);
        PhotoViewHolder photoViewHolder = new PhotoViewHolder(v);
        return photoViewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        String urlImage = photos.get(position).getUrl();
        if ((urlImage != null) && (!urlImage.isEmpty())) {
            Picasso.with(holder.imageView.getContext())
                    .load(urlImage)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .fit()
                    .into(holder.imageView);
        }
    }
}
