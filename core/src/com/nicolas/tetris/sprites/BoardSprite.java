package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.utils.Pair;

public class BoardSprite extends TetrisSprite {
    Sprite grayBackGroundSprite;
    Sprite blackBackGroundSprite;
    Sprite borderSprite;

//    COLS = 12;
//    ROWS = 22;

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
            grayBackGroundSprite = scale(grayBackGroundSprite);
            blackBackGroundSprite = scale(blackBackGroundSprite);
            borderSprite = scale(borderSprite);
            sprite = scale(sprite);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public BoardSprite(
            Sprite sprite,
            Pair<Integer, Integer> bottomLeft,
            float scale,
            Sprite grayBackGroundSprite,
            Sprite blackBackGroundSprite,
            Sprite borderSprite) {
        super(sprite, bottomLeft, scale);
        this.grayBackGroundSprite = grayBackGroundSprite;
        this.blackBackGroundSprite = blackBackGroundSprite;
        this.borderSprite = borderSprite;
        scaleSprites();
    }

}
