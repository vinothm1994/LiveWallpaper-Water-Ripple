package com.mygdx.game.ui.image_gallery;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.appodeal.ads.Appodeal;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.mygdx.game.R;
import com.mygdx.game.ui.bgSelector.SettingActivity;
import com.mygdx.game.ui.home.HomeActivity;
import com.mygdx.game.ui.libgdx.LiveWallpaperFragment;
import com.mygdx.game.utils.AppodealBannerCallbacks;

import code.apps.ripple.activityfolder.choosebg.ImageModal;
import code.apps.ripple.logic.Wallpaper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FullImageActivity.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FullImageActivity extends AppCompatActivity implements AndroidFragmentApplication.Callbacks {

    private static final String TAG = FullImageActivity.class.getName();
    private ImageModal imageModal;

    public FullImageActivity() {
        // Required empty public constructor
    }

    public static Intent getInstance(Context context, ImageModal imageModal) {
        Intent fullImageFragment = new Intent(context, FullImageActivity.class);
        fullImageFragment.putExtra("data", imageModal);
        return fullImageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        imageModal = getIntent().getParcelableExtra("data");

        FrameLayout child_fra_container = findViewById(R.id.child_fra_container);

//        Picasso.get().load(imageModal.filepath).placeholder(R.drawable.ic_launcher).into(banner_iv);
        getSupportFragmentManager().beginTransaction().add(R.id.child_fra_container, new LiveWallpaperFragment(), "ss").commit();
        findViewById(R.id.set_wallpaper_btn).setOnClickListener((v -> {
            setWallpaper(this);
        }));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener((v) -> {
            onBackPressed();
        });
        MenuItem settingmenu = toolbar.getMenu().add(0, 1, 1, "Setting");
        settingmenu.setIcon(R.drawable.ic_settings);
        settingmenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == 1) {
                Intent intent=new Intent(getBaseContext(), SettingActivity.class);
                startActivity(intent);

                return true;
            }
            return false;
        });

        init();


    }


    @Override
    protected void onResume() {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);

    }

    private void init() {
        Appodeal.setBannerViewId(R.id.appodealBannerView);
        Appodeal.initialize(this, HomeActivity.APP_KEY, Appodeal.BANNER, false);
        Appodeal.setBannerCallbacks(new AppodealBannerCallbacks(this));
        Appodeal.show(this, Appodeal.BANNER);
    }


    public void setWallpaper(@NonNull Context context) {

        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 16) {
            intent.setAction("android.service.wallpaper.CHANGE_LIVE_WALLPAPER");
            intent.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", new ComponentName(context, Wallpaper.class));
        } else {
            intent.setAction("android.service.wallpaper.LIVE_WALLPAPER_CHOOSER");
            String string = context.getResources().getString(R.string.app_name);
            String stringBuilder = "Set " +
                    string +
                    " Wallpaper";
            Toast.makeText(context, stringBuilder, Toast.LENGTH_LONG).show();
        }
        context.startActivity(intent);
    }

    @Override
    public void exit() {
        Log.i(TAG, "exit: ");

    }

}
