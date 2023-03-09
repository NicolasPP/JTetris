package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.sprites.BoardSprite;
import lombok.Data;

import java.util.stream.IntStream;

import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;

@Data
public abstract class UIComponent {
    private Vector2 bottomLeft;
    private Vector2 size;
    private int cols;
    private int rows;

    public UIComponent(Vector2 pos, int c, int r) {
        bottomLeft = pos;
        size = new Vector2(
                c * (CELL_SIZE * TEXTURE_SCALE),
                r * (CELL_SIZE * TEXTURE_SCALE));
        cols = c;
        rows = r;
    }

    public void renderBackGround(SpriteBatch batch, String subTextureName) {
        int cellSize = (int) (CELL_SIZE * TEXTURE_SCALE);
        IntStream.range(0, rows).forEach(row -> IntStream.range(0, cols).forEach(col ->
                BoardSprite.getInstance().renderSubTexture(batch, subTextureName,
                        new Vector2(bottomLeft.x + (cellSize * col), bottomLeft.y + (cellSize * row)),
                        BoardSprite.getInstance().getSubTextureSize(), false)
        ));
    }
}
