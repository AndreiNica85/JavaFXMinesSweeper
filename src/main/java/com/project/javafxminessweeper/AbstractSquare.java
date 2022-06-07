package com.project.javafxminessweeper;

import javafx.scene.control.ToggleButton;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/** Abstract Class for the 4 different states of Display squares */

public abstract class AbstractSquare extends ToggleButton {

    private int rowDigit;
    private int colDigit;
    private boolean revealedSquare;

    /* Keep track of squares revealed to be able to calculate the percentage of map revealed squares */
    protected static int numberOfRevealedSquares;

    public AbstractSquare(int row, int col){
        this();
        this.rowDigit = row;
        this.colDigit = col;
    }

    public AbstractSquare(){
        this.setMinSize(25,25);
        this.setMaxSize(25,25);
        this.setFont(Font.font("Times New Roman", FontWeight.NORMAL,10));
    }

    /* Method for setting square when reveal (the state of) an AbstractSquare */
    public void revealSelectedSquareOnClick(){
        if(isRevealedSquare()){
            if(!(this instanceof MineSquare)){
                numberOfRevealedSquares++;
            }
            this.revealSquare();
            this.setSelected(true);

            /* If is NumberSquare then don't disable the square, so we can click for the other NumberSquare options */
            if(!(this instanceof NumberSquare)){
                this.setDisable(true);
            }
            this.setOpacity(1.0);
        }
    }

    /* Reveal - Get and set the text for every class that implements AbstractSquare */
    protected abstract void revealSquare();

    /* Getters and Setters */
    public boolean isRevealedSquare(){
        return this.revealedSquare;
    }

    public void setRevealedSquare(boolean visible){
        this.revealedSquare = visible;
    }

    public int getRowDigit() {
        return this.rowDigit;
    }

    public int getColDigit() {
        return this.colDigit;
    }

    public void setRowDigit(int rowDigit) {
        this.rowDigit = rowDigit;
    }

    public void setColDigit(int colDigit) {
        this.colDigit = colDigit;
    }

    public static int getNumberOfRevealedSquares() {
        return numberOfRevealedSquares;
    }

    public static void setNumberOfRevealedSquares(int numberOfRevealedSquares) {
        AbstractSquare.numberOfRevealedSquares = numberOfRevealedSquares;
    }
}
