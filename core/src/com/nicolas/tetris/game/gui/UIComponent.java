package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.sprites.BoardSprite;
import com.nicolas.tetris.utils.Pos;
import lombok.Data;

import java.util.stream.IntStream;

import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;

@Data
public abstract class UIComponent {
    private Pos bottomLeft;
    private int width;
    private int height;

    public UIComponent(Pos pos, int w, int h){
        bottomLeft = pos;
        width = w;
        height = h;
    }

    public abstract void render(SpriteBatch batch);

    public void renderBackGround(SpriteBatch batch, String subTextureName){
        int cellSize = (int) (CELL_SIZE * TEXTURE_SCALE);
        IntStream.range(0, height).forEach(row -> IntStream.range(0, width).forEach(col ->
            BoardSprite.getInstance().renderSubTexture(batch, subTextureName,
            new Pos(bottomLeft.getRow() + (cellSize * row), bottomLeft.getCol() + (cellSize * col)))
        ));
    }
}
