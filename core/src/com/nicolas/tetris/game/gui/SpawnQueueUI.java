package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.utils.Pos;

import static com.nicolas.tetris.config.TetrisConfig.BG_BLACK_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SPAWN_QUEUE_UI_HEIGHT;
import static com.nicolas.tetris.config.TetrisConfig.SPAWN_QUEUE_UI_WIDTH;

public class SpawnQueueUI extends UIComponent {
    public SpawnQueueUI() {
        super(new Pos(0, 0), SPAWN_QUEUE_UI_WIDTH, SPAWN_QUEUE_UI_HEIGHT);
    }

    @Override
    public void render(SpriteBatch batch) {
        renderBackGround(batch, BG_BLACK_NAME);
    }
}
