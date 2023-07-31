package com.example.tetris;

import java.util.Comparator;

public class Score {

    private int score;
    private String name;

    public Score(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return this.score;
    }

    public String getName() {
        return this.name;
    }



}


class ScoreComparator implements Comparator<Score> {

    public int compare(Score s1, Score s2)
    {
        if (s1.getScore() == s2.getScore())
            return 0;
        else if (s1.getScore() > s2.getScore())
            return 1;
        else
            return -1;
    }
}