package code.apps.ripple.activityfolder;

import android.app.Application;


public class WallpaperApplication extends Application {

    static WallpaperApplication wallpaperApplication;

    public static WallpaperApplication getWallpaperApplication() {
        return wallpaperApplication;
    }

    public void onCreate() {
        super.onCreate();
        wallpaperApplication = this;
    }
}
