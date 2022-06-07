package com.project.javafxminessweeper;

import javafx.scene.paint.Color;

/** Class for Mine state of an AbstractSquare */

public class MineSquare extends AbstractSquare{

    public MineSquare(int row, int col){
        super(row,col);
        this.setText(" ");
    }

    @Override
    protected void revealSquare() {
        this.setText("*");
        this.setTextFill(Color.BLACK);
    }
}
