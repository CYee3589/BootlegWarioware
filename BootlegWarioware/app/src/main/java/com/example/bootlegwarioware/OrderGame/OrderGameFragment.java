package com.example.bootlegwarioware.OrderGame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.DemoGameFragmentArgs;
import com.example.bootlegwarioware.databinding.FragmentOrderGameBinding;

import java.util.ArrayList;
import java.util.Random;

public class OrderGameFragment extends Fragment {

    private FragmentOrderGameBinding binding;

    boolean isGameCleared = false;

    final String[] easyDifficultyList = {"RED", "BLUE", "GREEN", "YELLOW"};
    final String[] mediumDifficultyList = {"SPADE", "CLUB", "DIAMOND", "HEART"};
    final String[] hardDifficultyList = {"RED", "BLUE", "GREEN", "YELLOW", "SPADE", "CLUB", "DIAMOND", "HEART"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOrderGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        int difficulty = 2;
//        int difficulty = OrderGameFragmentArgs.fromBundle(requireArguments()).getDifficulty();
//        int speed = OrderGameFragmentArgs.fromBundle(requireArguments()).getSpeed();

        ArrayList<String> answerCombo = getCombination(difficulty);
//        System.out.println(generateComboText(answerCombo));

        binding.combination.setText(generateComboText(answerCombo));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public ArrayList<String> getCombination(int difficulty){
        int comboSize;
        switch (difficulty){
            case 1:
                return generateRandomCombo(easyDifficultyList, 4);
            case 2:
                return generateRandomCombo(mediumDifficultyList, 4);
            case 3:
            default:
                return generateRandomCombo(hardDifficultyList, 5);
        }
    }

    public ArrayList<String> generateRandomCombo(String[] stringArray, int arrayListSize){
        ArrayList<String> outputtedArrList = new ArrayList<String>();
        for (int i = 0; i < arrayListSize; i++){
            outputtedArrList.add(stringArray[new Random().nextInt(stringArray.length)]);
        }
        System.out.println(outputtedArrList);
        return outputtedArrList;
    }

    public String generateComboText(ArrayList<String> combo){
        String rightArrow = Character.toString("\u2192".toCharArray()[0]);
        String outputString = rightArrow;
        for(String x: combo){
            outputString += x + rightArrow;
        }
        return outputString += "WINNER";
    }






}