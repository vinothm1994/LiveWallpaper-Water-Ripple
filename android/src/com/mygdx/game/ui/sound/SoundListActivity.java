package com.mygdx.game.ui.sound;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mygdx.game.R;
import com.mygdx.game.data.AppDataManager;
import com.mygdx.game.ui.home.HomeActivity;
import com.mygdx.game.ui.sound.dummy.SoundContent;
import com.mygdx.game.ui.sound.dummy.SoundContent.SoundItem;

import java.util.ArrayList;
import java.util.Collections;

import code.apps.ripple.logic.Wallpaper;


public class SoundListActivity extends AppCompatActivity implements OnSoundFragmentListener {

    private OnSoundFragmentListener mListener;
    private SoundRecyclerViewAdapter soundRecyclerViewAdapter;
    private boolean isOnline;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SoundListActivity() {
    }

    @SuppressWarnings("unused")
    public static Intent newInstance(Context context, boolean isOnline) {

        Intent intent = new Intent(context, SoundListActivity.class);
        intent.putExtra("isOnline", isOnline);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isOnline = getIntent().getBooleanExtra("isOnline", false);
        setContentView(R.layout.activity_sound_list);

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        soundRecyclerViewAdapter = new SoundRecyclerViewAdapter(Collections.emptyList(), mListener);
        recyclerView.setAdapter(soundRecyclerViewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));


        View moreBtn = findViewById(R.id.more_wallpaper_btn);
        if (isOnline) {
            moreBtn.setVisibility(View.GONE);
            AppDataManager appDataManager = new AppDataManager();
            appDataManager.fetchSounds().observe(this, soundItems -> {
                soundRecyclerViewAdapter.setSoundItems(soundItems);
                soundRecyclerViewAdapter.notifyDataSetChanged();
            });
        } else {

        }

        findViewById(R.id.progress_bar).setVisibility(View.GONE);
        findViewById(R.id.no_data_tv).setVisibility(View.GONE);

        initData();
        moreBtn.setOnClickListener((v) -> {
            startActivity(newInstance(this, true));
        });
    }

    private void initData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Wallpaper.SHARED_PREF_NAME, MODE_PRIVATE);
        int sound = sharedPreferences.getInt("sound_pos", 0);

        ArrayList<SoundItem> soundItems = new ArrayList<>();
        try {
            String[] list = getResources().getAssets().list("data/sounds");
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    String filename = list[i];
                    String filePath = "data/sounds/" + filename;
                    SoundItem soundItem = new SoundItem(i + 1, filename.split("\\.")[0], filePath);
                    soundItems.add(soundItem);

                    soundItems.add(soundItem);
                    soundItems.add(soundItem);
                    soundItems.add(soundItem);
                }
            }
            soundRecyclerViewAdapter.selectedPos = sound;
            soundRecyclerViewAdapter.setSoundItems(soundItems);
            soundRecyclerViewAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void select(int pos) {
        if (isOnline) {
            AppDataManager appDataManager = new AppDataManager();
            appDataManager.downloadFile(soundRecyclerViewAdapter.getSoundItems().get(pos).filepath);
        }
    }


    @Override
    public void playSound(SoundContent.SoundItem item) {

        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            if (!isOnline) {
                AssetFileDescriptor descriptor = getAssets().openFd(item.filepath);
                mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                descriptor.close();
            } else {
                String file = "";
                mediaPlayer.setDataSource(file);
            }

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
