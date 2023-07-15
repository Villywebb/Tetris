package com.example.tetris;

import java.util.ArrayList;

public class LBlock1 implements Block {
    private static ArrayList<int[][]> rotations = new ArrayList<int[][]>();
    private double centerOffset = 17.5;
    private double yoff = 0;
    public double getOffsetY(){
        return yoff;
    }
    public double getOffsetX(){
        return centerOffset;
    }


    public LBlock1() {

        rotations.add(new int[][]{{0, 0, 1},
                                    {1, 1, 1},
                {0, 0, 0}
        });
        rotations.add(new int[][]{{0, 1, 0},
                {0, 1, 0},
                {0, 1, 1}
        });
        rotations.add(new int[][]{{0,0, 0},
                {1, 1, 1},
                {1, 0, 0}
        });
        rotations.add(new int[][]{{1, 1, 0},
                {0, 1, 0},
                {0, 1, 0}
        });

    }

    public static ArrayList<int[][]> getRotations(){
        return rotations;
    }

}
