package com.puzzle15.puzzles.functions;

import com.puzzle15.puzzles.Puzzles;
import com.puzzle15.puzzles.state.PuzzlesState;

public class IsSolvableStateImpl implements IsSolvableState {

    private final PuzzlesState state;

    private final boolean isEvenSize;

    public IsSolvableStateImpl(final PuzzlesState state) {
        this.state = state;
        if (state.rawsCount() != state.columnsCount()) {
            throw new IllegalArgumentException("Apply only for square puzzles");
        }
        isEvenSize = state.rawsCount() % 2 == 0;
    }

    @Override
    public boolean isSolvable() {
        int sum = state.getPosition(Puzzles.EMPTY_PUZZLE_NUMBER).getRaw() + 1;
        for (int i = 1; i < state.cellsCount(); i++) {
            sum += new InversionCountImpl(state, i).value();
        }
        final boolean isEvenSum = sum % 2 == 0;
        if (isEvenSize) {
            return isEvenSum;
        } else {
            return !isEvenSum;
        }
    }
}
