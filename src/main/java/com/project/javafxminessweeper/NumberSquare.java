package com.project.javafxminessweeper;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class NumberSquare extends AbstractSquare{
    private final int number;

    public NumberSquare(int row, int col, int number){
        super(row,col);
        this.number = number;
    }

    @Override
    public void revealedSquare(){
        switch (this.number) {
            case 1 -> this.setTextFill(Color.BLUE);
            case 2 -> this.setTextFill(Color.MEDIUMSEAGREEN);
            case 3 -> this.setTextFill(Color.ORANGERED);
            case 4 -> this.setTextFill(Color.DARKBLUE);
            case 5 -> this.setTextFill(Color.DARKRED);
            case 6 -> this.setTextFill(Color.DARKVIOLET);
            case 7 -> this.setTextFill(Color.DARKORANGE);
            case 8 -> this.setTextFill(Color.DARKSLATEGREY);
        }
        this.setText("" + this.number);
        this.setFont(Font.font("Times New Roman", FontWeight.BOLD,14));
    }

    public int getNumber() {
        return this.number;
    }
}
