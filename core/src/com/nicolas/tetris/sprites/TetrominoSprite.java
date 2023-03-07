package com.nicolas.tetris.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.game.cell.CellType;
import com.nicolas.tetris.game.state.TetrominoState;
import com.nicolas.tetris.utils.Pos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

import static com.nicolas.tetris.config.TetrisConfig.SHAPE_I_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_J_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_L_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_O_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_S_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_T_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_Z_NAME;

import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_I_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_J_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_L_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_O_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_S_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_T_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_Z_NAME;

import static com.nicolas.tetris.config.TetrisConfig.LIGHT_BLUE;
import static com.nicolas.tetris.config.TetrisConfig.BLUE;
import static com.nicolas.tetris.config.TetrisConfig.ORANGE;
import static com.nicolas.tetris.config.TetrisConfig.YELLOW;
import static com.nicolas.tetris.config.TetrisConfig.GREEN;
import static com.nicolas.tetris.config.TetrisConfig.PURPLE;
import static com.nicolas.tetris.config.TetrisConfig.RED;

import static com.nicolas.tetris.config.TetrisConfig.SHAPE_I_MAP;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_J_MAP;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_L_MAP;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_O_MAP;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_S_MAP;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_T_MAP;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_Z_MAP;

import static com.nicolas.tetris.config.TetrisConfig.SPAWN_ROW;
import static com.nicolas.tetris.config.TetrisConfig.SPAWN_COl;


public class TetrominoSprite extends TetrisSprite {
    private static HashMap<CellType, TetrominoSprite> tetrominos;
    private final String ghostName;
    private final String colorName;
    private final CellType cellType;

    final int[][] cellMap;

    public static void createAll() {
        tetrominos = new HashMap<>();
        tetrominos.put(CellType.I, new TetrominoSprite(CellType.I, SHAPE_I_NAME, GHOST_SHAPE_I_NAME, LIGHT_BLUE, SHAPE_I_MAP));
        tetrominos.put(CellType.J, new TetrominoSprite(CellType.J, SHAPE_J_NAME, GHOST_SHAPE_J_NAME, BLUE, SHAPE_J_MAP));
        tetrominos.put(CellType.L, new TetrominoSprite(CellType.L, SHAPE_L_NAME, GHOST_SHAPE_L_NAME, ORANGE, SHAPE_L_MAP));
        tetrominos.put(CellType.O, new TetrominoSprite(CellType.O, SHAPE_O_NAME, GHOST_SHAPE_O_NAME, YELLOW, SHAPE_O_MAP));
        tetrominos.put(CellType.S, new TetrominoSprite(CellType.S, SHAPE_S_NAME, GHOST_SHAPE_S_NAME, GREEN, SHAPE_S_MAP));
        tetrominos.put(CellType.T, new TetrominoSprite(CellType.T, SHAPE_T_NAME, GHOST_SHAPE_T_NAME, PURPLE, SHAPE_T_MAP));
        tetrominos.put(CellType.Z, new TetrominoSprite(CellType.Z, SHAPE_Z_NAME, GHOST_SHAPE_Z_NAME, RED, SHAPE_Z_MAP));
    }

    public static TetrominoSprite get(CellType cellType) {
        return tetrominos.get(cellType);
    }


    private TetrominoSprite(
            CellType type,
            String textureName,
            String ghostTextureName,
            String colorTextureName,
            int[][] cMap
    ) {
        super(textureName, Arrays.asList(colorTextureName, ghostTextureName));
        ghostName = ghostTextureName;
        colorName = colorTextureName;
        cellMap = cMap;
        cellType = type;
    }

    public void renderSquare(SpriteBatch batch, Pos position) {
        if (position.getRow() < 0 || position.getCol() < 0) {
            return;
        }
        batch.draw(getSubTextures().get(colorName),
                position.getRow(), position.getCol(),
                getSubTextureSize().x, getSubTextureSize().y);
    }

    public int[][] getCellMap() {
        return cellMap;
    }

    public int[][] getCellMapClone() {
        if (cellMap == null) return null;
        final int[][] cellMapClone = new int[cellMap.length][];
        IntStream.range(0, cellMap.length).forEach(index -> cellMapClone[index] = cellMap[index].clone());
        return cellMapClone;
    }

    public CellType getCellType() {
        return cellType;
    }

    public TetrominoState getNewState() {
        return TetrominoState.builder()
                .cellMap(getCellMapClone())
                .type(cellType)
                .pos(new Pos(SPAWN_ROW, SPAWN_COl))
                .build();
    }
}
