package com.nicolas.tetris.game;

import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.sprites.TetrominoSprite;
import lombok.Data;

import java.util.*;
import java.util.stream.IntStream;

import static com.nicolas.tetris.config.TetrisConfig.*;


@Data
public class GameState {
    private LinkedList<int[]> spawnQueue;
    private CellType spawnType;
    private final Vector2 boardPos;
    private final Cell[][] state;
    private boolean canSpawn;

    private int colSpawnOffset;

    public enum ShiftDirection{
        DOWN, RIGHT, LEFT
    }
    public GameState(Vector2 boardPosition) {
        boardPos = boardPosition;
        state = new Cell[GRID_ROWS][GRID_COLS];
        spawnQueue = new LinkedList<>();
        spawnType = CellType.EMPTY;
        canSpawn = true;
        colSpawnOffset = 0;
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
            state[SPAWN_ROW][SPAWN_COl + index + colSpawnOffset].setType(spawnType);
            state[SPAWN_ROW][SPAWN_COl + index + colSpawnOffset].setUpdateType(UpdateType.FALLING);
        }
    }

    public void shift(ShiftDirection direction, UpdateType updateType){
        Vector2 directionOffset = getDirectionOffset(direction);
        List<Vector2> cellsIndex = getCellsIndexByUpdateType(updateType);

        if (isCellsCollided(cellsIndex, directionOffset, updateType)){
            if (direction == ShiftDirection.DOWN){
                cellsIndex.forEach(index -> state[(int) index.x][(int) index.y].setUpdateType(UpdateType.FALLEN));
                canSpawn = true;
            }
        } else {
            if (direction != ShiftDirection.DOWN) {
                colSpawnOffset += directionOffset.y;
            }

            cellsIndex.forEach(index -> {
                state[(int) index.x][(int) index.y].setType(CellType.EMPTY);
                state[(int) index.x][(int) index.y].setUpdateType(UpdateType.EMPTY);
            });
            cellsIndex.forEach(index -> {
                state[(int) (index.x + directionOffset.x)][(int) (index.y + directionOffset.y)].setType(spawnType);
                state[(int) (index.x + directionOffset.x)][(int) (index.y + directionOffset.y)].setUpdateType(UpdateType.FALLING);

            });
        }
    }

    public void queueTetrominoSpawn(TetrominoSprite tetromino) {
        Collections.addAll(spawnQueue, tetromino.getCellMap());
        spawnType = tetromino.getCellType();
        canSpawn = false;
        colSpawnOffset = 0;
    }

    public boolean getCanSpawn() {return canSpawn;}

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
                        .bottomLeft(pos)
                        .build();
            }
        }
    }

    private List<Vector2> getCellsIndexByUpdateType(UpdateType updateType) {
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

    private boolean isFallingCollided(List<Vector2> fallingCellsIndex, Vector2 directionOffset) {
        List<Vector2> cellsIndex = new ArrayList<>(fallingCellsIndex);

        if (directionOffset.x == 0 && !spawnQueue.isEmpty()){
            spawnQueue.forEach(row ->
                IntStream.range(0, row.length).filter(index -> row[index] == 1).forEach(index ->
                        cellsIndex.add(new Vector2(SPAWN_ROW,SPAWN_COl + index + colSpawnOffset))));
        }

        for (Vector2 index : cellsIndex) {
            if (index.x + directionOffset.x < 0 || index.y + directionOffset.y < 0 || index.y + directionOffset.y >= GRID_COLS ||
                    state[(int) (index.x + directionOffset.x)][(int) (index.y + directionOffset.y)].getUpdateType() == UpdateType.FALLEN) {
                return true;
            }
        }
        return false;
    }

    private boolean isCellsCollided(List<Vector2> cellsIndex, Vector2 directionOffset, UpdateType updateType){
        switch (updateType){
            case FALLEN:
                return isFallenCollided(cellsIndex, directionOffset);
            case FALLING:
                return isFallingCollided(cellsIndex, directionOffset);
        }
        return false;
    }

    private boolean isFallenCollided(List<Vector2> cellsIndex, Vector2 directionOffset) {
        return false;
    }

    public static Vector2 getDirectionOffset(ShiftDirection direction){
        Vector2 offset = new Vector2(0, 0);
        switch (direction){
            case DOWN:
                offset =  new Vector2(-1, 0);
                break;
            case RIGHT:
                offset =  new Vector2(0, 1);
                break;
            case LEFT:
                offset =  new Vector2(0, -1);
                break;
        }
        return offset;
    }
}
