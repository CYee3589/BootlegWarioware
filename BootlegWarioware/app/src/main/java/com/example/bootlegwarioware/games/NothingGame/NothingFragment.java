package com.example.bootlegwarioware.games.NothingGame;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.R;
import com.example.bootlegwarioware.databinding.FragmentNothingBinding;

public class NothingFragment extends Fragment {

    private FragmentNothingBinding binding;

    // Timer Progression Bar Variables
    int i = 0;
    int milliSecCounter = 7000;
    int milliSecInterval= 700;

    // NOTE: Difficutly is the same no matter what difficutly setting

    boolean isGameCompleted = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNothingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        int difficulty = NothingFragmentArgs.fromBundle(requireArguments()).getDifficulty();
        int speed = NothingFragmentArgs.fromBundle(requireArguments()).getSpeed();
//        int difficulty = 1;

        // Start the animation for the animated background
        AnimationDrawable progressAnimation = (AnimationDrawable) binding.microgameNothingBackground.getBackground();
        progressAnimation.start();

        // If the nuke button is pushed, change all visuals and fail the game
        binding.nukeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                isGameCompleted = false;
                binding.nukeButton.setClickable(false);
                binding.nukeButton.setBackgroundResource(R.drawable.nuke_button_pressed);
                binding.microgameNothingBackground.setBackgroundResource(R.drawable.microgame_nothing_lose);
                final MediaPlayer nuke = MediaPlayer.create(getActivity(), R.raw.nuke_sound_effect);
                nuke.start();
            }
        });

        // Timer bar
        binding.minigameTimerBar.setProgress(100);
        CountDownTimer countDown = new CountDownTimer(milliSecCounter,milliSecInterval) {
            @Override
            public void onTick(long l) {
                i++;
                binding.minigameTimerBar.setProgress(100 - (i*100/(milliSecCounter/milliSecInterval)));
            }

            @Override
            public void onFinish() {
                i++;
                binding.minigameTimerBar.setProgress(0);
                NavDirections action = NothingFragmentDirections.actionNothingFragmentToGameResultsFragment(isGameCompleted);
                Navigation.findNavController(view).navigate(action);
            }
        };

        countDown.start();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}