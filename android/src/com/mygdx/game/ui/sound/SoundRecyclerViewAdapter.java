package com.mygdx.game.ui.sound;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mygdx.game.R;
import com.mygdx.game.ui.sound.SoundFragment.OnSoundFragmentListener;
import com.mygdx.game.ui.sound.dummy.SoundContent.SoundItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SoundItem} and makes a call to the
 * specified {@link OnSoundFragmentListener}.
 */
public class SoundRecyclerViewAdapter extends RecyclerView.Adapter<SoundRecyclerViewAdapter.ViewHolder> {

    private final OnSoundFragmentListener mListener;
    int selectedPos = 0;
    private List<SoundItem> soundItems;

    public SoundRecyclerViewAdapter(List<SoundItem> items, OnSoundFragmentListener listener) {
        soundItems = items;
        mListener = listener;
    }

    void setSoundItems(List<SoundItem> soundItems) {
        this.soundItems = soundItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sound_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = soundItems.get(position);
        holder.mIdView.setText(String.valueOf(soundItems.get(position).id));
        holder.mContentView.setText(soundItems.get(position).content);
        if (selectedPos == position) {
            holder.mIdView.setTextColor(ContextCompat.getColor(holder.mView.getContext(), R.color.colorAccent));
            holder.mContentView.setTextColor(ContextCompat.getColor(holder.mView.getContext(), R.color.colorAccent));
            holder.rb.setChecked(true);
        } else {
            holder.rb.setChecked(false);
            holder.mIdView.setTextColor(Color.BLACK);
            holder.mContentView.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return soundItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public SoundItem mItem;
        public RadioButton rb;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            rb = view.findViewById(R.id.rb);
            mContentView = view.findViewById(R.id.content);
            view.findViewById(R.id.play_iv).setOnClickListener(this);
            view.setOnClickListener(this);
            rb.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.play_iv) {
                mListener.playSound(soundItems.get(getAdapterPosition()));
            } else {
                selectedPos = getAdapterPosition();
                mListener.selectSound(selectedPos, soundItems.get(getAdapterPosition()));
            }
            notifyDataSetChanged();

        }
    }
}
