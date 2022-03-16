package com.project.javafxminessweeper;

import javafx.scene.control.Button;

public abstract class AbstractSquare extends Button {
    boolean revealedSquare;
    boolean clickedSquare;

    public String revealedSquareDisplay(){
        String squareString = "";
        return "";
    }

//    protected abstract String clickedSquare();
//    protected abstract String revealedSquare();

    public boolean isClickedSquare(){
        return this.clickedSquare;
    }

    public void setClickedSquare(boolean click){
        this.clickedSquare = click;
    }

    public boolean isRevealedSquare(){
        return this.revealedSquare;
    }

    public void setVisibleSquare(boolean visible){
        this.revealedSquare = visible;
    }
}
