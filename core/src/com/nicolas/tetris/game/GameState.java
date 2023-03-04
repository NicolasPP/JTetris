package com.nicolas.tetris.game;

import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.sprites.TetrominoSprite;
import lombok.Data;

import java.util.*;
import java.util.stream.IntStream;

import static com.nicolas.tetris.config.TetrisConfig.*;


@Data
public class GameState {
    private CellType spawnType;
    private final Vector2 boardPos;
    private final Cell[][] state;
    private boolean canSpawn;

    public enum ShiftDirection {
        DOWN, RIGHT, LEFT
    }

    public GameState(Vector2 boardPosition) {
        boardPos = boardPosition;
        state = new Cell[GRID_ROWS][GRID_COLS];
        spawnType = CellType.EMPTY;
        canSpawn = true;
        init();
    }

    public void shift(ShiftDirection direction, UpdateType updateType) {
        Vector2 directionOffset = getDirectionOffset(direction);
        List<Vector2> cellsIndex = getCellsIndexByUpdateType(updateType);

        if (isFallingCollided(cellsIndex, directionOffset)) {
            if (direction == ShiftDirection.DOWN) {
                cellsIndex.forEach(index -> state[(int) index.x][(int) index.y].setUpdateType(UpdateType.FALLEN));
                canSpawn = true;
            }
        } else {
            cellsIndex.forEach(index -> {
                if (index.x >= GRID_ROWS - SPAWN_ROW_COUNT) {
                    state[(int) index.x][(int) index.y].setType(CellType.SPAWN);
                    state[(int) index.x][(int) index.y].setUpdateType(UpdateType.SKIP);
                } else {
                    state[(int) index.x][(int) index.y].setType(CellType.EMPTY);
                    state[(int) index.x][(int) index.y].setUpdateType(UpdateType.SKIP);
                }
            });
            cellsIndex.forEach(index -> {
                state[(int) (index.x + directionOffset.x)][(int) (index.y + directionOffset.y)].setType(spawnType);
                state[(int) (index.x + directionOffset.x)][(int) (index.y + directionOffset.y)].setUpdateType(UpdateType.FALLING);
            });
        }
    }

    public void spawnTetromino(TetrominoSprite tetromino) {
        spawnType = tetromino.getCellType();
        canSpawn = false;
        IntStream.range(0, tetromino.getCellMap().length).forEach(row -> IntStream.range(0, tetromino.getCellMap()[row].length).forEach(col -> {
            if (tetromino.getCellMap()[row][col] == 1) {
                state[SPAWN_ROW - row][SPAWN_COl + col].setType(spawnType);
                state[SPAWN_ROW - row][SPAWN_COl + col].setUpdateType(UpdateType.FALLING);
            }
        }));
        print();
    }

    public boolean getCanSpawn() {
        return canSpawn;
    }

    public void print() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
//                System.out.print(state[row][col].getUpdateType() + " ");
                if (state[row][col].getType() == CellType.EMPTY || state[row][col].getType() == CellType.SPAWN) {
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
        Vector2 spawnPos = new Vector2(-1, -1);
        IntStream.range(0, GRID_ROWS).forEach(row -> IntStream.range(0, GRID_COLS).forEach(col -> {
            Vector2 pos = new Vector2((col + 1) * cellSize, (row + 1) * cellSize);
            state[row][col] = Cell.builder().type(CellType.EMPTY).updateType(UpdateType.SKIP).bottomLeft(pos).build();
            if (row >= GRID_ROWS - SPAWN_ROW_COUNT) {
                state[row][col].setType(CellType.SPAWN);
                state[row][col].setUpdateType(UpdateType.SKIP);
                state[row][col].setBottomLeft(spawnPos);
            }
        }));
    }

    private List<Vector2> getCellsIndexByUpdateType(UpdateType updateType) {
        List<Vector2> filteredIndexes = new ArrayList<>();
        IntStream.range(0, GRID_ROWS).forEach(row -> IntStream.range(0, GRID_COLS).forEach(col -> {
            if (state[row][col].getUpdateType() == updateType) {
                filteredIndexes.add(new Vector2(row, col));
            }
        }));
        return filteredIndexes;
    }

    private boolean isFallingCollided(List<Vector2> fallingCellsIndex, Vector2 directionOffset) {
        for (Vector2 index : fallingCellsIndex) {
            if (index.x + directionOffset.x < 0 || index.y + directionOffset.y < 0 || index.y + directionOffset.y >= GRID_COLS ||
                    state[(int) (index.x + directionOffset.x)][(int) (index.y + directionOffset.y)].getUpdateType() == UpdateType.FALLEN) {
                return true;
            }
        }
        return false;
    }

    public static Vector2 getDirectionOffset(ShiftDirection direction) {
        switch (direction) {
            case DOWN:
                return new Vector2(-1, 0);
            case RIGHT:
                return new Vector2(0, 1);
            case LEFT:
                return new Vector2(0, -1);
        }
        return new Vector2(0, 0);
    }
}
