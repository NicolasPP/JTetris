package com.nicolas.tetris.game.tetromino;

import com.nicolas.tetris.game.cell.CellType;
import com.nicolas.tetris.utils.Pos;
import com.nicolas.tetris.utils.Rotate2DArray;
import com.nicolas.tetris.utils.RotationDirection;
import lombok.Builder;
import lombok.Data;

import static com.nicolas.tetris.config.TetrisConfig.CELL_MAP_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.PIVOT_ID;

@Data
@Builder
public class TetrominoState {
    private final int[][] cellMap;
    private final CellType type;

    private final Pos pos;


    public void rotate(RotationDirection direction){
        if (type == CellType.O) return;

        Pos pivotOffset = getPivotIndex();

        if (type == CellType.I) {
            Rotate2DArray.transpose(cellMap);
        } else {
            Rotate2DArray.rotate90(cellMap, direction);
        }

        Pos afterPivotOffset = getPivotIndex();

        pivotOffset.decrement(afterPivotOffset);

        adjustForPivot(pivotOffset);
    }
    private Pos getPivotIndex(){
        for (int row = 0; row < cellMap.length; row++) {
            for (int col = 0; col < cellMap[row].length; col++) {
                if (cellMap[row][col] == PIVOT_ID) return new Pos(row, col);
            }
        }
        return new Pos(0, 0);
    }

    private void adjustForPivot(Pos pivot){
        for(int i = 0; i < cellMap.length; i++){
            for(int j = 0; j < cellMap[i].length; j++){
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


}
