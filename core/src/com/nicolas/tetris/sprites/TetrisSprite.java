package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.utils.Pair;


public abstract class TetrisSprite {
    Sprite sprite;
    Pair<Integer, Integer> bottomLeft;
    final float scale;

    public abstract void render(SpriteBatch batch);

    public abstract void update();

    public abstract void scaleSprites();

    public Sprite scale(Sprite sprite) throws Exception {
        if (sprite.getHeight() % (scale * sprite.getHeight()) != 0 ||
                sprite.getWidth() % (scale * sprite.getWidth()) != 0) {
            throw new Exception("scale not valid");
        }
        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        return sprite;
    }

    public TetrisSprite(Sprite sprite, Pair<Integer, Integer> bottomLeft, float scale) {
        this.sprite = sprite;
        this.bottomLeft = bottomLeft;
        this.scale = scale;
        sprite.setPosition(bottomLeft.getFirst(), bottomLeft.getSecond());
    }

}
