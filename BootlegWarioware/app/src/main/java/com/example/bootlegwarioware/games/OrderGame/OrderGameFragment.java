package com.example.bootlegwarioware.games.OrderGame;

import android.media.MediaPlayer;
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
//        int difficulty = 3;

        // Display the 5th light if difficulty is 3/hard
        if (difficulty == 1 || difficulty == 2){
            binding.statusLight5.setVisibility(View.GONE);
        }

        // Generate the random combo, and set the textView with the combo in words
        ArrayList<String> answerCombo = viewModel.getCombination(difficulty);
        binding.combination.setText(viewModel.generateComboText(answerCombo));

        // Club Button
        binding.clubButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                buttonProcedure(new String[]{"CLUB", "GREEN"}, answerCombo, difficulty);
            }
        });

        // Heart Button
        binding.heartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                buttonProcedure(new String[]{"HEART", "RED"}, answerCombo, difficulty);
            }
        });

        // Diamond Button
        binding.diamondButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                buttonProcedure(new String[]{"DIAMOND", "BLUE"}, answerCombo, difficulty);
            }
        });

        // Spade Button
        binding.spadeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                buttonProcedure(new String[]{"SPADE", "YELLOW"}, answerCombo, difficulty);
            }
        });

        // Timer Bar
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

    // Check if the button's key words match with the current combo's word based on index
    public void buttonProcedure(String[] buttonKeywords, ArrayList<String> answerCombo, int difficulty){
        if(checkIfCorrect(buttonKeywords, viewModel.i, answerCombo, viewModel.areButtonsDisabled)){
            viewModel.i += 1;
            if(isGameCompleted(viewModel.i, difficulty)){
                viewModel.isGameCleared = true;
                binding.microgameOrderBackground.setBackgroundResource(R.drawable.microgame_order_win);
                final MediaPlayer correct = MediaPlayer.create(getActivity(), R.raw.correct_sound_effect);
                correct.start();
            }
        } else {
            viewModel.areButtonsDisabled = true;
        }
    }

    // Check if the button clicked goes with the combo word at index i, and set that light at index i to green
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

    // Given an index, return the light corresponding that said index
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

    // If the game is correctly completed, disable all buttons and return true;
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

    // Set all buttons as disabled, set all lights to red, and fail the game
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
        binding.microgameOrderBackground.setBackgroundResource(R.drawable.microgame_order_lose);
        final MediaPlayer incorrect = MediaPlayer.create(getActivity(), R.raw.incorrect_sound_effect);
        incorrect.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}