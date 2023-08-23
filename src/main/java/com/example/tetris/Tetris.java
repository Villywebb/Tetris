package com.example.tetris;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import static javafx.scene.input.KeyCode.*;


public class Tetris extends Application {

    public final static int gridNumCols = 10;
    public final static int gridNumRows = 20;
    private static final Ruta[][] grid = new Ruta[30][10];
    private static final int[][] intGrid = new int[30][10];
    private static final int[][] rotGrid = new int[30][10];
    private static final int[][] ghostGrid = new int[30][10];
    private static final int[][] holdIntGrid = new int[4][4];
    private static final Ruta[][] holdGrid = new Ruta[4][4];
    static int[][] nextIntGrid = new int[16][4];
    static Ruta[][] nextGrid = new Ruta[16][4];
    static Group root = new Group();
    static Scene scene = new Scene(root, 750, 750, Color.rgb(35, 35, 35));
    static int tetrisNum = 0;
    static int linestonextlevel = 5;
    static int nextTetrisNum;
    static int totLinesCleared = 0;
    static int rotate = 0;
    static int level = 0;
    static Label scoreLabel;
    static Label levelLabel;
    static int score = 0;
    static Block[] blocks = new Block[]{new LBlock(), new JBlock(), new IBlock(), new OBlock(), new TBlock(), new SBlock(), new ZBlock()};
    static Color[] blockColors = new Color[]{Color.ORANGE, Color.BLUE, Color.CYAN, Color.YELLOW, Color.PURPLE, Color.GREEN, Color.RED};
    static int numBlocks = 1;
    static int frame = 0;
    static Timeline timeline;
    static int oldRotate;
    static Label linesLabel;
    static HashMap<Integer, Integer> sentBlocks = new HashMap<>();
    static HashMap<Integer, Integer> ll = new HashMap<>();
    static double speed = 200;
    static int hcount = 0;
    static Slider volumeSlider = new Slider();
    static Stage stage;
    static int holdBlockNum = -1;
    static MediaPlayer mediaPlayer;
    static Color ghost;
    static Timeline time;
    static int counter = 0;
    static int cycleCount = 160;
    static Label gameOver;
    static int nextTetrisNum2;
    static int nextTetrisNum3;
    static int nextTetrisNum4;
    static int rowFilledCount = 0;
    static Timeline tl;
    static Timeline t;
    static Color col = Color.rgb(255, 255, 70);
    static Button restart;
    static TextArea top5Scores = new TextArea();

    static void starten(boolean newgame) {
        top5Scores.setId("top");
        stage.setResizable(false);
        gameOver = new Label("GAME OVER");
        gameOver.setLayoutX(150);
        gameOver.setLayoutY(100);
        gameOver.setId("gameOver");
        gameOver.setVisible(false);
        Rectangle rect = new Rectangle(200, 20, 350, 700);
        rect.setFill(Color.rgb(25, 25, 25));
        rect.setStrokeWidth(5);
        rect.setStroke(Color.BLACK);
        rect.setViewOrder(1000);
        root.getChildren().add(rect);

        volumeSlider.setValue(30);
        volumeSlider.setPrefHeight(100);
        volumeSlider.setFocusTraversable(false);
        volumeSlider.setLayoutX(585);
        volumeSlider.setLayoutY(650);
        volumeSlider.setMax(100);
        volumeSlider.setMin(0);




        volumeSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            if (volumeSlider.isFocused()) root.requestFocus();

            mediaPlayer.setVolume(volumeSlider.getValue() / 100);
        });
        if (newgame)
            root.getChildren().add(volumeSlider);
        Label scoreL = new Label("SCORE");
        scoreL.setTextFill(Color.GRAY);
        Label levelL = new Label("LEVEL");
        levelL.setTextFill(Color.GRAY);
        Label linesL = new Label("LINES");
        linesL.setTextFill(Color.GRAY);


        scoreL.setLayoutX(30);
        levelL.setLayoutX(30);
        linesL.setLayoutX(30);
        scoreL.setLayoutY(400 - 130);
        levelL.setLayoutY(490 - 130);
        linesL.setLayoutY(580 - 130);
        if (newgame)
            root.getChildren().addAll(scoreL, linesL, levelL);
        songPlayer();
        if (newgame) {
            linesLabel = new Label("0");
            scoreLabel = new Label("0");
            levelLabel = new Label("0");
        }

        linesLabel.setTextFill(Color.GRAY);

        linesLabel.setLayoutX(40);
        linesLabel.setLayoutY(620 - 130);
        if (newgame)
            root.getChildren().add(linesLabel);

        scoreLabel.setTextFill(Color.GRAY);

        scoreLabel.setLayoutX(40);
        scoreLabel.setLayoutY(440 - 130);
        if (newgame)
            root.getChildren().add(scoreLabel);

        levelLabel.setTextFill(Color.GRAY);

        levelLabel.setLayoutX(40);
        levelLabel.setLayoutY(530 - 130);
        scoreLabel.setText("0");
        linesLabel.setText("0");
        levelLabel.setText("0");


        if (newgame)
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
        ll.put(10, 50);
        ll.put(11, 50);
        ll.put(12, 50);
        ll.put(13, 50);
        ll.put(14, 50);
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
                moveDown(1, 0);
                tetrisMoveSound();
                redraw();
                ghostPiece();
            }

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        Random rand = new Random();
        nextTetrisNum = rand.nextInt(3);
        nextTetrisNum2 = rand.nextInt(2) + 3;
        nextTetrisNum3 = rand.nextInt(2) + 5;

        scene.getStylesheets().add(Objects.requireNonNull(Tetris.class.getResource("styles.css")).toExternalForm());
        drawGrid();
        spawnBlock();
        stage.setTitle("Tetris Game");
        stage.setScene(scene);
        stage.show();
        redraw();
        scene.setOnKeyPressed(e -> {

            if (e.getCode() == UP) {

                oldRotate = rotate;
                rotate++;
                if (rotate > 3) {
                    rotate = 0;
                }
                if (!rotateBlock(rotate, oldRotate)) {
                    rotate = oldRotate;
                } else {
                    tetrisRotateSound();
                }


            } else if (e.getCode() == DOWN) {
                moveDown(1, 0);
                tetrisMoveSound();
            } else if (e.getCode() == RIGHT) {
                moveRight();
                tetrisMoveSound();
            } else if (e.getCode() == LEFT) {
                moveLeft();
                tetrisMoveSound();
            } else if (e.getCode() == SPACE) {
                hardDown(1);
            } else if (e.getCode() == C) {

                if (hcount == 0) {
                    holdPiece();
                    hcount++;
                }

            }
            redraw();
            ghostPiece();

        });

    }

    public static void holdPiece() {
        tetrisMenuSound();
        int blo = tetrisNum;
        //puts current piece in holdGrid, spawns next piece
        //if piece alr in hold switch place; holdpiece becomes current, current becomes holdPiece

        //clear hold
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                holdIntGrid[i][j] = 0;
                holdGrid[i][j].setX(0);
                holdGrid[i][j].setY(0);
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
        int u = 1;
        int h = 1;
        while (u < h + toPrint.length) {
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
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (holdIntGrid[i][j] == 1) {
                    holdGrid[i][j].setX(holdGrid[i][j].getX() + blocks[blo].getOffsetX());
                    holdGrid[i][j].setY(holdGrid[i][j].getY() + blocks[blo].getOffsetY());
                }
            }
        }


        //switch, hold full
        if (holdBlockNum != -1) {
            //spawns current block in hold and turns tetromino into holdblock
            spawnBlock(holdBlockNum);
            holdBlockNum = blo;
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

    public static void songPlayer() {
        String musicFile = "src/main/resources/com/example/tetris/TETRIS PHONK.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.35);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        MediaView mediaView = new MediaView(mediaPlayer);

        root.getChildren().add(mediaView);
    }

    public static void tetrisDownSound() {
        AudioClip audioClip = new AudioClip(Paths.get("src/main/resources/com/example/tetris/tetris-gb-27-piece-landed.mp3").toUri().toString());
        audioClip.play(0.4);
    }

    public static void tetrisMoveSound() {
        AudioClip audioClip = new AudioClip(Paths.get("src/main/resources/com/example/tetris/tetris-gb-18-move-piece.mp3").toUri().toString());
        audioClip.play(0.4);
    }

    public static void tetrisRotateSound() {
        AudioClip audioClip = new AudioClip(Paths.get("src/main/resources/com/example/tetris/tetris-gb-19-rotate-piece.mp3").toUri().toString());
        audioClip.play(0.4);
    }

    public static void tetrisMenuSound() {
        AudioClip audioClip = new AudioClip(Paths.get("src/main/resources/com/example/tetris/tetris-gb-17-menu-sound.mp3").toUri().toString());
        audioClip.play();
    }

    public static void tetrisRowSound() {
        AudioClip audioClip = new AudioClip(Paths.get("src/main/resources/com/example/tetris/tetris-gb-21-line-clear.mp3").toUri().toString());
        audioClip.play();
    }

    public static void tetris4LineSound() {
        AudioClip audioClip = new AudioClip(Paths.get("src/main/resources/com/example/tetris/tetris-gb-22-tetris-4-lines.mp3").toUri().toString());
        audioClip.play();
    }

    public static void tetrisGameOverSound() {
        AudioClip audioClip = new AudioClip(Paths.get("src/main/resources/com/example/tetris/tetris-gb-25-game-over.mp3").toUri().toString());
        audioClip.play();
    }

    public static void tetrisLevelUpSound() {
        AudioClip audioClip = new AudioClip(Paths.get("src/main/resources/com/example/tetris/tetris-gb-23-level-up-jingle-v1.mp3").toUri().toString());
        audioClip.play();
    }


    public static void ghostPiece() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                if (ghostGrid[i][j] == 1) {
                    grid[i][j].setColor(Color.TRANSPARENT);
                }
            }
        }

        for (int i = 0; i < 30; i++) {
            System.arraycopy(intGrid[i], 0, ghostGrid[i], 0, 10);
        }

        hardDown(2);

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                if (ghostGrid[i][j] == 1 && intGrid[i][j] != 1 && grid[i][j].getColor() != col) {
                    ghost = Color.web(blockColors[tetrisNum].toString(), 0.2);
                    grid[i][j].setColor(ghost);
                }
            }
        }

    }

    private static void hardDown(int mode) {

        for (int i = 0; i < 30; i++) {
            moveDown(mode, 1);
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

    private static void moveDown(int mode, int hard) {

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
                if (hard == 0) {
                    timer(cycleCount - (level * 12));
                } else if (hard == 1) {
                    timer(10);
                }


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

    public static void timer(int cycleCount) {
        if (counter == 0) {
            tetrisDownSound();
            time = new Timeline(new KeyFrame(Duration.millis(5), (ActionEvent event) -> {
            }));
            time.setCycleCount(cycleCount);
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
            tetrisGameOverSound();
            menu();

            gameOver.setVisible(true);
            root.getChildren().add(gameOver);
            timeline.stop();
        }
    }

    public static void menu() {

        HighScore highScore = new HighScore();

        top5(highScore, true);

        if (highScore.isTop5(score)) {
            Group nameRoot = new Group();
            Stage nameStage = new Stage();
            Scene nameScene = new Scene(nameRoot, 300, 130);
            nameStage.setScene(nameScene);
            nameStage.setTitle("NEW HIGHSCORE!");
            Label highScoreLbl = new Label("NEW HIGHSCORE! You scored: " + score);
            TextField nameInput = new TextField();
            nameInput.setPromptText("Name");
            nameInput.setLayoutY(50);
            Button enterName = new Button("Enter");
            enterName.setLayoutY(100);
            enterName.setOnAction(e -> {
                highScore.addScore(new Score(score, nameInput.getText().toUpperCase()));
                nameStage.hide();
                top5(highScore, false);
                tetrisMenuSound();
            });
            nameInput.setOnKeyPressed(e -> {
                if (e.getCode() == ENTER){
                    highScore.addScore(new Score(score, nameInput.getText().toUpperCase()));
                    nameStage.hide();
                    top5(highScore, false);
                    tetrisMenuSound();
                }
            });


            nameRoot.getChildren().addAll(highScoreLbl, nameInput, enterName);
            nameStage.show();

        } else {
            highScore.addScore(new Score(score, "-----"));
        }

        restart = new Button("Restart");
        restart.setPrefWidth(130);
        restart.setPrefHeight(35);
        restart.setLayoutY(580);
        restart.setLayoutX((750.0/2) - 65);
        root.getChildren().add(restart);
        restart.setOnAction(e -> {
            root.getChildren().remove(top5Scores);
            cleanUp();
            starten(false);
            tetrisMenuSound();
        });
        top5Scores.setOnKeyPressed(e -> {
            if (e.getCode() == ENTER) {
                root.getChildren().remove(top5Scores);
                cleanUp();
                starten(false);
                tetrisMenuSound();
            }
        });
    }

    public static void top5(HighScore highScore, Boolean first) {

        top5Scores.setLayoutX(750.0 / 4);
        top5Scores.setLayoutY(750.0 / 2);
        top5Scores.setPrefHeight(250);
        top5Scores.setPrefWidth(750.0 / 2);
        top5Scores.setEditable(false);
        StringBuilder text = new StringBuilder();
        if (first)
            root.getChildren().add(top5Scores);
        for (Score s : highScore.getTop5()) {
            text.append(s.getScore()).append("-").append(s.getName());
            text.append("\n");
        }
        top5Scores.setText(text.toString());

    }

    public static void cleanUp() {

        score = 0;
        totLinesCleared = 0;
        level = 0;
        cycleCount = 160;
        counter = 0;
        linestonextlevel = 5;
        speed = 200;

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                intGrid[i][j] = 0;
            }
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 4; j++) {
                nextIntGrid[i][j] = 0;
                nextGrid[i][j].setColor(Color.TRANSPARENT);
                nextGrid[i][j].setX(0);
                nextGrid[i][j].setY(0);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                holdIntGrid[i][j] = 0;
                holdGrid[i][j].setColor(Color.TRANSPARENT);
                holdGrid[i][j].setX(0);
                holdGrid[i][j].setY(0);
            }

        }
        redraw();

        mediaPlayer.stop();
        restart.setVisible(false);
        restart.setDisable(true);
        gameOver.setVisible(false);
    }

    public static int calcScore() {
        linestonextlevel -= rowFilledCount;
        if (linestonextlevel <= 0) {
            level++;
            tetrisLevelUpSound();
            speed = speed - 20;
            levelLabel.setText("" + level);
            linestonextlevel = ll.get(level);
        }


        int addScore = 0;
        switch (rowFilledCount) {
            case 1 -> addScore = (level + 1) * 40;
            case 2 -> addScore = (level + 1) * 100;
            case 3 -> addScore = (level + 1) * 300;
            case 4 -> {
                tetris4LineSound();
                addScore = (level + 1) * 1200;
            }
            default -> {
            }
        }

        return addScore;
    }

    static void updateScore() {
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

                nextGrid[i][j].setX(0);
                nextGrid[i][j].setY(0);
                nextIntGrid[i][j] = 0;
                nextGrid[i][j].setColor(Color.TRANSPARENT);

            }
        }
    }

    public static void renderNextBlock(int num) {
        int next = 0;
        int[][] toPrint = new int[0][];
        int y = 0;
        int x = 0;

        int u = 0;
        int h = 0;
        switch (num) {
            case 1 -> {
                toPrint = toPrint(nextTetrisNum, 0);
                next = nextTetrisNum;
            }
            case 2 -> {
                toPrint = toPrint(nextTetrisNum2, 0);
                next = nextTetrisNum2;
                u = 4;
                h = 4;
            }
            case 3 -> {
                toPrint = toPrint(nextTetrisNum3, 0);
                next = nextTetrisNum3;
                u = 8;
                h = 8;
            }
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
                if (nextIntGrid[i][j] == num) {
                    nextGrid[i][j].setColor(blockColors[next]);
                }
            }
        }

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 4; j++) {
                if (nextIntGrid[i][j] == num) {
                    nextGrid[i][j].setX(nextGrid[i][j].getX() + blocks[next].getOffsetX());
                    nextGrid[i][j].setY(nextGrid[i][j].getY() + blocks[next].getOffsetY());
                }
            }


        }
    }

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
        int yeah;
        if(tetrisNum == 3){
            yeah = 4;
        }
        else {
            yeah = 3;
        }
        for (int i = 8; i < 8 + toPrint.length; i++) {
            for (int j = yeah; j < yeah + toPrint[0].length; j++) {
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
        timeline.playFromStart();

    }

    public static int[][] toPrint(int tetrisNum, int rotate) {
        int[][] toPrint = new int[][]{};
        switch (tetrisNum) {
            case 0 -> toPrint = LBlock.getRotations().get(rotate);
            case 1 -> toPrint = JBlock.getRotations().get(rotate);
            case 2 -> toPrint = IBlock.getRotations().get(rotate);
            case 3 -> toPrint = OBlock.getRotations().get(rotate);
            case 4 -> toPrint = TBlock.getRotations().get(rotate);
            case 5 -> toPrint = SBlock.getRotations().get(rotate);
            case 6 -> toPrint = ZBlock.getRotations().get(rotate);
            default -> {
            }
        }
        return toPrint;
    }

    public static int rowFilled() {
        boolean isFilled;

        for (var row = 0; row < 30; row++) {
            isFilled = true;
            for (var col = 0; col < 10; col++) {
                if (intGrid[row][col] == 0) {
                    isFilled = false;
                    break;
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

    public static void checkRow() {
        int row = rowFilled();
        if (row != -1) {

            tl = new Timeline(new KeyFrame(Duration.millis(45), new EventHandler<>() {
                private int i = 4;
                private int j = 5;

                @Override
                public void handle(ActionEvent evnt) {
                    grid[row][i].setColor(col);
                    grid[row][j].setColor(col);
                    i--;
                    j++;
                }
            }));
            tl.setCycleCount(5);
            timeline.stop();
            tetrisRowSound();
            tl.playFromStart();

            tl.setOnFinished(event -> bom(row));
        } else {
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
                if (grid[i][j].getColor() == col) {
                    grid[i][j].setColor(Color.TRANSPARENT);
                }
            }
        }
        timeline.play();
        redraw();
        ghostPiece();
        checkRow();
    }

    static void bom(int row) {
        redraw();
        ghostPiece();
        for (int i = 0; i < 10; i++) {
            intGrid[row][i] = 0;
        }
        t = new Timeline(new KeyFrame(Duration.millis(150), evnt -> {
        }));

        t.setCycleCount(1);
        t.playFromStart();
        t.setOnFinished(event -> com(row));
    }

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
                    case 0 -> {
                    }
                    case 1, 3 -> förskjutningFrånFörstFunnaBlockDeliJLed = -1;
                    case 2 -> förskjutningFrånFörstFunnaBlockDeliILed = -1;
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
                break;
            case 3:
                //cube
                break;
            case 4:
                //tblock
                switch (oldRotate) {
                    case 0, 1, 3 -> förskjutningFrånFörstFunnaBlockDeliJLed = -1;
                    case 2 -> förskjutningFrånFörstFunnaBlockDeliILed = -1;
                }
                break;
            case 5:
                //z1
                switch (oldRotate) {
                    case 0:
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

    //test igen
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
                    isValidRotation = i >= 0 && j >= 0 && i < 30 && j < 10;
                }
                u++;
            }
            u = 0;
            k++;
        }


        if (upperleft[1] >= 0 && upperleft[1] <= 9) {
            if (isValidRotation) {

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
                grid[i][j] = new Ruta(Color.TRANSPARENT, 200 + 35 * j, -330 + 35 * i, 0);
                root.getChildren().add(grid[i][j].getRect());
            }
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 4; j++) {
                nextGrid[i][j] = new Ruta(Color.TRANSPARENT, 585 + 35 * j, 73 + 35 * i, 0);
                root.getChildren().add(nextGrid[i][j].getRect());
            }
        }

        Rectangle nextRect = new Rectangle(577, 55, 156, 11 * 35 + 10);
        nextRect.setFill(Color.rgb(25, 25, 25));
        nextRect.setStroke(Color.BLACK);
        nextRect.setStrokeWidth(5);
        nextRect.setViewOrder(1000);
        root.getChildren().add(nextRect);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                holdGrid[i][j] = new Ruta(Color.TRANSPARENT, 25 + 35 * j, 55 + 35 * i, 0);
                root.getChildren().add(holdGrid[i][j].getRect());
            }
        }

        Rectangle holdRect = new Rectangle(20, 55, 4 * 35 + 10, 4 * 35 + 10);
        holdRect.setFill(Color.rgb(25, 25, 25));
        holdRect.setStroke(Color.BLACK);
        holdRect.setStrokeWidth(5);
        holdRect.setViewOrder(1000);
        root.getChildren().add(holdRect);

    }

    public static void redraw() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                if (i > 9) {
                    if (intGrid[i][j] == 0) {
                        if (grid[i][j].getColor() != col) {
                            grid[i][j].setColor(Color.TRANSPARENT);
                            grid[i][j].setNum(0);
                        }
                    } else if (intGrid[i][j] == 1) {
                        grid[i][j].setColor(blockColors[tetrisNum]);
                        grid[i][j].setNum(1);
                    } else {
                        if (grid[i][j].getColor() != col) {
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

    @Override
    public void start(Stage s) {
        stage = new Stage();
        starten(true);
    }
}
