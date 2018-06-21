package com.puzzle15.puzzles.functions;

import com.puzzle15.puzzles.Position;
import com.puzzle15.puzzles.state.PuzzlesState;

public class IsWinStateImpl implements IsWinState {

    private final PuzzlesState state;

    public IsWinStateImpl(final PuzzlesState state) {
        this.state = state;
    }

    @Override
    public boolean isWin() {
        final int cellsCount = state.cellsCount();
        for (int i = 0; i < state.rawsCount(); i++) {
            for (int j = 0; j < state.columnsCount(); j++) {
                if (state.get(new Position(i, j)) != (i * state.columnsCount() + j + 1) % cellsCount) {
                    return false;
                }
            }
        }
        return true;
    }
}
