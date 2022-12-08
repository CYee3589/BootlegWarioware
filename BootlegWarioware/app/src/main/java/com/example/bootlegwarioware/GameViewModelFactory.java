//package com.example.bootlegwarioware;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModelProvider;
//
//
//public class GameViewModelFactory implements ViewModelProvider.Factory {
//    private final int livesLeft;
//    private final int points;
//    private final int difficulty;         // Ranges from 1-3: easy, medium, hard
//    private final int speed;              // Ranges from 1-4: slow, medium, fast, very fast
//
//    public GameViewModelFactory(int livesLeft, int points, int difficulty, int speed) {
//        this.livesLeft = livesLeft;
//        this.points = points;
//        this.difficulty = difficulty;
//        this.speed = speed;
//    }
//
//    @Override
//    public <T extends ViewModel> T create(@NonNull Class <T> modelClass) {
//        if (modelClass == GameViewModel.class) {
//            return (T) new GameViewModel(livesLeft, points, difficulty, speed);
//        }
//        return null;
//    }
//}
