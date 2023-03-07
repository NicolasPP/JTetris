package com.nicolas.tetris.game;

import com.nicolas.tetris.game.cell.Cell;
import com.nicolas.tetris.game.cell.CellType;
import com.nicolas.tetris.game.cell.UpdateType;
import com.nicolas.tetris.game.tetromino.RotationDirection;
import com.nicolas.tetris.game.tetromino.TetrominoRotator;
import com.nicolas.tetris.game.tetromino.TetrominoState;
import com.nicolas.tetris.sprites.TetrominoSprite;
import com.nicolas.tetris.utils.Pos;
import lombok.Data;

import java.util.*;
import java.util.stream.IntStream;

import static com.nicolas.tetris.config.TetrisConfig.*;


@Data
public class GameState {
    private final Cell[][] state = new Cell[GRID_ROWS][GRID_COLS];
    private boolean spawnUnlocked = true;

    private TetrominoState tetrominoState;

    public GameState() {
        init();
    }

    public void shift(ShiftDirection direction, UpdateType updateType) {
        if (tetrominoState == null) return;

        Pos directionOffset = getDirectionOffset(direction);
        List<Pos> cellsIndex = getCellsIndexByUpdateType(updateType);

        CellType[][] types = new CellType[GRID_ROWS][GRID_COLS];
        for (CellType[] row : types) Arrays.fill(row, CellType.EMPTY);


        boolean isCollided = isCollided(cellsIndex, directionOffset);
        if (isCollided) {
            if (direction == ShiftDirection.DOWN) {
                if(updateType == UpdateType.FALLING || updateType == UpdateType.LOCK_FALL){
                    cellsIndex.forEach(index -> state[index.getRow()][index.getCol()].setUpdateType(UpdateType.LOCKED));
                }
                if(updateType == UpdateType.FALLING) spawnUnlocked = true;
            }
        } else {
            if (updateType == UpdateType.FALLING) {
                tetrominoState.getPos().increment(directionOffset);
            }
            cellsIndex.forEach(index -> {
                types[index.getRow()][index.getCol()] = state[index.getRow()][index.getCol()].getType();
                if (index.getRow() >= GRID_ROWS - SPAWN_ROW_COUNT) {
                    state[index.getRow()][index.getCol()].setType(CellType.SPAWN);
                    state[index.getRow()][index.getCol()].setUpdateType(UpdateType.SKIP);
                } else {
                    state[index.getRow()][index.getCol()].setType(CellType.EMPTY);
                    state[index.getRow()][index.getCol()].setUpdateType(UpdateType.SKIP);
                }
            });

            cellsIndex.forEach(index -> {
                CellType type = types[index.getRow()][index.getCol()];
                index.increment(directionOffset);
                state[index.getRow()][index.getCol()].setType(type);
                state[index.getRow()][index.getCol()].setUpdateType(updateType);
            });
        }
    }

    public void rotate(RotationDirection direction) {
        if (tetrominoState.getType() == CellType.O) return;

        Pos pivotOffset = getPivotIndex(tetrominoState.getCellMap());

        if (tetrominoState.getType() == CellType.I) {
            TetrominoRotator.transpose(tetrominoState.getCellMap());
        } else {
            TetrominoRotator.rotate(tetrominoState.getCellMap(), direction);
        }

        Pos afterPivotOffset = getPivotIndex(tetrominoState.getCellMap());

        pivotOffset.decrement(afterPivotOffset);

        TetrominoRotator.adjustForPivot(tetrominoState.getCellMap(), pivotOffset);

        if (!isRotateSafe()) return;

        Arrays.stream(state).forEach(row -> Arrays.stream(row).filter(Cell::isFalling).forEach(cell -> {
            cell.setType(CellType.EMPTY);
            cell.setUpdateType(UpdateType.SKIP);
        }));

        IntStream.range(0, CELL_MAP_SIZE).forEach(row -> IntStream.range(0, CELL_MAP_SIZE).forEach(col -> {
            if (tetrominoState.getCellMap()[row][col] > 0) {
                state[tetrominoState.getPos().getRow() - row][tetrominoState.getPos().getCol() + col].setType(tetrominoState.getType());
                state[tetrominoState.getPos().getRow() - row][tetrominoState.getPos().getCol() + col].setUpdateType(UpdateType.FALLING);
            }
        }));
    }

    public void spawnTetromino(TetrominoSprite nextTetromino) {
        tetrominoState = nextTetromino.getNewState();
        spawnUnlocked = false;
        IntStream.range(0, CELL_MAP_SIZE).forEach(row -> IntStream.range(0, CELL_MAP_SIZE).forEach(col -> {
            if (nextTetromino.getCellMap()[row][col] > 0) {
                state[SPAWN_ROW - row][SPAWN_COl + col].setType(nextTetromino.getCellType());
                state[SPAWN_ROW - row][SPAWN_COl + col].setUpdateType(UpdateType.FALLING);
            }
        }));
    }

    public int processFilledLines() {
        List<Cell[]> lines = new ArrayList<>();

        int minRow = GRID_ROWS;

        for (int row = 0; row < state.length; row++) {
            if (row < GRID_ROWS - SPAWN_ROW_COUNT &&
                    Arrays.stream(state[row]).filter(Cell::isLocked).count() == 10) {
                lines.add(state[row]);
                minRow = Math.min(minRow, row);
            }
        }

        if (lines.size() == 0) return 0;


        lines.forEach(line -> Arrays.stream(line).forEach(cell -> {
            cell.setType(CellType.EMPTY);
            cell.setUpdateType(UpdateType.SKIP);
        }));


        for (int row = 0; row < state.length; row++) {
            if (row >= GRID_ROWS - SPAWN_ROW_COUNT || row < minRow) continue;
            for (int col = 0; col < state[row].length; col++) {
                Cell cell = state[row][col];
                if(cell.getUpdateType() == UpdateType.LOCKED) cell.setUpdateType(UpdateType.LOCK_FALL);
            }
        }

        return lines.size();
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
        int cellSize = (int) (CELL_SIZE * TEXTURE_SCALE);
        IntStream.range(0, GRID_ROWS).forEach(row -> IntStream.range(0, GRID_COLS).forEach(col -> {
            Pos pos = new Pos((col + 1) * cellSize, (row + 1) * cellSize);
            state[row][col] = Cell.builder().type(CellType.EMPTY).updateType(UpdateType.SKIP).bottomLeft(pos).build();
            if (row >= GRID_ROWS - SPAWN_ROW_COUNT) {
                state[row][col].setType(CellType.SPAWN);
                state[row][col].setUpdateType(UpdateType.SKIP);
                state[row][col].setBottomLeft(new Pos(-1, -1));
            }
        }));
    }

    private boolean isRotateSafe() {
        for (int row = 0; row < CELL_MAP_SIZE; row++) {
            for (int col = 0; col < CELL_MAP_SIZE; col++) {
                int nextRow = tetrominoState.getPos().getRow() - row;
                int nextCol = tetrominoState.getPos().getCol() + col;
                if (tetrominoState.getCellMap()[row][col] == 0) continue;
                if (nextRow >= GRID_ROWS || nextRow < 0 || nextCol >= GRID_COLS || nextCol < 0) return false;
                if (state[nextRow][nextCol].getUpdateType() == UpdateType.LOCKED) return false;
            }
        }
        return true;
    }

    private List<Pos> getCellsIndexByUpdateType(UpdateType updateType) {
        List<Pos> filteredIndexes = new ArrayList<>();
        IntStream.range(0, GRID_ROWS).forEach(row -> IntStream.range(0, GRID_COLS).forEach(col -> {
            if (state[row][col].getUpdateType() == updateType) {
                filteredIndexes.add(new Pos(row, col));
            }
        }));
        return filteredIndexes;
    }

    private Pos getPivotIndex(int[][] cellMap) {
        for (int row = 0; row < cellMap.length; row++) {
            for (int col = 0; col < cellMap[row].length; col++) {
                if (cellMap[row][col] == PIVOT_ID) return new Pos(row, col);
            }
        }
        return new Pos(0, 0);
    }

    private boolean isCollided(List<Pos> cellsIndex, Pos directionOffset) {
        for (Pos index : cellsIndex) {
            int row = index.getRow() + directionOffset.getRow();
            int col = index.getCol() + directionOffset.getCol();
            if (row < 0 || col < 0 || col >= GRID_COLS
                    || state[row][col].getUpdateType() == UpdateType.LOCKED) return true;
        }
        return false;
    }

    public static Pos getDirectionOffset(ShiftDirection direction) {
        switch (direction) {
            case DOWN:
                return new Pos(-1, 0);
            case RIGHT:
                return new Pos(0, 1);
            case LEFT:
                return new Pos(0, -1);
        }
        return new Pos(0, 0);
    }
}
