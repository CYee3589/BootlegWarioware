package com.example.bootlegwarioware;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
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

        final MediaPlayer titleMusic = MediaPlayer.create(getActivity(), R.raw.title_music);
        titleMusic.setLooping(true);
        titleMusic.start();

        binding.backButtonSettings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                titleMusic.stop();
                NavDirections action = SettingsFragmentDirections.actionSettingsFragmentToTitleFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton button = radioGroup.findViewById(i);
                switch(button.getText().toString()) {
                    case "Hard":
                        ((MainActivity)getContext()).setDifficulty(3);
                        break;
                    case "Medium":
                        ((MainActivity)getContext()).setDifficulty(2);
                        break;
                    case "Easy":
                        ((MainActivity)getContext()).setDifficulty(1);
                        break;
                }
            }
        });

        return view;
    }
}