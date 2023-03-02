package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;

public class TetrominoSprite extends TetrisSprite {
    String ghostName;
    String colorName;

    public TetrominoSprite(String textureName, String ghostTextureName, String colorTextureName) {
        super(textureName, Arrays.asList(colorTextureName, ghostTextureName));
        ghostName = ghostTextureName;
        colorName = colorTextureName;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, 0, 0, textureSize.x, textureSize.y);
    }

    @Override
    public void update() {
//        System.out.println("Not Implemented");
    }
}
