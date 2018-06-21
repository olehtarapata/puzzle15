package com.puzzle15.puzzles.factory;

import com.puzzle15.puzzles.functions.IsSolvableStateImpl;
import com.puzzle15.puzzles.functions.IsWinStateImpl;
import com.puzzle15.puzzles.state.ModifiablePuzzlesState;

public class SolvablePuzzlesFactory implements PuzzlesFactory {

    private final PuzzlesFactory originFactory;

    public SolvablePuzzlesFactory(final PuzzlesFactory originFactory) {
        this.originFactory = originFactory;
    }

    @Override
    public ModifiablePuzzlesState generate() {
        while (true) {
            final ModifiablePuzzlesState state = originFactory.generate();
            if (new IsSolvableStateImpl(state).isSolvable() && !new IsWinStateImpl(state).isWin()) {
                return state;
            }
        }
    }
}
