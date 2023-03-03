package com.nicolas.tetris.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.game.Cell;
import com.nicolas.tetris.game.CellType;
import com.nicolas.tetris.game.GameState;
import com.nicolas.tetris.sprites.BoardSprite;
import com.nicolas.tetris.sprites.TetrominoSprite;

import java.util.Arrays;
import java.util.HashMap;

import static com.nicolas.tetris.config.TetrisConfig.*;

public class SpriteManager implements InputProcessor {
    private final HashMap<CellType, TetrominoSprite> tetrominos;
    private final BoardSprite board;

    private final GameState gameState;

    private int level;

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

        Arrays.stream(gameState.getState()).forEach(row -> Arrays.stream(row)
                        .filter(Cell::isCellTypeNotEmpty)
                        .forEach(cell -> tetrominos.get(cell.getType()).render(batch, cell.getBottomLeft()))
        );

    }

    public void update(float dt) {
        accumulator += dt;
        if (accumulator >= getTimePerCell()){
            if (gameState.getSpawnQueue().isEmpty()){
                gameState.queueShape(tetrominos.get(CellType.O));
            }
            gameState.spawn();
            accumulator = 0f;
        }
    }

    private float getTimePerCell(){
        return (float) Math.pow(0.8f - ((level -1)*0.007f), level - 1);
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
