package com.nicolas.tetris.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nicolas.tetris.game.*;
import com.nicolas.tetris.game.cell.Cell;
import com.nicolas.tetris.game.cell.UpdateType;
import com.nicolas.tetris.game.tetromino.RotationDirection;
import com.nicolas.tetris.game.tetromino.TetrominoBagRandomizer;
import com.nicolas.tetris.sprites.BoardSprite;
import com.nicolas.tetris.sprites.TetrominoSprite;

import java.util.Arrays;

import static com.nicolas.tetris.config.TetrisConfig.LINES_PER_LEVEL;

public class GameManager implements InputProcessor {
    private final GameState gameState = new GameState();
    private final TetrominoBagRandomizer bagRandomizer = new TetrominoBagRandomizer();
    private int level = 0;
    private int score = 0;
    private int totalLinesCleared = 0;
    private float accumulator = 0f;

    public GameManager() {
        Gdx.input.setInputProcessor(this);
    }

    public void render(SpriteBatch batch) {
        BoardSprite.get().render(batch, new Vector2(0, 0));

        Arrays.stream(gameState.getState()).forEach(
                row -> Arrays.stream(row).filter(Cell::isNotEmpty).filter(Cell::isNotSpawn).forEach(
                        cell -> TetrominoSprite.get(cell.getType()).renderSquare(batch, cell.getBottomLeft())));
    }

    public void update(float dt) {
        accumulator += dt;
        if (accumulator >= getTimePerCell()) {
            if (gameState.isSpawnUnlocked())
                gameState.spawnTetromino(bagRandomizer.getNext());
            gameState.shift(ShiftDirection.DOWN, UpdateType.FALLING);
            processClearedLines(gameState.processFilledLines());
            gameState.shift(ShiftDirection.DOWN, UpdateType.LOCK_FALL);
            accumulator = 0f;
        }
    }

    private void processClearedLines(int linesCleared){
        totalLinesCleared += linesCleared;
        level = calculateLevel();
        score += calculateScore(linesCleared);
    }

    private int calculateScore(int linesCleared){
        switch (linesCleared){
            case 1: return 40 * (level + 1);
            case 2: return 100 * (level + 1);
            case 3: return 300 * (level + 1);
            case 4: return 1200 * (level + 1);
        }
        return 0;
    }
    private int calculateLevel(){
        return Math.floorDiv(totalLinesCleared, LINES_PER_LEVEL);
    }
    private float getTimePerCell() {
        return (float) Math.pow(0.8f - ((level - 1) * 0.007f), level - 1);
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
        System.out.println(score);
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
