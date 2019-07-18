package com.mygdx.game.ui.dashboard;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.appodeal.ads.Appodeal;
import com.mygdx.game.R;
import com.mygdx.game.ui.bgSelector.SettingActivity;
import com.mygdx.game.ui.home.HomeActivity;
import com.mygdx.game.ui.image_gallery.WallPaperListActivity;
import com.mygdx.game.ui.sound.SoundListActivity;
import com.mygdx.game.utils.AppodealBannerCallbacks;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {


    public DashboardActivity() {
        // Required empty public constructor
    }


    private void init() {
        Appodeal.disableWriteExternalStoragePermissionCheck();
        Appodeal.disableLocationPermissionCheck();
        //Appodeal.setTesting(true);
        Appodeal.setBannerViewId(R.id.appodealBannerView);
        Appodeal.initialize(this, HomeActivity.APP_KEY, Appodeal.BANNER, false);
        Appodeal.setBannerCallbacks(new AppodealBannerCallbacks(this));
        Appodeal.show(this, Appodeal.BANNER);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        findViewById(R.id.ll_wallapper).setOnClickListener(this);
        findViewById(R.id.ll_sounds).setOnClickListener(this);
        findViewById(R.id.ll_setting).setOnClickListener(this);
        findViewById(R.id.ll_share).setOnClickListener(this);
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_wallapper:
                startActivity(WallPaperListActivity.newInstance(this, false));

                break;
            case R.id.ll_sounds:
                startActivity(SoundListActivity.newInstance(this, false));
                break;
            case R.id.ll_setting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_share:
                Toast.makeText(this, "todo", Toast.LENGTH_SHORT).show();
                break;

        }

    }
}
