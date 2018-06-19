package com.puzzle15.puzzles.state;

public final class EmptyRawImpl implements EmptyRaw {

    private final PuzzlesState state;

    @Override
    public int value() {
        return 0;
    }
}
