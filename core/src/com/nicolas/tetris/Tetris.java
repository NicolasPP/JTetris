package com.nicolas.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.nicolas.tetris.manager.ResourceManager;
import com.nicolas.tetris.manager.GameManager;
import com.nicolas.tetris.sprites.TetrominoSprite;

public class Tetris extends ApplicationAdapter {
    SpriteBatch batch;
    GameManager gameManager;


    @Override
    public void create() {
        ResourceManager.loadData();
        TetrominoSprite.createAll();
        batch = new SpriteBatch();
        gameManager = new GameManager();
    }

    @Override
    public void render() {
        gameManager.update(Gdx.graphics.getDeltaTime());
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        gameManager.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
