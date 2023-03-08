package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.game.cell.CellType;
import com.nicolas.tetris.game.randomizer.SpriteBagRand;
import com.nicolas.tetris.sprites.TetrominoSprite;
import com.nicolas.tetris.utils.Pos;

import static com.nicolas.tetris.config.TetrisConfig.SCORE_UI_HEIGHT;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;
import static com.nicolas.tetris.config.TetrisConfig.STATS_UI_WIDTH;
import static com.nicolas.tetris.config.TetrisConfig.BOARD_COLS;
import static com.nicolas.tetris.config.TetrisConfig.SPAWN_QUEUE_UI_WIDTH;
import static com.nicolas.tetris.config.TetrisConfig.SPAWN_QUEUE_UI_HEIGHT;
import static com.nicolas.tetris.config.TetrisConfig.BG_BLACK_NAME;

public class SpawnQueueUI extends UIComponent {
    SpriteBagRand bagRandomizer;

    public SpawnQueueUI(SpriteBagRand randomizer) {
        super(new Pos(SCORE_UI_HEIGHT * (int) (CELL_SIZE * TEXTURE_SCALE),
                        (STATS_UI_WIDTH + BOARD_COLS) * (int) (CELL_SIZE * TEXTURE_SCALE)),
                SPAWN_QUEUE_UI_WIDTH, SPAWN_QUEUE_UI_HEIGHT);
        bagRandomizer = randomizer;
    }

    private int getTotalHeight() {
        int totalHeight = 0;
        for (CellType type : bagRandomizer.peekQueue()) {
            int height = (int) TetrominoSprite.get(type).getTextureSize().y;
            if (type == CellType.I) height = CELL_SIZE;
            totalHeight += height;
        }
        return totalHeight;
    }

    @Override
    public void render(SpriteBatch batch) {
        renderBackGround(batch, BG_BLACK_NAME);
        Pos currentPos = new Pos(getBottomLeft());
        CellType[] queuePeek = bagRandomizer.peekQueue();

        int vertSpacing = (getHeightUI() - getTotalHeight()) / (queuePeek.length + 1);

        boolean rotate;
        for (int index = queuePeek.length - 1; index >= 0; index--) {
            CellType type = queuePeek[index];
            TetrominoSprite tetromino = TetrominoSprite.get(type);

            int horzSpacing = (getWidthUI() - (int) tetromino.getTextureSize().x) / 2;

            currentPos.setRow(currentPos.getRow() + vertSpacing);
            currentPos.setCol(getBottomLeft().getCol() + horzSpacing);


            rotate = false;
            Pos pos = new Pos((int) tetromino.getTextureSize().y, 0);
            if (type == CellType.I) {
                currentPos.setCol(getBottomLeft().getCol());
                pos.setRow((int) tetromino.getTextureSize().x);
                rotate = true;
            }

            tetromino.renderTexture(batch, currentPos, rotate);

            currentPos.increment(pos);
        }
    }
}
