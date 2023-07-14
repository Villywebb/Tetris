package com.example.tetris;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Ruta {
    public double y;
    public double oldX;
    private Color color;
    private double x;
    private Rectangle rutan;
    private Rectangle rutan2;
    private final Label label;

    public Ruta(Color color, double x, double y, int num) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.rutan = new Rectangle(x + 5, y + 5, 25, 25);
        this.rutan.setStrokeWidth(0);
        this.rutan.setStroke(Color.TRANSPARENT);
        this.rutan.setFill(Color.TRANSPARENT);
        this.label = new Label(" " + num);
        this.label.setFont(Font.font(20));
        this.label.setLayoutX(x);
        this.label.setLayoutY(y);
        this.rutan2 = new Rectangle(x, y, 30, 30);
        this.rutan2.setViewOrder(1);
        this.rutan2.setFill(Color.TRANSPARENT);
        Tetris.root.getChildren().add(this.rutan2);
        oldX = x;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
        this.rutan.setLayoutX(this.x);
        this.rutan2.setLayoutX(this.x);
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
            this.rutan.setStroke(Color.WHITE);
        } else {
            this.rutan.setStroke(Color.TRANSPARENT);
            this.rutan2.setFill(Color.TRANSPARENT);
        }
    }
}
