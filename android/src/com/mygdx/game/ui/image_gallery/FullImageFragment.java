package com.mygdx.game.ui.image_gallery;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.R;
import com.mygdx.game.ui.libgdx.GameFragment;
import com.squareup.picasso.Picasso;

import code.apps.ripple.activityfolder.choosebg.ImageModal;
import code.apps.ripple.logic.Wallpaper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FullImageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FullImageFragment extends Fragment {

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
        FrameLayout banner_iv = view.findViewById(R.id.banner_iv);
//        Picasso.get().load(imageModal.filepath).placeholder(R.drawable.ic_launcher).into(banner_iv);

        getChildFragmentManager().beginTransaction().add(R.id.banner_iv,new GameFragment(),"ss").commit();
        view.findViewById(R.id.set_wallpaper_btn).setOnClickListener((v -> {
            setWallpaper(requireContext());
        }));

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
            intent.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", new ComponentName(getActivity().getPackageName(), Wallpaper.class.getCanonicalName()));
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
