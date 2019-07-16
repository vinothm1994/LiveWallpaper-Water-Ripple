package com.mygdx.game.ui.sound.dummy;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class SoundContent {



    public static class SoundItem implements  Comparable<SoundItem>{
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

        @Override
        public int compareTo(SoundItem soundItem) {
            return Integer.compare(id,soundItem.id);
        }


        public  static Comparator<SoundItem> sizeCom=new Comparator<SoundItem>() {
            @Override
            public int compare(SoundItem soundItem, SoundItem t1) {
                return 0;
            }
        };

        public  static   Comparator<SoundItem> isCOm=new Comparator<SoundItem>() {
            @Override
            public int compare(SoundItem soundItem, SoundItem t1) {
                return 0;
            }
        };

    }
}
