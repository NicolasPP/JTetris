package com.nicolas.tetris.game.cell;

import com.nicolas.tetris.utils.Pos;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cell {
    private CellType type;
    private UpdateType updateType;
    private Pos bottomLeft;
    public boolean isLocked() {return updateType == UpdateType.LOCKED;}
    public boolean isNotEmpty(){return type != CellType.EMPTY;}
    public boolean isNotSpawn(){
        return type != CellType.SPAWN;
    }
    public boolean isFalling(){
        return updateType == UpdateType.FALLING;
    }
}
