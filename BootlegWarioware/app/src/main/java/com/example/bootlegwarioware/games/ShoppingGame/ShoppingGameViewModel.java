package com.example.bootlegwarioware.games.ShoppingGame;

import androidx.lifecycle.ViewModel;

public class ShoppingGameViewModel extends ViewModel {

    boolean isGameCompleted = false;

    // Timer Progression Bar Variables
    int i = 0;
    int milliSecCounter = 10000;
    int milliSecInterval= 1000;

    // The names of the draggable objects available based on difficulty
    // Difficutly is based on how many draggable object there are available
    String candyOfChoice;
    String[] masterCandyList = {"CANDY_TOP_LEFT", "CANDY_TOP_CENTER", "CANDY_TOP_RIGHT"};
    final String[] additionalMediumDifficultyList = {"CANDY_MID_LEFT", "CANDY_MID_CENTER", "CANDY_MID_RIGHT"};
    final String[] additionalHardDifficultyList = {"CANDY_LOWER_LEFT", "CANDY_LOWER_CENTER", "CANDY_LOWER_RIGHT"};

    // Combine 2 string arrays to form 1. Used to combine the masterCandyList
    String[] stringArrayCombine(String[] array1, String[] array2){
        String[] result = new String[array1.length + array2.length];

        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);

        return result;
    }

}
