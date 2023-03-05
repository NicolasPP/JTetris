package com.nicolas.tetris.sprites;

import java.util.Arrays;

import static com.nicolas.tetris.config.TetrisConfig.BOARD_NAME;
import static com.nicolas.tetris.config.TetrisConfig.BG_GRAY_NAME;
import static com.nicolas.tetris.config.TetrisConfig.BG_BLACK_NAME;
import static com.nicolas.tetris.config.TetrisConfig.BORDER_NAME;
public class BoardSprite extends TetrisSprite {

    private final static BoardSprite board = new BoardSprite();

    public static BoardSprite get(){return board;}
    public BoardSprite() {
        super(
                BOARD_NAME,
                Arrays.asList(BG_GRAY_NAME, BG_BLACK_NAME, BORDER_NAME)
        );
    }

}
