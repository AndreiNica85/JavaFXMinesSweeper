package com.project.javafxminessweeper;

public class NumberSquare extends AbstractSquare{
    static int test = 1;

    @Override
    public String toString(){
        return "" + test++;
    }
}
