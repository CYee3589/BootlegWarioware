package com.example.bootlegwarioware;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.bootlegwarioware.databinding.FragmentLoadingBinding;

import java.util.concurrent.ThreadLocalRandom;

public class LoadingFragment extends Fragment {

    private FragmentLoadingBinding binding;

    int i = 0;
    int j;
    int milliSecCounter = 1500;
    int milliSecInterval= 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // The 2 arrays that shows the game of index j: game hint and NavDirection
        NavDirections[] gameDestinations = new NavDirections[]{
                LoadingFragmentDirections.actionLoadingFragmentToDemoGameFragment(
                        ((MainActivity)getContext()).getDifficulty(), ((MainActivity)getContext()).getSpeed()),
                LoadingFragmentDirections.actionLoadingFragmentToOrderGameFragment(
                        ((MainActivity)getContext()).getDifficulty(), ((MainActivity)getContext()).getSpeed())
        };

        String[] gameNames = new String[]{
                "DEMO",
                "ORDER"
        };

        binding = FragmentLoadingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Make animated background animate
        AnimationDrawable progressAnimation = (AnimationDrawable) binding.getRoot().getBackground();
        progressAnimation.start();

        // Initialize the beginning looks of the app
        binding.progressbar.setProgress(100);
        updateLifeSymbols(((MainActivity)getContext()).getLivesLeft());
        binding.scoreTextView.setText(String.valueOf(((MainActivity)getContext()).getScore()));

        // Set up random game index and fill in
        j = getRandomNumber(gameNames.length - 1);

        // Run the game hint animation
        binding.gameWordTextView.setText(gameNames[j]);
        runAnimation();

        // Set-up and start the countdowntimer for the progress bar and timing
        CountDownTimer countDown = new CountDownTimer(milliSecCounter,milliSecInterval) {
            @Override
            public void onTick(long l) {
                i++;
                binding.progressbar.setProgress(100 - (i*100/(milliSecCounter/milliSecInterval)));
            }

            @Override
            public void onFinish() {
                i++;
                binding.progressbar.setProgress(0);
                Navigation.findNavController(view).navigate(gameDestinations[j]);
            }
        };

        countDown.start();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Updates the imageView status of the lives
    private void updateLifeSymbols(int livesLeft){
        switch(livesLeft) {
            case 0:
                binding.life1ImageView.setVisibility(View.INVISIBLE);
            case 1:
                binding.life2ImageView.setVisibility(View.INVISIBLE);
            case 2:
                binding.life3ImageView.setVisibility(View.INVISIBLE);
            case 3:
                binding.life4ImageView.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    // Run game hint animation
    private void runAnimation() {
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.word_anim);
        anim.reset();
        binding.gameWordTextView.clearAnimation();
        binding.gameWordTextView.startAnimation(anim);
    }

    // Get a random number betwee 0-max
    private int getRandomNumber(int max) {
        return ThreadLocalRandom.current().nextInt(0, max+1);
    }
}