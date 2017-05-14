package com.gustavosilvadesousa.etflickr.service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SignatureInterceptor implements Interceptor {

    private static final String SECRET = "2d35e3ac24e410da";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        StringBuilder builder = new StringBuilder();
        builder.append(SECRET);

        int queryIndex=0;

        HashMap<String, String> queryParams = new HashMap<>();
        Set<String> queryParmsNames = request.url().queryParameterNames();
        for (String queryParamName: queryParmsNames) {
            queryParams.put(queryParamName, request.url().queryParameterValue(queryIndex));
            queryIndex++;
        }

        TreeSet<String> queryParamsNamesOrdered = new TreeSet<>(queryParmsNames);

        for (String queryParamName : queryParamsNamesOrdered) {
            builder.append(queryParamName);
            builder.append(queryParams.get(queryParamName));
        }

        HttpUrl url = request.url().newBuilder().addQueryParameter("api_sig",md5(builder.toString())).build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
