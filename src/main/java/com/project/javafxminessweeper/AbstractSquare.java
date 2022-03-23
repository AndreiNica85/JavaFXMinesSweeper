package com.project.javafxminessweeper;

import javafx.scene.control.Button;

public abstract class AbstractSquare extends Button {
    int rowDigit;
    int colDigit;
    boolean revealedSquare;
    boolean flaggedSquare;

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
        return rowDigit;
    }

    public int getColDigit() {
        return colDigit;
    }

    public void revealedSquareDisplayOnClick(){
        if(isRevealedSquare()){
            revealedSquare();
        }
    }

    protected abstract void revealedSquare();

    public boolean isFlaggedSquare(){
        return this.flaggedSquare;
    }

    public void setFlaggedSquare(){
        this.flaggedSquare = true;
        this.setText("" + (char)(9760));
    }

    public boolean isRevealedSquare(){
        return this.revealedSquare;
    }

    public void setRevealedSquare(boolean visible){
        this.revealedSquare = visible;
    }
}
