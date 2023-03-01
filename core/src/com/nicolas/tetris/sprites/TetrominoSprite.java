package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.utils.Pair;

import java.util.Arrays;

public class TetrominoSprite extends TetrisSprite {

    String ghostName;
    String colorName;

    public TetrominoSprite(
            String spriteName,
            String colorSquareName,
            String ghostSpriteName,
            Pair<Integer, Integer> bottomLeft,
            float scale
    ) {
        super(spriteName, Arrays.asList(colorSquareName, ghostSpriteName), bottomLeft, scale);
        ghostName = ghostSpriteName;
        colorName = colorSquareName;
    }

    @Override
    public void render(SpriteBatch batch) {
        subSprites.get(colorName).draw(batch);
    }

    @Override
    public void update() {
//        System.out.println("Not Implemented");
    }
}
