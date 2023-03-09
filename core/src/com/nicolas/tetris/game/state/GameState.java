package com.nicolas.tetris.game.state;

import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.game.cell.Cell;
import com.nicolas.tetris.game.cell.CellType;
import com.nicolas.tetris.game.cell.UpdateType;
import com.nicolas.tetris.utils.RotationDirection;
import com.nicolas.tetris.sprites.TetrominoSprite;
import com.nicolas.tetris.utils.Index;
import lombok.Data;

import java.util.*;
import java.util.stream.IntStream;

import static com.nicolas.tetris.config.TetrisConfig.*;


@Data
public class GameState {
    private final Cell[][] state = new Cell[GRID_ROWS][GRID_COLS];
    private boolean spawnUnlocked = true;

    private TetrominoState tetrominoState;

    public GameState(Vector2 boardPos) {
        init(boardPos);
    }

    public void shift(ShiftDirection direction, UpdateType updateType) {
        if (tetrominoState == null) return;

        Index directionOffset = direction.getDirectionOffset();
        List<Index> cellsIndex = getCellsIndexByUpdateType(updateType);

        CellType[][] types = new CellType[GRID_ROWS][GRID_COLS];
        for (CellType[] row : types) Arrays.fill(row, CellType.EMPTY);


        boolean isCollided = isCollided(cellsIndex, directionOffset);
        if (isCollided) {
            if (direction == ShiftDirection.DOWN) {
                if(updateType == UpdateType.FALLING){
                    cellsIndex.forEach(index -> state[index.getRow()][index.getCol()].setUpdateType(UpdateType.LOCKED));
                    spawnUnlocked = true;
                }
                if(updateType == UpdateType.LOCK_FALL) setNeighboursUpdateType(cellsIndex, directionOffset);
            }
        } else {
            if (updateType == UpdateType.FALLING) {
                tetrominoState.getPosIndex().increment(directionOffset);
            }
            cellsIndex.forEach(index -> {
                types[index.getRow()][index.getCol()] = state[index.getRow()][index.getCol()].getType();
                Cell cell = state[index.getRow()][index.getCol()];
                if (index.getRow() >= GRID_ROWS - SPAWN_ROW_COUNT) {
                    cell.setType(CellType.SPAWN); cell.setUpdateType(UpdateType.SKIP);
                } else {
                    cell.setType(CellType.EMPTY); cell.setUpdateType(UpdateType.SKIP);
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

    private void setNeighboursUpdateType(List<Index> cellsIndex, Index directionOffset){
        LinkedList<Index> q = new LinkedList<>();
        Set<Index> visited = new HashSet<>();

        for (Index index : cellsIndex) {
            if (isCellCollided(index, directionOffset)) {
                q.add(index);
                break;
            }
        }

        Index[] directions = {new Index(1, 0), new Index(-1, 0), new Index(0, 1), new Index(0, -1)};

        while (!q.isEmpty()) {
            Index cellIndex = q.poll();
            for (Index index : directions) {
                Index nIndex = new Index(cellIndex.getRow(), cellIndex.getCol());
                nIndex.increment(index);
                if (nIndex.getRow() >= GRID_ROWS || nIndex.getCol() >= GRID_COLS) continue;
                if (nIndex.getRow() < 0 || nIndex.getCol() < 0) continue;
                if (visited.contains(index)) continue;
                if (state[nIndex.getRow()][nIndex.getCol()].getUpdateType() == UpdateType.LOCK_FALL) q.add(nIndex);
            }
            state[cellIndex.getRow()][cellIndex.getCol()].setUpdateType(UpdateType.LOCKED);
            visited.add(cellIndex);
        }
    }

    public boolean isGameOver(){
        for(Cell cell : state[SPAWN_ROW - SPAWN_ROW_COUNT]){
            if (cell.getUpdateType() == UpdateType.LOCKED) return true;
        }
        return false;
    }

    public void restartGame(Vector2 boardPos){
        init(boardPos);
    }

    public void rotate(RotationDirection direction) {
        tetrominoState.rotate(direction);

        if (!isRotateSafe()) return;

        Arrays.stream(state).forEach(row -> Arrays.stream(row).filter(Cell::isFalling).forEach(cell -> {
            cell.setType(CellType.EMPTY);
            cell.setUpdateType(UpdateType.SKIP);
        }));

        IntStream.range(0, CELL_MAP_SIZE).forEach(row -> IntStream.range(0, CELL_MAP_SIZE).forEach(col -> {
            if (tetrominoState.getCellMap()[row][col] > 0) {
                Cell nextCell = state[tetrominoState.getPosIndex().getRow() - row][tetrominoState.getPosIndex().getCol() + col];
                nextCell.setType(tetrominoState.getType());
                nextCell.setUpdateType(UpdateType.FALLING);
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

    private void init(Vector2 boardPos) {
        int cellSize = (int) (CELL_SIZE * TEXTURE_SCALE);
        IntStream.range(0, GRID_ROWS).forEach(row -> IntStream.range(0, GRID_COLS).forEach(col -> {
            Vector2 pos = new Vector2(((col + 1) * cellSize) + (int)boardPos.x, ((row + 1) * cellSize) + (int)boardPos.y);
            state[row][col] = Cell.builder().type(CellType.EMPTY).updateType(UpdateType.SKIP).bottomLeft(pos).build();
            if (row >= GRID_ROWS - SPAWN_ROW_COUNT) {
                state[row][col].setType(CellType.SPAWN);
                state[row][col].setUpdateType(UpdateType.SKIP);
                state[row][col].setBottomLeft(new Vector2(-1, -1));
            }
        }));
    }

    private boolean isRotateSafe() {
        for (int row = 0; row < CELL_MAP_SIZE; row++) {
            for (int col = 0; col < CELL_MAP_SIZE; col++) {
                int nextRow = tetrominoState.getPosIndex().getRow() - row;
                int nextCol = tetrominoState.getPosIndex().getCol() + col;
                if (tetrominoState.getCellMap()[row][col] == 0) continue;
                if (nextRow >= GRID_ROWS || nextRow < 0 || nextCol >= GRID_COLS || nextCol < 0) return false;
                if (state[nextRow][nextCol].getUpdateType() == UpdateType.LOCKED) return false;
            }
        }
        return true;
    }

    private List<Index> getCellsIndexByUpdateType(UpdateType updateType) {
        List<Index> filteredIndexes = new ArrayList<>();
        IntStream.range(0, GRID_ROWS).forEach(row -> IntStream.range(0, GRID_COLS).forEach(col -> {
            if (state[row][col].getUpdateType() == updateType) {
                filteredIndexes.add(new Index(row, col));
            }
        }));
        return filteredIndexes;
    }
    private boolean isCollided(List<Index> cellsIndex, Index directionOffset) {
        for (Index index : cellsIndex)
            if (isCellCollided(index, directionOffset)) return true;
        return false;
    }

    private boolean isCellCollided(Index cellIndex, Index directionOffset){
        int row = cellIndex.getRow() + directionOffset.getRow();
        int col = cellIndex.getCol() + directionOffset.getCol();
        return row < 0 || col < 0 || col >= GRID_COLS
                || state[row][col].getUpdateType() == UpdateType.LOCKED;
    }
}
