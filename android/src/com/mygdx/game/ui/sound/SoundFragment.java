package com.mygdx.game.ui.sound;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mygdx.game.R;
import com.mygdx.game.ui.sound.dummy.SoundContent;
import com.mygdx.game.ui.sound.dummy.SoundContent.SoundItem;

import java.util.ArrayList;

import code.apps.ripple.logic.Wallpaper;

import static android.content.Context.MODE_PRIVATE;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnSoundFragmentListener}
 * interface.
 */
public class SoundFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnSoundFragmentListener mListener;
    private SoundRecyclerViewAdapter soundRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SoundFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SoundFragment newInstance(int columnCount) {
        SoundFragment fragment = new SoundFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sound, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            soundRecyclerViewAdapter = new SoundRecyclerViewAdapter(SoundContent.ITEMS, mListener);
            recyclerView.setAdapter(soundRecyclerViewAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), RecyclerView.VERTICAL));
        }
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
        initData();
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
                    SoundItem soundItem = new SoundItem(i+1, filename.split("\\.")[0], filePath);
                    soundItems.add(soundItem);
                }
            }
            soundRecyclerViewAdapter.selectedPos=sound;
            soundRecyclerViewAdapter.setSoundItems(soundItems);
            soundRecyclerViewAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnSoundFragmentListener {
        void playSound(SoundItem item);
        void selectSound(int pos,SoundItem item);
    }
}
