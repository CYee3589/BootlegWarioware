package com.example.bootlegwarioware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.bootlegwarioware.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.backButtonSettings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                NavDirections action = SettingsFragmentDirections.actionSettingsFragmentToTitleFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton button = binding.radioGroup.findViewById(i);
                switch(button.getText().toString()) {
                    case "Hard":
                        ((MainActivity)getContext()).setDifficulty(3);
                    case "Medium":
                        ((MainActivity)getContext()).setDifficulty(2);
                    case "Easy":
                        ((MainActivity)getContext()).setDifficulty(1);
                }
            }
        });

        return view;
    }
}