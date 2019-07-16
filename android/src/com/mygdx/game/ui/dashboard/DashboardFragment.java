package com.mygdx.game.ui.dashboard;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.appodeal.ads.Appodeal;
import com.mygdx.game.R;
import com.mygdx.game.ui.bgSelector.SettingFragment;
import com.mygdx.game.ui.home.HomeActivity;
import com.mygdx.game.ui.image_gallery.WallPaperListFragment;
import com.mygdx.game.ui.sound.SoundFragment;
import com.mygdx.game.utils.AppodealBannerCallbacks;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    private void init() {
        Appodeal.disableWriteExternalStoragePermissionCheck();
        Appodeal.disableLocationPermissionCheck();
        Appodeal.setTesting(true);
        Appodeal.setBannerViewId(R.id.appodealBannerView);
        Appodeal.initialize(requireActivity(), HomeActivity.APP_KEY, Appodeal.BANNER, false);
        Appodeal.setBannerCallbacks(new AppodealBannerCallbacks(requireActivity()));
        Appodeal.show(requireActivity(),Appodeal.BANNER);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        view.findViewById(R.id.ll_wallapper).setOnClickListener(this);
        view.findViewById(R.id.ll_sounds).setOnClickListener(this);
        view.findViewById(R.id.ll_setting).setOnClickListener(this);
        view.findViewById(R.id.ll_share).setOnClickListener(this);
        init();

    }

    @Override
    public void onClick(View view) {
        HomeActivity homeActivity = (HomeActivity) requireActivity();
        switch (view.getId()) {
            case R.id.ll_wallapper:
                homeActivity.addFragment(WallPaperListFragment.newInstance(false));

                break;
            case R.id.ll_sounds:
                homeActivity.addFragment(new SoundFragment());
                break;
            case R.id.ll_setting:
                homeActivity.addFragment(new SettingFragment());
                break;
            case R.id.ll_share:
                Toast.makeText(homeActivity, "todo", Toast.LENGTH_SHORT).show();
                break;

        }

    }
}
