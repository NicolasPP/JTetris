package com.nicolas.tetris.game.state;

import com.nicolas.tetris.utils.Index;

public enum ShiftDirection {
    DOWN, RIGHT, LEFT;

    public Index getDirectionOffset() {
        switch (this) {
            case DOWN:
                return new Index(-1, 0);
            case RIGHT:
                return new Index(0, 1);
            case LEFT:
                return new Index(0, -1);
        }
        return new Index(0, 0);
    }

}
