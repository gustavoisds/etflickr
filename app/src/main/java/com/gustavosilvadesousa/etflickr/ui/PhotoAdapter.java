package com.gustavosilvadesousa.etflickr.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.domain.Photo;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

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

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false);
        PhotoViewHolder photoViewHolder = getHolder(v);
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

    public void clear() {
        photos.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Photo> photos) {
        this.photos.addAll(photos);
        notifyDataSetChanged();
    }

    protected abstract PhotoViewHolder getHolder(View view);

    protected abstract int getLayout();

    protected class PhotoViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;

        PhotoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.photoImage);
        }
    }
}
