package com.bignerdranch.android.beatbox;

public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundId;
    private Integer mStreamId;

    public Sound(String assetPath) {
        this.mAssetPath=assetPath;

        String[] split = assetPath.split("/");
        mName = split[split.length - 1].replace(".wav","");

    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }

    public Integer getStreamId() {
        return mStreamId;
    }

    public void setStreamId(Integer streamId) {
        mStreamId = streamId;
    }
}
