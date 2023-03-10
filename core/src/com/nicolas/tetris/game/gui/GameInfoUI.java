package com.nicolas.tetris.game.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.game.randomizer.SpriteBagRand;
import lombok.Data;

@Data
public class GameInfoUI {
    private final LevelUI levelUI;
    private final SpawnQueueUI spawnQueueUI;
    private final StatsUI statsUI;
    private final HoldUI holdUI;
    public GameInfoUI(SpriteBagRand bagRandomizer) {
        levelUI = new LevelUI();
        spawnQueueUI = new SpawnQueueUI(bagRandomizer);
        statsUI = new StatsUI();
        holdUI = new HoldUI();
    }

    public void render(SpriteBatch batch) {
        levelUI.render(batch);
        spawnQueueUI.render(batch);
        statsUI.render(batch);
        holdUI.render(batch);
    }

    public void restartGame(){
        statsUI.reset();
        spawnQueueUI.reset();
        levelUI.reset();
        holdUI.reset();
    }
}
