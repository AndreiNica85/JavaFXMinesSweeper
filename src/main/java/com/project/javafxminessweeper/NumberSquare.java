package com.project.javafxminessweeper;

import javafx.scene.effect.Effect;

public class NumberSquare extends AbstractSquare{
    private final int number;
    private Effect injectedEffectFromDisplay;

    public NumberSquare(int row, int col, int number){
        super(row,col);
        this.number = number;
    }

    @Override
    public void revealedSquare(){
        this.setText("" + this.number);
        //set color of GridPane DropShadow efect and change square inner shadow effect
    }
}
