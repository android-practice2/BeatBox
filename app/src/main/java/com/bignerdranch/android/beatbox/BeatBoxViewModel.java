package com.bignerdranch.android.beatbox;

import android.media.SoundPool;
import android.widget.SeekBar;

public class BeatBoxViewModel {

    private BeatBox mBeatBox;
    private Callbacks mCallbacks;


    public BeatBoxViewModel(BeatBox beatBox, Callbacks callbacks) {
        mBeatBox = beatBox;
        mCallbacks=callbacks;
    }

    public interface  Callbacks{
        void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);
    }


    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mCallbacks.onProgressChanged(seekBar,progress,fromUser);
        float rate= RateUtil.convertToRate(progress);
        mBeatBox.setRate(rate);
        for (Sound sound : mBeatBox.getSounds()) {
            Integer streamId = sound.getStreamId();
            if (streamId != null) {
                SoundPool soundPool = mBeatBox.getSoundPool();
                soundPool.setRate(streamId, rate);
            }
        }


    }

}
