package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.utils.Pos;

import static com.nicolas.tetris.config.TetrisConfig.STATS_UI_WIDTH;
import static com.nicolas.tetris.config.TetrisConfig.BOARD_COLS;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;
import static com.nicolas.tetris.config.TetrisConfig.SCORE_UI_WIDTH;
import static com.nicolas.tetris.config.TetrisConfig.SCORE_UI_HEIGHT;
import static com.nicolas.tetris.config.TetrisConfig.BG_GRAY_NAME;

public class ScoreUI extends UIComponent {

    public ScoreUI() {
        super(new Pos(0, (STATS_UI_WIDTH + BOARD_COLS) * (int)(CELL_SIZE * TEXTURE_SCALE)),
                SCORE_UI_WIDTH, SCORE_UI_HEIGHT);
    }

    @Override
    public void render(SpriteBatch batch) {
        renderBackGround(batch, BG_GRAY_NAME);
    }
}
