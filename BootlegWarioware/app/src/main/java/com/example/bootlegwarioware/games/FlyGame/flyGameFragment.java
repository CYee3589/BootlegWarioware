package com.example.bootlegwarioware.games.FlyGame;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.bootlegwarioware.R;
import com.example.bootlegwarioware.databinding.FragmentFlyGameBinding;


public class flyGameFragment extends Fragment {

    private FragmentFlyGameBinding binding;

    // Timer Progression Bar Variables
    int i = 0;
    int milliSecCounter = 10000;
    int milliSecInterval= 1000;

    // Difficulty is based on how fast the flies are
    int difficutlySpeeds[] = {12000, 11000, 10000};

    boolean isGameCompleted = false;
    boolean flyButton1IsPressed, flyButton2IsPressed = false;
    boolean running = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFlyGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        int difficulty = flyGameFragmentArgs.fromBundle(requireArguments()).getDifficulty();
//        int difficulty = 1;

        // Start the animation for the animated background
        AnimationDrawable progressAnimation = (AnimationDrawable) binding.microgameFlyBackground.getBackground();
        progressAnimation.start();

        DisplayMetrics displayMetrics = view.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels;
        float dpWidth = displayMetrics.widthPixels;

        // If difficulty is 3/hard, add the second fly
        if (difficulty == 3){
            binding.flyButton2.setVisibility(View.VISIBLE);
        }

        // Make the flies move
        ObjectAnimator animatorBug1 = createRandomMovingAnimator(difficulty, dpHeight, dpWidth, binding.flyButton);
        ObjectAnimator animatorBug2 = createRandomMovingAnimator(difficulty, dpHeight, dpWidth, binding.flyButton2);
        animatorBug1.setDuration(difficutlySpeeds[difficulty-1]);
        animatorBug2.setDuration(difficutlySpeeds[difficulty-1]);
        animatorBug1.start();
        animatorBug2.start();

        // Set functionality for fly button 1
        binding.flyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                animatorBug1.cancel();
                binding.flyButton.setBackgroundResource(R.drawable.fly_splat);
                flyButton1IsPressed = true;
                isGameCompleted = checkIfAllBugsAreDead(difficulty);
            }
        });

        // Set functionality for fly button 2
        binding.flyButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                animatorBug2.cancel();
                binding.flyButton2.setImageResource(R.drawable.fly_splat);
                flyButton2IsPressed = true;
                isGameCompleted = checkIfAllBugsAreDead(difficulty);
            }
        });

        // Timer bar
        binding.minigameTimerBar.setProgress(100);
        CountDownTimer countDown = new CountDownTimer(milliSecCounter,milliSecInterval) {
            @Override
            public void onTick(long l) {
                i++;
                running = true;
                binding.minigameTimerBar.setProgress(100 - (i*100/(milliSecCounter/milliSecInterval)));
            }

            @Override
            public void onFinish() {
                i++;
                running = false;
                binding.minigameTimerBar.setProgress(0);

                NavDirections action = flyGameFragmentDirections.actionFlyGameFragmentToGameResultsFragment(isGameCompleted);
                Navigation.findNavController(view).navigate(action);
            }
        };

        countDown.start();

        return view;
    }

    // Based on difficutly, check if all the flies have been clicked on.
    public boolean checkIfAllBugsAreDead(int difficulty){
        boolean results;

        if (difficulty == 3){
            results = flyButton1IsPressed && flyButton2IsPressed;
        } else {
            results = flyButton1IsPressed;
        }

        // If game is completed, show the win background
        if (results){
            binding.microgameFlyBackground.setBackgroundResource(R.drawable.microgame_fly_win);
            final MediaPlayer correct = MediaPlayer.create(getActivity(), R.raw.correct_sound_effect);
            correct.start();
        }

        return results;
    }

    // Set the object animator to go wildly and randomly
    public ObjectAnimator createRandomMovingAnimator(int difficulty, float height, float width, ImageButton button){
        Path path = new Path();
        float w = randNum(width-500,0);
        float h = randNum(height-500,0);

        for(int i=0;i<30;i++){
            path.quadTo(w, h, w = randNum(width-500,0), h = randNum(height-500,0));
        }

        return ObjectAnimator.ofFloat(button, View.X, View.Y, path);
    }

    // Generater a random number based on min and max bound
    public int randNum(float min, float max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


