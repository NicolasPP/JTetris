package com.nicolas.tetris.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.tetris.game.*;
import com.nicolas.tetris.game.cell.Cell;
import com.nicolas.tetris.game.cell.UpdateType;
import com.nicolas.tetris.utils.RotationDirection;
import com.nicolas.tetris.game.tetromino.TetrominoBagRandomizer;
import com.nicolas.tetris.sprites.BoardSprite;
import com.nicolas.tetris.sprites.TetrominoSprite;
import com.nicolas.tetris.utils.Pos;

import java.util.Arrays;

public class GameManager implements InputProcessor {
    private final GameState gameState = new GameState();
    private final TetrominoBagRandomizer bagRandomizer = new TetrominoBagRandomizer();
    private final LevelManager levelMan = new LevelManager();
    private final BoardSprite board = BoardSprite.getInstance();
    private float accumulator = 0f;

    public GameManager() {
        Gdx.input.setInputProcessor(this);
    }

    public void render(SpriteBatch batch) {
        board.render(batch, new Pos(0, 0));

        Arrays.stream(gameState.getState()).forEach(
                row -> Arrays.stream(row).filter(Cell::isNotEmpty).filter(Cell::isNotSpawn).forEach(
                        cell -> TetrominoSprite.get(cell.getType()).renderSquare(batch, cell.getBottomLeft())));
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
