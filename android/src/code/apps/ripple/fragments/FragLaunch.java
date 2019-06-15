package code.apps.ripple.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mygdx.game.R;

import code.apps.ripple.activityfolder.SettingsActivity;
import code.apps.ripple.logic.Utils;
import code.apps.ripple.logic.Wallpaper;

public class FragLaunch extends Fragment implements OnClickListener {
    public static FragLaunch newInstance() {
        return new FragLaunch();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i = 0;
        View inflate = layoutInflater.inflate(R.layout.fragment_launcher, viewGroup, false);
        int[] iArr = new int[]{R.id.set_wallpaper, R.id.settings, R.id.rate, R.id.more};
        int length = iArr.length;
        while (i < length) {
            inflate.findViewById(iArr[i]).setOnClickListener(this);
            i++;
        }
        return inflate;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more /*2131296358*/:
                Utils.moreapps(getActivity(), getString(122));
                return;
            case R.id.rate /*2131296375*/:
                Utils.rateThisApp(getActivity());
                return;
            case R.id.set_wallpaper /*2131296399*/:
                setWallpaper(getActivity());
                return;
            case R.id.settings /*2131296400*/:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return;
            default:
                return;
        }
    }

    @SuppressLint({"InlinedApi"})
    public void setWallpaper(Activity activity) {
        Intent intent = new Intent();
        if (VERSION.SDK_INT >= 16) {
            intent.setAction("android.service.wallpaper.CHANGE_LIVE_WALLPAPER");
            intent.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", new ComponentName(getActivity().getPackageName(), Wallpaper.class.getCanonicalName()));
        } else {
            intent.setAction("android.service.wallpaper.LIVE_WALLPAPER_CHOOSER");
            String string = activity.getResources().getString(R.string.app_name);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Set ");
            stringBuilder.append(string);
            stringBuilder.append(" Wallpaper");
            Toast.makeText(activity, stringBuilder.toString(), 1).show();
        }
        activity.startActivity(intent);
    }
}
