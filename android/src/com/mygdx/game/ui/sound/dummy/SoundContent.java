package com.mygdx.game.ui.sound.dummy;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class SoundContent {


    public static final List<SoundItem> ITEMS = new ArrayList<SoundItem>();


    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(SoundItem item) {
        ITEMS.add(item);
    }

    private static SoundItem createDummyItem(int position) {
        return new SoundItem(position, "Item " + position, "");
    }


    public static class SoundItem {
        public final int id;
        public final String content;
        public final String filepath;

        public SoundItem(int id, String content, String filepath) {
            this.id = id;
            this.content = content;
            this.filepath = filepath;
        }

        @NonNull
        @Override
        public String toString() {
            return content;
        }
    }
}
