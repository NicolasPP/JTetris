package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.manager.ResourceManager;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;

@Data
public abstract class TetrisSprite {
    private final TextureRegion texture;
    private final HashMap<String, TextureRegion> subTextures;
    private final Vector2 textureSize;
    private final Vector2 subTextureSize;


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

    public void render(SpriteBatch batch, Vector2 position) {
        batch.draw(getTexture(), position.x, position.y, getTextureSize().x, getTextureSize().y);
    }

    private void init(List<String> subTextureNames) {
        subTextureNames.forEach(subTextureName ->
                subTextures.put(
                        subTextureName,
                        ResourceManager.get(subTextureName)
                )
        );
    }
}
