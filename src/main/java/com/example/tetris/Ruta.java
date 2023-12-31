package com.example.tetris;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Ruta {
    private final Label label;
    public double y;
    public double oldX;
    private Color color;
    private double x;
    private Rectangle rutan;
    private Rectangle rutan2;
    private Rectangle border;
    Polygon top = new Polygon();
    Polygon mid1 = new Polygon();
    Polygon mid2 = new Polygon();

    Polygon bot = new Polygon();
    public Ruta(Color color, double x, double y, int num) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.rutan = new Rectangle(x+8.5, y+8.5, 18, 18);
        this.border = new Rectangle(x, y, 35, 35);
        border.setFill(Color.TRANSPARENT);
        border.setViewOrder(0);
        border.setStroke(Color.TRANSPARENT);
        border.setStrokeWidth(4);
        Tetris.root.getChildren().add(border);
        this.rutan.setStrokeWidth(0);
        this.rutan.setStroke(Color.TRANSPARENT);
        this.rutan.setFill(Color.TRANSPARENT);
        this.label = new Label(" " + num);
        this.label.setFont(Font.font(20));
        this.label.setLayoutX(x);
        this.label.setLayoutY(y);
        this.rutan2 = new Rectangle(x, y, 35, 35);
        this.rutan2.setViewOrder(1);
        this.rutan2.setFill(Color.TRANSPARENT);
        //Tetris.root.getChildren().add(this.rutan2);
        oldX = x;



        top.getPoints().addAll(x, y,
                x+35, y,
                x+17.5, y+17.5);

        mid1.getPoints().addAll(x, y,
                x, y+35,
                x+17.5, y+17.5);
        mid2.getPoints().addAll(x+35, y,
                x+35, y+35,
                x+17.5, y+17.5);

        bot.getPoints().addAll(x, y+35,
                x+35, y+35,
                x+17.5, y+17.5);
        top.setFill(color.darker());
        mid1.setFill(color.darker().darker().darker());
        mid2.setFill(color.darker().darker().darker());
        bot.setFill(color.darker().darker().darker().darker());
        top.setViewOrder(1);
        mid1.setViewOrder(1);
        mid2.setViewOrder(1);
        bot.setViewOrder(1);

        Tetris.root.getChildren().addAll(top,mid1,mid2,bot);
    }

    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
        this.rutan.setLayoutX(this.x);
        this.rutan2.setLayoutX(this.x);
        top.setLayoutX(this.x);
        bot.setLayoutX(this.x);
        mid1.setLayoutX(this.x);
        mid2.setLayoutX(this.x);
        this.border.setLayoutX(this.x);
    }
    public void setY(double y) {
        this.y = y;
        this.rutan.setLayoutY(this.y);
        this.rutan2.setLayoutY(this.y);
        top.setLayoutY(this.y);
        bot.setLayoutY(this.y);
        mid1.setLayoutY(this.y);
        mid2.setLayoutY(this.y);
        this.border.setLayoutY(this.y);
    }

    public double getOldX() {
        return this.oldX;
    }

    public Rectangle getRect() {
        return this.rutan;
    }

    public Label getLabel() {
        return this.label;
    }

    public void setNum(int num) {
        this.label.setText(" " + num);
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
        this.rutan.setFill(color);

        if (color != Color.TRANSPARENT) {
            this.rutan2.setFill(color.darker().darker());
            this.border.setStroke(Color.BLACK);
            top.setFill(color.darker());
            mid1.setFill(color.darker().darker());
            mid2.setFill(color.darker().darker());
            bot.setFill(color.darker().darker().darker());
        } else {
            top.setFill(color.darker());
            mid1.setFill(color.darker().darker());
            mid2.setFill(color.darker().darker());
            bot.setFill(color.darker().darker().darker());
            this.border.setStroke(Color.TRANSPARENT);
            this.rutan2.setFill(Color.TRANSPARENT);
        }
    }
}
