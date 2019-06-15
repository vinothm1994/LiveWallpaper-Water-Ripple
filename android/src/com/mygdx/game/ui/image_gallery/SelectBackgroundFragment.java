package com.mygdx.game.ui.image_gallery;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mygdx.game.R;
import com.mygdx.game.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import code.apps.ripple.activityfolder.choosebg.ImageModal;
import code.apps.ripple.logic.Assets;
import code.apps.ripple.logic.Wallpaper;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectBackgroundFragment extends Fragment implements OnImageSelection {


    private List<ImageModal> imageModals;
    private RecyclerView recyclerView;
    private WallpaperChooseAdapter wallpaperChooseAdapter;
    private SharedPreferences sharedPreferences;

    public SelectBackgroundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_backgoud, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            requireActivity().onBackPressed();

        });
        initData();
        setUidata();
    }

    private void initData() {

        this.sharedPreferences = requireContext().getSharedPreferences(Wallpaper.SHARED_PREF_NAME, 0);
        int background_selected = Integer.parseInt(sharedPreferences.getString("bg", "1"));
        imageModals = new ArrayList<>();
        try {
            String[] list = getResources().getAssets().list("data/images");
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    String stringBuilder = "file:///android_asset/data/images/" + list[i];
                    ImageModal imageModal = new ImageModal(stringBuilder);
                    if (background_selected == i) {
                        imageModal.isSelected = true;
                    }
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
        for (ImageModal imageModal : this.imageModals) {
            imageModal.isSelected = false;
        }
        ImageModal imageModal = this.imageModals.get(i);
        imageModal.isSelected = true;
        this.wallpaperChooseAdapter.notifyDataSetChanged();

        String stringBuilder = "" + i;
        this.sharedPreferences.edit().putString("bg", stringBuilder).commit();
        Assets.setTextureChanged(true);
        Assets.setOnlineImage(this.imageModals.get(i).isOnlineImage());

        ((HomeActivity) requireActivity()).addFragment(FullImageFragment.getInstance(imageModal));


    }
}
