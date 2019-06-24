package com.mygdx.game.ui.bgSelector;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.mygdx.game.R;

import code.apps.ripple.logic.Wallpaper;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {


    private SharedPreferences sharedPreferences;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sharedPreferences = requireContext().getSharedPreferences(Wallpaper.SHARED_PREF_NAME, 0);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        SwitchCompat sw_ripple = view.findViewById(R.id.sw_ripple);
        SwitchCompat sw_particle = view.findViewById(R.id.sw_particle);
        SwitchCompat sw_sound = view.findViewById(R.id.sw_sound);
        setButtonView(sw_ripple);
        setButtonView(sw_particle);
        setButtonView(sw_sound);

        sw_ripple.setOnCheckedChangeListener(this);
        sw_particle.setOnCheckedChangeListener(this);
        sw_sound.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String key = "";
        int id = buttonView.getId();
        if (id == R.id.sw_ripple)
            key = "ripple";
        else if (id == R.id.sw_particle)
            key = "floating_particle";
        else if (id == R.id.sw_sound)
            key = "sound";
        sharedPreferences.edit().putBoolean(key, isChecked).apply();
        Wallpaper.loadPrefs(sharedPreferences);

    }


    public void setButtonView(CompoundButton buttonView) {
        String key = "";
        int id = buttonView.getId();
        if (id == R.id.sw_ripple)
            key = "ripple";
        else if (id == R.id.sw_particle)
            key = "floating_particle";
        else if (id == R.id.sw_sound)
            key = "sound";
        boolean en = sharedPreferences.getBoolean(key, true);
        buttonView.setChecked(en);
    }
}
