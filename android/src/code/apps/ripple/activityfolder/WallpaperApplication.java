package code.apps.ripple.activityfolder;

import android.app.Application;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;


public class WallpaperApplication extends Application {
    public static final String admob_banner_test_id = "ca-app-pub-3940256099942544/6300978111";
    public static final String admob_interstitial_test_id = "ca-app-pub-3940256099942544/1033173712";
    public static final String[] device_test_id = new String[]{"A380BD7A716353D75579F55DF158E2F3"};

    public void onCreate() {
        StrictMode.setThreadPolicy(new Builder().permitAll().build());
        super.onCreate();
    }
}
