package com.gustavosilvadesousa.etflickr.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.domain.Photo;
import com.gustavosilvadesousa.etflickr.service.FlickrService;
import com.gustavosilvadesousa.etflickr.service.SearchPhotosResponse;
import com.gustavosilvadesousa.etflickr.utils.ConnectionUtils;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PhotoAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
    private Snackbar snackbar;
    private boolean gridView = false;

    private List<Photo> photos = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        fetchPhotos();
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
        mRecyclerView.setLayoutManager(getLayoutManager());
        PhotoAdapter adapter = new PhotoAdapter(photos);
        mRecyclerView.setAdapter(adapter);

        swipeContainer = (SwipeRefreshLayout)view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPhotos();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.photo_list_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_change_layout: {
                mRecyclerView.setLayoutManager(getLayoutManager());
                return true;
            }
            default:
                break;
        }

        return false;
    }

    private void updateView() {
        if (adapter == null) {
            adapter = new PhotoAdapter(photos);
            mRecyclerView.setAdapter(adapter);
        }
        else {
            adapter.clear();
            adapter.addAll(photos);
        }
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
        stopLoading();
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        RecyclerView.LayoutManager manager = gridView ? new LinearLayoutManager(getActivity()) : new GridLayoutManager(getActivity(), 3);
        gridView = !gridView;
        return manager;
    }

    private void fetchPhotos() {

        if (ConnectionUtils.isConnectionAvailable(getActivity())) {
            FlickrService flickrService = FlickrService.getInstance();

            Call<SearchPhotosResponse> call = flickrService.getFlickrApi().getPhotos("154797495@N05");

            call.enqueue(new Callback<SearchPhotosResponse>() {
                @Override
                public void onResponse(Call<SearchPhotosResponse> call, Response<SearchPhotosResponse> response) {

                    try {
                        SearchPhotosResponse searchPhotosResponse = response.body();
                        photos = searchPhotosResponse.getPhotoInfo().getPhotos();
                        updateView();
                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<SearchPhotosResponse> call, Throwable t) {
                    Log.d("onFailure", t.getMessage());
                }
            });
        }
        else {
            stopLoading();
            snackbar = Snackbar.make(swipeContainer, "No network connection", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showLoading();
                            fetchPhotos();
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ));
            snackbar.show();
        }
    }

    private void showLoading() {
        swipeContainer.setRefreshing(true);
    }

    private void stopLoading() {
        swipeContainer.setRefreshing(false);
    }

    public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>{

        List<Photo> photos = new ArrayList<>();

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
