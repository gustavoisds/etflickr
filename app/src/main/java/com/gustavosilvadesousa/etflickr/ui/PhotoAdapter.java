package com.gustavosilvadesousa.etflickr.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.domain.PhotoSimple;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public abstract class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static final int ITEM = 0;
    protected static final int LOADING = 1;
    protected List<PhotoSimple> photos = new ArrayList<>();
    protected OnPhotoClickedListener onPhotoClickedListener;

    private boolean isLoadingAdded;

    public PhotoAdapter() {
        super();
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == photos.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                View v = LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false);
                viewHolder = getHolder(v);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (getItemViewType(holder.getAdapterPosition())) {
            case ITEM:
                final PhotoViewHolder photoHolder = (PhotoViewHolder) holder;
                photoHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onPhotoClickedListener != null) {
                            onPhotoClickedListener.onPhotoClicked(position);
                        }
                    }
                });

                String urlImage = photos.get(position).getUrl();
                if ((urlImage != null) && (!urlImage.isEmpty())) {
                    Picasso.with(photoHolder.imageView.getContext())
                            .load(urlImage)
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .fit()
                            .into(photoHolder.imageView);
                }
                break;

            case LOADING:
//                Do nothing
                break;
        }
    }

    public void clear() {
        photos.clear();
        notifyDataSetChanged();
    }

    public void add(PhotoSimple photo) {
        photos.add(photo);
        notifyItemInserted(photos.size() - 1);
    }

    public void addAll(List<PhotoSimple> photos) {
        for (PhotoSimple photoSimple : photos) {
            add(photoSimple);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new PhotoSimple());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = photos.size() - 1;
        PhotoSimple result = photos.get(position);

        if (result != null) {
            photos.remove(position);
            notifyItemRemoved(position);
        }
    }

    protected abstract PhotoViewHolder getHolder(View view);

    protected abstract int getLayout();

    public void setOnPhotoClickedListener(OnPhotoClickedListener onPhotoClickedListener) {
        this.onPhotoClickedListener = onPhotoClickedListener;
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    protected class PhotoViewHolder extends RecyclerView.ViewHolder{
        protected ImageView imageView;

        PhotoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.photoImage);

        }

    }

    public interface OnPhotoClickedListener {
        void onPhotoClicked(int position);
    }
}
