package com.gustavosilvadesousa.etflickr.ui;

import android.view.View;
import android.widget.TextView;

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.domain.Photo;

import java.util.List;

public class PhotoRowAdapter extends PhotoAdapter {

    public PhotoRowAdapter(List<Photo> photos) {
        super(photos);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        PhotoRowViewHolder photoRowViewHolder = (PhotoRowViewHolder)holder;
        photoRowViewHolder.userName.setText("gusisds");
        photoRowViewHolder.photoTitle.setText(photos.get(position).getTitle());
    }

    @Override
    protected PhotoViewHolder getHolder(View view) {
        return new PhotoRowViewHolder(view);
    }

    @Override
    protected int getLayout() {
        return R.layout.photo_item_row;
    }

    private class PhotoRowViewHolder extends PhotoViewHolder{

        protected TextView userName;
        protected TextView photoTitle;

        PhotoRowViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            photoTitle = (TextView) itemView.findViewById(R.id.photoTitle);
        }
    }

}
