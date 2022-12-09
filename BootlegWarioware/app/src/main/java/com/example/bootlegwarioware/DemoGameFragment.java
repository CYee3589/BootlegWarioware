package com.example.bootlegwarioware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.databinding.FragmentDemoGameBinding;
import com.example.bootlegwarioware.databinding.FragmentLoadingBinding;

public class DemoGameFragment extends Fragment {

    private FragmentDemoGameBinding binding;

    int i = 0;
    int milliSecCounter = 2500;
    int milliSecInterval= 100;

    boolean isGameCompleted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDemoGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        int difficulty = DemoGameFragmentArgs.fromBundle(requireArguments()).getDifficulty();
        int speed = DemoGameFragmentArgs.fromBundle(requireArguments()).getSpeed();

        binding.demoButton.setOnClickListener(new View.OnClickListener(){
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
                binding.minigameTimerBar.setProgress(100 - (i*100/(milliSecCounter/milliSecInterval)));
            }

            @Override
            public void onFinish() {
                i++;
                binding.minigameTimerBar.setProgress(0);
                NavDirections action = DemoGameFragmentDirections.actionDemoGameFragmentToGameResultsFragment(isGameCompleted);
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