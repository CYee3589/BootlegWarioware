package com.example.bootlegwarioware;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.example.bootlegwarioware.databinding.FragmentEndingCutsceneBinding;

public class EndingCutsceneFragment extends Fragment {

    private FragmentEndingCutsceneBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEndingCutsceneBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Set up the MediaController for the VideoView
        MediaController mediaController = new MediaController(requireContext());
        mediaController.setAnchorView(binding.cutsceneVideoView);

        // Set the path of the video file to be played
        Uri uri = Uri.parse("android.resource://" + requireActivity().getPackageName() + "/" + R.raw.ending_cutscene);
        binding.cutsceneVideoView.setVideoURI(uri);

        // Start playing the video
        binding.cutsceneVideoView.start();

        // Set up a listener for when the video playback is complete
        binding.cutsceneVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                System.out.println("Video is Done");
                NavDirections action = EndingCutsceneFragmentDirections.actionEndingCutsceneFragmentToGameSelectFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        // Skip Button
        binding.skipButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                binding.cutsceneVideoView.stopPlayback();
                NavDirections action = EndingCutsceneFragmentDirections.actionEndingCutsceneFragmentToGameSelectFragment();
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