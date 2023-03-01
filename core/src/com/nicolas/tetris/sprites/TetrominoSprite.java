package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.utils.Pair;

public class TetrominoSprite extends TetrisSprite {
    Sprite square;
    Sprite ghostSprite;

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void update() {
        System.out.println("Not Implemented");
    }

    @Override
    public void scaleSprites() {
        try {
            square = scale(square);
            ghostSprite = scale(ghostSprite);
            sprite = scale(sprite);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TetrominoSprite(
            Sprite sprite,
            Pair<Integer, Integer> bottomLeft,
            float scale,
            Sprite square,
            Sprite ghostSprite) {
        super(sprite, bottomLeft, scale);
        this.square = square;
        this.ghostSprite = ghostSprite;
        scaleSprites();
    }
}
