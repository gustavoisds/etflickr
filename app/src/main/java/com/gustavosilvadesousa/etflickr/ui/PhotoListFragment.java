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

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.domain.Photo;
import com.gustavosilvadesousa.etflickr.service.FlickrService;
import com.gustavosilvadesousa.etflickr.service.SearchPhotosResponse;
import com.gustavosilvadesousa.etflickr.utils.ConnectionUtils;

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PhotoRowAdapter(photos);
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
                swapLayoutManager();
                return true;
            }
            default:
                break;
        }

        return false;
    }

    private void updateView() {
        adapter.clear();
        adapter.addAll(photos);
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
        stopLoading();
    }

    private void swapLayoutManager() {
        gridView = !gridView;
        RecyclerView.LayoutManager manager = gridView ? new GridLayoutManager(getActivity(), 3) : new LinearLayoutManager(getActivity());
        RecyclerView.Adapter adapter = gridView ? new PhotoGridAdapter(photos) : new PhotoRowAdapter(photos);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.swapAdapter(adapter, false);
    }

    private void fetchPhotos() {

        if (ConnectionUtils.isConnectionAvailable(getActivity())) {
            FlickrService flickrService = FlickrService.getInstance();

            Call<SearchPhotosResponse> call = flickrService.getPublicPhotos("154797495@N05");

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
}
