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
        super(new Pos(SCORE_UI_HEIGHT * (int)(CELL_SIZE * TEXTURE_SCALE),
                        (STATS_UI_WIDTH + BOARD_COLS) * (int)(CELL_SIZE * TEXTURE_SCALE)),
                SPAWN_QUEUE_UI_WIDTH, SPAWN_QUEUE_UI_HEIGHT);
        bagRandomizer = randomizer;
        System.out.println(randomizer.peekQueue().length);
    }

    @Override
    public void render(SpriteBatch batch) {
        renderBackGround(batch, BG_BLACK_NAME);
        int heightUI = (int)(CELL_SIZE * TEXTURE_SCALE) *getHeight();
        int widthUI = (int)(CELL_SIZE * TEXTURE_SCALE) *getWidth();
        Pos currentPos = new Pos(getBottomLeft());
        CellType[] queuePeek = bagRandomizer.peekQueue();
        int totalHeight = 0;
        for(CellType type: queuePeek){
            if (type == CellType.I){
                totalHeight += CELL_SIZE;

            }else{
                totalHeight += TetrominoSprite.get(type).getTextureSize().y;
            }
        }

        Pos spacing = new Pos((heightUI - totalHeight) / (queuePeek.length + 1), 0);
        System.out.println(spacing);

        boolean rotate;
        for (int index = queuePeek.length -1; index >= 0; index--){
            CellType type = queuePeek[index];
            TetrominoSprite tetromino = TetrominoSprite.get(type);
            currentPos.increment(spacing);

            int diff = widthUI - (int)tetromino.getTextureSize().x;
            currentPos.setCol(getBottomLeft().getCol() + diff / 2);


            rotate = false;
            Pos pos = new Pos((int) tetromino.getTextureSize().y,  0);
            if (type == CellType.I) {
                currentPos.setCol(getBottomLeft().getCol());
                rotate = true;
                pos = new Pos((int) tetromino.getTextureSize().x,  0);
            }
            tetromino.renderTexture(batch, currentPos, rotate);

            currentPos.increment(pos);
        }
    }
}
