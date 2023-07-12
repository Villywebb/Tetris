package com.example.tetris;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Ruta {
    private Color color;
    private double x;
    private double y;
    private Rectangle rutan;
    private Label label;

    public Ruta(Color color, double x, double y, int num) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.rutan = new Rectangle(x, y, 35,35);
        this.rutan.setStrokeWidth(5);
        this.rutan.setStroke(Color.TRANSPARENT);
        this.label = new Label(" "+num);
        this.label.setFont(Font.font(20));
        label.setLayoutX(x);
        label.setLayoutY(y);
        this.rutan.setFill(color);
    }

    public Rectangle getRect() {
        return this.rutan;
    }
    public Label getLabel() {
        return this.label;
    }

    public void setColor(Color color){
        this.color = color;
        this.rutan.setFill(color);
        if(color != Color.TRANSPARENT){
            this.rutan.setStroke(Color.BLACK);
        }
        else {
            this.rutan.setStroke(Color.TRANSPARENT);
        }
    }
    public void setNum(int num){
        this.label.setText(" "+num);
    }
    public Color getColor(){
        return this.color;
    }
}
