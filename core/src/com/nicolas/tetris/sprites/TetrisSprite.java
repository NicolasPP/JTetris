package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.manager.ResourceManager;

import java.util.HashMap;
import java.util.List;

import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;


public abstract class TetrisSprite {
    Sprite sprite;
    HashMap<String, Sprite> subSprites;
    Vector2 bottomLeft;
    final private float scale;

    public TetrisSprite(
            String spriteName,
            List<String> subSpriteNames,
            Vector2 bottomLeft,
            float scale
    ) {
        this.sprite = ResourceManager.get(spriteName);
        this.scale = TetrisSprite.validateScale(scale);
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
            subSprites.get(subSpriteName).setPosition(bottomLeft.x, bottomLeft.y);

        }
        sprite.setPosition(bottomLeft.x, bottomLeft.y);
    }

    static private float validateScale(float scale) {
//            invalid scale value
        if (CELL_SIZE % (scale * CELL_SIZE) != 0) { return 1f; }
        return scale;
    }
}
