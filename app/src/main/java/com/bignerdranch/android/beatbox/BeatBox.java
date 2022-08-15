package com.bignerdranch.android.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeatBox {
    private static final String TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;
    private List<Sound> mSounds;
    private AssetManager mAssetManager;
    private SoundPool mSoundPool;

    private float rate=1f;


    public BeatBox(Context context) {
        mAssetManager = context.getAssets();
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);

        loadSounds();
    }

    public void playSound(Sound sound) {
        if (sound.getSoundId() == null) {
            return;
        }

        int streamId = mSoundPool.play(sound.getSoundId(), 1f, 1f, 1, 0, rate);
        sound.setStreamId(streamId);

    }

    public void release() {
        mSoundPool.release();
    }


    private void loadSounds() {
        String[] filenames;
        try {
            filenames = mAssetManager.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found " + filenames.length + " Sounds");
        } catch (IOException e) {
            Log.e(TAG, "Could not list assets", e);
            return;
        }

        mSounds = new ArrayList<>(filenames.length);
        for (String filename : filenames) {
            String assetPath = SOUNDS_FOLDER + "/" + filename;
            Sound sound = new Sound(assetPath);

            try {
                loadSound(assetPath, sound);
            } catch (Exception e) {
                Log.e(TAG, "Could not openFd asset " + assetPath, e);
                return;
            }
            mSounds.add(sound);

        }


    }

    private void loadSound(String assetPath, Sound sound) throws IOException {
        AssetFileDescriptor assetFileDescriptor = mAssetManager.openFd(assetPath);
        int soundId = mSoundPool.load(assetFileDescriptor, 1);
        sound.setSoundId(soundId);
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    public SoundPool getSoundPool() {
        return mSoundPool;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
