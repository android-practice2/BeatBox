package com.bignerdranch.android.beatbox;

import android.media.AudioManager;
import android.media.SoundPool;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class SoundViewModel extends BaseObservable {
    private BeatBox mBeatBox;
    private Sound mSound;

    public SoundViewModel(BeatBox beatBox) {
        mBeatBox = beatBox;
    }
    @Bindable
    public String getTitle() {
        return mSound.getName();
    }
    public Sound getSound() {
        return mSound;
    }

    public void setSound(Sound sound) {
        mSound = sound;
        notifyChange();
    }

    public void onButtonClicked() {
        mBeatBox.playSound(mSound);
    }


}
