package com.nicolas.tetris.game;

import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.sprites.TetrominoSprite;
import lombok.Data;

import java.util.Collections;
import java.util.LinkedList;

import static com.nicolas.tetris.config.TetrisConfig.GRID_COLS;
import static com.nicolas.tetris.config.TetrisConfig.GRID_ROWS;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;


@Data
public class GameState {

    private LinkedList<int[]> spawnQueue;
    private CellType spawnType;

    private final Vector2 boardPos;
    private final Cell[][] state;

    private final Vector2 spawnPos;

    public GameState(Vector2 boardPosition) {
        state = new Cell[GRID_ROWS][GRID_COLS];
        spawnQueue = new LinkedList<>();
        spawnType = CellType.EMPTY;
        boardPos = boardPosition;
        spawnPos = new Vector2(3, 19);
        init();
        print();
    }

    private void init() {
        float cellSize = CELL_SIZE * TEXTURE_SCALE;
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                Vector2 pos = new Vector2((col + 1) * cellSize, (row + 1) * cellSize);
                state[row][col] = Cell.builder()
                        .type(CellType.EMPTY)
                        .updateType(UpdateType.EMPTY)
                        .bottomLeft(pos).build();

            }
        }
    }

    public void print() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                if (state[row][col].getType() == CellType.EMPTY) {
                    System.out.print(state[row][col].getType() + " ");
                } else {
                    System.out.print("  " + state[row][col].getType() + "  " + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void queueShape(TetrominoSprite tetromino) {
        Collections.addAll(spawnQueue, tetromino.getCellMap());
        spawnType = tetromino.getCellType();
    }

    public void spawn() {
        if (spawnQueue.isEmpty()) {
            return;
        }
        int[] shapeRow = spawnQueue.poll();
        for (int index = 0; index < shapeRow.length; index++) {
            if (shapeRow[index] < 1) {
                continue;
            }
            state[(int) spawnPos.y][(int) spawnPos.x + index].setType(spawnType);
            state[(int) spawnPos.y][(int) spawnPos.x + index].setUpdateType(UpdateType.FALLING);
        }
    }
}
