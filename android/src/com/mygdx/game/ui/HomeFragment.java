package com.mygdx.game.ui;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.mygdx.game.R;
import com.mygdx.game.ui.bgSelector.SettingFragment;
import com.mygdx.game.ui.home.HomeActivity;

import code.apps.ripple.logic.Wallpaper;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar=view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        view.findViewById(R.id.ll_livewallapper).setOnClickListener((v) -> {
            setWallpaper(requireActivity());
        });

        view.findViewById(R.id.ll_livewallapper_setting).setOnClickListener((v) -> {
            ((HomeActivity) requireActivity()).addFragment(new SettingFragment());
        });

    }

    @SuppressLint({"InlinedApi"})
    public void setWallpaper(@NonNull Activity activity) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 16) {
            intent.setAction("android.service.wallpaper.CHANGE_LIVE_WALLPAPER");
            intent.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", new ComponentName(getActivity().getPackageName(), Wallpaper.class.getCanonicalName()));
        } else {
            intent.setAction("android.service.wallpaper.LIVE_WALLPAPER_CHOOSER");
            String string = activity.getResources().getString(R.string.app_name);
            String stringBuilder = "Set " +
                    string +
                    " Wallpaper";
            Toast.makeText(activity, stringBuilder, Toast.LENGTH_LONG).show();
        }
        activity.startActivity(intent);
    }
}
