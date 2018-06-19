package com.puzzle15.puzzles.state;

import com.puzzle15.puzzles.Puzzles;

public class SolvableStateImpl implements SolvableState {

    private final PuzzlesState state;

    public SolvableStateImpl(final PuzzlesState state) {
        this.state = state;
    }

    @Override
    public boolean isSolvable() {
        int sum = state.getPosition(Puzzles.EMPTY_PUZZLE_NUMBER).getRaw() + 1;
        for (int i = 1; i < state.cellsCount(); i++) {
            sum += new InversionCountImpl(state, i).value();
        }
        return sum % 2 == 0;
    }
}
