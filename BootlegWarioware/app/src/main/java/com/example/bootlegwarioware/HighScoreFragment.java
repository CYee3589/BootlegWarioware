package com.example.bootlegwarioware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import com.example.bootlegwarioware.databinding.FragmentHighScoreBinding;

import java.util.ArrayList;

public class HighScoreFragment extends Fragment {
    private FragmentHighScoreBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHighScoreBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ArrayList<Integer> highscores = ((MainActivity)getContext()).getHighScores();
        binding.highScoreList.setText(TextUtils.join("\n", highscores));

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