package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

public class TetrominoSprite extends TetrisSprite {
    String ghostName;
    String colorName;

    int[][] cellMap;

    public TetrominoSprite(
            String textureName,
            String ghostTextureName,
            String colorTextureName,
            int [][] cMap
            ) {
        super(textureName, Arrays.asList(colorTextureName, ghostTextureName));
        ghostName = ghostTextureName;
        colorName = colorTextureName;
        cellMap = cMap;
    }

    @Override
    public void render(SpriteBatch batch, Vector2 position) {
        batch.draw(subTextures.get(colorName), position.x, position.y, subTextureSize.x, subTextureSize.y);
    }

    @Override
    public void update() {
//        System.out.println("Not Implemented");
    }
}
