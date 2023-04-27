package com.example.bootlegwarioware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.databinding.FragmentGameOverBinding;

import java.util.ArrayList;
import java.util.Collections;

public class GameOverFragment extends Fragment {

    private FragmentGameOverBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGameOverBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Get the overall score and set it to the resultsTextView
        int finalScore = GameOverFragmentArgs.fromBundle(requireArguments()).getScore();
        addHighScore(finalScore);
        binding.resultsTextView.setText(String.valueOf(finalScore));

        // Reset game integers, based on weither its story or endless mode
        if (((MainActivity)getContext()).getIsStoryMode()){
            ((MainActivity)getContext()).setBackToNormalStory();
        } else {
            ((MainActivity)getContext()).setBackToNormalEndless();
        }

        // When clicked, you will go back to the title screen
        binding.returnToTitleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                NavDirections action = GameOverFragmentDirections.actionGameOverFragmentToTitleFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        // When clicked, you start the game all over again
        binding.retryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                NavDirections action = GameOverFragmentDirections.actionGameOverFragmentToIntroCountdownFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // Publishes your score to the highscore
    public void addHighScore(int score){
        ArrayList<Integer> highscores = ((MainActivity)getContext()).getHighScores();
        highscores.add(score);
        Collections.sort(highscores,Collections.reverseOrder());
        ((MainActivity)getContext()).setHighscores(highscores);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}