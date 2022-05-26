package com.project.javafxminessweeper;

import javafx.scene.paint.Color;

public class MineSquare extends AbstractSquare{

    public MineSquare(int row, int col){
        super(row,col);
        this.setText(" ");
    }

    @Override
    protected void revealedSquare() {
        this.setText("*");
        this.setTextFill(Color.BLACK);
    }
}
