package com.nicolas.tetris.game.tetromino;

import com.nicolas.tetris.game.cell.CellType;
import com.nicolas.tetris.utils.Pos;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TetrominoState {
    private final int[][] cellMap;
    private final CellType type;

    private final Pos pos;
}
