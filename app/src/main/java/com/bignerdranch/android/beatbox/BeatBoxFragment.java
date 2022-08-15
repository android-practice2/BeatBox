package com.bignerdranch.android.beatbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.beatbox.databinding.FragmentBeatBoxBinding;
import com.bignerdranch.android.beatbox.databinding.ListItemSoundBinding;

import java.util.List;

public class BeatBoxFragment extends Fragment implements BeatBoxViewModel.Callbacks {

    private BeatBox mBeatBox;
    private TextView mSeek_bar_label;

    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBeatBox = new BeatBox(getActivity());
        setRetainInstance(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentBeatBoxBinding beatBoxBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_beat_box, container, false);

        beatBoxBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        beatBoxBinding.recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));

        beatBoxBinding.setViewModel(new BeatBoxViewModel(mBeatBox, this));
        View root = beatBoxBinding.getRoot();

        SeekBar seekBar = root.findViewById(R.id.seek_bar);
        seekBar.setProgress(RateUtil.INITIAL_PROGRESS);

        mSeek_bar_label = root.findViewById(R.id.seek_bar_text);
        updateSeekBarLabel(RateUtil.INITIAL_RATE);


        return root;

    }

    private void updateSeekBarLabel(float rate) {
        mSeek_bar_label.setText(getString(R.string.playback_laybel, rate * 100));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        updateSeekBarLabel(RateUtil.convertToRate(progress));
    }

    private class SoundHolder extends RecyclerView.ViewHolder {
        private final ListItemSoundBinding mBinding;

        public SoundHolder(@NonNull ListItemSoundBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new SoundViewModel(mBeatBox));
        }

        public void bind(Sound sound) {
            mBinding.getViewModel().setSound(sound);
            mBinding.executePendingBindings();
        }
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {

        private final List<Sound> mSounds;

        public SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }

        @NonNull
        @Override
        public SoundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ListItemSoundBinding soundBinding = DataBindingUtil.inflate(inflater, R.layout.list_item_sound, parent, false);
            return new SoundHolder(soundBinding);
        }


        @Override
        public void onBindViewHolder(@NonNull SoundHolder holder, int position) {
            holder.bind(mSounds.get(position));

        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }
}
