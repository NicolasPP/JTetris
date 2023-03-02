package com.nicolas.tetris.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.sprites.BoardSprite;
import com.nicolas.tetris.sprites.TetrominoSprite;

import java.util.ArrayList;

import static com.nicolas.tetris.config.TetrisConfig.SHAPE_I_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_J_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_L_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_O_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_S_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_T_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_Z_NAME;

import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_I_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_J_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_L_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_O_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_S_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_T_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_Z_NAME;


import static com.nicolas.tetris.config.TetrisConfig.LIGHT_BLUE;
import static com.nicolas.tetris.config.TetrisConfig.BLUE;
import static com.nicolas.tetris.config.TetrisConfig.ORANGE;
import static com.nicolas.tetris.config.TetrisConfig.YELLOW;
import static com.nicolas.tetris.config.TetrisConfig.GREEN;
import static com.nicolas.tetris.config.TetrisConfig.PURPLE;
import static com.nicolas.tetris.config.TetrisConfig.RED;
public class SpriteManager {
    final ArrayList<TetrominoSprite> tetrominos = new ArrayList<>();
    final BoardSprite board;

    public SpriteManager() {
       final float SPRITE_SCALE = 0.25f;

        board = new BoardSprite(new Vector2(0, 0), SPRITE_SCALE);

        tetrominos.add(new TetrominoSprite(
                SHAPE_I_NAME,
                GHOST_SHAPE_I_NAME,
                LIGHT_BLUE,
                new Vector2(0, 0),
                SPRITE_SCALE));

        tetrominos.add(new TetrominoSprite(
                SHAPE_J_NAME,
                GHOST_SHAPE_J_NAME,
                BLUE,
                new Vector2(0, 50),
                SPRITE_SCALE));

        tetrominos.add(new TetrominoSprite(
                SHAPE_L_NAME,
                GHOST_SHAPE_L_NAME,
                ORANGE,
                new Vector2(0, 100),
                SPRITE_SCALE));

        tetrominos.add(new TetrominoSprite(
                SHAPE_O_NAME,
                GHOST_SHAPE_O_NAME,
                YELLOW,
                new Vector2(0, 150),
                SPRITE_SCALE));

        tetrominos.add(new TetrominoSprite(
                SHAPE_S_NAME,
                GHOST_SHAPE_S_NAME,
                GREEN,
                new Vector2(0, 200),
                SPRITE_SCALE));

        tetrominos.add(new TetrominoSprite(
                SHAPE_T_NAME,
                GHOST_SHAPE_T_NAME,
                PURPLE,
                new Vector2(0, 250),
                SPRITE_SCALE));

        tetrominos.add(new TetrominoSprite(
                SHAPE_Z_NAME,
                GHOST_SHAPE_Z_NAME,
                RED,
                new Vector2(0, 300),
                SPRITE_SCALE));
    }

    public void render(SpriteBatch batch) {
        board.render(batch);
        for (TetrominoSprite t : tetrominos) {
            t.render(batch);
        }
    }

    public void update() {
        board.update();
        for (TetrominoSprite t : tetrominos) {
            t.update();
        }
    }

}
