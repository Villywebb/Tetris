package com.example.tetris;

import java.util.ArrayList;

public class TBlock implements Block {
    private static ArrayList<int[][]> rotations = new ArrayList<int[][]>();

    private double centerOffset = 17.5;

    public double getOffset(){
        return centerOffset;
    }

    public TBlock() {

        rotations.add(new int[][]{{0, 1, 0},
                {1, 1, 1},
                {0, 0, 0},

        });
        rotations.add(new int[][]{{0, 1, 0},
                {0, 1, 1},
                {0, 1, 0},

        });
        rotations.add(new int[][]{{0, 0, 0},
                {1, 1, 1},
                {0, 1, 0},

        });
        rotations.add(new int[][]{{0, 1, 0},
                {1, 1, 0},
                {0, 1, 0},

        });

    }

    public static ArrayList<int[][]> getRotations() {
        return rotations;
    }

}
