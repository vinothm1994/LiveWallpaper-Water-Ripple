package com.mygdx.game.utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.appodeal.ads.BannerCallbacks;

public class AppodealBannerCallbacks implements BannerCallbacks {

    private final Activity mActivity;

    public AppodealBannerCallbacks(Activity activity) {
        mActivity = activity;
    }

    public static void showToast(Activity activity, String text) {
        Log.d("Appodeal", text);
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBannerLoaded(int height, boolean isPrecache) {
        showToast(mActivity, String.format("onBannerLoaded, %sdp, isPrecache: %s", height, isPrecache));
    }

    @Override
    public void onBannerFailedToLoad() {
        showToast(mActivity, "onBannerFailedToLoad");
    }

    @Override
    public void onBannerShown() {
        showToast(mActivity, "onBannerShown");
    }

    @Override
    public void onBannerClicked() {
        showToast(mActivity, "onBannerClicked");
    }

    @Override
    public void onBannerExpired() {
        showToast(mActivity, "onBannerExpired");
    }

}
