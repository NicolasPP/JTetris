package com.nicolas.tetris.game.gui;

import com.nicolas.tetris.utils.Pos;
import lombok.Data;

@Data
public abstract class UIComponent {
    private Pos bottomLeft;
    private int width;
    private int height;

    public abstract void render();
}
