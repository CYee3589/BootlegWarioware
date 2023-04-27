package com.example.bootlegwarioware;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.bootlegwarioware.databinding.FragmentLoadingBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import kotlin.Triple;

public class LoadingFragment extends Fragment {

    private FragmentLoadingBinding binding;

    int i = 0;
    int j;
    int milliSecCounter = 1900;
    int milliSecInterval= 100;

    String[] actions = {"TOUCH", "DRAG"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {

        // Create the list of pairs with
//        List<Pair<String, NavDirections>> gameList = new ArrayList<>();
        List<Triple<String, String, NavDirections>> gameList = new ArrayList<>();

        // Set all the games into this list
        gameList.add(new Triple<>("ORDER", actions[0], LoadingFragmentDirections.actionLoadingFragmentToOrderGameFragment(
                ((MainActivity)getContext()).getDifficulty(), ((MainActivity)getContext()).getSpeed())));
        gameList.add(new Triple<>("SWAT", actions[0], LoadingFragmentDirections.actionLoadingFragmentToFlyGameFragment(
                ((MainActivity)getContext()).getDifficulty(), ((MainActivity)getContext()).getSpeed())));
        gameList.add(new Triple<>("FLIRT", actions[0], LoadingFragmentDirections.actionLoadingFragmentToFlirtGameFragment(
                ((MainActivity)getContext()).getDifficulty(), ((MainActivity)getContext()).getSpeed())));
        gameList.add(new Triple<>("GIVE", actions[1], LoadingFragmentDirections.actionLoadingFragmentToShoppingGameFragment(
                ((MainActivity)getContext()).getDifficulty(), ((MainActivity)getContext()).getSpeed())));
        gameList.add(new Triple<>("NOTHING", actions[0], LoadingFragmentDirections.actionLoadingFragmentToNothingFragment(
                ((MainActivity)getContext()).getDifficulty(), ((MainActivity)getContext()).getSpeed())));
        gameList.add(new Triple<>("BULK UP", actions[0], LoadingFragmentDirections.actionLoadingFragmentToStrengthGameFragment(
                ((MainActivity)getContext()).getDifficulty(), ((MainActivity)getContext()).getSpeed())));

        // Set the boss games into this list
        Triple<String, String, NavDirections> bossGame = new Triple<>("DUEL", actions[0],
                LoadingFragmentDirections.actionLoadingFragmentToDuelGameFragment(
                        ((MainActivity)getContext()).getDifficulty(), ((MainActivity)getContext()).getSpeed()));

        binding = FragmentLoadingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Start animation for background animate
        AnimationDrawable progressAnimation = (AnimationDrawable) binding.getRoot().getBackground();
        progressAnimation.start();

        // Start Music
        final MediaPlayer nextGameMusic = MediaPlayer.create(getActivity(), R.raw.next_game_music);
        nextGameMusic.start();

        // Initialize the beginning looks of the app
        binding.progressbar.setProgress(100);
        updateLifeSymbols(((MainActivity)getContext()).getLivesLeft());
        binding.scoreTextView.setText(String.valueOf(((MainActivity)getContext()).getScore()));

        // Set up random game index and fill in
//        j = getRandomNumber(gameList.size() - 1);
        j = ((MainActivity)getContext()).getCurrentIndex();

        // Show the hint and set the icon
        // Boss level
        if(j == ((MainActivity)getContext()).getGameIndexSize()){
            binding.gameWordTextView.setText(bossGame.getFirst());
            if(Objects.equals(bossGame.getSecond(), actions[0])){
                System.out.println("Boss Touch");
                binding.actionIcon.setBackgroundResource(R.drawable.symbol_touch);
            } else if(Objects.equals(bossGame.getSecond(), actions[1])) {
                System.out.println("Boss Drag");
                binding.actionIcon.setBackgroundResource(R.drawable.symbol_drag);
            }
        // Regular level
        } else {
            binding.gameWordTextView.setText(gameList.get(j).getFirst());
            if(Objects.equals(gameList.get(j).getSecond(), actions[0])){
                System.out.println("Regular touch");
                binding.actionIcon.setBackgroundResource(R.drawable.symbol_touch);
            } else if(Objects.equals(gameList.get(j).getSecond(), actions[1])) {
                System.out.println("Regular drag");
                binding.actionIcon.setBackgroundResource(R.drawable.symbol_drag);
            }
        }

        // Run the game hint animation
        runAnimation();

        // Set-up and start the countdowntimer for the progress bar and timing
        CountDownTimer countDown = new CountDownTimer(milliSecCounter,milliSecInterval) {
            @Override
            public void onTick(long l) {
                i++;
                binding.progressbar.setProgress(100 - (i*100/(milliSecCounter/milliSecInterval)));
            }

            @Override
            public void onFinish() {
                ((MainActivity)getContext()).incrementCurrentIndex();
                binding.progressbar.setProgress(0);
                if(j == ((MainActivity)getContext()).getGameIndexSize()){
                    Navigation.findNavController(view).navigate(bossGame.getThird());
                } else {
                    i++;
                    Navigation.findNavController(view).navigate(gameList.get(j).getThird());
                }
            }
        };

        countDown.start();

        return view;
    }

    // Updates the imageView status of the lives
    private void updateLifeSymbols(int livesLeft){
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
                break;
        }
    }

    // Run game hint animation
    private void runAnimation() {
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.word_anim);
        anim.reset();
        binding.gameWordTextView.clearAnimation();
        binding.gameWordTextView.startAnimation(anim);
    }

    // Get a random number betwee 0-max
    private int getRandomNumber(int max) {
        return ThreadLocalRandom.current().nextInt(0, max+1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}