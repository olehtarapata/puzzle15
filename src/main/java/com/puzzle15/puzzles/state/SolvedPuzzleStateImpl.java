package com.puzzle15.puzzles.state;

import com.puzzle15.puzzles.Position;

public class SolvedPuzzleStateImpl implements SolvedPuzzlesState {

    private final ModifiablePuzzlesState state;

    public SolvedPuzzleStateImpl(final ModifiablePuzzlesState state) {
        this.state = state;
    }

    @Override
    public boolean isSolved() {
        return false;
    }

    @Override
    public void swap(final Position position1, final Position position2) {
        state.swap(position1, position2);
    }

    @Override
    public Position getPosition(final int puzzleNumber) {
        return state.getPosition(puzzleNumber);
    }

    @Override
    public int rawsCount() {
        return state.rawsCount();
    }

    @Override
    public int columnsCount() {
        return state.columnsCount();
    }

    @Override
    public int get(final Position position) {
        return state.get(position);
    }
}
