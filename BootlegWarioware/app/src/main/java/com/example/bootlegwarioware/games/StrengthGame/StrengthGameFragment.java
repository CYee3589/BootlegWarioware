package com.example.bootlegwarioware.games.StrengthGame;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.R;
import com.example.bootlegwarioware.databinding.FragmentStrengthGameBinding;

public class StrengthGameFragment extends Fragment {

    private FragmentStrengthGameBinding binding;

    // Timer Progression Bar Variables
    int i = 0;
    int milliSecCounter = 10000;
    int milliSecInterval= 1000;

    boolean isGameCompleted = false;
    int[] difficultyStrengthGoals = {10, 15, 20};
    int clickGoal;
    int currentNumberOfClicks = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStrengthGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

//        int difficulty = StrengthGameFragmentArgs.fromBundle(requireArguments()).getDifficulty();
//        int speed = StrengthGameFragmentArgs.fromBundle(requireArguments()).getSpeed();

        int difficulty = 1;

        // Make animated hands animate
        AnimationDrawable progressAnimation = (AnimationDrawable) binding.microgameStrengthBackground.getBackground();
        progressAnimation.start();

        clickGoal = difficultyStrengthGoals[difficulty - 1];
        binding.powerProgressBar.setProgress(0);

        // Get the Drawable custom_progressbar
        Drawable draw = getResources().getDrawable(R.drawable.strength_progressbar);
// set the drawable as progress drawable
        binding.powerProgressBar.setProgressDrawable(draw);

        binding.strengthButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                currentNumberOfClicks++;
                System.out.println(currentNumberOfClicks);
                binding.powerProgressBar.setProgress((100*currentNumberOfClicks)/clickGoal);

                // Once the goal has been reached, switch to winning background, make strength bar and button invisable
                if (currentNumberOfClicks == clickGoal){
                    isGameCompleted = true;
                    binding.strengthButton.setEnabled(false);
                    binding.strengthButton.setVisibility(View.INVISIBLE);
                    binding.powerProgressBar.setVisibility(View.INVISIBLE);
                    binding.microgameStrengthBackground.setBackgroundResource(R.drawable.microgame_strength_win);
                }
            }
        });


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
                NavDirections action = StrengthGameFragmentDirections.actionStrengthGameFragmentToGameResultsFragment(isGameCompleted);
                Navigation.findNavController(view).navigate(action);
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
}