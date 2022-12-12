package com.example.bootlegwarioware;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.bootlegwarioware.databinding.FragmentGameResultsBinding;
import com.example.bootlegwarioware.databinding.FragmentLoadingBinding;


public class GameResultsFragment extends Fragment {

    private FragmentGameResultsBinding binding;

    int i = 0;
    int milliSecCounter = 2100;
    int milliSecInterval= 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGameResultsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        boolean isGameCompleted = GameResultsFragmentArgs.fromBundle(requireArguments()).getIsGameCompleted();
        final MediaPlayer gameResultMusic;

        // If Game wasn't completed, lose a life and set background.
        if (!isGameCompleted){
            ((MainActivity)getContext()).loseLife();
            binding.getRoot().setBackgroundResource(R.drawable.bootleg_wario_failed_background);
            gameResultMusic =  MediaPlayer.create(getActivity(), R.raw.lose_music);
        } else {
            binding.getRoot().setBackgroundResource(R.drawable.bootleg_wario_win_background);
            gameResultMusic =  MediaPlayer.create(getActivity(), R.raw.win_music);
        }
        gameResultMusic.start();

        // Make animated background animate
        AnimationDrawable progressAnimation = (AnimationDrawable) binding.getRoot().getBackground();
        progressAnimation.start();

        binding.scoreTextView.setText(String.valueOf(((MainActivity)getContext()).getScore()));
        updateLifeSymbols(((MainActivity)getContext()).getLivesLeft());

        binding.progressbar.setProgress(100);
        CountDownTimer countDown = new CountDownTimer(milliSecCounter,milliSecInterval) {
            @Override
            public void onTick(long l) {
                i++;
                binding.progressbar.setProgress(100 - (i*100/(milliSecCounter/milliSecInterval)));
            }

            @Override
            public void onFinish() {
                i++;
                binding.progressbar.setProgress(0);

                // Check if Game Over, otherwise continue with the game
                if (((MainActivity)getContext()).isGameOver()){
                    binding.gameOverTextView.setText(R.string.game_over);
                    runGameOverAnimation();
                    final MediaPlayer gameOverMusic =  MediaPlayer.create(getActivity(), R.raw.game_over_music);
                    gameOverMusic.start();
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            NavDirections action = GameResultsFragmentDirections.actionGameResultsFragmentToGameOverFragment(((MainActivity)getContext()).getScore());
                            Navigation.findNavController(view).navigate(action);
                        }
                    }, 4000);

                } else {
                    ((MainActivity)getContext()).incrementScore();
                    NavDirections action = GameResultsFragmentDirections.actionGameResultsFragmentToLoadingFragment();
                    Navigation.findNavController(view).navigate(action);
                }
            }
        };

        countDown.start();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateLifeSymbols(int livesLeft){
        switch(livesLeft) {
            case 0:
                binding.life1ImageView.setVisibility(View.INVISIBLE);
            case 1:
                binding.life2ImageView.setVisibility(View.INVISIBLE);
            case 2:
                binding.life3ImageView.setVisibility(View.INVISIBLE);
            case 3:
                binding.life4ImageView.setVisibility(View.INVISIBLE);
                break;
            default:
                System.out.println("ERROR here");
                break;
        }
    }

    private void runGameOverAnimation() {
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.game_over_anim);
        anim.reset();
        binding.gameOverTextView.clearAnimation();
        binding.gameOverTextView.startAnimation(anim);
    }
}