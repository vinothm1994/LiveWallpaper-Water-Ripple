package com.mygdx.game.ui.sound;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import static android.content.Context.MODE_PRIVATE;



public class SoundFragment extends Fragment implements OnSoundFragmentListener {

    private OnSoundFragmentListener mListener;
    private SoundRecyclerViewAdapter soundRecyclerViewAdapter;
    private boolean isOnline;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SoundFragment() {
    }

    @SuppressWarnings("unused")
    public static SoundFragment newInstance(boolean isOnline) {
        SoundFragment fragment = new SoundFragment();
        Bundle args = new Bundle();
        args.putBoolean("isOnline", isOnline);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            isOnline = getArguments().getBoolean("isOnline");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sound, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        soundRecyclerViewAdapter = new SoundRecyclerViewAdapter(Collections.emptyList(), mListener);
        recyclerView.setAdapter(soundRecyclerViewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), RecyclerView.VERTICAL));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSoundFragmentListener) {
            mListener = (OnSoundFragmentListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View moreBtn = view.findViewById(R.id.more_wallpaper_btn);
        if (isOnline) {
            moreBtn.setVisibility(View.GONE);
            AppDataManager appDataManager = new AppDataManager();
            appDataManager.fetchSounds().observe(this, soundItems -> {
                soundRecyclerViewAdapter.setSoundItems(soundItems);
                soundRecyclerViewAdapter.notifyDataSetChanged();
            });
        } else {

        }

        view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
        view.findViewById(R.id.no_data_tv).setVisibility(View.GONE);

        initData();
        moreBtn.setOnClickListener((v) -> {
            HomeActivity homeActivity = (HomeActivity) requireActivity();
            homeActivity.addFragment(newInstance(true));
        });
    }

    private void initData() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(Wallpaper.SHARED_PREF_NAME, MODE_PRIVATE);
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
            if (!isOnline){
                AssetFileDescriptor descriptor = requireContext().getAssets().openFd(item.filepath);
                mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                descriptor.close();
            }
            else {
                String file="";
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
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(Wallpaper.SHARED_PREF_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putInt("sound_pos", pos).apply();
        Wallpaper.loadPrefs(sharedPreferences);

    }


}
