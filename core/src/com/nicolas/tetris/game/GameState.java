package com.nicolas.tetris.game;

import java.util.Arrays;
import java.util.stream.IntStream;

import static com.nicolas.tetris.config.TetrisConfig.GRID_ROWS;
import static com.nicolas.tetris.config.TetrisConfig.GRID_COLS;
import static com.nicolas.tetris.config.TetrisConfig.SPAWN_ROWS;

public class GameState {
    final private CellType[][] state;

    public GameState() {
        state = new CellType[GRID_ROWS][GRID_COLS];
        init();
        print();
    }

    private void init() {
        Arrays.stream(state).forEach(row -> Arrays.fill(row, CellType.EMPTY));
        IntStream.range(0, SPAWN_ROWS).forEach(index -> Arrays.fill(state[index], CellType.SPAWN));
    }

    public void print() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                System.out.print(state[row][col] + " ");
            }
            System.out.println();
        }
    }

    public void spawnShape(CellType type){}
}
