package com.project.javafxminessweeper;

import javafx.scene.control.ToggleButton;

public abstract class AbstractSquare extends ToggleButton {
    private int rowDigit;
    private int colDigit;
    private boolean revealedSquare;
    static int numberOfRevealedSquares;

    public AbstractSquare(int row, int col){
        this();
        this.rowDigit = row;
        this.colDigit = col;
    }

    public AbstractSquare(){
        this.setMinSize(25,25);
        this.setMaxSize(25,25);
        this.setPrefSize(25,25);
    }

    public int getRowDigit() {
        return this.rowDigit;
    }

    public int getColDigit() {
        return this.colDigit;
    }

    public void revealedAndDiscoveredSquareDisplayOnClick(){
        if(isRevealedSquare()){
            if(!(this instanceof MineSquare)){
                numberOfRevealedSquares++;
            }
            this.revealedSquare();
            this.setSelected(true);
            if(!(this instanceof NumberSquare)){
                this.setDisable(true);
            }
            this.setOpacity(1.0);
        }
    }

    protected abstract void revealedSquare();

    /* Reveal square */
    public boolean isRevealedSquare(){
        return this.revealedSquare;
    }

    public void setRevealedSquare(boolean visible){
        this.revealedSquare = visible;
    }
}
