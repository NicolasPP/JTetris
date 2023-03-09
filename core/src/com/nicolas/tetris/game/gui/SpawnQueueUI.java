package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.game.cell.CellType;
import com.nicolas.tetris.game.randomizer.SpriteBagRand;
import com.nicolas.tetris.sprites.TetrominoSprite;

import static com.nicolas.tetris.config.TetrisConfig.SCORE_UI_ROWS;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;
import static com.nicolas.tetris.config.TetrisConfig.STATS_UI_COLS;
import static com.nicolas.tetris.config.TetrisConfig.BOARD_COLS;
import static com.nicolas.tetris.config.TetrisConfig.SPAWN_QUEUE_UI_COLS;
import static com.nicolas.tetris.config.TetrisConfig.SPAWN_QUEUE_UI_ROWS;
import static com.nicolas.tetris.config.TetrisConfig.BG_BLACK_NAME;

public class SpawnQueueUI extends UIComponent {

    final private Vector2[] positions;
    final SpriteBagRand bagRandomizer;

    public SpawnQueueUI(SpriteBagRand randomizer) {
        super(new Vector2((STATS_UI_COLS + BOARD_COLS) * (int) (CELL_SIZE * TEXTURE_SCALE),
                        SCORE_UI_ROWS * (int) (CELL_SIZE * TEXTURE_SCALE)),
                SPAWN_QUEUE_UI_COLS, SPAWN_QUEUE_UI_ROWS);
        bagRandomizer = randomizer;
        positions = new Vector2[randomizer.peekWholeQueue().length];
        updatePositions();
    }

    public void updatePositions(){
        Vector2 currentPos = new Vector2(getBottomLeft());

        CellType[] queueWholePeek = bagRandomizer.peekWholeQueue();

        int totalRows = 0;
        for (CellType type : queueWholePeek) {
            if (type == CellType.I) totalRows++;
            else totalRows += 2;
        }

        float vertSpacing = (getSize().y - (totalRows * (CELL_SIZE * TEXTURE_SCALE))) / 8;

        for (int index = queueWholePeek.length - 1; index >= 0; index--) {
            CellType type = queueWholePeek[index];
            TetrominoSprite tetromino = TetrominoSprite.get(type);

            float tetrominoW = tetromino.getTextureSize().x;
            float tetrominoH = tetromino.getTextureSize().y;

            float horzSpacing = (getSize().x - tetrominoW) / 2;

            currentPos.x = getBottomLeft().x + horzSpacing;
            currentPos.y += vertSpacing;

            if (type == CellType.I) {
                currentPos.x = getBottomLeft().x;
                tetrominoH = tetromino.getTextureSize().x;
            }

            positions[index] = new Vector2(currentPos);

            currentPos.y += tetrominoH;
        }
    }

    public void render(SpriteBatch batch) {
        renderBackGround(batch, BG_BLACK_NAME);
        CellType[] queueWholePeek = bagRandomizer.peekWholeQueue();

        for (int index = queueWholePeek.length - 1; index >= 0; index--) {
            CellType type = queueWholePeek[index];
            TetrominoSprite tetromino = TetrominoSprite.get(type);
            tetromino.renderTexture(batch, positions[index], type == CellType.I);
        }
    }
}
