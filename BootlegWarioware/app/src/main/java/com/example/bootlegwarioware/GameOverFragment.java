package com.example.bootlegwarioware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.databinding.FragmentGameOverBinding;

public class GameOverFragment extends Fragment {

    private FragmentGameOverBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGameOverBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        int finalScore = GameOverFragmentArgs.fromBundle(requireArguments()).getScore();
        binding.resultsTextView.setText(String.valueOf(finalScore));

        // Reset game integers
        ((MainActivity)getContext()).setBackToNormal();

        binding.returnToTitleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                NavDirections action = GameOverFragmentDirections.actionGameOverFragmentToTitleFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}