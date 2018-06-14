package com.puzzle15.puzzles;

import static com.puzzle15.puzzles.Puzzles.CELLS_COUNT;

public final class WinPuzzlesFactory implements PuzzlesFactory {

    @Override
    public int[] generate() {
        final int[] puzzles = new int[CELLS_COUNT];
        for (int i = 0; i < CELLS_COUNT; i++) {
            puzzles[i] = (i + 1) % CELLS_COUNT;
        }
        return puzzles;
    }
}
