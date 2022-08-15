package com.bignerdranch.android.beatbox;

public abstract  class RateUtil {
    public static final int INITIAL_PROGRESS = 50;
    public static final float INITIAL_RATE = convertToRate(INITIAL_PROGRESS);

    public static float convertToRate(float progress) {
        return progress /INITIAL_PROGRESS;
    }
}
