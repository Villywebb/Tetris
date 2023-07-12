package com.example.tetris;

import java.util.ArrayList;

public class ZBlock2 {
    private static ArrayList<int[][]> rotations = new ArrayList<int[][]>();

    public ZBlock2() {

        rotations.add(new int[][]{{1, 1, 0},
                {0, 1, 1},
                {0, 0, 0},

        });
        rotations.add(new int[][]{{0, 0, 1},
                                {0, 1, 1},
                              {0, 1, 0},

        });
        rotations.add(new int[][]{{0, 0, 0},
                {1, 1, 0},
                {0, 1, 1},

        });
        rotations.add(new int[][]{{0, 1, 0},
                {1, 1, 0},
                {1, 0, 0},

        });

    }

    public static ArrayList<int[][]> getRotations() {
        return rotations;
    }

}
