package com.example.bootlegwarioware;

import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {
    int livesLeft = 4;
    int points = 1;

    // Speed and difficulty settings
    int difficulty = 1;         // Ranges from 1-3: easy, medium, hard
    int speed = 1;              // Ranges from 1-4: slow, medium, fast, very fast

/*
    GameViewModel(int newLivesLeft, int newPoints, int newDifficulty, int newSpeed){
        livesLeft = newLivesLeft;
        points = newPoints;
        difficulty = newDifficulty;
        speed = newSpeed;
    }*/
}