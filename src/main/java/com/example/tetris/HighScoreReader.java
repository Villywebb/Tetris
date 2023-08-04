package com.example.tetris;

import java.io.*;
import java.util.ArrayList;

public class HighScoreReader {

    public ArrayList<Score> read() throws IOException {


        ArrayList<Score> scores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                scores.add(new Score(Integer.parseInt(values[0]), values[1]));
            }
        }
        return scores;
    }



}
