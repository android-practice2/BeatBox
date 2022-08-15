package com.bignerdranch.android.beatbox;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.hamcrest.junit.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class SoundViewModelTest {
    private BeatBox mBeatBox;
    private Sound mSound;
    private SoundViewModel mSubject;

    @Before
    public void setUp() throws Exception {
        mBeatBox = mock(BeatBox.class);
        mSound = new Sound("assetPath");
        mSubject = new SoundViewModel(mBeatBox);
        mSubject.setSound(mSound);

    }

    @Test
    public void exposesSoundNameAsTitle() {
        MatcherAssert.assertThat(mSubject.getTitle(),is(mSound.getName()));
    }

    @Test
    public void callsBeatBoxPlayOnButtonClicked() {
        mSubject.onButtonClicked();
        verify(mBeatBox).playSound(mSound);

    }
}