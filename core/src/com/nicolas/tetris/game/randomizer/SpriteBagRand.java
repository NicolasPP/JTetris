package com.nicolas.tetris.game.randomizer;

import com.nicolas.tetris.game.cell.CellType;
import com.nicolas.tetris.sprites.TetrominoSprite;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SpriteBagRand {
    private final LinkedList<CellType> queue;
    private final LinkedList<CellType> stock;
    private final List<CellType> bag = Arrays.asList(
            CellType.I, CellType.L, CellType.J, CellType.S, CellType.O, CellType.T, CellType.Z);

    public SpriteBagRand() {
        queue = new LinkedList<>();
        stock = new LinkedList<>();
        init();
    }

    private void init(){
        Collections.shuffle(bag);
        queue.addAll(bag);
    }

    private void restock() {
        Collections.shuffle(bag);
        stock.addAll(bag);
    }

    public TetrominoSprite getNext() {
        if (stock.isEmpty()) restock();
        CellType next = queue.poll();
        queue.add(stock.poll());
        return TetrominoSprite.get(next);
    }

    public CellType[] peekWholeQueue() {
        CellType[] cellTypes = new CellType[queue.size()];
        queue.toArray(cellTypes);
        return cellTypes;
    }

    public void reset(){
        stock.clear();
        queue.clear();
        init();
    }

    public CellType peekQueue() {
        return queue.peekFirst();
    }
}
