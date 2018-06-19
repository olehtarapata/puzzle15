package com.puzzle15.puzzles.factory;

import com.puzzle15.puzzles.state.ModifiablePuzzlesState;
import com.puzzle15.puzzles.state.ModifiablePuzzlesStateImpl;

public final class WinPuzzlesFactory implements PuzzlesFactory {

    private final int rawsCount;

    private final int columnsCount;

    public WinPuzzlesFactory(final int size) {
        this(size, size);
    }

    public WinPuzzlesFactory(final int rawsCount, final int columnsCount) {
        this.rawsCount = rawsCount;
        this.columnsCount = columnsCount;
    }

    @Override
    public ModifiablePuzzlesState generate() {
        final int[][] state = new int[rawsCount][columnsCount];
        int cellsCount = rawsCount * columnsCount;
        for (int i = 0; i < rawsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                state[i][j] = (i * columnsCount + j + 1) % cellsCount;
            }
        }
        return new ModifiablePuzzlesStateImpl(state);
    }
}
