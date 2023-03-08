package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.utils.Pos;

import static com.nicolas.tetris.config.TetrisConfig.STATS_UI_HEIGHT;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;
import static com.nicolas.tetris.config.TetrisConfig.HOLD_UI_WIDTH;
import static com.nicolas.tetris.config.TetrisConfig.HOLD_UI_HEIGHT;
import static com.nicolas.tetris.config.TetrisConfig.BG_GRAY_NAME;

public class HoldUI extends UIComponent{
    @Override
    public void render(SpriteBatch batch) {
        renderBackGround(batch, BG_GRAY_NAME);
    }
    public HoldUI(){
        super(new Pos(STATS_UI_HEIGHT * (int)(CELL_SIZE * TEXTURE_SCALE), 0), HOLD_UI_WIDTH, HOLD_UI_HEIGHT);
    }
}
