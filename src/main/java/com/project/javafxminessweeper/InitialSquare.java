package com.project.javafxminessweeper;

public class InitialSquare extends AbstractSquare{

    public InitialSquare(int row, int col) {
        super(row, col);
    }

    @Override
    protected void revealedSquare() {
        this.setText(" ");
    }

}
