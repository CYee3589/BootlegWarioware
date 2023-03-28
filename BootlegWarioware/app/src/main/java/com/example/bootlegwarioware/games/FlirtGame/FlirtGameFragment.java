package com.example.bootlegwarioware.games.FlirtGame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bootlegwarioware.R;
import com.example.bootlegwarioware.databinding.FragmentFlirtGameBinding;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class FlirtGameFragment extends Fragment {

    private FragmentFlirtGameBinding binding;

    final String[][] difficultyWords = {{"YOU'RE CUTE", "YOU'RE UGLY"},
            {"YOU'RE BEAUTIFUL", "YOU'RE ADOPTED", "YOU'RE DISGUSTING", "YOU'RE UNSIGHTLY"},
            {"YOU'RE RAVISHING", "YOU'RE REPULSIVE", "YOU'RE RAUNCHY", "YOU'RE RUTHLESS", "YOU'RE RIDICULOUS", "YOU'RE RANCOROUS"}};

    final String[] correctDifficultyWords = {"YOU'RE CUTE", "YOU'RE BEAUTIFUL", "YOU'RE RAVISHING"};

    // Timer Progression Bar Variables
    int i = 0;
    int milliSecCounter = 2500;
    int milliSecInterval= 100;

    boolean isGameCompleted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFlirtGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        int difficulty = FlirtGameFragmentArgs.fromBundle(requireArguments()).getDifficulty();
//        int difficulty = 3;
        int speed = FlirtGameFragmentArgs.fromBundle(requireArguments()).getSpeed();

        // Intialize all buttons based on difficulty
        setButtonsWithDifficulty(difficulty);

        // Intialize all buttons based on difficulty
        binding.button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){checkIfCorrectAnswer(view, difficulty);}
        });

        binding.button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){checkIfCorrectAnswer(view, difficulty);}
        });

        binding.button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){checkIfCorrectAnswer(view, difficulty);}
        });

        binding.button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){checkIfCorrectAnswer(view, difficulty);}
        });

        binding.button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){checkIfCorrectAnswer(view, difficulty);}
        });

        binding.button6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){checkIfCorrectAnswer(view, difficulty);}
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
                NavDirections action = FlirtGameFragmentDirections.actionFlirtGameFragmentToGameResultsFragment(isGameCompleted);
                Navigation.findNavController(view).navigate(action);
            }
        };


        // Inflate the layout for this fragment
        return view;
    }

    public void setButtonsWithDifficulty(int difficulty){
        String[] words = difficultyWords[difficulty - 1];
        words = shuffle(words);
        switch (difficulty){
            case 3:
                binding.button5.setText(words[4]);
                binding.button6.setText(words[5]);
            case 2:
                binding.button3.setText(words[2]);
                binding.button4.setText(words[3]);
                if (difficulty == 2){
                    binding.button5.setVisibility(View.INVISIBLE);
                    binding.button6.setVisibility(View.INVISIBLE);
                }

            case 1:
                binding.button1.setText(words[0]);
                binding.button2.setText(words[1]);
                if (difficulty == 1){
                    binding.button3.setVisibility(View.INVISIBLE);
                    binding.button4.setVisibility(View.INVISIBLE);
                    binding.button5.setVisibility(View.INVISIBLE);
                    binding.button6.setVisibility(View.INVISIBLE);
                }
        }

    }

    public String[] shuffle(String[] input) {
        String[] output = Arrays.copyOf(input, input.length);
        Collections.shuffle(Arrays.asList(output));
        return output;
    }

    public boolean checkIfCorrectAnswer(View v, int difficulty){
        Button b = (Button)v;
        String buttonText = b.getText().toString();

        // Set Clicked button to hot pink, and disable all buttons
        b.setBackgroundColor(getResources().getColor(R.color.hot_pink));
        binding.button1.setClickable(false);
        binding.button2.setClickable(false);
        binding.button3.setClickable(false);
        binding.button4.setClickable(false);
        binding.button5.setClickable(false);
        binding.button6.setClickable(false);

        isGameCompleted = Objects.equals(correctDifficultyWords[difficulty - 1], buttonText);
        System.out.println("ANSWER: " + isGameCompleted);
        return Objects.equals(correctDifficultyWords[difficulty - 1], buttonText);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}