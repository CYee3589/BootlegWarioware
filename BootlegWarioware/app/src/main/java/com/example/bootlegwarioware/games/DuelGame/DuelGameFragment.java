package com.example.bootlegwarioware.games.DuelGame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.R;
import com.example.bootlegwarioware.databinding.FragmentDuelGameBinding;
import com.example.bootlegwarioware.games.FlyGame.flyGameFragmentDirections;

import java.util.Random;

public class DuelGameFragment extends Fragment {

    private FragmentDuelGameBinding binding;

    boolean isGameCompleted = false;
    boolean isRightTimeToDuel = false;
    boolean isAlreadyPressed = false;
    boolean isTooEarly = true;
    final long[] reactionTimeDifficutly = {4000, 2500, 1000};
    int difficulty;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDuelGameBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        int speed = DuelGameFragmentArgs.fromBundle(requireArguments()).getSpeed();
        difficulty = DuelGameFragmentArgs.fromBundle(requireArguments()).getDifficulty();
//        difficulty = 3;

        // At the beginning of the game, the player can't duel yet
        binding.microgameDuelBackground.setEnabled(false);

        gameTimeline();

        // Once the player has tapped their screen,
        binding.microgameDuelBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGameCompleted = isRightTimeToDuel;
                isAlreadyPressed = true;
                binding.microgameDuelBackground.setClickable(false);
                binding.duelText.setVisibility(View.INVISIBLE);

                System.out.println("Game Status: " + isGameCompleted);

                // If the click was too early,
                if (isTooEarly){
                    tooEarly();
                } else if (isGameCompleted) {
                    wonDuel();
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // Time line of the entire game.
    public void gameTimeline(){
        // Wait 3 seconds, then end the forewarning
        (new Handler()).postDelayed(this::endWarning, 3000);
    }

    // Erase the forewarning from the duel format, and have the screen clickable
    public void endWarning(){
//        int i = randomLongGenerator(3000, 10000);
//        System.out.println("Reaction TIme: " + i);
        long reactionTime = new Long(randomIntGenerator(3000, 10000));
        System.out.println("Reaction TIme: " + reactionTime);

        binding.duelText.setVisibility(getView().INVISIBLE);
        binding.microgameDuelBackground.setEnabled(true);

        // Wait 3-10 seconds, then start the duel
        (new Handler()).postDelayed(this::startDuel, reactionTime);

    }

    // The duel has started
    public void startDuel(){
        if(!isAlreadyPressed){
            isTooEarly = false;
            binding.duelText.setText(getString(R.string.DUEL));
            binding.duelText.setTextSize(100);
            binding.duelText.setVisibility(getView().VISIBLE);
            isRightTimeToDuel = true;

            // Wait for reaction time to end, then times up.
            (new Handler()).postDelayed(this::timesUp, reactionTimeDifficutly[difficulty - 1]);
        }
    }

    // Times up. Change the background to too late, wait for 3 seconds, and exit game
    public void timesUp(){
        if(!isAlreadyPressed) {
            binding.duelText.setVisibility(getView().INVISIBLE);
            isRightTimeToDuel = false;
            binding.microgameDuelBackground.setBackgroundResource(R.drawable.microgame_duel_lose_late);
            (new Handler()).postDelayed(this::exitGame, 3000);
        }
    }

    public int randomIntGenerator(int lowerBound, int upperBound){
        Random random = new Random();
        return lowerBound + random.nextInt(upperBound - lowerBound);
    }

    // If the click was too early, give them the too early screen and end microgame
    public void tooEarly(){
        binding.microgameDuelBackground.setBackgroundResource(R.drawable.microgame_duel_lose_early);
        (new Handler()).postDelayed(this::exitGame, 3000);
    }

    // If the click was too early, give them the too early screen and end microgame
    public void wonDuel(){
        binding.microgameDuelBackground.setBackgroundResource(R.drawable.microgame_duel_win);
        (new Handler()).postDelayed(this::exitGame, 3000);
    }

    // Exit the game
    public void exitGame(){
        NavDirections action = DuelGameFragmentDirections.actionDuelGameFragmentToGameResultsFragment(isGameCompleted);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}