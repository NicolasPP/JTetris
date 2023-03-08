package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.utils.Pos;

import static com.nicolas.tetris.config.TetrisConfig.SCORE_UI_HEIGHT;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;
import static com.nicolas.tetris.config.TetrisConfig.STATS_UI_WIDTH;
import static com.nicolas.tetris.config.TetrisConfig.BOARD_COLS;
import static com.nicolas.tetris.config.TetrisConfig.SPAWN_QUEUE_UI_WIDTH;
import static com.nicolas.tetris.config.TetrisConfig.SPAWN_QUEUE_UI_HEIGHT;
import static com.nicolas.tetris.config.TetrisConfig.BG_BLACK_NAME;

public class SpawnQueueUI extends UIComponent {
    public SpawnQueueUI() {
        super(new Pos(SCORE_UI_HEIGHT * (int)(CELL_SIZE * TEXTURE_SCALE),
                        (STATS_UI_WIDTH + BOARD_COLS) * (int)(CELL_SIZE * TEXTURE_SCALE)),
                SPAWN_QUEUE_UI_WIDTH, SPAWN_QUEUE_UI_HEIGHT);
    }

    @Override
    public void render(SpriteBatch batch) {
        renderBackGround(batch, BG_BLACK_NAME);
    }
}
