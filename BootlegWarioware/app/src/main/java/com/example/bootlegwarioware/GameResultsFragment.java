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

import com.example.bootlegwarioware.databinding.FragmentGameResultsBinding;
import com.example.bootlegwarioware.databinding.FragmentLoadingBinding;


public class GameResultsFragment extends Fragment {

    private FragmentGameResultsBinding binding;
    private GameViewModel viewModel;
//    private GameViewModelFactory viewModelFactory;

    int i = 0;
    int milliSecCounter = 2000;
    int milliSecInterval= 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGameResultsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        boolean isGameCompleted = GameResultsFragmentArgs.fromBundle(requireArguments()).getIsGameCompleted();

        // Used to test if data transferal is successful
        if (isGameCompleted){
            binding.scoreTextView.setText("TRUE");
        } else {
            binding.scoreTextView.setText("FALSE");
        }

        binding.progressbar.setProgress(100);
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
                Navigation.findNavController(view).navigate(R.id.action_gameResultsFragment_to_loadingFragment);
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