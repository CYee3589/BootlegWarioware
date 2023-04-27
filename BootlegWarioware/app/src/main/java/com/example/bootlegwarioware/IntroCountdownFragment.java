package com.example.bootlegwarioware;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.databinding.FragmentIntroCountdownBinding;

public class IntroCountdownFragment extends Fragment {

    private FragmentIntroCountdownBinding binding;

    // Count Down Numbers
    int milliSecCounter = 3750;
    int milliSecInterval= 1250;
    int i = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentIntroCountdownBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Count Down
        CountDownTimer countDown = new CountDownTimer(milliSecCounter,milliSecInterval) {
            @Override
            public void onTick(long l) {
                binding.countDownText.setText(String.valueOf(i));
                i--;
            }

            @Override
            public void onFinish() {
                binding.countDownText.setText(R.string.countdownGO);
                NavDirections action = IntroCountdownFragmentDirections.actionIntroCountdownFragmentToLoadingFragment();
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