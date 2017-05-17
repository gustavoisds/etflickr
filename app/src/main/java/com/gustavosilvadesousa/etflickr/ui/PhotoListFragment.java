package com.gustavosilvadesousa.etflickr.ui;

import android.content.Intent;
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
import com.gustavosilvadesousa.etflickr.domain.PhotoSimple;
import com.gustavosilvadesousa.etflickr.service.FlickrService;
import com.gustavosilvadesousa.etflickr.service.GetPhotosResponse;
import com.gustavosilvadesousa.etflickr.service.PhotoInfo;
import com.gustavosilvadesousa.etflickr.utils.ConnectionUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoListFragment extends Fragment implements PhotoAdapter.OnPhotoClickedListener{

    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private PhotoAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
    private Snackbar snackbar;
    private boolean gridView = false;

    private List<PhotoSimple> photos = new ArrayList<>();
    private PhotoInfo photosInfo;

    private static final int PAGE_START = 1;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int currentPage = PAGE_START;
    private int PAGE_SIZE = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        fetchFirstPage();
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
        mRecyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new PhotoRowAdapter();
        adapter.setOnPhotoClickedListener(this);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        isLoading = true;
                        currentPage += 1;
                        fetchPhotos();
                    }
                }
            }
        });


        swipeContainer = (SwipeRefreshLayout)view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                fetchPhotos();
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
        adapter.addAll(photosInfo.getPhotos());
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
        if (currentPage < photosInfo.getPages()) {
            adapter.addLoadingFooter();
        }
        else {
            isLastPage = true;
        }
        stopLoading();
    }

    private void swapLayoutManager() {
        gridView = !gridView;
        RecyclerView.LayoutManager manager = gridView ? new GridLayoutManager(getActivity(), 3) : new LinearLayoutManager(getActivity());
        PhotoAdapter adapter = gridView ? new PhotoGridAdapter() : new PhotoRowAdapter();
        adapter.setOnPhotoClickedListener(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.swapAdapter(adapter, true);
    }

    private void fetchPhotos() {

        if (ConnectionUtils.isConnectionAvailable(getActivity())) {
            FlickrService flickrService = FlickrService.getInstance();

            Call<GetPhotosResponse> call = flickrService.getPublicPhotos("154797495@N05", PAGE_SIZE, currentPage);

            call.enqueue(new Callback<GetPhotosResponse>() {
                @Override
                public void onResponse(Call<GetPhotosResponse> call, Response<GetPhotosResponse> response) {

                    try {
                        adapter.removeLoadingFooter();
                        photosInfo = response.body().getPhotoInfo();
                        photos.addAll(photosInfo.getPhotos());
                        updateView();
                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GetPhotosResponse> call, Throwable t) {
                    Log.d("onFailure", t.getMessage());
                }
            });
        }
        else {
            showSnackbar();
        }
    }

    private void showSnackbar() {
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


    private void fetchFirstPage() {

        if (ConnectionUtils.isConnectionAvailable(getActivity())) {
            FlickrService flickrService = FlickrService.getInstance();

            Call<GetPhotosResponse> call = flickrService.getPublicPhotos("154797495@N05", PAGE_SIZE, currentPage);

            call.enqueue(new Callback<GetPhotosResponse>() {
                @Override
                public void onResponse(Call<GetPhotosResponse> call, Response<GetPhotosResponse> response) {

                    try {
                        photosInfo = response.body().getPhotoInfo();
                        photos.addAll(photosInfo.getPhotos());
                        updateView();
                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GetPhotosResponse> call, Throwable t) {
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
//                            fetchPhotos();
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

    @Override
    public void onPhotoClicked(int position) {
        Intent intent = new Intent(getActivity(), PhotoDetailActivity.class);
        intent.putExtra(PhotoDetailActivity.PHOTO_ID, photos.get(position).getId());
        startActivity(intent);
    }
}
