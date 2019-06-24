package com.mygdx.game.ui.image_gallery;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.mygdx.game.R;
import com.mygdx.game.ui.bgSelector.SettingFragment;
import com.mygdx.game.ui.home.HomeActivity;
import com.mygdx.game.ui.libgdx.GameFragment;

import code.apps.ripple.activityfolder.choosebg.ImageModal;
import code.apps.ripple.logic.Wallpaper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FullImageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FullImageFragment extends Fragment implements AndroidFragmentApplication.Callbacks {

    private static final String TAG = FullImageFragment.class.getName();
    private ImageModal imageModal;
    private OnFragmentInteractionListener mListener;

    public FullImageFragment() {
        // Required empty public constructor
    }

    public static FullImageFragment getInstance(ImageModal imageModal) {
        FullImageFragment fullImageFragment = new FullImageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", imageModal);
        fullImageFragment.setArguments(bundle);
        return fullImageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageModal = getArguments().getParcelable("data");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_image, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout child_fra_container = view.findViewById(R.id.child_fra_container);

//        Picasso.get().load(imageModal.filepath).placeholder(R.drawable.ic_launcher).into(banner_iv);
        getChildFragmentManager().beginTransaction().add(R.id.child_fra_container, new GameFragment(), "ss").commit();
        view.findViewById(R.id.set_wallpaper_btn).setOnClickListener((v -> {
            setWallpaper(requireContext());
        }));

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener((v) -> {
            requireActivity().onBackPressed();
        });
        MenuItem settingmenu = toolbar.getMenu().add(0, 1, 1, "Setting");
        settingmenu.setIcon(R.drawable.ic_settings);
        settingmenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == 1) {
                    ((HomeActivity) requireActivity()).addFragment(new SettingFragment());
                    return true;
                }
                return false;
            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setWallpaper(@NonNull Context context) {

        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 16) {
            intent.setAction("android.service.wallpaper.CHANGE_LIVE_WALLPAPER");
            intent.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", new ComponentName(context, Wallpaper.class));
        } else {
            intent.setAction("android.service.wallpaper.LIVE_WALLPAPER_CHOOSER");
            String string = context.getResources().getString(R.string.app_name);
            String stringBuilder = "Set " +
                    string +
                    " Wallpaper";
            Toast.makeText(context, stringBuilder, Toast.LENGTH_LONG).show();
        }
        context.startActivity(intent);
    }

    @Override
    public void exit() {
        Log.i(TAG, "exit: ");

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
