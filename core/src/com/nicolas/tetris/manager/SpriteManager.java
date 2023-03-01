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
        float SPRITE_SCALE = 0.25f;

        board = new BoardSprite(
                ResourceManager.get("Board"),
                new Pair<>(0, 0),
                SPRITE_SCALE,
                ResourceManager.get("BG_gray"),
                ResourceManager.get("BG_black"),
                ResourceManager.get("Border")
        );

        tetrominos.add(new TetrominoSprite(
                ResourceManager.get("shape_I"),
                Pair.createPair(0, 0),
                SPRITE_SCALE,
                ResourceManager.get("LightBlue"),
                ResourceManager.get("ghost_shape_I")
        ));

        tetrominos.add(new TetrominoSprite(
                ResourceManager.get("shape_J"),
                Pair.createPair(0, 20),
                SPRITE_SCALE,
                ResourceManager.get("Blue"),
                ResourceManager.get("ghost_shape_J")
        ));

        tetrominos.add(new TetrominoSprite(
                ResourceManager.get("shape_L"),
                Pair.createPair(0, 30),
                SPRITE_SCALE,
                ResourceManager.get("Orange"),
                ResourceManager.get("ghost_shape_L")
        ));

        tetrominos.add(new TetrominoSprite(
                ResourceManager.get("shape_O"),
                Pair.createPair(0, 40),
                SPRITE_SCALE,
                ResourceManager.get("Yellow"),
                ResourceManager.get("ghost_shape_O")
        ));

        tetrominos.add(new TetrominoSprite(
                ResourceManager.get("shape_S"),
                Pair.createPair(0, 50),
                SPRITE_SCALE,
                ResourceManager.get("Green"),
                ResourceManager.get("ghost_shape_S")
        ));

        tetrominos.add(new TetrominoSprite(
                ResourceManager.get("shape_T"),
                Pair.createPair(0, 60),
                SPRITE_SCALE,
                ResourceManager.get("Purple"),
                ResourceManager.get("ghost_shape_T")
        ));

        tetrominos.add(new TetrominoSprite(
                ResourceManager.get("shape_Z"),
                Pair.createPair(0, 70),
                SPRITE_SCALE,
                ResourceManager.get("Red"),
                ResourceManager.get("ghost_shape_Z")
        ));

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
