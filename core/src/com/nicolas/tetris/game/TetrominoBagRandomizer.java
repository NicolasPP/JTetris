package com.nicolas.tetris.game;

import java.util.*;

public class TetrominoBagRandomizer {
    private final LinkedList<CellType> queue = new LinkedList<>();
    private final LinkedList<CellType> store = new LinkedList<>();
    private final List<CellType> bag = Arrays.asList(
            CellType.I, CellType.L, CellType.J, CellType.S, CellType.O, CellType.T, CellType.Z);
    public TetrominoBagRandomizer(){
        Collections.shuffle(bag);
        queue.addAll(bag);
    }

    private void updateStore(){
        Collections.shuffle(bag);
        store.addAll(bag);
    }

    public CellType getNext(){
        if (store.isEmpty()) updateStore();
        CellType next = queue.poll();
        queue.add(store.poll());
        return next;
    }
}
