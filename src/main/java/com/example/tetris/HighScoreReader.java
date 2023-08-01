package com.example.tetris;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HighScoreReader {

    public ArrayList<Score> read() throws IOException {


        ArrayList<Score> scores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println(values[0]);
                scores.add(new Score(Integer.parseInt(values[0]), values[1]));
            }
        }
        return scores;
    }


    public ArrayList<Score> read2() throws FileNotFoundException {
        ArrayList<Score> scores = new ArrayList<>();
        Scanner sc = new Scanner(new File("data.csv"));
        //parsing a CSV file into the constructor of Scanner class
        //sc.useDelimiter(",");
        //setting comma as delimiter pattern
        while (sc.hasNext()) {
            String line = sc.next();
            String[] values = line.split(",");
            System.out.println(values[0]);
            scores.add(new Score(Integer.getInteger(values[0]), values[1]));
        }
        sc.close();
        //closes the scanne
        return scores;
    }
}
