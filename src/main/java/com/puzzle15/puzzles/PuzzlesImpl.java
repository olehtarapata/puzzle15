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
    public Status move(final Direction direction) {
        final Position emptyPosition = state.getPosition(EMPTY_PUZZLE_NUMBER);
        Position newPosition = null;
        if (Direction.UP == direction) {
            if (emptyPosition.getRaw() == 0) {
                return Status.OUT_OF_BORDER;
            }
            newPosition = new Position(emptyPosition.getRaw() - 1, emptyPosition.getColumn());
        }
        if (Direction.DOWN == direction) {
            if (emptyPosition.getRaw() == state.rawsCount() - 1) {
                return Status.OUT_OF_BORDER;
            }
            newPosition = new Position(emptyPosition.getRaw() + 1, emptyPosition.getColumn());
        }
        if (Direction.LEFT == direction) {
            if (emptyPosition.getColumn() == 0) {
                return Status.OUT_OF_BORDER;
            }
            newPosition = new Position(emptyPosition.getRaw(), emptyPosition.getColumn() - 1);
        }
        if (Direction.RIGHT == direction) {
            if (emptyPosition.getColumn() == state.columnsCount() - 1) {
                return Status.OUT_OF_BORDER;
            }
            newPosition = new Position(emptyPosition.getRaw(), emptyPosition.getColumn() + 1);
        }
        state.swap(emptyPosition, newPosition);
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
