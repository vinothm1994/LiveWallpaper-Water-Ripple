package com.mygdx.game.utils;

import com.appodeal.ads.NativeCallbacks;


import android.app.Activity;

import com.appodeal.ads.NativeAd;

import static com.mygdx.game.utils.AppodealBannerCallbacks.showToast;

public class AppodealNativeCallbacks implements NativeCallbacks {

    private final Activity mActivity;

    public AppodealNativeCallbacks(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onNativeLoaded() {
        showToast(mActivity, "onNativeLoaded");
    }

    @Override
    public void onNativeFailedToLoad() {
        showToast(mActivity, "onNativeFailedToLoad");
    }

    @Override
    public void onNativeShown(NativeAd nativeAd) {
        showToast(mActivity, "onNativeShown");
    }

    @Override
    public void onNativeClicked(NativeAd nativeAd) {
        showToast(mActivity, "onNativeClicked");
    }

    @Override
    public void onNativeExpired() {
        showToast(mActivity, "onNativeExpired");
    }

}

