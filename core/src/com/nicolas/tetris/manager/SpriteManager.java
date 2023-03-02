package com.nicolas.tetris.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.game.CellType;
import com.nicolas.tetris.game.GameState;
import com.nicolas.tetris.sprites.BoardSprite;
import com.nicolas.tetris.sprites.TetrominoSprite;

import java.util.ArrayList;

import static com.nicolas.tetris.config.TetrisConfig.SHAPE_I_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_J_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_L_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_O_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_S_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_T_NAME;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_Z_NAME;

import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_I_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_J_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_L_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_O_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_S_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_T_NAME;
import static com.nicolas.tetris.config.TetrisConfig.GHOST_SHAPE_Z_NAME;


import static com.nicolas.tetris.config.TetrisConfig.LIGHT_BLUE;
import static com.nicolas.tetris.config.TetrisConfig.BLUE;
import static com.nicolas.tetris.config.TetrisConfig.ORANGE;
import static com.nicolas.tetris.config.TetrisConfig.YELLOW;
import static com.nicolas.tetris.config.TetrisConfig.GREEN;
import static com.nicolas.tetris.config.TetrisConfig.PURPLE;
import static com.nicolas.tetris.config.TetrisConfig.RED;

import static com.nicolas.tetris.config.TetrisConfig.SHAPE_I_MAP;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_J_MAP;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_L_MAP;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_O_MAP;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_S_MAP;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_T_MAP;
import static com.nicolas.tetris.config.TetrisConfig.SHAPE_Z_MAP;

public class SpriteManager implements InputProcessor {
    private final ArrayList<TetrominoSprite> tetrominos;
    private final BoardSprite board;

    private final GameState gameState;



    public SpriteManager() {
        board = new BoardSprite(new Vector2(0, 0));
        tetrominos = new ArrayList<>();
        gameState = new GameState();
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);

        tetrominos.add(new TetrominoSprite(SHAPE_I_NAME, GHOST_SHAPE_I_NAME, LIGHT_BLUE, CellType.I, SHAPE_I_MAP));
        tetrominos.add(new TetrominoSprite(SHAPE_J_NAME, GHOST_SHAPE_J_NAME, BLUE, CellType.J, SHAPE_J_MAP));
        tetrominos.add(new TetrominoSprite(SHAPE_L_NAME, GHOST_SHAPE_L_NAME, ORANGE, CellType.L, SHAPE_L_MAP));
        tetrominos.add(new TetrominoSprite(SHAPE_O_NAME, GHOST_SHAPE_O_NAME, YELLOW, CellType.O, SHAPE_O_MAP));
        tetrominos.add(new TetrominoSprite(SHAPE_S_NAME, GHOST_SHAPE_S_NAME, GREEN, CellType.S, SHAPE_S_MAP));
        tetrominos.add(new TetrominoSprite(SHAPE_T_NAME, GHOST_SHAPE_T_NAME, PURPLE, CellType.T, SHAPE_T_MAP));
        tetrominos.add(new TetrominoSprite(SHAPE_Z_NAME, GHOST_SHAPE_Z_NAME, RED, CellType.Z, SHAPE_Z_MAP));
    }

    public void render(SpriteBatch batch) {
        board.render(batch);
        tetrominos.forEach(tetrominoSprite -> tetrominoSprite.render(batch));
    }

    public void update() {
        board.update();
        tetrominos.forEach(TetrominoSprite::update);
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
