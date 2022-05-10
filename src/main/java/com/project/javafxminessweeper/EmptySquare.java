package com.project.javafxminessweeper;

public class EmptySquare extends AbstractSquare{

    public EmptySquare(int row, int col){
        super(row,col);
        this.setText(" ");
    }

    @Override
    public void revealedSquare(){
        this.setText(" ");
    }
}
