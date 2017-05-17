package com.gustavosilvadesousa.etflickr.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gustavosilvadesousa.etflickr.R;
import com.gustavosilvadesousa.etflickr.domain.PhotoFull;
import com.gustavosilvadesousa.etflickr.service.FlickrService;
import com.gustavosilvadesousa.etflickr.service.GetPhotoDetailResponse;
import com.gustavosilvadesousa.etflickr.utils.ConnectionUtils;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDetailFragment extends Fragment {

    private PhotoFull photo;
    private String photoId;

    private ImageView photoImage;
    private TextView userName;
    private TextView photoDescription;
    private TextView photoTitle;
    private TextView photoDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        photoId = getArguments().getString(PhotoDetailActivity.PHOTO_ID);
        fetchPhotoDetail();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.photo_detail_fragment, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photoImage = (ImageView) view.findViewById(R.id.photoImage);
        userName= (TextView) view.findViewById(R.id.userName);
        photoDescription= (TextView) view.findViewById(R.id.photoDescription);
        photoTitle= (TextView) view.findViewById(R.id.photoTitle);
        photoDate= (TextView) view.findViewById(R.id.photoDate);
    }

    private void fetchPhotoDetail() {

        if (ConnectionUtils.isConnectionAvailable(getActivity())) {
            FlickrService flickrService = FlickrService.getInstance();

            Call<GetPhotoDetailResponse> call = flickrService.getPhotoDetail("154797495@N05", photoId);

            call.enqueue(new Callback<GetPhotoDetailResponse>() {
                @Override
                public void onResponse(Call<GetPhotoDetailResponse> call, Response<GetPhotoDetailResponse> response) {
                    try {
                        photo = response.body().getPhotoFull();
                        updateView();
                    } catch (Exception e) {
                        Log.d("onResponse", "There is an error");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GetPhotoDetailResponse> call, Throwable t) {
                    Log.d("onFailure", t.getMessage());
                }
            });
        }

    }

    private void updateView() {
        if (photo != null) {
            String urlImage = photo.getUrl();
            if ((urlImage != null) && (!urlImage.isEmpty())) {
                Picasso.with(getActivity())
                        .load(urlImage)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .fit()
                        .into(photoImage);
            }
            userName.setText("dasdas");
            photoDescription.setText(photo.getDescription().getContent());
            photoTitle.setText(photo.getTitle().getContent());
            photoDate.setText(photo.getDateUploaded());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date(Long.parseLong(photo.getDateUploaded()));
            photoDate.setText(df.format(date));
        }

    }

}
