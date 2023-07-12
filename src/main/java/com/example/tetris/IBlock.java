package com.example.tetris;

import java.util.ArrayList;

public class IBlock {
    private static ArrayList<int[][]> rotations = new ArrayList<int[][]>();

    public IBlock() {

        rotations.add(new int[][]{{0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
        rotations.add(new int[][]{{0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0}
        });
        rotations.add(new int[][]{{0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0}
        });
        rotations.add(new int[][]{{0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        });

    }

    public static ArrayList<int[][]> getRotations() {
        return rotations;
    }

}
