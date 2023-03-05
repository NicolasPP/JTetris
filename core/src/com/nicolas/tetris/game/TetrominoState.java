package com.nicolas.tetris.game;

import com.badlogic.gdx.math.Vector2;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TetrominoState {
    private final int[][] cellMap;
    private final CellType type;

    private final Vector2 pos;
}
