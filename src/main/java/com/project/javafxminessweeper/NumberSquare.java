package com.project.javafxminessweeper;

public class NumberSquare extends AbstractSquare{
    static int test = 1;

    public NumberSquare(){
        if(test == 9){
            test = 1;
        }
        this.setText("" + test++);
    }


}
