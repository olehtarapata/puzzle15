package com.puzzle15.puzzles;

import com.puzzle15.puzzles.factory.PuzzlesFactory;
import com.puzzle15.puzzles.functions.IsWinState;
import com.puzzle15.puzzles.functions.IsWinStateImpl;
import com.puzzle15.puzzles.state.ModifiablePuzzlesState;
import com.puzzle15.puzzles.state.PuzzlesState;

/**
 * Puzzles 15 game engine implementation.
 * Not thread safe.
 *
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public class PuzzlesImpl implements Puzzles {

    private final ModifiablePuzzlesState state;

    private final IsWinState isWinState;

    public PuzzlesImpl(final PuzzlesFactory factory) {
        this(factory.generate());
    }

    public PuzzlesImpl(final ModifiablePuzzlesState state) {
        this.state = state;
        this.isWinState = new IsWinStateImpl(state);
    }

    @Override
    public Status move(final int puzzleNumber) {
        if (puzzleNumber <= EMPTY_PUZZLE_NUMBER || puzzleNumber >= state.cellsCount()) {
            return Status.ILLEGAL_PUZZLE_NUMBER;
        }
        final Position emptyPosition = state.getPosition(EMPTY_PUZZLE_NUMBER);
        final Position position = state.getPosition(puzzleNumber);
        if (!emptyPosition.isNeighbor(position)) {
            return Status.NOT_NEIGHBORS;
        }
        state.swap(emptyPosition, position);
        return Status.OK;
    }

    @Override
    public boolean isWin() {
        return isWinState.isWin();
    }

    @Override
    public PuzzlesState puzzles() {
        return state;
    }
}
