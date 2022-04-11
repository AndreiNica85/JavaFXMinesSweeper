package com.project.javafxminessweeper;

import javafx.scene.control.ToggleButton;

public abstract class AbstractSquare extends ToggleButton {
    private int rowDigit;
    private int colDigit;
    private boolean revealedSquare;
    private boolean flaggedSquare;

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
            this.revealedSquare();
        }
    }

    protected abstract void revealedSquare();

    public boolean checkIfFlaggedSquare(){
        return this.flaggedSquare;
    }

    public void setFlaggedSquare(boolean flag){
        this.flaggedSquare = flag;
    }

    /* Reveal square */
    public boolean isRevealedSquare(){
        return this.revealedSquare;
    }

    public void setRevealedSquare(boolean visible){
        this.revealedSquare = visible;
    }
}
