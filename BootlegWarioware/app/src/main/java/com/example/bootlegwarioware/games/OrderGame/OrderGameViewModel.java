package com.example.bootlegwarioware.games.OrderGame;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Random;

public class OrderGameViewModel extends ViewModel {

    boolean isGameCleared = false;
    boolean areButtonsDisabled = false;
    int i = 0;
    int milliSecCounter = 10000;
    int milliSecInterval= 1000;
    final String[] easyDifficultyList = {"RED", "BLUE", "GREEN", "YELLOW"};
    final String[] mediumDifficultyList = {"SPADE", "CLUB", "DIAMOND", "HEART"};
    final String[] hardDifficultyList = {"RED", "BLUE", "GREEN", "YELLOW", "SPADE", "CLUB", "DIAMOND", "HEART"};

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
//        System.out.println(outputtedArrList);
        return outputtedArrList;
    }

    public String generateComboText(ArrayList<String> combo){
        String rightArrow = Character.toString("-".toCharArray()[0]);
        String outputString = rightArrow;
        for(String x: combo){
            outputString += x + rightArrow;
        }
        return outputString;
//        return outputString += "WINNER";
    }

    public boolean isElementInArray(String x, String[] array){
        for (String element : array) {
            if (element == x) {
                return true;
            }
        }
        return false;
    }

}
