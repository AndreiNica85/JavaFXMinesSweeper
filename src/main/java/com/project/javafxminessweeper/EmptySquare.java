package com.project.javafxminessweeper;

/** Class for empty square state of an AbstractSquare when revealed. */

public class EmptySquare extends AbstractSquare{

    public EmptySquare(int row, int col){
        super(row,col);
    }

    @Override
    public void revealSquare(){
        this.setText(" ");
    }

}
