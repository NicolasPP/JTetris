package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import static com.nicolas.tetris.config.TetrisConfig.STATS_UI_ROWS;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;
import static com.nicolas.tetris.config.TetrisConfig.HOLD_UI_COLS;
import static com.nicolas.tetris.config.TetrisConfig.HOLD_UI_ROWS;
import static com.nicolas.tetris.config.TetrisConfig.BG_GRAY_NAME;

public class HoldUI extends UIComponent {
    @Override
    public void render(SpriteBatch batch) {
        renderBackGround(batch, BG_GRAY_NAME);
    }

    public HoldUI() {
        super(new Vector2(0, STATS_UI_ROWS * CELL_SIZE * TEXTURE_SCALE), HOLD_UI_COLS, HOLD_UI_ROWS);
    }
}
