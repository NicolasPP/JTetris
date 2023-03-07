package com.nicolas.tetris.manager;

import lombok.Data;

import static com.nicolas.tetris.config.TetrisConfig.LINES_PER_LEVEL;

@Data
public class LevelManager {
    private int level = 0;
    private int score = 0;
    private int totalLinesCleared = 0;

    public void processClearedLines(int linesCleared){
        totalLinesCleared += linesCleared;
        level = calculateLevel();
        score += calculateScore(linesCleared);
    }

    public float getTimePerCell() {
        return (float) Math.pow(0.8f - ((level - 1) * 0.007f), level - 1);
    }
    private int calculateScore(int linesCleared){
        switch (linesCleared){
            case 1: return 40 * (level + 1);
            case 2: return 100 * (level + 1);
            case 3: return 300 * (level + 1);
            case 4: return 1200 * (level + 1);
        }
        return 0;
    }
    private int calculateLevel(){
        return Math.floorDiv(totalLinesCleared, LINES_PER_LEVEL);
    }

}
