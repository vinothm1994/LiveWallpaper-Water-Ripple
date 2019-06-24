package com.mygdx.game.ui.home;

import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.mygdx.game.R;
import com.mygdx.game.ui.HomeFragment;
import com.mygdx.game.ui.sound.SoundFragment;
import com.mygdx.game.ui.sound.dummy.SoundContent;

import code.apps.ripple.logic.Wallpaper;

public class HomeActivity extends AppCompatActivity implements SoundFragment.OnSoundFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    public void addFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).addToBackStack(fragment.getTag()).commit();
    }

    @Override
    public void playSound(SoundContent.SoundItem item) {

        try {
            AssetFileDescriptor descriptor = getAssets().openFd(item.filepath);
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void selectSound(int pos, SoundContent.SoundItem item) {
        SharedPreferences sharedPreferences = getSharedPreferences(Wallpaper.SHARED_PREF_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putInt("sound_pos", pos).apply();
        Wallpaper.loadPrefs(sharedPreferences);

    }
}
