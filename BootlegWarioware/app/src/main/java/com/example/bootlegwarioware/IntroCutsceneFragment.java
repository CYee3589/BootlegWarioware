package com.example.bootlegwarioware;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.example.bootlegwarioware.databinding.FragmentIntroCutsceneBinding;

public class IntroCutsceneFragment extends Fragment {

    private FragmentIntroCutsceneBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIntroCutsceneBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        final Handler handler = new Handler();

        // Set up the MediaController for the VideoView
        MediaController mediaController = new MediaController(requireContext());
        mediaController.setAnchorView(binding.cutsceneVideoView);

        // Set the path of the video file to be played
        Uri uri = Uri.parse("android.resource://" + requireActivity().getPackageName() + "/" + R.raw.intro_cutscene);
        binding.cutsceneVideoView.setVideoURI(uri);

        // Start playing the video
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.cutsceneVideoView.start();
            }
        }, 1000);

        // Set up a listener for when the video playback is complete
        binding.cutsceneVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                System.out.println("Video is Done");
                NavDirections action = IntroCutsceneFragmentDirections.actionIntroCutsceneFragmentToIntroCountdownFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        // Skip Button
        binding.skipButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                binding.cutsceneVideoView.stopPlayback();
                NavDirections action = IntroCutsceneFragmentDirections.actionIntroCutsceneFragmentToIntroCountdownFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}