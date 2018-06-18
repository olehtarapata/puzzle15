package com.puzzle15.puzzles.factory;

import com.puzzle15.puzzles.Position;
import com.puzzle15.puzzles.state.ModifiablePuzzlesState;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public final class ShuffledPuzzlesFactory implements PuzzlesFactory {

    private final PuzzlesFactory originFactory;

    public ShuffledPuzzlesFactory(final PuzzlesFactory originFactory) {
        this.originFactory = originFactory;
    }

    @Override
    public ModifiablePuzzlesState generate() {
        final ModifiablePuzzlesState state = originFactory.generate();
        final Random random = ThreadLocalRandom.current();
        for (int i = state.cellsCount() - 1; i > 1; i--) {
            final int index = random.nextInt(i);
            state.swap(
                    new Position(index, state.rawsCount(), state.columnsCount()),
                    new Position(i, state.rawsCount(), state.columnsCount())
            );
        }
        return state;
    }
}
