package code.apps.ripple.activityfolder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.ViewGroup;
import android.widget.Toast;


import com.mygdx.game.R;

import code.apps.ripple.activityfolder.choosebg.BackgroundChooseActivity;
import code.apps.ripple.activityfolder.chooseparticle.ParticleChooseActivity;
import code.apps.ripple.logic.Assets;
import code.apps.ripple.logic.Wallpaper;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener, OnPreferenceClickListener {
    public static final String device_id = "70682F79C44FC98C3B40BC5E621FCE35";
    private String[] r0;


    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getPreferenceManager().setSharedPreferencesName(Wallpaper.SHARED_PREF_NAME);
        addPreferencesFromResource(R.xml.pref_headers);
        setContentView(R.layout.activity_settings);
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        getString(R.string.Banner_Id);
        getString(R.string.Fullpage_Id);
        findPreference("bg").setOnPreferenceClickListener(this);
        findPreference("particle_type").setOnPreferenceClickListener(this);
        findPreference("reset").setOnPreferenceClickListener(this);
        Wallpaper.loadPrefs(getSharedPreferences(Wallpaper.SHARED_PREF_NAME, 0));
        a();
        b();
        c();
    }

    private void a() {
        r0 = new String[4];
        int i = 0;
        r0[0] = "particle_animation";
        r0[1] = "particle_speed";
        r0[2] = "particle_density";
        r0[3] = "particle_type";
        int length = r0.length;
        while (i < length) {
            findPreference(r0[i]).setEnabled(Wallpaper.particle_effect);
            i++;
        }
    }

    private void b() {
        r0 = new String[6];
        int i = 0;
        r0[0] = "sound";
        r0[1] = "radius";
        r0[2] = "displacement";
        r0[3] = "random_drop";
        r0[4] = "frequency";
        r0[5] = "water_tail";
        int length = r0.length;
        while (i < length) {
            findPreference(r0[i]).setEnabled(Wallpaper.ripple_effect);
            i++;
        }
    }

    private void c() {
        findPreference("frequency").setEnabled(Wallpaper.ripple_random);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        Wallpaper.loadPrefs(sharedPreferences);
        a();
        b();
        c();
    }

    private void d() {
        Toast.makeText(this, "Reset Complete...", 0).show();
        Editor edit = getSharedPreferences(Wallpaper.SHARED_PREF_NAME, 0).edit();
        edit.clear();
        edit.commit();
        recreate();
    }



    @Override
    public void onDestroy() {
        Assets.setTextureChanged(true);
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().contentEquals("bg")) {
            startActivity(new Intent(this, BackgroundChooseActivity.class));
        } else if (preference.getKey().contentEquals("particle_type")) {
            startActivity(new Intent(this, ParticleChooseActivity.class));
        } else if (preference.getKey().contentEquals("reset")) {
            d();
        }
        return false;
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
