package code.apps.ripple.activityfolder.chooseparticle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.mygdx.game.R;
import com.mygdx.game.ui.image_gallery.OnImageSelection;
import com.squareup.picasso.Picasso;

import java.util.List;

import code.apps.ripple.activityfolder.choosebg.ImageModal;

public class ParticleChooseAdadpter extends RecyclerView.Adapter<ParticleChooseAdadpter.ViewHolder> {
    Activity a;
    OnImageSelection b;
    private List<ImageModal> c;

    public ParticleChooseAdadpter(List<ImageModal> list, Activity activity) {
        this.c = list;
        this.a = activity;
    }

    public void setListener(OnImageSelection onImageSelection) {
        this.b = onImageSelection;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_pchooser, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        ImageModal imageModal = (ImageModal) this.c.get(i);
        Picasso.get().load(imageModal.filepath).placeholder((int) R.drawable.ic_launcher).into(viewHolder.logo);
        viewHolder.view_selected.setVisibility(imageModal.isSelected ? 0 : 4);
        viewHolder.mView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ParticleChooseAdadpter.this.b != null) {
                    ParticleChooseAdadpter.this.b.selected(i);
                }
            }
        });
    }

    public int getItemCount() {
        return this.c.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView logo;
        public final View mView;
        public final View view_selected;

        public ViewHolder(View view) {
            super(view);
            this.mView = view;
            this.logo = (ImageView) view.findViewById(R.id.imageView);
            this.view_selected = view.findViewById(R.id.view_selected);
            this.view_selected.setVisibility(4);
        }
    }
}
