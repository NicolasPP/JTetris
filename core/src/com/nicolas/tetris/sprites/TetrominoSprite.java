package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.game.CellType;
import com.nicolas.tetris.game.TetrominoState;

import java.util.Arrays;
import java.util.stream.IntStream;

import static com.nicolas.tetris.config.TetrisConfig.SPAWN_COl;
import static com.nicolas.tetris.config.TetrisConfig.SPAWN_ROW;

public class TetrominoSprite extends TetrisSprite {
    private final String ghostName;
    private final String colorName;
    private final CellType cellType;

    final int[][] cellMap;

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

    public void renderSquare(SpriteBatch batch, Vector2 position) {
        if (position.x < 0 || position.y < 0){
            return;
        }
        batch.draw(getSubTextures().get(colorName), position.x, position.y, getSubTextureSize().x, getSubTextureSize().y);
    }

    public int[][] getCellMap() {return cellMap;}
    public int[][] getCellMapClone() {
        if (cellMap == null) return null;
        final int[][] cellMapClone = new int[cellMap.length][];
        IntStream.range(0, cellMap.length).forEach(index -> cellMapClone[index] = cellMap[index].clone());
        return cellMapClone;
    }
    public CellType getCellType() {return cellType;}

    public TetrominoState getNewState(){
        return TetrominoState.builder()
                .cellMap(getCellMapClone())
                .type(cellType)
                .pos(new Vector2(SPAWN_ROW, SPAWN_COl))
                .build();
    }
}
