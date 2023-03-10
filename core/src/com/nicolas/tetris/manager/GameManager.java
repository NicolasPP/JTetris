package com.nicolas.tetris.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.game.cell.Cell;
import com.nicolas.tetris.game.cell.UpdateType;
import com.nicolas.tetris.game.gui.GameInfoUI;
import com.nicolas.tetris.game.state.GameState;
import com.nicolas.tetris.game.state.ShiftDirection;
import com.nicolas.tetris.utils.RotationDirection;
import com.nicolas.tetris.game.randomizer.SpriteBagRand;
import com.nicolas.tetris.sprites.BoardSprite;
import com.nicolas.tetris.sprites.TetrominoSprite;

import java.util.Arrays;

import static com.nicolas.tetris.config.TetrisConfig.STATS_UI_COLS;
import static com.nicolas.tetris.config.TetrisConfig.CELL_SIZE;
import static com.nicolas.tetris.config.TetrisConfig.TEXTURE_SCALE;
import static com.nicolas.tetris.config.TetrisConfig.MAX_LINES;

public class GameManager implements InputProcessor {
    private final GameState gameState;
    private final SpriteBagRand bagRandomizer = new SpriteBagRand();
    private final LevelManager levelMan = new LevelManager();
    private final BoardSprite board = BoardSprite.getInstance();
    private final GameInfoUI gameUI = new GameInfoUI(bagRandomizer);
    private final Vector2 boardPos = new Vector2(STATS_UI_COLS * (int)(CELL_SIZE * TEXTURE_SCALE), 0);
    private float accumulator = 0f;

    public GameManager() {
        gameState = new GameState(boardPos);
        Gdx.input.setInputProcessor(this);
    }

    public void render(SpriteBatch batch) {
        board.renderTexture(batch, boardPos, false);

        Arrays.stream(gameState.getState()).forEach(
                row -> Arrays.stream(row).filter(Cell::isNotEmpty).filter(Cell::isNotSpawn).forEach(cell -> {
                    if (cell.getBottomLeft().y > 0 && cell.getBottomLeft().x > 0) {
                        TetrominoSprite tetromino = TetrominoSprite.get(cell.getType());
                        tetromino.renderSubTexture(batch, tetromino.getColorName(), cell.getBottomLeft(), tetromino.getSubTextureSize(), false);
                    }
                }));

        gameUI.render(batch);
    }

    public void update(float dt) {
        accumulator += dt;
        if (accumulator >= levelMan.getTimePerCell()) {
            if (gameState.isSpawnUnlocked()){
                gameUI.getStatsUI().addStat(bagRandomizer.peekQueue());
                gameState.spawnTetromino(bagRandomizer.getNext());
                gameUI.getSpawnQueueUI().updatePositions();
            }

            gameState.shift(ShiftDirection.DOWN, UpdateType.FALLING);
            levelMan.processClearedLines(gameState.processFilledLines());
            gameState.shift(ShiftDirection.DOWN, UpdateType.LOCK_FALL);

            accumulator = 0f;
            gameUI.getLevelUI().updateValues(levelMan.getScore(), levelMan.getLevel(), levelMan.getTotalLinesCleared());

            if (gameState.isGameOver() || levelMan.getTotalLinesCleared() >= MAX_LINES){
                gameState.restartGame(boardPos);
                gameUI.restartGame();
                bagRandomizer.reset();
            }
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
            case Input.Keys.DOWN:
                gameState.rotate(RotationDirection.ANTICLOCKWISE);
                break;
            case Input.Keys.UP:
                gameState.rotate(RotationDirection.CLOCKWISE);
                break;
            case Input.Keys.SPACE:
                gameState.shift(ShiftDirection.DOWN, UpdateType.FALLING);
                break;
            case Input.Keys.H:
                gameState.hold(gameUI.getHoldUI());
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
        gameState.print();
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
