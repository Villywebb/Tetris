package com.example.tetris;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

public class HighScoreWriter {


    public void write(ArrayList<Score> scores) throws IOException {
        File file = new File("data.csv");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        final String formatTemplate = "{0},{1}";
        for (Score s : scores) {
            String lineString = MessageFormat.format(formatTemplate,s.getScore(),s.getName());
            bw.write(lineString);
            bw.newLine();
        }
        bw.close();
        fw.close();

    }
}

