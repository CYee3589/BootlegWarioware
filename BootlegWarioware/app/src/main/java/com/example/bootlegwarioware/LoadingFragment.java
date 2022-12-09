package com.example.bootlegwarioware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.databinding.FragmentLoadingBinding;

public class LoadingFragment extends Fragment {

    private FragmentLoadingBinding binding;

    int i = 0;
    int milliSecCounter = 1500;
    int milliSecInterval= 100;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoadingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.progressbar.setProgress(100);
        updateLifeSymbols(((MainActivity)getContext()).getLivesLeft());

        binding.scoreTextView.setText(String.valueOf(((MainActivity)getContext()).getScore()));

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
                NavDirections action = LoadingFragmentDirections.actionLoadingFragmentToDemoGameFragment(
                        ((MainActivity)getContext()).getDifficulty(),
                        ((MainActivity)getContext()).getSpeed());
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

    public void updateLifeSymbols(int livesLeft){
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
                System.out.println("ERROR here");
                break;
        }
    }
}