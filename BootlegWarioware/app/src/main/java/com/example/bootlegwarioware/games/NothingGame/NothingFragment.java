package com.example.bootlegwarioware.games.NothingGame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.databinding.FragmentNothingBinding;

public class NothingFragment extends Fragment {

    private FragmentNothingBinding binding;

    int i = 0;
    int milliSecCounter = 2500;
    int milliSecInterval= 100;

    boolean isGameCompleted = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNothingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        int difficulty = NothingFragmentArgs.fromBundle(requireArguments()).getDifficulty();
//        int difficulty = 1;
        int speed = NothingFragmentArgs.fromBundle(requireArguments()).getSpeed();


        binding.nukeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                isGameCompleted = false;
                binding.nukeButton.setClickable(false);
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