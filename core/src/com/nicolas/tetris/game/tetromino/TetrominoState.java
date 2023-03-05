package com.nicolas.tetris.game.tetromino;

import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.game.cell.CellType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TetrominoState {
    private final int[][] cellMap;
    private final CellType type;

    private final Vector2 pos;
}
