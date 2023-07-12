package com.example.tetris;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static javafx.scene.input.KeyCode.*;


public class Tetris extends Application {


    private static Ruta[][] grid = new Ruta[30][10];
    private static int[][] intGrid = new int[30][10];
    private static int[][] rotGrid = new int[30][10];
    private static int[][] ghostGrid = new int[30][10];
    private static int[][] holdIntGrid = new int[4][4];
    private static Ruta[][] holdGrid = new Ruta[4][4];
    static int[][] nextIntGrid = new int[16][4];
    static Ruta[][] nextGrid = new Ruta[16][4];
    public final static int gridNumCols = 10;
    public final static int gridNumRows = 20;

    static Group root = new Group();
    Scene scene = new Scene(root, 750, 750);
    int blocksize;
    ArrayList<Integer> nextFour = new ArrayList<>();
    static int tetrisNum = 0;
    static int linestonextlevel = 5;
    static int nextTetrisNum;
    static int totLinesCleared = 0;
    static int rotate = 0;
    static int level = 0;
    static Label scoreLabel;
    static Label levelLabel;
    static int score = 0;
    static Object[] blocks = new Object[]{new LBlock1(), new LBlock2(), new IBlock(), new CubeBlock(), new TBlock(), new ZBlock1(), new ZBlock2()};

    static Color[] blockColors = new Color[]{Color.ORANGE, Color.BLUE, Color.CYAN, Color.YELLOW, Color.PURPLE, Color.GREEN, Color.RED};

    static int numBlocks = 1;
    int frame = 0;
    static Timeline timeline;
    static int oldRotate;
    static Label linesLabel;

    static HashMap<Integer, Integer> sentBlocks = new HashMap<>();
    static HashMap<Integer, Integer> ll = new HashMap<>();
    static double speed = 125;

    static int hcount = 0;
    Slider volumeSlider = new Slider();

    @Override
    public void start(Stage stage) throws IOException {

        volumeSlider.setValue(50);
        volumeSlider.setPrefHeight(100);
        volumeSlider.setFocusTraversable(false);

        volumeSlider.setLayoutX(600);
        volumeSlider.setLayoutY(600);
        volumeSlider.setMax(100);
        volumeSlider.setMin(0);

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
               //change volume

                if (volumeSlider.isFocused()){
                    volumeSlider.setDisable(true);
                }
                else{
                    volumeSlider.setDisable(false);
                }
                mediaPlayer.setVolume(volumeSlider.getValue()/100);
            }
        });

        root.getChildren().add(volumeSlider);
        Label scoreL = new Label("SCORE");
        Label levelL = new Label("LEVEL");
        Label linesL = new Label("LINES");
        scoreL.setFont(Font.font(30));
        levelL.setFont(Font.font(30));
        linesL.setFont(Font.font(30));
        scoreL.setLayoutX(30);
        levelL.setLayoutX(30);
        linesL.setLayoutX(30);
        scoreL.setLayoutY(400);
        levelL.setLayoutY(490);
        linesL.setLayoutY(580);
        root.getChildren().addAll(scoreL, linesL, levelL);

        songPlayer();
        linesLabel = new Label("0");
        linesLabel.setFont(Font.font(30));
        linesLabel.setLayoutX(40);
        linesLabel.setLayoutY(620);
        root.getChildren().add(linesLabel);

        scoreLabel = new Label("0");
        scoreLabel.setFont(Font.font(30));
        scoreLabel.setLayoutX(40);
        scoreLabel.setLayoutY(440);
        root.getChildren().add(scoreLabel);
        levelLabel = new Label("0");
        levelLabel.setFont(Font.font(30));
        levelLabel.setLayoutX(40);
        levelLabel.setLayoutY(530);
        root.getChildren().add(levelLabel);


        ll.put(0, 10);
        ll.put(1, 10);
        ll.put(2, 10);
        ll.put(3, 10);
        ll.put(4, 10);
        ll.put(5, 20);
        ll.put(6, 20);
        ll.put(7, 20);
        ll.put(8, 20);
        ll.put(9, 20);
        ll.put(10, 100);
        ll.put(11, 100);
        ll.put(12, 100);
        ll.put(13, 100);
        ll.put(14, 100);
        ll.put(15, 100);
        ll.put(16, 110);
        ll.put(17, 120);
        ll.put(18, 130);
        ll.put(19, 140);
        ll.put(20, 150);
        ll.put(21, 160);
        ll.put(22, 170);
        ll.put(23, 180);
        ll.put(24, 190);
        ll.put(25, 200);
        ll.put(26, 200);
        ll.put(27, 200);
        ll.put(28, 200);


        timeline = new Timeline(new KeyFrame(Duration.millis(5), (ActionEvent event) -> {
            frame++;
            if (frame % speed == 0) {
                moveDown(1);
                redraw();
                ghostPiece();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        Random rand = new Random();
        nextTetrisNum = rand.nextInt(3);
        nextTetrisNum2 = rand.nextInt(2) + 3;
        nextTetrisNum3 = rand.nextInt(2) + 5;
        drawGrid();
        spawnBlock();
        stage.setTitle("tetris");
        stage.setScene(scene);
        stage.show();
        redraw();
        //200 fps


        scene.setOnKeyPressed(e -> {

            if (e.getCode() == UP) {

                oldRotate = rotate;
                rotate++;
                if (rotate > 3) {
                    rotate = 0;
                }
                if (!rotateBlock(rotate, oldRotate)) {
                    rotate = oldRotate;
                }


            } else if (e.getCode() == DOWN) {
                moveDown(1);
            } else if (e.getCode() == RIGHT) {
                moveRight();
            } else if (e.getCode() == LEFT) {
                moveLeft();
            } else if (e.getCode() == SPACE) {
                hardDown(1);
            } else if (e.getCode() == C) {

                if(hcount == 0) {
                    holdPiece();
                    hcount++;
                }

            }
            redraw();
            ghostPiece();

        });

    }

    int holdBlockNum = -1;

    public void holdPiece() {
        int blo = tetrisNum;
        //puts current piece in holdGrid, spawns next piece
        //if piece alr in hold switch place; holdpiece becomes current, current becomes holdPiece

        //clear hold
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                holdIntGrid[i][j] = 0;
                holdGrid[i][j].setColor(Color.TRANSPARENT);
            }
        }
        //clear intGrid of current piece
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                if (intGrid[i][j] == 1) {
                    intGrid[i][j] = 0;
                    grid[i][j].setColor(Color.TRANSPARENT);
                }
            }
        }

        //print current piece to hold

        int[][] toPrint = toPrint(blo, 0);
        int y = 0;
        int x = 0;
        int u = 0;
        while (u < toPrint.length) {
            for (int j = 0; j < toPrint[0].length; j++) {
                if (toPrint[y][x] == 1) {
                    holdIntGrid[u][j] = 1;
                }
                x++;
            }
            x = 0;
            u++;
            y++;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (holdIntGrid[i][j] == 1) {
                    holdGrid[i][j].setColor(blockColors[blo]);
                }
            }
        }

        //switch, hold full
        if (holdBlockNum != -1) {
            //spawns current block in hold and turns tetromino into holdblock
            spawnBlock(holdBlockNum);
            holdBlockNum = blo;
            System.out.println(holdBlockNum);

        }
        //hold is empty
        else {
            //spawn next piece
            //sets holdblocknum to current block in hold
            holdBlockNum = blo;
            spawnBlock();
        }
    }

    static void spawnBlock() {
        spawnBlock(-1);
    }
    MediaPlayer mediaPlayer;
    public void songPlayer() {
        String musicFile = "src/main/resources/com/example/tetris/Tetris.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
    }

    static Color ghost;

    public static void ghostPiece() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                if (ghostGrid[i][j] == 1) {
                    grid[i][j].setColor(Color.TRANSPARENT);
                }
            }
        }

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                ghostGrid[i][j] = intGrid[i][j];
            }
        }

        hardDown(2);

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                if (ghostGrid[i][j] == 1 && intGrid[i][j] != 1 && grid[i][j].getColor() != Color.SADDLEBROWN) {
                    ghost = Color.web(blockColors[tetrisNum].toString(), 0.2);
                    grid[i][j].setColor(ghost);
                }
            }
        }

    }

    private static void hardDown(int mode) {

        for (int i = 0; i < 30; i++) {
            moveDown(mode);
        }
    }

    private static void moveLeft() {
        boolean collideLeft = false;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                if (intGrid[i][j] == 1) {
                    if (j - 1 >= 0) {
                    } else {
                        collideLeft = true;
                    }
                }
            }
        }
        for (int i = 0; i < 30; i++) {
            for (int j = 1; j < 10; j++) {
                if (intGrid[i][j] == 1) {
                    if (intGrid[i][j - 1] != 0 && intGrid[i][j - 1] != 1) {
                        collideLeft = true;
                    }
                }
            }
        }
        if (!collideLeft) {
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 10; j++) {
                    if (intGrid[i][j] == 1 && intGrid[i][j - 1] == 0) {
                        intGrid[i][j] = 0;
                        intGrid[i][j - 1] = 1;
                    }
                }
            }
        }
    }

    private static void moveRight() {
        boolean collideLeft = false;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                if (intGrid[i][j] == 1) {
                    if (j + 1 == 10) {
                        collideLeft = true;
                    }
                }
            }
        }
        for (int i = 0; i < 30; i++) {
            for (int j = 8; j >= 0; j--) {
                if (intGrid[i][j] == 1) {
                    if (intGrid[i][j + 1] != 0 && intGrid[i][j + 1] != 1) {
                        collideLeft = true;
                    }
                }
            }
        }
        if (!collideLeft) {
            for (int i = 0; i < 30; i++) {
                for (int j = 9; j >= 0; j--) {
                    if (intGrid[i][j] == 1 && intGrid[i][j + 1] == 0) {
                        intGrid[i][j] = 0;
                        intGrid[i][j + 1] = 1;
                    }
                }
            }
        }
    }


    private static void moveDown(int mode) {

        if (mode == 1) {
            if (!checkDown(1)) {
                for (int i = 29; i >= 0; i--) {
                    for (int j = 0; j < 10; j++) {
                        if (intGrid[i][j] == 1) {

                            if (intGrid[i][j] == 1 && intGrid[i + 1][j] == 0) {
                                intGrid[i][j] = 0;
                                intGrid[i + 1][j] = 1;
                            }

                        }
                    }
                }
            }
            if (checkDown(1)) {
                timeline.stop();
                timer();
            }
        } else {
            if (!checkDown(2)) {
                for (int i = 28; i >= 0; i--) {
                    for (int j = 0; j < 10; j++) {
                        if (ghostGrid[i][j] == 1 && ghostGrid[i + 1][j] == 0) {
                            ghostGrid[i][j] = 0;
                            ghostGrid[i + 1][j] = 1;
                        }
                    }
                }
            }
            if (checkDown(2)) {
                redraw();
            }

        }
    }

    static Timeline time;
    static int counter = 0;

    public static void timer() {
        if (counter == 0) {

            time = new Timeline(new KeyFrame(Duration.millis(5), (ActionEvent event) -> {
            }));
            time.setCycleCount(100);
            time.play();
        }

        counter++;
        time.setOnFinished(actionEvent -> handleDown());
    }


    public static void handleDown() {
        if (!gameOver()) {
            hardDown(1);
            checkRow();


            counter = 0;

            numBlocks++;
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 10; j++) {
                    if (intGrid[i][j] == 1) {
                        intGrid[i][j] = numBlocks;
                    }
                }
            }
            spawnBlock();
        } else {
            //game over
            Label gameOver = new Label("GAME OVER");
            gameOver.setLayoutX(50);
            gameOver.setLayoutY(300);
            gameOver.setFont(Font.font("Arial Black", 50));
            gameOver.setTextFill(Color.RED);
            root.getChildren().add(gameOver);
            timeline.stop();
        }
    }
//TODO:ve

    public static int calcScore() {
        System.out.println(rowFilledCount);
        linestonextlevel -= rowFilledCount;
        if (linestonextlevel <= 0) {
            level++;
            speed = speed - 10;


            levelLabel.setText("" + level);
            linestonextlevel = ll.get(level);
        }


        int addScore = 0;
        switch (rowFilledCount) {
            case 0:
                break;
            case 1:
                addScore = (level + 1) * 40;
                break;
            case 2:
                addScore = (level + 1) * 100;
                break;
            case 3:
                addScore = (level + 1) * 300;
                break;
            case 4:
                addScore = (level + 1) * 1200;
                break;
            default:
                break;
        }
        return addScore;
    }
    static void updateScore(){
        score += calcScore();
        scoreLabel.setText("" + score);
        rowFilledCount = 0;
    }

    public static boolean gameOver() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                if (intGrid[i][j] != 0 && intGrid[i][j] != 1 && i <= 10) {
                    return true;
                }
            }
        }
        return false;
    }
    static void clearNextBlockRender() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 4; j++) {
                nextIntGrid[i][j] = 0;
                nextGrid[i][j].setColor(Color.TRANSPARENT);
            }
        }
    }
    //i love books
    public static void renderNextBlock(int num) {
        int[][] toPrint = new int[0][];
        int y = 0;
        int x = 0;

        int u = 0;
        int h = 0;
        switch (num) {
            case 1:
                toPrint = toPrint(nextTetrisNum, 0);
                break;
            case 2:
                toPrint = toPrint(nextTetrisNum2, 0);
                u = 4;
                h = 4;
                break;
            case 3:
                toPrint = toPrint(nextTetrisNum3, 0);
                u = 8;
                h = 8;
                break;
        }

        while (u < h + toPrint.length) {
            for (int j = 0; j < toPrint[0].length; j++) {
                if (toPrint[y][x] == 1) {
                    nextIntGrid[u][j] = num;
                }
                x++;
            }
            x = 0;
            u++;
            y++;
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 4; j++) {
                if (nextIntGrid[i][j] == 1) {
                    nextGrid[i][j].setColor(blockColors[nextTetrisNum]);
                }
                if (nextIntGrid[i][j] == 2) {
                    nextGrid[i][j].setColor(blockColors[nextTetrisNum2]);
                }
                if (nextIntGrid[i][j] == 3) {
                    nextGrid[i][j].setColor(blockColors[nextTetrisNum3]);
                }
            }
        }

    }


    static int nextTetrisNum2;
    static int nextTetrisNum3;
    static int nextTetrisNum4;

    public static void spawnBlock(int spawnNum) {
        hcount = 0;

        rotate = 0;

        if (spawnNum == -1) {
            Random rand = new Random();
            tetrisNum = nextTetrisNum;
            nextTetrisNum = nextTetrisNum2;
            nextTetrisNum2 = nextTetrisNum3;
            nextTetrisNum3 = nextTetrisNum4;
            nextTetrisNum4 = rand.nextInt(7);
            int hum = 0;
            while (nextTetrisNum4 == nextTetrisNum3 || nextTetrisNum4 == nextTetrisNum2 || nextTetrisNum4 == nextTetrisNum && hum < 3) {
                nextTetrisNum4 = rand.nextInt(7);
                hum++;
            }
            clearNextBlockRender();
            renderNextBlock(1);
            renderNextBlock(2);
            renderNextBlock(3);
        } else {
            tetrisNum = spawnNum;
        }
        sentBlocks.put(numBlocks + 1, tetrisNum);
        int[][] toPrint = toPrint(tetrisNum, 0);

        int y = 0;
        int x = 0;
        for (int i = 8; i < 8 + toPrint.length; i++) {
            for (int j = 3; j < 3 + toPrint[0].length; j++) {
                if (toPrint[y][x] == 1) {
                    intGrid[i][j] = 1;
                }
                x++;
            }
            x = 0;
            y++;
        }

        redraw();
        ghostPiece();
        timeline.play();

    }

    public static int[][] toPrint(int tetrisNum, int rotate) {
        int[][] toPrint = new int[][]{};
        switch (tetrisNum) {
            case 0:
                toPrint = LBlock1.getRotations().get(rotate);
                break;
            case 1:
                toPrint = LBlock2.getRotations().get(rotate);
                break;
            case 2:
                toPrint = IBlock.getRotations().get(rotate);
                break;
            case 3:
                toPrint = CubeBlock.getRotations().get(rotate);
                break;
            case 4:
                toPrint = TBlock.getRotations().get(rotate);
                break;
            case 5:
                toPrint = ZBlock1.getRotations().get(rotate);
                break;
            case 6:
                toPrint = ZBlock2.getRotations().get(rotate);
                break;

            default:
                System.out.println("wlak");
        }
        return toPrint;
    }

    //TODO: 1231123
    //returns i coord of filled row -1 if no row filled
    static int rowFilledCount = 0;

    public static int rowFilled() {
        boolean isFilled;

        for (var row = 0; row < 30; row++) {
            isFilled = true;
            for (var col = 0; col < 10; col++) {
                if (intGrid[row][col] == 0) {
                    isFilled = false;
                }
            }
            //if isFilled is still true then current row is filled
            if (isFilled) {
                totLinesCleared++;
                rowFilledCount++;
                linesLabel.setText("" + totLinesCleared);
                return row;
            }

        }
        return -1;
    }

    static Timeline tl;
    static Timeline t;

    public static void checkRow() {
        int row = rowFilled();
        if (row != -1) {

            tl = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
                private int i = 0;

                @Override
                public void handle(ActionEvent evnt) {
                    grid[row][i].setColor(Color.SADDLEBROWN);
                    i++;
                }
            }));
            tl.setCycleCount(10);
            tl.playFromStart();

            tl.setOnFinished(event -> bom(row));
        }
        else{
            updateScore();
        }
    }

    public static void com(int row) {

        for (int u = row - 1; u >= 0; u--) {
            for (int y = 0; y < 10; y++) {
                if (intGrid[u][y] != 0) {
                    intGrid[u + 1][y] = intGrid[u][y];
                    intGrid[u][y] = 0;

                }
            }
        }
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                if (grid[i][j].getColor() == Color.SADDLEBROWN) {
                    grid[i][j].setColor(Color.TRANSPARENT);
                }
            }
        }
        redraw();
        checkRow();
    }

    static void bom(int row){
        for (int i = 0; i < 10; i++) {
            intGrid[row][i] = 0;
        }
        t = new Timeline(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent evnt) {
            }
        }));
        t.setCycleCount(1);
        t.playFromStart();
        t.setOnFinished(event ->com(row) );
    }


    //TODO:måste byggas om för olika block
    public static int[] upperLeft(int i, int j, int oldRotate) {
        int förskjutningFrånFörstFunnaBlockDeliJLed = 0;
        int förskjutningFrånFörstFunnaBlockDeliILed = 0;
        switch (tetrisNum) {
            case 0:
                //l1
                switch (oldRotate) {
                    case 0:
                        förskjutningFrånFörstFunnaBlockDeliJLed = -2;
                        break;
                    case 1:
                        förskjutningFrånFörstFunnaBlockDeliJLed = -1;
                        break;
                    case 2:
                        förskjutningFrånFörstFunnaBlockDeliILed = -1;
                        break;
                    case 3:
                        break;

                }
                break;
            case 1:
                //l2
                switch (oldRotate) {
                    case 0 -> förskjutningFrånFörstFunnaBlockDeliILed = 0;
                    case 1 -> förskjutningFrånFörstFunnaBlockDeliJLed = -1;
                    case 2 -> förskjutningFrånFörstFunnaBlockDeliILed = -1;
                    case 3 -> förskjutningFrånFörstFunnaBlockDeliJLed = -1;
                }
                break;
            case 2:
                //Iblock
                switch (oldRotate) {
                    case 0 -> förskjutningFrånFörstFunnaBlockDeliILed = -1;
                    case 1 -> förskjutningFrånFörstFunnaBlockDeliJLed = -2;
                    case 2 -> förskjutningFrånFörstFunnaBlockDeliILed = -2;
                    case 3 -> förskjutningFrånFörstFunnaBlockDeliJLed = -1;
                }
                ;
                break;
            case 3:
                //cube
                break;
            case 4:
                //tblock
                switch (oldRotate) {
                    case 0 -> förskjutningFrånFörstFunnaBlockDeliJLed = -1;
                    case 1 -> förskjutningFrånFörstFunnaBlockDeliJLed = -1;
                    case 2 -> förskjutningFrånFörstFunnaBlockDeliILed = -1;
                    case 3 -> förskjutningFrånFörstFunnaBlockDeliJLed = -1;
                }
                break;
            case 5:
                //z1
                switch (oldRotate) {
                    case 0:
                        förskjutningFrånFörstFunnaBlockDeliJLed = -1;
                        break;
                    case 1:
                        förskjutningFrånFörstFunnaBlockDeliJLed = -1;
                        break;
                    case 2:
                        förskjutningFrånFörstFunnaBlockDeliILed = -1;
                        förskjutningFrånFörstFunnaBlockDeliJLed = -1;
                        break;
                    case 3:
                        break;

                }
                break;
            case 6:
                //z2
                switch (oldRotate) {
                    case 0:
                        break;
                    case 1:
                        förskjutningFrånFörstFunnaBlockDeliJLed = -2;
                        break;
                    case 2:
                        förskjutningFrånFörstFunnaBlockDeliILed = -1;
                        break;
                    case 3:
                        förskjutningFrånFörstFunnaBlockDeliJLed = -1;
                        break;

                }
                break;

        }
        return new int[]{i + förskjutningFrånFörstFunnaBlockDeliILed, j + förskjutningFrånFörstFunnaBlockDeliJLed};

    }

    private static boolean checkDown(int num) {
        if (num == 1) {
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 10; j++) {
                    if (intGrid[i][j] == 1) {
                        if (i + 1 == 30 || intGrid[i + 1][j] != 0 && intGrid[i + 1][j] != 1) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                if (ghostGrid[i][j] == 1) {
                    if (i + 1 == 30 || ghostGrid[i + 1][j] != 0 && ghostGrid[i + 1][j] != 1) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    //TODO: INVALIDATE ROTATION INTO OTHER BLOCS
    private static boolean rotateBlock(int rotate, int oldRotate) {


        int[] upperleft = new int[2];
        int counter = 0;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                if (intGrid[i][j] == 1) {
                    if (counter == 0) {
                        upperleft = upperLeft(i, j, oldRotate);
                    }
                    counter++;
                }

            }
        }
        int[][] toPrint = toPrint(tetrisNum, rotate);
        int k = 0;
        int u = 0;
        boolean isValidRotation = true;
        for (int i = upperleft[0]; i < upperleft[0] + toPrint.length; i++) {
            for (int j = upperleft[1]; j < upperleft[1] + toPrint[0].length; j++) {
                if (toPrint[k][u] == 1) {
                    if (i >= 0 && j >= 0 && i < 30 && j < 10) {
                        isValidRotation = true;
                    } else {
                        isValidRotation = false;
                    }
                }
                u++;
            }
            u = 0;
            k++;
        }


        //bsjdbsjdb
        //sbdjsdj
        if (upperleft[1] >= 0 && upperleft[1] <= 9) {
            if (isValidRotation) {

                //TODO: count non 0 and non 1 ,make 2nd grid, perform rotate on second grid, count non 0 and non 1 if same rotation = true

                // copy intgrid to rotgrid and count non 0 non 1
                int blockcount = 0;
                for (int i = 0; i < 30; i++) {
                    for (int j = 0; j < 10; j++) {
                        rotGrid[i][j] = intGrid[i][j];
                        if (intGrid[i][j] != 0 && intGrid[i][j] != 1) {
                            blockcount++;
                        }
                    }
                }


                //perform rotation on rotGrid
                for (int i = 0; i < 30; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (rotGrid[i][j] == 1) {
                            rotGrid[i][j] = 0;
                        }
                    }
                }
                int y = 0;
                int x = 0;
                for (int i = upperleft[0]; i < upperleft[0] + toPrint.length; i++) {
                    for (int j = upperleft[1]; j < upperleft[1] + toPrint[0].length; j++) {
                        if (toPrint[y][x] == 1) {
                            rotGrid[i][j] = 1;
                        }
                        x++;
                    }
                    x = 0;
                    y++;
                }

                //count non 0 non 1 after rot
                int blockCount2 = 0;
                for (int i = 0; i < 30; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (rotGrid[i][j] != 0 && rotGrid[i][j] != 1) {
                            blockCount2++;
                        }
                    }
                }


                //if same continue rotation on intgrid

                if (blockCount2 == blockcount) {
                    for (int i = 0; i < 30; i++) {
                        for (int j = 0; j < 10; j++) {
                            if (intGrid[i][j] == 1) {
                                intGrid[i][j] = 0;
                            }
                        }
                    }
                    int q = 0;
                    int w = 0;
                    for (int i = upperleft[0]; i < upperleft[0] + toPrint.length; i++) {
                        for (int j = upperleft[1]; j < upperleft[1] + toPrint[0].length; j++) {
                            if (toPrint[q][w] == 1) {
                                intGrid[i][j] = 1;
                            }
                            w++;
                        }
                        w = 0;
                        q++;
                    }
                } else {
                    isValidRotation = false;
                }
            }
        }

        return isValidRotation;


    }


    public static void drawGrid() {
        for (int i = 0; i < gridNumCols + 1; i++) {
            Line line = new Line();
            line.setStrokeWidth(3);
            line.setStartX(200 + 35 * i);
            line.setStartY(20);
            line.setEndX(200 + 35 * i);
            line.setEndY(720);
            root.getChildren().add(line);
        }
        for (int i = 0; i < gridNumRows + 1; i++) {
            Line line = new Line();
            line.setStrokeWidth(3);
            line.setStartY(20 + 35 * i);
            line.setStartX(200);
            line.setEndY(20 + 35 * i);
            line.setEndX(550);
            root.getChildren().add(line);
        }

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                Color färg = Color.TRANSPARENT;
                grid[i][j] = new Ruta(färg, 200 + 35 * j, -330 + 35 * i, 0);
                root.getChildren().add(grid[i][j].getRect());
                // root.getChildren().add(grid[i][j].getLabel());
            }
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 4; j++) {
                nextGrid[i][j] = new Ruta(Color.TRANSPARENT, 600 + 35 * j, 50 + 35 * i, 0);
                root.getChildren().add(nextGrid[i][j].getRect());
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                holdGrid[i][j] = new Ruta(Color.TRANSPARENT, 40 + 35 * j, 50 + 35 * i, 0);
                root.getChildren().add(holdGrid[i][j].getRect());
            }
        }
    }

    public static void redraw() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                if (i > 9) {
                    if (intGrid[i][j] == 0) {
                        if (grid[i][j].getColor() != Color.SADDLEBROWN) {
                            grid[i][j].setColor(Color.TRANSPARENT);
                            grid[i][j].setNum(0);
                        }
                    } else if (intGrid[i][j] == 1) {
                        grid[i][j].setColor(blockColors[tetrisNum]);
                        grid[i][j].setNum(1);
                    } else {
                        if (grid[i][j].getColor() != Color.SADDLEBROWN) {
                            grid[i][j].setColor(blockColors[sentBlocks.get(intGrid[i][j])]);
                            grid[i][j].setNum(intGrid[i][j]);
                        }
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        launch();
    }
}
