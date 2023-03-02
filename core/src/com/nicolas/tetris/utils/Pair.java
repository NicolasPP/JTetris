package com.nicolas.tetris.utils;

public class Pair<K, V> {
    private final K first;
    private final V second;

    public static <K, V> Pair<K, V> createPair(K first, V second) {
        return new Pair<>(first, second);
    }

    public Pair(K f, V s) {
        first = f;
        second = s;
    }

    public K getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }
}
