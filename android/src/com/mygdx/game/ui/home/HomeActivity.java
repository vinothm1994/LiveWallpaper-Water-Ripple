package com.mygdx.game.ui.home;

import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.appodeal.ads.Appodeal;
import com.mygdx.game.R;
import com.mygdx.game.ui.HomeFragment;
import com.mygdx.game.ui.dashboard.DashboardFragment;
import com.mygdx.game.ui.sound.SoundFragment;
import com.mygdx.game.ui.sound.dummy.SoundContent;
import com.mygdx.game.utils.AppodealBannerCallbacks;

import code.apps.ripple.logic.Wallpaper;

public class HomeActivity extends AppCompatActivity  {
    public static final String APP_KEY = "fee50c333ff3825fd6ad6d38cff78154de3025546d47a84f";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
    }

    public void addFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(fragment.getTag()).commit();
    }


}
