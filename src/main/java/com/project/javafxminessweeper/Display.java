package com.project.javafxminessweeper;

import javafx.scene.control.ToggleButton;

/** Class for In game Display matrix of square Nodes */

public class Display {

    final private int mines;
    final private ToggleButton[][] display2DArray;

    public Display(int rows, int columns, int numberOfMines, int numberOfMinesMaxLimit) throws BoundaryException{

        if((rows < 2 || rows > 24) || (columns < 2 || columns > 28)){
            throw new WidthAndHeightBoundaryException();

        /* NumberOfMines must be less than numberOfMinesMaxLimit - meaning no more than 15 percent of total number
        of squares for Custom Games */
        }else if(numberOfMines < 1 || numberOfMines > numberOfMinesMaxLimit){
            throw new NumberOfMinesBoundaryException();
        }

        /* Display is a 2D array ToggleButtons (extends Node abstract class from JavaFX) elements */
        this.display2DArray = new ToggleButton[rows][columns];
        this.mines = numberOfMines;

        /* Restart the numberOfRevealedSquares static member and number of mouse clicks static member
        for every new Display object created */
        AbstractSquare.setNumberOfRevealedSquares(0);
        InGameController.setCountMouseClicks(0);
    }

    /** Getters */
    public int getMines() {
        return mines;
    }

    public ToggleButton[][] getDisplay2DArray() {
        return display2DArray;
    }
}
