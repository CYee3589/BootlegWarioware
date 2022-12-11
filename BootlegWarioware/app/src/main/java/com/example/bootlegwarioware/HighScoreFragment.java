package com.example.bootlegwarioware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.databinding.FragmentHighScoreBinding;


public class HighScoreFragment extends Fragment {

    private FragmentHighScoreBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHighScoreBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.backButtonScore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                NavDirections action = HighScoreFragmentDirections.actionHighScoreFragmentToTitleFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
        return view;
    }
}