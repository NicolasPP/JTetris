package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.game.cell.CellType;
import com.nicolas.tetris.sprites.TetrominoSprite;

import static com.nicolas.tetris.config.TetrisConfig.STATS_UI_ROWS;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;
import static com.nicolas.tetris.config.TetrisConfig.HOLD_UI_COLS;
import static com.nicolas.tetris.config.TetrisConfig.HOLD_UI_ROWS;
import static com.nicolas.tetris.config.TetrisConfig.BG_GRAY_NAME;

public class HoldUI extends UIComponent {

    private CellType holdTetromino = null;
    private Vector2 holdPos = null;
    public void render(SpriteBatch batch) {
        renderBackGround(batch, BG_GRAY_NAME);
        if (holdPos == null || holdTetromino == null) return;
        TetrominoSprite.get(holdTetromino).renderTexture(batch, holdPos, holdTetromino == CellType.I);
    }

    public HoldUI() {
        super(new Vector2(0, STATS_UI_ROWS * CELL_SIZE * TEXTURE_SCALE), HOLD_UI_COLS, HOLD_UI_ROWS);
    }

    public void setHoldTetromino(CellType holdTetromino) {
        this.holdTetromino = holdTetromino;
        float x = getBottomLeft().x + (getSize().x - TetrominoSprite.get(holdTetromino).getTextureSize().x) / 2;
        float y = getBottomLeft().y + (getSize().y - TetrominoSprite.get(holdTetromino).getTextureSize().y) / 2;

        if (holdTetromino == CellType.I){
            x = getBottomLeft().x + (getSize().x - TetrominoSprite.get(holdTetromino).getTextureSize().y) / 2;
            y = getBottomLeft().y + (getSize().y - TetrominoSprite.get(holdTetromino).getTextureSize().x) / 2;
        }

        holdPos = new Vector2(x, y);
    }
}
