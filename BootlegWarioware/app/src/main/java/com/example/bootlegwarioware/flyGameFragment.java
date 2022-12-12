package com.example.bootlegwarioware;

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

import com.example.bootlegwarioware.databinding.FragmentFlyGameBinding;


public class flyGameFragment extends Fragment {

    private FragmentFlyGameBinding binding;

    int i = 0;
    int milliSecCounter = 2500;
    int milliSecInterval= 100;

    boolean isGameCompleted = false;
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

        move(difficulty,dpHeight,dpWidth);

        binding.flyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                isGameCompleted = true;
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

    public void move(int difficulty, float height, float width){
        Path path = new Path();
        float w = randNum(width-500,0);
        float h = randNum(height-500,0);
        for(int i=0;i<30;i++){
            path.quadTo(w, h, w = randNum(width-500,0), h = randNum(height-500,0));
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.flyButton, View.X, View.Y, path);
        animator.setDuration(7500/difficulty);
        animator.start();
    }

    public int randNum(float min, float max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}


