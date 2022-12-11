package com.example.bootlegwarioware.OrderGame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bootlegwarioware.R;
import com.example.bootlegwarioware.databinding.FragmentOrderGameBinding;

import java.util.ArrayList;

public class OrderGameFragment extends Fragment {

    private OrderGameViewModel viewModel;
    private FragmentOrderGameBinding binding;
    int time = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(OrderGameViewModel.class);
        binding = FragmentOrderGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        int difficulty = OrderGameFragmentArgs.fromBundle(requireArguments()).getDifficulty();
        int speed = OrderGameFragmentArgs.fromBundle(requireArguments()).getSpeed();

        // Display the 5th light if difficulty is 3/hard
        if (difficulty == 1 || difficulty == 2){
            binding.statusLight5.setVisibility(View.GONE);
        }

        ArrayList<String> answerCombo = viewModel.getCombination(difficulty);

        binding.combination.setText(viewModel.generateComboText(answerCombo));

        // Club Button
        binding.clubButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(checkIfCorrect(new String[]{"CLUB", "GREEN"}, viewModel.i, answerCombo, viewModel.areButtonsDisabled)){
                    viewModel.i += 1;
                    if(isGameCompleted(viewModel.i, difficulty)){
                        viewModel.isGameCleared = true;
                    }
                } else {
                    viewModel.areButtonsDisabled = true;
                }
            }
        });

        // Heart Button
        binding.heartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(checkIfCorrect(new String[]{"HEART", "RED"}, viewModel.i, answerCombo, viewModel.areButtonsDisabled)){
                    viewModel.i += 1;
                    if(isGameCompleted(viewModel.i, difficulty)){
                        viewModel.isGameCleared = true;
                    }
                } else {
                    viewModel.areButtonsDisabled = true;
                }
            }
        });

        // Diamond Button
        binding.diamondButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(checkIfCorrect(new String[]{"DIAMOND", "BLUE"}, viewModel.i, answerCombo, viewModel.areButtonsDisabled)){
                    viewModel.i += 1;
                    if(isGameCompleted(viewModel.i, difficulty)){
                        viewModel.isGameCleared = true;
                    }
                } else {
                    viewModel.areButtonsDisabled = true;
                }
            }
        });

        // Spade Button
        binding.spadeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(checkIfCorrect(new String[]{"SPADE", "YELLOW"}, viewModel.i, answerCombo, viewModel.areButtonsDisabled)){
                    viewModel.i += 1;
                    if(isGameCompleted(viewModel.i, difficulty)){
                        viewModel.isGameCleared = true;
                    }
                } else {
                    viewModel.areButtonsDisabled = true;
                }
            }
        });

        binding.minigameTimerBar.setProgress(100);
        CountDownTimer countDown = new CountDownTimer(viewModel.milliSecCounter, viewModel.milliSecInterval) {
            @Override
            public void onTick(long l) {
                time++;
                binding.minigameTimerBar.setProgress(100 - (time*100/(viewModel.milliSecCounter/viewModel.milliSecInterval)));
            }

            @Override
            public void onFinish() {
                time++;
                binding.minigameTimerBar.setProgress(0);
                NavDirections action = OrderGameFragmentDirections.actionOrderGameFragmentToGameResultsFragment(viewModel.isGameCleared);
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

    public boolean checkIfCorrect(String[] buttonKeywords, int i, ArrayList<String> answerCombo, boolean areButtonsDisabled){
        if (!areButtonsDisabled){
            if (viewModel.isElementInArray(answerCombo.get(i), buttonKeywords)){
                currentLight(i).setBackground(getResources().getDrawable(R.drawable.order_game_status_light_correct));
                return true;
            } else {
                incorrectResponse();
            }
        }
        return false;
    }

    public ImageView currentLight(int i){
        switch (i){
            case 0:
                return binding.statusLight1;
            case 1:
                return binding.statusLight2;
            case 2:
                return binding.statusLight3;
            case 3:
                return binding.statusLight4;
            case 4:
                return binding.statusLight5;
            default:
                System.out.println("Given a non-valid int in currentLight");
                return binding.statusLight1;
        }
    }

    public boolean isGameCompleted(int i, int difficulty){
        int comboSize = difficulty == 1 || difficulty == 2 ? 4 : 5;
        if(i == comboSize){
            binding.diamondButton.setEnabled(false);
            binding.heartButton.setEnabled(false);
            binding.clubButton.setEnabled(false);
            binding.spadeButton.setEnabled(false);
            return true;
        }
        return false;
    }

    public void incorrectResponse(){
        binding.statusLight1.setBackground(getResources().getDrawable(R.drawable.order_game_status_light_incorrect));
        binding.statusLight2.setBackground(getResources().getDrawable(R.drawable.order_game_status_light_incorrect));
        binding.statusLight3.setBackground(getResources().getDrawable(R.drawable.order_game_status_light_incorrect));
        binding.statusLight4.setBackground(getResources().getDrawable(R.drawable.order_game_status_light_incorrect));
        binding.statusLight5.setBackground(getResources().getDrawable(R.drawable.order_game_status_light_incorrect));
        binding.diamondButton.setEnabled(false);
        binding.heartButton.setEnabled(false);
        binding.clubButton.setEnabled(false);
        binding.spadeButton.setEnabled(false);
    }
}