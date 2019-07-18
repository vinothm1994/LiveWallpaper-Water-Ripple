package com.mygdx.game.ui.image_gallery;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
public class WallPaperListActivity extends AppCompatActivity implements OnImageSelection {


    private List<ImageModal> imageModals;
    private RecyclerView recyclerView;
    private WallpaperChooseAdapter wallpaperChooseAdapter;
    private SharedPreferences sharedPreferences;
    private boolean isOnline = true;


    public WallPaperListActivity() {
        // Required empty public constructor
    }

    public static Intent newInstance(Context context, boolean isOnline) {
        Intent intent = new Intent(context, WallPaperListActivity.class);
        intent.putExtra("isOnline", isOnline);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOnline = getIntent().getBooleanExtra("isOnline", false);
        setContentView(R.layout.activity_wallpaper_list);


        View moreBtn = findViewById(R.id.more_wallpaper_btn);
        if (isOnline) {
            moreBtn.setVisibility(View.GONE);
            AppDataManager appDataManager = new AppDataManager();
            appDataManager.fetchImage().observe(this, new Observer<List<ImageModal>>() {
                @Override
                public void onChanged(List<ImageModal> imageModals) {
                    WallPaperListActivity.this.imageModals = imageModals;
                    wallpaperChooseAdapter.setImageModals(imageModals);
                    wallpaperChooseAdapter.notifyDataSetChanged();
                }
            });
        } else {


        }
        findViewById(R.id.progress_bar).setVisibility(View.GONE);
        findViewById(R.id.no_data_tv).setVisibility(View.GONE);
        initData();
        setUidata();


        moreBtn.setOnClickListener((v) -> {
            startActivity(WallPaperListActivity.newInstance(this, true));
        });
    }

    private void initData() {

        this.sharedPreferences = getSharedPreferences(Wallpaper.SHARED_PREF_NAME, 0);
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
        this.recyclerView = findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.wallpaperChooseAdapter = new WallpaperChooseAdapter(this.imageModals, this);
        this.recyclerView.setAdapter(wallpaperChooseAdapter);
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
        startActivity(FullImageActivity.getInstance(this,imageModal));
        //todo

        if (isOnline) {
            AppDataManager appDataManager = new AppDataManager();
            appDataManager.downloadFile(imageModal.filepath);
        }
    }
}
