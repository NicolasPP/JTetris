package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.manager.ResourceManager;
import com.nicolas.tetris.utils.Pos;
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
        this.texture = ResourceManager.getTexture(textureName);
        this.subTextures = new HashMap<>();
        this.textureSize = new Vector2(
                TEXTURE_SCALE * texture.getRegionWidth(),
                TEXTURE_SCALE * texture.getRegionHeight());
        this.subTextureSize = new Vector2(
                TEXTURE_SCALE * CELL_SIZE,
                TEXTURE_SCALE * CELL_SIZE);
        init(subTextureNames);
    }

    public void renderTexture(SpriteBatch batch, Pos position) {
        batch.draw(getTexture(), position.getCol(), position.getRow(), getTextureSize().x, getTextureSize().y);
    }

    public void renderSubTexture(SpriteBatch batch, String subTextureName, Pos position) {
        TextureRegion subTexture = getSubTextures().get(subTextureName);
        if (subTexture == null) return;
        batch.draw(subTexture, position.getCol(), position.getRow(), getSubTextureSize().x, getSubTextureSize().y);
    }

    private void init(List<String> subTextureNames) {
        subTextureNames.forEach(subTextureName ->
                subTextures.put(
                        subTextureName,
                        ResourceManager.getTexture(subTextureName)
                )
        );
    }
}
