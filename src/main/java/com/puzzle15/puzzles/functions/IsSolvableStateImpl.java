package com.puzzle15.puzzles.functions;

import com.puzzle15.puzzles.Puzzles;
import com.puzzle15.puzzles.state.PuzzlesState;

public class IsSolvableStateImpl implements IsSolvableState {

    private final PuzzlesState state;

    private final boolean isOddSize;

    public IsSolvableStateImpl(final PuzzlesState state) {
        this.state = state;
        if (state.rawsCount() != state.columnsCount()) {
            throw new IllegalArgumentException("Apply only for square puzzles");
        }
        isOddSize = state.rawsCount() % 2 == 1;
    }

    @Override
    public boolean isSolvable() {
        int inversionsCount = 0;
        for (int i = 1; i < state.cellsCount(); i++) {
            inversionsCount += new InversionCountImpl(state, i).value();
        }
        if (isOddSize) {
            return inversionsCount % 2 == 0;
        }
        if (state.getPosition(Puzzles.EMPTY_PUZZLE_NUMBER).getRaw() % 2 == 0) {
            return inversionsCount % 2 == 1;
        } else {
            return inversionsCount % 2 == 0;
        }
    }
}
