package com.mygdx.game.ui.image_gallery;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mygdx.game.R;
import com.mygdx.game.data.AppDataManager;
import com.mygdx.game.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import code.apps.ripple.activityfolder.choosebg.ImageModal;
import code.apps.ripple.logic.Assets;
import code.apps.ripple.logic.Wallpaper;

/**
 * A simple {@link Fragment} subclass.
 */
public class WallPaperListFragment extends Fragment implements OnImageSelection {


    private List<ImageModal> imageModals;
    private RecyclerView recyclerView;
    private WallpaperChooseAdapter wallpaperChooseAdapter;
    private SharedPreferences sharedPreferences;
    private boolean isOnline = true;


    public WallPaperListFragment() {
        // Required empty public constructor
    }

    public static WallPaperListFragment newInstance(boolean isOnline) {
        WallPaperListFragment wallPaperListFragment = new WallPaperListFragment();
        Bundle args = new Bundle();
        args.putBoolean("isOnline", isOnline);
        wallPaperListFragment.setArguments(args);

        return wallPaperListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOnline = getArguments().getBoolean("isOnline");

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_backgoud, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View moreBtn = view.findViewById(R.id.more_wallpaper_btn);
        if (isOnline) {
            moreBtn.setVisibility(View.GONE);
            AppDataManager appDataManager = new AppDataManager();
            appDataManager.fetchImage().observe(this, new Observer<List<ImageModal>>() {
                @Override
                public void onChanged(List<ImageModal> imageModals) {
                    WallPaperListFragment.this.imageModals = imageModals;
                    wallpaperChooseAdapter.setImageModals(imageModals);
                    wallpaperChooseAdapter.notifyDataSetChanged();
                }
            });
        } else {


        }
        view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
        view.findViewById(R.id.no_data_tv).setVisibility(View.GONE);
        initData();
        setUidata();


        moreBtn.setOnClickListener((v) -> {
            HomeActivity homeActivity = (HomeActivity) requireActivity();
            homeActivity.addFragment(newInstance(true));
        });
    }

    private void initData() {

        this.sharedPreferences = requireContext().getSharedPreferences(Wallpaper.SHARED_PREF_NAME, 0);
        int background_selected = Integer.parseInt(sharedPreferences.getString("bg", "1"));
        imageModals = new ArrayList<>();
        try {
            String[] list = getResources().getAssets().list("data/images");
            if (list != null) {
                for (String s : list) {
                    String stringBuilder = "file:///android_asset/data/images/" + s;
                    ImageModal imageModal = new ImageModal(stringBuilder);
                    this.imageModals.add(imageModal);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUidata() {
        this.recyclerView = getView().findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(requireContext(), 2);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.wallpaperChooseAdapter = new WallpaperChooseAdapter(this.imageModals, requireContext());
        this.recyclerView.setAdapter(this.wallpaperChooseAdapter);
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this.recyclerView.getContext(), linearLayoutManager.getOrientation()));
        this.wallpaperChooseAdapter.setListener(this);
    }

    @Override
    public void selected(int i) {
        ImageModal imageModal = this.imageModals.get(i);
        this.wallpaperChooseAdapter.notifyDataSetChanged();
        String stringBuilder = "" + i;
        this.sharedPreferences.edit().putString("bg", stringBuilder).commit();
        Assets.setTextureChanged(true);
        Assets.setOnlineImage(this.imageModals.get(i).isOnlineImage());
        ((HomeActivity) requireActivity()).addFragment(FullImageFragment.getInstance(imageModal));
        if (isOnline) {
            AppDataManager appDataManager = new AppDataManager();
            appDataManager.downloadFile(imageModal.filepath);
        }
    }
}
