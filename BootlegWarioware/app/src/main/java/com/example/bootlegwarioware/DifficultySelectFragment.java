package com.example.bootlegwarioware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.databinding.FragmentDifficultySelectBinding;
import com.example.bootlegwarioware.databinding.FragmentGameSelectBinding;

public class DifficultySelectFragment extends Fragment {

    private FragmentDifficultySelectBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDifficultySelectBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Easy Button
        binding.easyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                goToEndlessOrStory(view, ((MainActivity)getContext()).getIsStoryMode(), 1);
            }
        });

        // Medium Button
        binding.mediumButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                goToEndlessOrStory(view, ((MainActivity)getContext()).getIsStoryMode(), 2);
            }
        });

        // Hard Button
        binding.hardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                goToEndlessOrStory(view, ((MainActivity)getContext()).getIsStoryMode(), 3);
            }
        });

        // Back Button
        binding.backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                NavDirections action = DifficultySelectFragmentDirections.actionDifficultySelectFragmentToGameSelectFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // Based on what was clicked, navigate to the appropriate location
    void goToEndlessOrStory(View view, Boolean isStory, int difficulty){
        ((MainActivity)getContext()).shuffleGameIndexes();
        NavDirections action;
        if (isStory){
            action = DifficultySelectFragmentDirections.actionDifficultySelectFragmentToIntroCutsceneFragment();
            ((MainActivity)getContext()).setDifficulty(difficulty);
        } else {
            action = DifficultySelectFragmentDirections.actionDifficultySelectFragmentToGameSelectFragment();
            ((MainActivity)getContext()).setDifficulty(1);
        }

        Navigation.findNavController(view).navigate(action);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}