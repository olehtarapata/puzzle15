package com.puzzle15.puzzles.functions;

import com.puzzle15.puzzles.Position;
import com.puzzle15.puzzles.Puzzles;
import com.puzzle15.puzzles.state.PuzzlesState;

public class InversionCountImpl implements InversionCount {

    private final PuzzlesState state;

    private final int puzzleNumber;

    public InversionCountImpl(final PuzzlesState state, final int puzzleNumber) {
        this.state = state;
        this.puzzleNumber = puzzleNumber;
    }

    @Override
    public int value() {
        final Position position = state.getPosition(puzzleNumber);
        final Position nextPosition = position.next(state.columnsCount());
        int result = 0;
        int j = nextPosition.getColumn();
        for (int i = nextPosition.getRaw(); i < state.rawsCount(); i++) {
            for (; j < state.columnsCount(); j++) {
                final int currentPuzzleNumber = state.get(new Position(i, j));
                if (currentPuzzleNumber != Puzzles.EMPTY_PUZZLE_NUMBER && currentPuzzleNumber < puzzleNumber) {
                    result++;
                }
            }
            j = 0;
        }
        return result;
    }
}
