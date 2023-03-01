package com.nicolas.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.nicolas.tetris.manager.ResourceManager;
import com.nicolas.tetris.manager.SpriteManager;

public class Tetris extends ApplicationAdapter {
    SpriteBatch batch;
    SpriteManager spriteManager;

    @Override
    public void create() {
        ResourceManager.loadData();
        batch = new SpriteBatch();
        spriteManager = new SpriteManager();
    }

    @Override
    public void render() {
        spriteManager.update();
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        spriteManager.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
