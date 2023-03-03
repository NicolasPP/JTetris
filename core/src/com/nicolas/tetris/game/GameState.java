package com.nicolas.tetris.game;

import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.sprites.TetrominoSprite;
import lombok.Data;

import java.util.*;

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
    private boolean spawnTetromino;


    public GameState(Vector2 boardPosition) {
        boardPos = boardPosition;
        state = new Cell[GRID_ROWS][GRID_COLS];
        spawnQueue = new LinkedList<>();
        spawnType = CellType.EMPTY;
        spawnTetromino = true;
        spawnPos = new Vector2(3, 19);
        init();
    }

    public void spawn() {
        if (spawnQueue.isEmpty()) {
            return;
        }
        int[] shapeRow = spawnQueue.poll();
        for (int index = 0; index < shapeRow.length; index++) {
            if (shapeRow[index] != 1) {
                continue;
            }
            state[(int) spawnPos.y][(int) spawnPos.x + index].setType(spawnType);
            state[(int) spawnPos.y][(int) spawnPos.x + index].setUpdateType(UpdateType.FALLING);
        }
    }

    public void fall(UpdateType cellUpdateType) {
        List<Vector2> fallingCellsIndex = getCellsByUpdateType(cellUpdateType);

        if (isFallingCollided(fallingCellsIndex)) {
            fallingCellsIndex.forEach(index -> state[(int) index.x][(int) index.y].setUpdateType(UpdateType.FALLEN));
            spawnTetromino = true;
        } else {
            fallingCellsIndex.forEach(index -> {
                state[(int) index.x][(int) index.y].setType(CellType.EMPTY);
                state[(int) index.x][(int) index.y].setUpdateType(UpdateType.EMPTY);
                state[(int) index.x - 1][(int) index.y].setType(spawnType);
                state[(int) index.x - 1][(int) index.y].setUpdateType(UpdateType.FALLING);
            });
        }
    }

    public void queueShape(TetrominoSprite tetromino) {
        Collections.addAll(spawnQueue, tetromino.getCellMap());
        spawnType = tetromino.getCellType();
        spawnTetromino = false;
    }

    public boolean getSpawnTetromino() {return spawnTetromino;}

    public void print() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
//                System.out.print(state[row][col].getUpdateType() + " ");
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

    private List<Vector2> getCellsByUpdateType(UpdateType updateType) {
        List<Vector2> filteredIndexes = new ArrayList<>();
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                Cell cell = state[row][col];
                if (cell.getUpdateType() == updateType) {
                    filteredIndexes.add(new Vector2(row, col));
                }
            }
        }
        return filteredIndexes;
    }

    private boolean isFallingCollided(List<Vector2> fallingCellsIndex) {
        for (Vector2 index : fallingCellsIndex) {
            if (index.x - 1 < 0 || state[(int) index.x - 1][(int) index.y].getUpdateType() == UpdateType.FALLEN) {
                return true;
            }
        }
        return false;
    }
}
