package com.mygdx.game.ui.libgdx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

import code.apps.ripple.logic.App;
import code.apps.ripple.logic.Wallpaper;

public class GameFragment extends AndroidFragmentApplication implements AndroidFragmentApplication.Callbacks {

    private SharedPreferences a;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        AndroidApplicationConfiguration androidApplicationConfiguration = new AndroidApplicationConfiguration();
        androidApplicationConfiguration.useCompass = false;
        androidApplicationConfiguration.useWakelock = false;
        androidApplicationConfiguration.useAccelerometer = false;
        androidApplicationConfiguration.getTouchEventsForLiveWallpaper = true;
        App app = new App(getContext());
        this.a = getContext().getSharedPreferences(Wallpaper.SHARED_PREF_NAME, 0);
        Wallpaper.loadPrefs(this.a);
        return initializeForView(app, androidApplicationConfiguration);
    }


    @Override
    public void exit() {

    }

    @Override
    public void startActivity(Intent intent) {

    }
}