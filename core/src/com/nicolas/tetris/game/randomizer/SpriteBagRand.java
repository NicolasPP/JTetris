package com.nicolas.tetris.game.randomizer;

import com.nicolas.tetris.game.cell.CellType;
import com.nicolas.tetris.sprites.TetrominoSprite;

import java.util.*;

public class SpriteBagRand {
    private final LinkedList<CellType> queue = new LinkedList<>();
    private final LinkedList<CellType> stock = new LinkedList<>();
    private final List<CellType> bag = Arrays.asList(
            CellType.I, CellType.L, CellType.J, CellType.S, CellType.O, CellType.T, CellType.Z);
    public SpriteBagRand(){
        Collections.shuffle(bag);
        queue.addAll(bag);
        System.out.println(queue);
    }

    private void restock(){
        Collections.shuffle(bag);
        stock.addAll(bag);
    }

    public TetrominoSprite getNext(){
        if (stock.isEmpty()) restock();
        CellType next = queue.poll();
        queue.add(stock.poll());
        return TetrominoSprite.get(next);
    }
    public CellType[] peekQueue(){
        CellType[] cellTypes = new CellType[queue.size()];
        queue.toArray(cellTypes);
        return cellTypes;
    }
}
