package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.game.randomizer.SpriteBagRand;
import lombok.Data;

@Data
public class GameInfoUI {
    private final ScoreUI score;
    private final SpawnQueueUI spawnQueue;
    private final StatsUI stats;
    private final HoldUI hold;
    public GameInfoUI(SpriteBagRand bagRandomizer) {
        score = new ScoreUI();
        spawnQueue = new SpawnQueueUI(bagRandomizer);
        stats = new StatsUI();
        hold = new HoldUI();
    }

    public void render(SpriteBatch batch) {
        score.render(batch);
        spawnQueue.render(batch);
        stats.render(batch);
        hold.render(batch);
    }

    public void restartGame(){
        stats.reset();
        spawnQueue.reset();
        score.reset();
    }
}
