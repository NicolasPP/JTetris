package com.nicolas.tetris.game;

import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.sprites.TetrominoSprite;
import lombok.Data;

import java.util.*;
import java.util.stream.IntStream;

import static com.nicolas.tetris.config.TetrisConfig.*;


@Data
public class GameState {
    private final Cell[][] state;
    private TetrominoState tetrominoState;
    private boolean canSpawn;

    public GameState() {
        state = new Cell[GRID_ROWS][GRID_COLS];
        canSpawn = true;
        init();
    }

    public void shift(ShiftDirection direction, UpdateType updateType) {
        if (tetrominoState == null) return;

        Vector2 directionOffset = getDirectionOffset(direction);
        List<Vector2> cellsIndex = getCellsIndexByUpdateType(updateType);

        if (isFallingCollided(cellsIndex, directionOffset)) {
            if (direction == ShiftDirection.DOWN) {
                cellsIndex.forEach(index -> state[(int) index.x][(int) index.y].setUpdateType(UpdateType.FALLEN));
                canSpawn = true;
            }
        } else {
            tetrominoState.getPos().x += directionOffset.x;
            tetrominoState.getPos().y += directionOffset.y;
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
                state[(int) (index.x + directionOffset.x)][(int) (index.y + directionOffset.y)].setType(tetrominoState.getType());
                state[(int) (index.x + directionOffset.x)][(int) (index.y + directionOffset.y)].setUpdateType(UpdateType.FALLING);
            });
        }
    }

    public void rotate(RotationDirection direction) {
        if (tetrominoState.getType() == CellType.O) return;

        Vector2 beforePivotIndex = getPivotIndex(tetrominoState.getCellMap());

        if (tetrominoState.getType() == CellType.I) {
            TetrominoRotator.transpose(tetrominoState.getCellMap());
        } else {
            TetrominoRotator.rotate(tetrominoState.getCellMap(), direction);
        }

        Vector2 afterPivotIndex = getPivotIndex(tetrominoState.getCellMap());

        Vector2 pivotOffset = new Vector2((beforePivotIndex.x - afterPivotIndex.x), (beforePivotIndex.y - afterPivotIndex.y));

        TetrominoRotator.adjustForPivot(tetrominoState.getCellMap(), pivotOffset);

        if (!isRotateSafe()) return;

        Arrays.stream(state).forEach(row -> Arrays.stream(row).filter(Cell::isFalling).forEach(cell -> {
            cell.setType(CellType.EMPTY);
            cell.setUpdateType(UpdateType.SKIP);
        }));

        IntStream.range(0, CELL_MAP_SIZE).forEach(row -> IntStream.range(0, CELL_MAP_SIZE).forEach(col -> {
            if (tetrominoState.getCellMap()[row][col] > 0) {
                state[(int) tetrominoState.getPos().x - row][(int) tetrominoState.getPos().y + col].setType(tetrominoState.getType());
                state[(int) tetrominoState.getPos().x - row][(int) tetrominoState.getPos().y + col].setUpdateType(UpdateType.FALLING);
            }
        }));
    }

    public void spawnTetromino(TetrominoSprite nextTetromino) {
        tetrominoState = nextTetromino.getNewState();
        canSpawn = false;
        IntStream.range(0, CELL_MAP_SIZE).forEach(row -> IntStream.range(0, CELL_MAP_SIZE).forEach(col -> {
            if (nextTetromino.getCellMap()[row][col] > 0) {
                state[SPAWN_ROW - row][SPAWN_COl + col].setType(nextTetromino.getCellType());
                state[SPAWN_ROW - row][SPAWN_COl + col].setUpdateType(UpdateType.FALLING);
            }
        }));
    }
    public void print() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
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

    private boolean isRotateSafe() {
        for (int row = 0; row < CELL_MAP_SIZE; row++) {
            for (int col = 0; col < CELL_MAP_SIZE; col++) {
                int nextRow = (int) (tetrominoState.getPos().x - row);
                int nextCol = (int) (tetrominoState.getPos().y + col);
                if (tetrominoState.getCellMap()[row][col] == 0) continue;
                if (nextRow >= GRID_ROWS || nextRow < 0 || nextCol >= GRID_COLS || nextCol < 0) return false;
                if (state[nextRow][nextCol].getUpdateType() == UpdateType.FALLEN) return false;
            }
        }
        return true;
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

    private Vector2 getPivotIndex(int[][] cellMap) {
        for (int row = 0; row < cellMap.length; row++) {
            for (int col = 0; col < cellMap[row].length; col++) {
                if (cellMap[row][col] == PIVOT_ID) return new Vector2(row, col);
            }
        }
        return new Vector2(0, 0);
    }

    private boolean isFallingCollided(List<Vector2> fallingCellsIndex, Vector2 directionOffset) {
        for (Vector2 index : fallingCellsIndex) {
            int row = (int) (index.x + directionOffset.x);
            int col = (int) (index.y + directionOffset.y);
            if (row < 0 || col < 0 || col >= GRID_COLS
                    || state[row][col].getUpdateType() == UpdateType.FALLEN) return true;
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
