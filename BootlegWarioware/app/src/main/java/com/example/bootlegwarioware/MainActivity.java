package com.example.bootlegwarioware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
Mobile App Development II -- COMP.4631 Honor Statement
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

Date: 3/27/2023
Name: Calvin Yee
*/

// Game private varibles
public class MainActivity extends AppCompatActivity {
    private int livesLeft = 4;
    private int score = 1;
    private int difficulty = 1;             // difficutly runs from 1 to 3 (easy to difficuly)
    private int speed = 1;                  // speed runs from 1 to 5 (slowest to fastest)
    private int currentIndex = 0;
    private final int gameIndexSize = 6;    // Size of the "gameIndexes" array
    private Boolean isStoryMode = true;
    private int[] gameIndexes = {0, 1, 2, 3, 4, 5};
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

    public void setCurrentIndex(int newCurrentIndex){
        this.currentIndex = newCurrentIndex;
    }

    public void setIsStoryMode(Boolean newBool){
        this.isStoryMode = newBool;
    }


    public void setBackToNormalStory(){
        this.livesLeft = 4;
        this.score = 1;
        this.speed = 1;
        this.currentIndex = 0;
    }

    public void setBackToNormalEndless(){
        this.livesLeft = 4;
        this.score = 1;
        this.difficulty = 1;
        this.speed = 1;
        this.currentIndex = 0;
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

    public int getSpeed() { return this.speed; }

    public int getCurrentIndex() { return this.currentIndex; }

    public int getGameIndexSize() { return this.gameIndexSize; }

    public Boolean getIsStoryMode() { return this.isStoryMode; }

    public ArrayList<Integer> getHighScores() { return this.highscores; }

    public void loseLife(){
        this.livesLeft -= 1;
    }

    public void incrementScore(){
        this.score += 1;
    }

    public void incrementCurrentIndex(){
        this.currentIndex += 1;
    }

    public void incrementDifficulty(){
        if (!(this.difficulty == 3)){
            this.difficulty += 1;
        }
    }

    public boolean isGameOver(){
        return this.livesLeft == 0;
    }

    public void shuffleGameIndexes() {
        int[] output = Arrays.copyOf(this.gameIndexes, this.gameIndexes.length);
        Collections.shuffle(Arrays.asList(output));
        this.gameIndexes = output;
    }

}