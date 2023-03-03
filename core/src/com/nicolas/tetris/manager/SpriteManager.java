package com.nicolas.tetris.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.game.Cell;
import com.nicolas.tetris.game.CellType;
import com.nicolas.tetris.game.GameState;
import com.nicolas.tetris.game.UpdateType;
import com.nicolas.tetris.sprites.BoardSprite;
import com.nicolas.tetris.sprites.TetrominoSprite;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

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
    private final HashMap<CellType, TetrominoSprite> tetrominos;
    private final BoardSprite board;

    private final GameState gameState;

    private final int level;

    private float accumulator;


    public SpriteManager() {
        board = new BoardSprite();
        tetrominos = new HashMap<>();
        gameState = new GameState(new Vector2(0, 0));
        level = 1;
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);

        tetrominos.put(CellType.I, new TetrominoSprite(CellType.I, SHAPE_I_NAME, GHOST_SHAPE_I_NAME, LIGHT_BLUE, SHAPE_I_MAP));
        tetrominos.put(CellType.J, new TetrominoSprite(CellType.J, SHAPE_J_NAME, GHOST_SHAPE_J_NAME, BLUE, SHAPE_J_MAP));
        tetrominos.put(CellType.L, new TetrominoSprite(CellType.L, SHAPE_L_NAME, GHOST_SHAPE_L_NAME, ORANGE, SHAPE_L_MAP));
        tetrominos.put(CellType.O, new TetrominoSprite(CellType.O, SHAPE_O_NAME, GHOST_SHAPE_O_NAME, YELLOW, SHAPE_O_MAP));
        tetrominos.put(CellType.S, new TetrominoSprite(CellType.S, SHAPE_S_NAME, GHOST_SHAPE_S_NAME, GREEN, SHAPE_S_MAP));
        tetrominos.put(CellType.T, new TetrominoSprite(CellType.T, SHAPE_T_NAME, GHOST_SHAPE_T_NAME, PURPLE, SHAPE_T_MAP));
        tetrominos.put(CellType.Z, new TetrominoSprite(CellType.Z, SHAPE_Z_NAME, GHOST_SHAPE_Z_NAME, RED, SHAPE_Z_MAP));
    }

    public void render(SpriteBatch batch) {
        board.render(batch, gameState.getBoardPos());

        Arrays.stream(gameState.getState()).forEach(
                row -> Arrays.stream(row).filter(Cell::isCellTypeNotEmpty).forEach(
                        cell -> tetrominos.get(cell.getType()).renderSquare(batch, cell.getBottomLeft())));

    }

    public void update(float dt) {
        accumulator += dt;
        if (accumulator >= getTimePerCell()) {
            if (gameState.getCanSpawn()) {
                gameState.queueTetrominoSpawn(getRandomTetromino());
            }
            gameState.shift(GameState.ShiftDirection.DOWN, UpdateType.FALLING);
            gameState.spawn();
            accumulator = 0f;
        }
    }

    private TetrominoSprite getRandomTetromino() {
        CellType[] keyArr = new CellType[tetrominos.size()];
        int randomIndex = new Random().nextInt(tetrominos.size());
        tetrominos.keySet().toArray(keyArr);
        return tetrominos.get(keyArr[randomIndex]);
    }

    private float getTimePerCell() {
        return (float) Math.pow(0.8f - ((level - 1) * 0.007f), level - 1);
    }

    @Override
    public boolean keyDown(int i) {
        switch (i) {
            case Input.Keys.RIGHT:
                gameState.shift(GameState.ShiftDirection.RIGHT, UpdateType.FALLING);
                break;
            case Input.Keys.LEFT:
                gameState.shift(GameState.ShiftDirection.LEFT, UpdateType.FALLING);
                break;

        }
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
