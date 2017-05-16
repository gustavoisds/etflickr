package com.gustavosilvadesousa.etflickr.service;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class FlickrService {

    private static final String API_KEY = "api_key";
    private static final String FORMAT = "format";
    private static final String NOJSONCALLBACK = "nojsoncallback";
    private static final String MINI_TOKEN = "mini_token";
    private static final String AUTH_TOKEN = "auth_token";

    private static FlickrService INSTANCE = new FlickrService();

    private Retrofit retrofit;
    private FlickrApi flickrApi;

    private FlickrService(){
        init();
    }

    public static FlickrService getInstance() {
        return INSTANCE;
    }

    public void init() {

        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).connectTimeout(5, TimeUnit.SECONDS);


        addInterceptors(httpClient);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(httpClient.build())
                .build();

        flickrApi =  retrofit.create(FlickrApi.class);
    }

    private void addInterceptors(OkHttpClient.Builder httpClient) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter(API_KEY, "1b4c450c1feb07a5bc17981308124c06")
                        .addQueryParameter(FORMAT, "json")
                        .addQueryParameter(AUTH_TOKEN,"72157680757377963-b457215f031ddeb9" )
                        .addQueryParameter(NOJSONCALLBACK,"1")
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        SignatureInterceptor signatureInterceptor = new SignatureInterceptor();
        httpClient.addInterceptor(signatureInterceptor);
    }

    public FlickrApi getFlickrApi() {
        return flickrApi;
    }
}
