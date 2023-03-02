package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

import static com.nicolas.tetris.config.TetrisConfig.BOARD_NAME;
import static com.nicolas.tetris.config.TetrisConfig.BG_GRAY_NAME;
import static com.nicolas.tetris.config.TetrisConfig.BG_BLACK_NAME;
import static com.nicolas.tetris.config.TetrisConfig.BORDER_NAME;

public class BoardSprite extends TetrisSprite {

//    COLS = 12;
//    ROWS = 22;

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }


    @Override
    public void update() {
//        System.out.println("Not Implemented");
    }


    public BoardSprite(Vector2 bottomLeft, float scale) {
        super(BOARD_NAME, Arrays.asList(BG_GRAY_NAME, BG_BLACK_NAME, BORDER_NAME), bottomLeft, scale);
    }

}
