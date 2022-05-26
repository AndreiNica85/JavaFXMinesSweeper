package com.project.javafxminessweeper;

import javafx.scene.control.ToggleButton;

/** In game Display matrix of square Nodes */

public class Display {

    final int mines;
    ToggleButton[][] toggleButtonsSquares2DArray;

    public Display(int rows, int columns, int numberOfMines, int numberOfMinesMaxLimit) throws BoundaryException{
        if((rows < 2 || rows > 24) || (columns < 2 || columns > 28)){
            throw new WidthAndHeightBoundaryException();
        }else if(numberOfMines < 1 || numberOfMines > numberOfMinesMaxLimit){
            throw new NumberOfMinesBoundaryException();
        }
        this.toggleButtonsSquares2DArray = new ToggleButton[rows][columns];
        this.mines = numberOfMines;
        AbstractSquare.numberOfRevealedSquares = 0;
        InGameController.countMouseClicks = 0;
    }

    /* Getters and setters */
    public int getMines() {
        return this.mines;
    }
}
