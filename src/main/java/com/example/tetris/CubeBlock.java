package com.example.tetris;

import java.util.ArrayList;

public class CubeBlock {
    private static ArrayList<int[][]> rotations = new ArrayList<>();

    public CubeBlock() {

        rotations.add(new int[][]{{1, 1, 0},
                {1, 1, 0},
                {0, 0, 0},

        });
        rotations.add(new int[][]{{1, 1, 0},
                {1, 1, 0},
                {0, 0, 0},

        });
        rotations.add(new int[][]{{1, 1, 0},
                {1, 1, 0},
                {0, 0, 0},

        });
        rotations.add(new int[][]{{1, 1, 0},
                {1, 1, 0},
                {0, 0, 0},

        });

    }

    public static ArrayList<int[][]> getRotations() {
        return rotations;
    }

}
