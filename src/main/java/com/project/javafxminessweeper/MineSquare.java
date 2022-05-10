package com.project.javafxminessweeper;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MineSquare extends AbstractSquare{

    public MineSquare(int row, int col){
        super(row,col);
        this.setText(" ");
    }

    @Override
    protected void revealedSquare() {
        this.setFont(Font.font("Times New Roman", FontWeight.BOLD,14));
        this.setText("*");
        this.setTextFill(Color.BLACK);
    }
}
