package com.example.bootlegwarioware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.databinding.FragmentTitleBinding;

public class TitleFragment extends Fragment {

    private FragmentTitleBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentTitleBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                NavDirections action = TitleFragmentDirections.actionTitleFragmentToIntroCountdownFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        binding.highscoreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                NavDirections action = TitleFragmentDirections.actionTitleFragmentToHighScoreFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        binding.settingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                NavDirections action = TitleFragmentDirections.actionTitleFragmentToSettingsFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}