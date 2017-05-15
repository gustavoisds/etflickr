package com.gustavosilvadesousa.etflickr.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.domain.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoListFragment extends Fragment {

    private static final String PHOTOS = "photos";
    private RecyclerView mRecyclerView;
    private List<Photo> photos;

    public static PhotoListFragment newInstance(ArrayList<Photo> photos) {
        PhotoListFragment fragment = new PhotoListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PHOTOS, photos);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photos = (List<Photo>)getArguments().getSerializable(PHOTOS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.photo_list_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        PhotoAdapter adapter = new PhotoAdapter(photos);
        mRecyclerView.setAdapter(adapter);
    }

    public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>{

        List<Photo> photos;

        public PhotoAdapter(List<Photo> photos) {
            this.photos = photos;
        }

        @Override
        public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
            PhotoViewHolder photoViewHolder = new PhotoViewHolder(v);
            return photoViewHolder;
        }

        @Override
        public void onBindViewHolder(PhotoViewHolder holder, int position) {
            String urlImage = photos.get(position).getUrl();
            if ((urlImage != null) && (!urlImage.isEmpty())) {
                Picasso.with(holder.imageView.getContext())
                        .load(urlImage)
                        .fit()
                        .into(holder.imageView);
            }
        }

        @Override
        public int getItemCount() {
            return photos.size();
        }

        public class PhotoViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            PhotoViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.photoImage);
            }
        }

    }
}
