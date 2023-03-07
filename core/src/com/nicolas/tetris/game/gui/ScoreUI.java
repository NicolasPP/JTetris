package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.utils.Pos;

import static com.nicolas.tetris.config.TetrisConfig.BG_GRAY_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SCORE_UI_HEIGHT;
import static com.nicolas.tetris.config.TetrisConfig.SCORE_UI_WIDTH;

public class ScoreUI extends UIComponent {

    public ScoreUI() {
        super(new Pos(0, 0), SCORE_UI_WIDTH, SCORE_UI_HEIGHT);
    }

    @Override
    public void render(SpriteBatch batch) {
        renderBackGround(batch, BG_GRAY_NAME);
    }
}
