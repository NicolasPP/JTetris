package com.nicolas.tetris.game;

import com.nicolas.tetris.utils.Pos;

public enum ShiftDirection {
    DOWN, RIGHT, LEFT;

    public Pos getDirectionOffset() {
        switch (this) {
            case DOWN:
                return new Pos(-1, 0);
            case RIGHT:
                return new Pos(0, 1);
            case LEFT:
                return new Pos(0, -1);
        }
        return new Pos(0, 0);
    }

}
