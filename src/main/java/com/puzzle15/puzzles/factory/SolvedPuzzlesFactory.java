package com.puzzle15.puzzles.factory;

import com.puzzle15.puzzles.Position;
import com.puzzle15.puzzles.Puzzles;
import com.puzzle15.puzzles.state.ModifiablePuzzlesState;
import com.puzzle15.puzzles.state.PuzzlesState;

public class SolvedPuzzlesFactory implements PuzzlesFactory {

    private final PuzzlesFactory originFactory;

    public SolvedPuzzlesFactory(final PuzzlesFactory originFactory) {
        this.originFactory = originFactory;
    }

    @Override
    public ModifiablePuzzlesState generate() {
        while (true) {
            final ModifiablePuzzlesState state = originFactory.generate();
            if (checkIfSolved(state)) {
                return state;
            }
        }
    }

    private boolean checkIfSolved(final ModifiablePuzzlesState state) {
        int sum = state.getPosition(Puzzles.EMPTY_PUZZLE_NUMBER).getRaw() + 1;
        for (int i = 0; i < state.rawsCount(); i++) {
            for (int j = 0; j < state.columnsCount(); j++) {
                sum += puzzlesCountWithLessNumbers(state, new Position(i, j));
            }
        }
        return sum % 2 == 0;
    }

    private int puzzlesCountWithLessNumbers(final PuzzlesState state, final Position position) {
        final int puzzleNumber = state.get(position);
        if (puzzleNumber == Puzzles.EMPTY_PUZZLE_NUMBER) {
            return 0;
        }
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
