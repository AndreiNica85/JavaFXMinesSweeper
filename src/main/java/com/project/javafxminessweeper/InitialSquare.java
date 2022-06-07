package com.project.javafxminessweeper;

/** Class for Initial state of an AbstractSquare BEFORE first mouse click on Display (before initializeDisplayOnFirstClick method is used) */

public class InitialSquare extends AbstractSquare{

    public InitialSquare(int row, int col) {
        super(row, col);
    }

    @Override
    protected void revealSquare() {
        this.setText(" ");
    }

}
