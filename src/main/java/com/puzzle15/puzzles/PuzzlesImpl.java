package com.puzzle15.puzzles;

/**
 * Puzzles 15 game engine implementation.
 * Not thread safe.
 *
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public class PuzzlesImpl implements Puzzles {

    private final PuzzlesFactory factory;

    private int[] puzzles;

    private int[] puzzleToPosition;

    public PuzzlesImpl(final PuzzlesFactory factory) {
        this.factory = factory;
    }

    private void checkIfPuzzlesGenerated() {
        if (puzzles == null) {
            this.puzzles = this.factory.generate();
            this.puzzleToPosition = new int[CELLS_COUNT];
            for (int i = 0; i < CELLS_COUNT; i++) {
                puzzleToPosition[puzzles[i]] = i;
            }
        }
    }

    @Override
    public Status move(final int puzzleNumber) {
        checkIfPuzzlesGenerated();
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
        checkIfPuzzlesGenerated();
        for (int i = 0; i < CELLS_COUNT; i++) {
            if (puzzles[i] != (i + 1) % CELLS_COUNT) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int[] puzzles() {
        checkIfPuzzlesGenerated();
        return puzzles;
    }

    int[] puzzleToPosition() {
        checkIfPuzzlesGenerated();
        return puzzleToPosition;
    }
}
