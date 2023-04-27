package com.example.bootlegwarioware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.databinding.FragmentGameSelectBinding;
import com.example.bootlegwarioware.databinding.FragmentTitleBinding;

public class GameSelectFragment extends Fragment {

    private FragmentGameSelectBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameSelectBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Endless Mode Button
        binding.endlessModeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                selectGameMode(view, false);
            }
        });

        // Story Mode Button
        binding.storyModeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                selectGameMode(view, true);
            }
        });

        // Back Button
        binding.backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                NavDirections action = GameSelectFragmentDirections.actionGameSelectFragmentToTitleFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // Based on what button was clicked, the the isStoryMode variable and nacivate to their
    void selectGameMode(View view, Boolean isStoryMode){
        ((MainActivity)getContext()).setIsStoryMode(isStoryMode);
        System.out.println(((MainActivity)getContext()).getIsStoryMode());
        NavDirections action;
        if (((MainActivity)getContext()).getIsStoryMode()){
            action = GameSelectFragmentDirections.actionGameSelectFragmentToDifficultySelectFragment();
        } else {
            action = GameSelectFragmentDirections.actionGameSelectFragmentToIntroCountdownFragment();
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