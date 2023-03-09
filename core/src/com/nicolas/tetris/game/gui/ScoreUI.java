package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import static com.nicolas.tetris.config.TetrisConfig.STATS_UI_COLS;
import static com.nicolas.tetris.config.TetrisConfig.BOARD_COLS;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;
import static com.nicolas.tetris.config.TetrisConfig.SCORE_UI_COLS;
import static com.nicolas.tetris.config.TetrisConfig.SCORE_UI_ROWS;
import static com.nicolas.tetris.config.TetrisConfig.BG_GRAY_NAME;

public class ScoreUI extends UIComponent {

    public ScoreUI() {
        super(new Vector2((STATS_UI_COLS + BOARD_COLS) * (CELL_SIZE * TEXTURE_SCALE), 0),
                SCORE_UI_COLS, SCORE_UI_ROWS);
    }

    @Override
    public void render(SpriteBatch batch) {
        renderBackGround(batch, BG_GRAY_NAME);
    }
}
