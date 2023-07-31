package com.example.tetris;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HighScore {
    private ArrayList<Score> scores;

    public HighScore() {
        HighScoreReader reader = new HighScoreReader();
        try {
            scores = reader.read();
        } catch (IOException e) {
            System.out.println("?");
        }
        if (scores == null){
            scores = new ArrayList<>();
        }
    }

    public void addScore(Score score) {
        scores.add(score);
        HighScoreWriter writer = new HighScoreWriter();
        try {
            writer.write(scores);
        } catch (IOException e) {
            System.out.println("?");
        }
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public ArrayList<Score> getTop5() {
        ArrayList<Score> top5 = new ArrayList<>();
        Collections.sort(scores, new ScoreComparator());
        if (scores.size() >= 5) {
            for (int i = scores.size(); i > scores.size() - 5; i--) {
                top5.add(scores.get(i));
            }
            return top5;
        } else {
            return scores;
        }
    }

    public Boolean isTop5(int score) {
        int count = 0;
        for (Score s : scores) {
            if (s.getScore() > score) {
                count++;
            }
        }
        return count < 5;
    }
}
