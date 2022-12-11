package com.example.bootlegwarioware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {

    private int livesLeft = 4;
    private int score = 1;
    private int difficulty = 1;
    private int speed = 1;
    private ArrayList<Integer> highscores = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setLivesLeft(int newLives){
        this.livesLeft = newLives;
    }

    public void setScore(int newScore){
        this.score = newScore;
    }

    public void setDifficulty(int newDifficulty){
        this.difficulty = newDifficulty;
    }

    public void setHighscores(ArrayList<Integer> newList){this.highscores = newList;}

    public void setSpeed(int newSpeed){
        this.speed = newSpeed;
    }

    public void setBackToNormal(){
        this.livesLeft = 4;
        this.score = 1;
        this.difficulty = 1;
        this.speed = 1;
    }


    public int getLivesLeft(){
        return this.livesLeft;
    }

    public int getScore(){
        return this.score;
    }

    public int getDifficulty(){
        return this.difficulty;
    }

    public int getSpeed(){
        return this.speed;
    }

    public ArrayList<Integer> getHighScores() { return this.highscores; }

    public void loseLife(){
        this.livesLeft -= 1;
    }

    public void incrementScore(){
        this.score += 1;
    }

    public boolean isGameOver(){
        return this.livesLeft == 0;
    }

}