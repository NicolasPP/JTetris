package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.game.CellType;

import java.util.Arrays;

public class TetrominoSprite extends TetrisSprite {
    private final String ghostName;
    private final String colorName;
    private final CellType cellType;

    int[][] cellMap;

    public TetrominoSprite(
            CellType type,
            String textureName,
            String ghostTextureName,
            String colorTextureName,
            int [][] cMap
            ) {
        super(textureName, Arrays.asList(colorTextureName, ghostTextureName));
        ghostName = ghostTextureName;
        colorName = colorTextureName;
        cellMap = cMap;
        cellType = type;
    }

    @Override
    public void render(SpriteBatch batch, Vector2 position) {
        batch.draw(subTextures.get(colorName), position.x, position.y, subTextureSize.x, subTextureSize.y);
    }

    @Override
    public void update() {
//        System.out.println("Not Implemented");
    }

    public int[][] getCellMap() {return cellMap;}
    public CellType getCellType() {return cellType;}
    public String getGhostName() {return ghostName;}
    public String getColorName() {return colorName;}
}
