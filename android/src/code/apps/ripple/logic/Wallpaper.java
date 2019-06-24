package code.apps.ripple.logic;

import android.content.SharedPreferences;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;

public class Wallpaper extends AndroidLiveWallpaperService {
    public static final String SHARED_PREF_NAME = "seet";
    public static String PREFKEY_PARTICLE_PREFIX = "pref_parti_";
    public static String PREFKEY_TOTAL_PARTICLE = "total_particle";
    public static int background_selected = 1;
    public static boolean floating_particle = true;
    public static int particle_animation = 1;
    public static long particle_density = 1000;
    public static boolean particle_effect = true;
    public static int particle_speed = 1;
    public static int ripple_displacement = 4;
    public static boolean ripple_effect = true;
    public static int ripple_radius = 2;
    public static boolean ripple_random = true;
    public static long ripple_random_freq = 2000;
    public static boolean ripple_sound = true;
    public static boolean ripple_water_tail = false;
    public  static  int sound = 0;
    SharedPreferences a;

    public static void loadPrefs(SharedPreferences sharedPreferences) {
        background_selected = Integer.parseInt(sharedPreferences.getString("bg", "1"));
        floating_particle = sharedPreferences.getBoolean("floating_particle", true);
        particle_effect = sharedPreferences.getBoolean("particle", false);
        particle_density = Long.parseLong(sharedPreferences.getString("particle_density", "1000"));
        particle_speed = Integer.parseInt(sharedPreferences.getString("particle_speed", "1"));
        particle_animation = Integer.parseInt(sharedPreferences.getString("particle_animation", "-1"));
        ripple_effect = sharedPreferences.getBoolean("ripple", true);
        ripple_sound = sharedPreferences.getBoolean("sound", true);
        ripple_random = sharedPreferences.getBoolean("random_drop", false);
        ripple_random_freq = Long.parseLong(sharedPreferences.getString("frequency", "2000"));
        ripple_water_tail = sharedPreferences.getBoolean("water_tail", true);
        ripple_radius = sharedPreferences.getInt("radius", 2);
        sound = sharedPreferences.getInt("sound_pos", 0);
        if (ripple_radius == 0) {
            ripple_radius = 1;
        }
        ripple_displacement = sharedPreferences.getInt("displacement", 4);
        if (ripple_displacement == 0) {
            ripple_displacement = 1;
        }

    }

    public void onCreateApplication() {
        super.onCreateApplication();
        AndroidApplicationConfiguration androidApplicationConfiguration = new AndroidApplicationConfiguration();
        androidApplicationConfiguration.useCompass = false;
        androidApplicationConfiguration.useWakelock = false;
        androidApplicationConfiguration.useAccelerometer = false;
        androidApplicationConfiguration.getTouchEventsForLiveWallpaper = true;
        App app = new App(this);
        this.a = getSharedPreferences(SHARED_PREF_NAME, 0);
        loadPrefs(this.a);
        initialize(app, androidApplicationConfiguration);
    }
}
