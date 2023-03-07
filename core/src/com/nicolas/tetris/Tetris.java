package com.nicolas.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.nicolas.tetris.manager.ResourceManager;
import com.nicolas.tetris.manager.GameManager;
import com.nicolas.tetris.sprites.TetrominoSprite;

import static com.nicolas.tetris.config.TetrisConfig.PIXEL_FONT_TTF;

public class Tetris extends ApplicationAdapter {
    SpriteBatch batch;
    GameManager gameManager;

    @Override
    public void create() {
        ResourceManager.loadData(PIXEL_FONT_TTF);
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
