package com.nicolas.tetris.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.game.cell.Cell;
import com.nicolas.tetris.game.cell.UpdateType;
import com.nicolas.tetris.game.gui.GameInfoUI;
import com.nicolas.tetris.game.state.GameState;
import com.nicolas.tetris.game.state.ShiftDirection;
import com.nicolas.tetris.utils.RotationDirection;
import com.nicolas.tetris.game.randomizer.SpriteBagRand;
import com.nicolas.tetris.sprites.BoardSprite;
import com.nicolas.tetris.sprites.TetrominoSprite;
import com.nicolas.tetris.utils.Pos;

import java.util.Arrays;

import static com.nicolas.tetris.config.TetrisConfig.GRID_ROWS;
import static com.nicolas.tetris.config.TetrisConfig.SPAWN_ROW_COUNT;
import static com.nicolas.tetris.config.TetrisConfig.STATS_UI_WIDTH;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;

public class GameManager implements InputProcessor {
    private final GameState gameState;
    private final SpriteBagRand bagRandomizer = new SpriteBagRand();
    private final LevelManager levelMan = new LevelManager();
    private final BoardSprite board = BoardSprite.getInstance();
    private final GameInfoUI gameUI = new GameInfoUI();
    private final Pos boardPos = new Pos(0, STATS_UI_WIDTH * (int)(CELL_SIZE * TEXTURE_SCALE));
    private float accumulator = 0f;

    public GameManager() {
        gameState = new GameState(boardPos);
        Gdx.input.setInputProcessor(this);
    }

    public void render(SpriteBatch batch) {
        board.renderTexture(batch, boardPos);

        Arrays.stream(gameState.getState()).forEach(
                row -> Arrays.stream(row).filter(Cell::isNotEmpty).filter(Cell::isNotSpawn).forEach(cell -> {
                    if (cell.getBottomLeft().getRow() > GRID_ROWS - SPAWN_ROW_COUNT) {
                        TetrominoSprite tetromino = TetrominoSprite.get(cell.getType());
                        tetromino.renderSubTexture(batch, tetromino.getColorName(), cell.getBottomLeft());
                    }
                }));

        gameUI.render(batch);
    }

    public void update(float dt) {
        accumulator += dt;
        if (accumulator >= levelMan.getTimePerCell()) {
            if (gameState.isSpawnUnlocked())
                gameState.spawnTetromino(bagRandomizer.getNext());
            gameState.shift(ShiftDirection.DOWN, UpdateType.FALLING);
            levelMan.processClearedLines(gameState.processFilledLines());
            gameState.shift(ShiftDirection.DOWN, UpdateType.LOCK_FALL);
            accumulator = 0f;
        }
    }

    @Override
    public boolean keyDown(int i) {
        switch (i) {
            case Input.Keys.RIGHT:
                gameState.shift(ShiftDirection.RIGHT, UpdateType.FALLING);
                break;
            case Input.Keys.LEFT:
                gameState.shift(ShiftDirection.LEFT, UpdateType.FALLING);
                break;
            case Input.Keys.A:
                gameState.rotate(RotationDirection.ANTICLOCKWISE);
                break;
            case Input.Keys.S:
                gameState.rotate(RotationDirection.CLOCKWISE);
                break;
            case Input.Keys.SPACE:
                gameState.shift(ShiftDirection.DOWN, UpdateType.FALLING);
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
        gameState.print();
        System.out.println(levelMan.getScore());
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
