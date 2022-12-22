package com.example.bootlegwarioware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

/*
Mobile App Development I -- COMP.4630 Honor Statement
The practice of good ethical behavior is essential for maintaining good order in the classroom,
providing an enriching learning experience for students, and training as a practicing computing
professional upon graduation. This practice is manifested in the University's Academic Integrity
policy. Students are expected to strictly avoid academic dishonesty and adhere to the Academic
Integrity policy as outlined in the course catalog. Violations will be dealt with as outlined
therein. All programming assignments in this class are to be done by the student alone unless
otherwise specified. No outside help is permitted except the instructor and approved tutors.
I certify that the work submitted with this assignment is mine and was generated in a manner
consistent with this document, the course academic policy on the course website on Blackboard,
and the UMass Lowell academic code.
Date: 12/21/2022
Name: Calvin Yee and Tony Choma
*/

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