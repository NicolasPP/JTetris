package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameInfoUI {
    private final ScoreUI score;
    private final SpawnQueueUI spawnQueue;
    private final StatsUI stats;

    private final HoldUI hold;

    public GameInfoUI() {
        score = new ScoreUI();
        spawnQueue = new SpawnQueueUI();
        stats = new StatsUI();
        hold = new HoldUI();
    }

    public void render(SpriteBatch batch) {
        score.render(batch);
        spawnQueue.render(batch);
        stats.render(batch);
        hold.render(batch);
    }

}
