package com.nicolas.tetris.manager;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ResourceManager {
    private static TextureAtlas texturesAtlas;

    public static void loadData() {
        texturesAtlas = new TextureAtlas("sprites.txt");
    }

    public static TextureRegion get(String name) {
        Sprite sprite = texturesAtlas.createSprite(name);
        return new TextureRegion(
                sprite.getTexture(),
                sprite.getRegionX(),
                sprite.getRegionY(),
                sprite.getRegionWidth(),
                sprite.getRegionHeight()
        );
    }
}
