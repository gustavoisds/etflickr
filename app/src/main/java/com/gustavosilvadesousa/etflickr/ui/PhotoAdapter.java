package com.gustavosilvadesousa.etflickr.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.domain.Photo;

import java.util.ArrayList;
import java.util.List;

public abstract class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    protected List<Photo> photos = new ArrayList<>();

    public PhotoAdapter(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    protected class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        PhotoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.photoImage);
        }
    }

    public void clear() {
        photos.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Photo> photos) {
        this.photos.addAll(photos);
        notifyDataSetChanged();
    }
}
