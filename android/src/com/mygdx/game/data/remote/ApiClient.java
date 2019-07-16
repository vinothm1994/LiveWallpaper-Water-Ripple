package com.mygdx.game.data.remote;

import com.google.gson.JsonElement;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiClient {


    @GET("api/app/{appId}/wallpaper")
    Call<JsonElement> fetchWallpaper(@Path("appId") String appId);

    @GET("api/app/{appId}/sound")
    Call<JsonElement> fetchSound(@Path("appId") String appId);

    @GET("app_version")
    Call<JsonElement> appVersion();

    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);


}
