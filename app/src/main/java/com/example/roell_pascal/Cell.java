package com.example.roell_pascal;

public class Cell {
    public boolean
        wall = false,
        end = false,
        start = false;
        int row, col;
    public Cell( int col, int row){
        this.row = row;
        this.col = col;
    }

}
