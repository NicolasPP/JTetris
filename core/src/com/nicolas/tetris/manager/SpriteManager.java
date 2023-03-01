package com.nicolas.tetris.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.sprites.BoardSprite;
import com.nicolas.tetris.sprites.TetrominoSprite;
import com.nicolas.tetris.utils.Pair;

import java.util.ArrayList;

public class SpriteManager {
    final ArrayList<TetrominoSprite> tetrominos = new ArrayList<>();
    final BoardSprite board;

    public SpriteManager() {
       final float SPRITE_SCALE = 0.25f;

        board = new BoardSprite(new Pair<>(0, 0), SPRITE_SCALE);

        tetrominos.add(new TetrominoSprite(
                "shape_I",
                "LightBlue",
                "ghost_shape_I",
                Pair.createPair(0, 0),
                SPRITE_SCALE));

        tetrominos.add(new TetrominoSprite(
                "shape_J",
                "Blue",
                "ghost_shape_J",
                Pair.createPair(0, 50),
                SPRITE_SCALE));

        tetrominos.add(new TetrominoSprite(
                "shape_L",
                "Orange",
                "ghost_shape_L",
                Pair.createPair(0, 100),
                SPRITE_SCALE));

        tetrominos.add(new TetrominoSprite(
                "shape_O",
                "Yellow",
                "ghost_shape_O",
                Pair.createPair(0, 150),
                SPRITE_SCALE));

        tetrominos.add(new TetrominoSprite(
                "shape_S",
                "Green",
                "ghost_shape_S",
                Pair.createPair(0, 200),
                SPRITE_SCALE));

        tetrominos.add(new TetrominoSprite(
                "shape_T",
                "Purple",
                "ghost_shape_T",
                Pair.createPair(0, 250),
                SPRITE_SCALE));

        tetrominos.add(new TetrominoSprite(
                "shape_Z",
                "Red",
                "ghost_shape_Z",
                Pair.createPair(0, 300),
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
