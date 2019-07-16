package com.mygdx.game.ui.sound;

import com.mygdx.game.ui.sound.dummy.SoundContent;

interface OnSoundFragmentListener {
    void playSound(SoundContent.SoundItem item);

    void selectSound(int pos, SoundContent.SoundItem item);
}