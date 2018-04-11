package ViewModel;


import model.Point;

import javax.swing.*;

public class MyButton extends JButton {

    private int row = 0;
    private int col = 0;

    public MyButton(int x, int y){
        super();
        row = x;
        col = y;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }
}
