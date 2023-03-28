package com.example.bootlegwarioware.games.FlyGame;

import android.animation.ObjectAnimator;
import android.graphics.Path;
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

import com.example.bootlegwarioware.databinding.FragmentFlyGameBinding;


public class flyGameFragment extends Fragment {

    private FragmentFlyGameBinding binding;

    int i = 0;
    int milliSecCounter = 10000;
    int milliSecInterval= 750;

    int difficutlySpeeds[] = {12000, 11000, 10000};

    boolean flyButton1IsPressed, flyButton2IsPressed = false;
    boolean running = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFlyGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        DisplayMetrics displayMetrics = view.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels;
        float dpWidth = displayMetrics.widthPixels;

        int difficulty = flyGameFragmentArgs.fromBundle(requireArguments()).getDifficulty();

        if (difficulty == 3){
            binding.flyButton2.setVisibility(View.VISIBLE);
        }

        ObjectAnimator animatorBug1 = createRandomMovingAnimator(difficulty, dpHeight, dpWidth, binding.flyButton);
        ObjectAnimator animatorBug2 = createRandomMovingAnimator(difficulty, dpHeight, dpWidth, binding.flyButton2);
        animatorBug1.setDuration(difficutlySpeeds[difficulty-1]);
        animatorBug2.setDuration(difficutlySpeeds[difficulty-1]);
        animatorBug1.start();
        animatorBug2.start();

        binding.flyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                animatorBug1.end();
                flyButton1IsPressed = true;
            }
        });

        binding.flyButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                animatorBug2.cancel();
                flyButton2IsPressed = true;
            }
        });


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
                boolean isGameCompleted;

                if (difficulty == 3){
                    isGameCompleted = (flyButton1IsPressed && flyButton2IsPressed);
                } else {
                    isGameCompleted = flyButton1IsPressed;
                }

                NavDirections action = flyGameFragmentDirections.actionFlyGameFragmentToGameResultsFragment(isGameCompleted);
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

    public ObjectAnimator createRandomMovingAnimator(int difficulty, float height, float width, ImageButton button){
        Path path = new Path();
        float w = randNum(width-500,0);
        float h = randNum(height-500,0);

        for(int i=0;i<30;i++){
            path.quadTo(w, h, w = randNum(width-500,0), h = randNum(height-500,0));
        }

        return ObjectAnimator.ofFloat(button, View.X, View.Y, path);
    }

    public int randNum(float min, float max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}


