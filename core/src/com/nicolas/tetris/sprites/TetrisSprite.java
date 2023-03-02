package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.manager.ResourceManager;

import java.util.HashMap;
import java.util.List;

import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;


public abstract class TetrisSprite {
    TextureRegion texture;
    HashMap<String, TextureRegion> subTextures;
    Vector2 textureSize;
    Vector2 subTextureSize;


    public TetrisSprite(String textureName, List<String> subTextureNames) {
        this.texture = ResourceManager.get(textureName);
        this.subTextures = new HashMap<>();
        this.textureSize = new Vector2(
                TEXTURE_SCALE * texture.getRegionWidth(),
                TEXTURE_SCALE * texture.getRegionHeight());
        this.subTextureSize = new Vector2(
                TEXTURE_SCALE * CELL_SIZE,
                TEXTURE_SCALE * CELL_SIZE);
        init(subTextureNames);
    }

    public abstract void render(SpriteBatch batch);

    public abstract void update();

    private void init(List<String> subTextureNames) {
        subTextureNames.forEach(subTextureName ->
                subTextures.put(
                        subTextureName,
                        ResourceManager.get(subTextureName)
                )
        );
    }
}
