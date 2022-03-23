package com.project.javafxminessweeper;

public class NumberSquare extends AbstractSquare{
    private final int number;

    public NumberSquare(int row, int col, int number){
        super(row,col);
        this.number = number;
    }

    public void revealedSquare(){
        this.setText("" + number);
        //set color of GridPane DropShadow efect and change square inner shadow effect
    }
}
