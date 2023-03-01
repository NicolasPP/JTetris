package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.utils.Pair;

import java.util.Arrays;

public class BoardSprite extends TetrisSprite {
    final static private String BG_GRAY_NAME = "BG_gray";
    final static private String BG_BLACK_NAME = "BG_black";
    final static private String BORDER_NAME = "Border";
    final static private String BOARD_NAME = "Board";

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


    public BoardSprite(Pair<Integer, Integer> bottomLeft, float scale) {
        super(BOARD_NAME, Arrays.asList(BG_GRAY_NAME, BG_BLACK_NAME, BORDER_NAME), bottomLeft, scale);
    }

}
