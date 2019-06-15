package com.mygdx.game.ui.image_gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mygdx.game.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import code.apps.ripple.activityfolder.choosebg.ImageModal;

public class WallpaperChooseAdapter extends RecyclerView.Adapter<WallpaperChooseAdapter.ViewHolder> {
    Context activity;
    OnImageSelection onImageSelection;
    private List<ImageModal> imageModals;

    public WallpaperChooseAdapter(List<ImageModal> list, Context context) {
        this.imageModals = list;
        this.activity = context;
    }

    public void setListener(OnImageSelection onImageSelection) {
        this.onImageSelection = onImageSelection;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_bgchooser, viewGroup, false));
    }


    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        ImageModal imageModal = this.imageModals.get(position);
        String str = imageModal.filepath;
        if (imageModal.isOnlineImage()) {
            Picasso.get().load(this.activity.getFileStreamPath(str)).fit().centerCrop().into(viewHolder.logo);
        } else {
            Picasso.get().load(str).fit().centerCrop().into(viewHolder.logo);
        }
        viewHolder.view_selected.setChecked(imageModal.isSelected);

    }

    public int getItemCount() {
        return this.imageModals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView logo;
        public final View mView;
        public final RadioButton view_selected;

        public ViewHolder(View view) {
            super(view);
            this.mView = view;
            this.logo = view.findViewById(R.id.imageView);
            this.view_selected = view.findViewById(R.id.view_selected);
            mView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (WallpaperChooseAdapter.this.onImageSelection != null) {
                        WallpaperChooseAdapter.this.onImageSelection.selected(getAdapterPosition());
                    }
                }
            });
            view_selected.setVisibility(View.INVISIBLE);
        }
    }
}
