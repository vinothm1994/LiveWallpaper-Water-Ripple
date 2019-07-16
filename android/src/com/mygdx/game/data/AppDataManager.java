package com.mygdx.game.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mygdx.game.data.remote.ApiClient;
import com.mygdx.game.data.remote.ApiService;
import com.mygdx.game.ui.sound.dummy.SoundContent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import code.apps.ripple.activityfolder.WallpaperApplication;
import code.apps.ripple.activityfolder.choosebg.ImageModal;
import code.apps.ripple.logic.Wallpaper;
import code.apps.ripple.webcomponents.FileDownloader;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppDataManager {

    private static final String TAG = "AppDataManager";
    private ApiClient apiClient = ApiService.getApiClient();


    public LiveData<List<SoundContent.SoundItem>> fetchSounds() {
        MutableLiveData<List<SoundContent.SoundItem>> liveData = new MutableLiveData<>();
        apiClient.fetchSound("1").enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if (response.isSuccessful()) {
                    liveData.postValue(jsonToSounds(response.body()));
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                t.printStackTrace();

            }
        });


        return liveData;
    }


    public LiveData<List<ImageModal>> fetchImage() {
        MutableLiveData<List<ImageModal>> liveData = new MutableLiveData<>();
        apiClient.fetchWallpaper("1").enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(jsonToImages(response.body()));
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                t.printStackTrace();

            }
        });


        return liveData;
    }

    private List<SoundContent.SoundItem> jsonToSounds(JsonElement jsonElement) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        List<SoundContent.SoundItem> soundItems = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            SoundContent.SoundItem soundItem = new SoundContent.SoundItem(i, jsonObject.get("file_name").getAsString(), jsonObject.get("sound").getAsString());
            soundItems.add(soundItem);


        }
        return soundItems;

    }


    private List<ImageModal> jsonToImages(JsonElement jsonElement) {

        JsonArray jsonArray = jsonElement.getAsJsonArray();

        List<ImageModal> soundItems = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            ImageModal image = new ImageModal(jsonObject.get("image").getAsString());
            image.setOnline(true);
            soundItems.add(image);


        }
        return soundItems;

    }


    public void downloadFile(String url) {
        apiClient.downloadFile(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    Log.d(TAG, "server contacted and has file");

                    boolean writtenToDisk = writeResponseBodyToDisk(Uri.parse(url), response.body());

                    Log.d(TAG, "file download was a success? " + writtenToDisk);
                } else {
                    Log.d(TAG, "server contact failed");
                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    private boolean writeResponseBodyToDisk(Uri uri, ResponseBody body) {
        Log.i(TAG, "writeResponseBodyToDisk: ");
        try {

            String fileName = uri.getLastPathSegment();
            Context appCont = WallpaperApplication.getWallpaperApplication();
            File futureStudioIconFile = new File(appCont.getFilesDir(), fileName);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                SharedPreferences sharedPreferences = appCont.getSharedPreferences(Wallpaper.SHARED_PREF_NAME, 0);

                sharedPreferences.edit().putString(FileDownloader.SPKEY_SELECTED_IMAGE, futureStudioIconFile.getAbsolutePath()).apply();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
