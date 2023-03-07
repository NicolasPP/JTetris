package com.nicolas.tetris.game.tetromino;

import com.nicolas.tetris.utils.Pos;

import static com.nicolas.tetris.config.TetrisConfig.CELL_MAP_SIZE;

public class TetrominoRotator {
    public static void rotate(int[][] cellMap, RotationDirection direction){
        int size = cellMap.length;
        DirectionMethod dirMethod = getDirectionMethod(direction);
        for (int x = 0; x < Math.floorDiv(size, 2); x++){
            for(int y = x; y < size - x - 1; y++){
                int temp = cellMap[x][y];
                dirMethod.exec(cellMap, size, x, y, temp);
            }
        }
    }
    public static void transpose(int[][] cellMap) {
        int size = cellMap.length;
        for (int x = 0; x < size; x++){
            for(int y = x; y < size; y++){
                int temp = cellMap[x][y];
                cellMap[x][y] = cellMap[y][x];
                cellMap[y][x] = temp;
            }
        }
    }

    public static void adjustForPivot(int[][] cellMap, Pos pivot){
        for(int i = 0; i < CELL_MAP_SIZE; i++){
            for(int j = 0; j < CELL_MAP_SIZE; j++){
                int row = pivot.getRow() + i; int col = pivot.getCol() + j;
                if (cellMap[i][j] > 0){
                    if (row < 0 || row >= CELL_MAP_SIZE || col < 0 || col >= CELL_MAP_SIZE) continue;
                    int temp = cellMap[i][j];
                    cellMap[i][j] = 0;
                    cellMap[row][col] = temp;
                }
            }
        }
    }

    private static void rotateClockWise(int[][] cellMap, int size, int x, int y, int temp){
        cellMap[x][y] = cellMap[size - 1 - y][x];
        cellMap[size - 1 - y][x] = cellMap[size - 1 - x][size - 1 - y];
        cellMap[size - 1 - x][size - 1 - y] = cellMap[y][size - 1 - x];
        cellMap[y][size - 1 - x] = temp;
    }
    private static void rotateAntiClockWise(int[][] cellMap, int size, int x, int y, int temp){
        cellMap[x][y] = cellMap[y][size-1-x];
        cellMap[y][size-1-x] = cellMap[size-1-x][size-1-y];
        cellMap[size-1-x][size-1-y] = cellMap[size-1-y][x];
        cellMap[size-1-y][x] = temp;
    }

    private static DirectionMethod getDirectionMethod(RotationDirection direction){
        if (direction == RotationDirection.CLOCKWISE){
            return TetrominoRotator::rotateClockWise;
        }else{
            return TetrominoRotator::rotateAntiClockWise;
        }
    }

    private interface DirectionMethod{
        void exec(int[][] cellMap, int size, int x, int y, int temp);
    }
}
