package com.mygdx.game.ui.bgSelector;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.mygdx.game.R;
import com.mygdx.game.ui.home.HomeActivity;

import code.apps.ripple.logic.Wallpaper;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {


    private SharedPreferences sharedPreferences;

    public SettingActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sharedPreferences = getSharedPreferences(Wallpaper.SHARED_PREF_NAME, 0);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        SwitchCompat sw_ripple = findViewById(R.id.sw_ripple);
        SwitchCompat sw_particle = findViewById(R.id.sw_particle);
        SwitchCompat sw_sound = findViewById(R.id.sw_sound);
        setButtonView(sw_ripple);
        setButtonView(sw_particle);
        setButtonView(sw_sound);

        sw_ripple.setOnCheckedChangeListener(this);
        sw_particle.setOnCheckedChangeListener(this);
        sw_sound.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();


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
