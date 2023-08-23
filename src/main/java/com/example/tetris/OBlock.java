package com.example.tetris;

import java.util.ArrayList;

public class OBlock implements Block {
    private static ArrayList<int[][]> rotations = new ArrayList<>();
    private double centerOffset = 35;

    private double yoff = 0;
    public double getOffsetY(){
        return yoff;
    }
    public double getOffsetX(){
        return centerOffset;
    }



    public OBlock() {

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
