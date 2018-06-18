package com.puzzle15.puzzles.state;

import com.puzzle15.puzzles.Position;

public class ModifiablePuzzlesStateImpl implements ModifiablePuzzlesState {

    private final int[][] state;

    private final int rawsCount;

    private final int columnsCount;

    public ModifiablePuzzlesStateImpl(final int[][] state) {
        this.state = state;
        this.rawsCount = state.length;
        this.columnsCount = state[0].length;
    }

    @Override
    public void swap(final Position position1, final Position position2) {
        final int buffer = state[position1.getRaw()][position1.getColumn()];
        state[position1.getRaw()][position1.getColumn()] = state[position2.getRaw()][position2.getColumn()];
        state[position2.getRaw()][position2.getColumn()] = buffer;
    }

    @Override
    public Position getPosition(final int puzzleNumber) {
        for (int i = 0; i < rawsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                if (state[i][j] == puzzleNumber) {
                    return new Position(i, j);
                }
            }
        }
        throw new IllegalArgumentException("Puzzle number not found: " + puzzleNumber);
    }

    @Override
    public int rawsCount() {
        return this.rawsCount;
    }

    @Override
    public int columnsCount() {
        return this.columnsCount;
    }

    @Override
    public int get(final Position position) {
        return state[position.getRaw()][position.getColumn()];
    }
}
