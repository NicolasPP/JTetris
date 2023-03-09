package com.nicolas.tetris.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Index {
    private int row;
    private int col;

    public void decrement(Index other){
        row -= other.row; col -= other.col;

    }
    public void increment(Index other){
        row += other.row; col += other.col;
    }
}
