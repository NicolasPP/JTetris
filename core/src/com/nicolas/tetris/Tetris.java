package com.nicolas.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import com.nicolas.tetris.manager.ResourceManager;
import jdk.internal.loader.Resource;

public class Tetris extends ApplicationAdapter {
	SpriteBatch batch;

	@Override
	public void create () {
		ResourceManager.loadData();
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		ResourceManager.get("Board").draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
