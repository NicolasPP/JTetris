package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.manager.ResourceManager;
import com.nicolas.tetris.utils.Pair;

import java.util.HashMap;
import java.util.List;


public abstract class TetrisSprite {
    Sprite sprite;
    HashMap<String, Sprite> subSprites;
    Pair<Integer, Integer> bottomLeft;
    final private float scale;

    public TetrisSprite(
            String spriteName,
            List<String> subSpriteNames,
            Pair<Integer, Integer> bottomLeft,
            float scale
    ) {
        this.sprite = ResourceManager.get(spriteName);
        this.scale = validateScale(scale);
        this.bottomLeft = bottomLeft;
        this.subSprites = new HashMap<>();
        init(subSpriteNames);
    }

    public abstract void render(SpriteBatch batch);

    public abstract void update();

    private void scaleSprite(Sprite sprite) {
        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
    }

    private void init(List<String> subSpriteNames) {
        scaleSprite(sprite);
        for (String subSpriteName : subSpriteNames) {
            subSprites.put(subSpriteName, ResourceManager.get(subSpriteName));
            scaleSprite(subSprites.get(subSpriteName));
            subSprites.get(subSpriteName).setPosition(bottomLeft.getFirst(), bottomLeft.getSecond());

        }
        sprite.setPosition(bottomLeft.getFirst(), bottomLeft.getSecond());
    }

    private float validateScale(float scale) {
        if (sprite.getHeight() % (scale * sprite.getHeight()) != 0 ||
                sprite.getWidth() % (scale * sprite.getWidth()) != 0) {
//            invalid scale value
            return 1f;
        }
        return scale;
    }
}
