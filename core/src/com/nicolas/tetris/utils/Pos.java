package com.nicolas.tetris.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pos {
    private int row;
    private int col;

    public Pos(Pos pos){
        row = pos.getRow();
        col = pos.getCol();
    }

    public void decrement(Pos other){
        row -= other.row; col -= other.col;

    }
    public void increment(Pos other){
        row += other.row; col += other.col;
    }
}
