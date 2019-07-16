package com.mygdx.game.data.remote;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiService {

    public static final String BASE_URL = "http://192.168.1.116:3030/";
    public static int REQUEST_TIMEOUT = 10;
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient;
    private static ApiClient apiClient;

    public static Retrofit getClient() {
        if (okHttpClient == null)
            initOkHttp();

        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    private static void initOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json");
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });


        okHttpClient = httpClient.build();
    }

    public static ApiClient getApiClient() {
        if (apiClient == null)
            apiClient = getClient().create(ApiClient.class);
        return apiClient;
    }
}