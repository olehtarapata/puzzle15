package com.puzzle15.puzzles;

import java.util.Arrays;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public class PuzzlesImpl implements Puzzles {

    private final int[] puzzles;

    private final int[] puzzleToPosition;

    public PuzzlesImpl(final int[] puzzles) {
        this.puzzles = puzzles;
        this.puzzleToPosition = new int[CELLS_COUNT];
        for (int i = 0; i < CELLS_COUNT; i++) {
            puzzleToPosition[puzzles[i]] = i;
        }
    }

    @Override
    public Status move(final int puzzleNumber) {
        if (puzzleNumber <= 0 || puzzleNumber >= CELLS_COUNT) {
            return Status.ILLEGAL_PUZZLE_NUMBER;
        }
        int emptyPosition = puzzleToPosition[0];
        int puzzlePosition = puzzleToPosition[puzzleNumber];
        if (!PuzzlePositionUtil.isNeighbors(emptyPosition, puzzlePosition)) {
            return Status.NOT_NEIGHBORS;
        }
        puzzleToPosition[0] = puzzlePosition;
        puzzleToPosition[puzzleNumber] = emptyPosition;
        puzzles[emptyPosition] = puzzleNumber;
        puzzles[puzzlePosition] = 0;
        return Status.OK;
    }

    @Override
    public boolean isWin() {
        for (int i = 0; i < CELLS_COUNT; i++) {
            if (puzzles[i] != (i + 1) % CELLS_COUNT) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int[] puzzles() {
        return Arrays.copyOf(puzzles, Puzzles.CELLS_COUNT);
    }
}
