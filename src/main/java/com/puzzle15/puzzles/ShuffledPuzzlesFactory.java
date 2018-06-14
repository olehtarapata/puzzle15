package com.puzzle15.puzzles;

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
    public int[] generate() {
        final int[] puzzles = originFactory.generate();
        final Random random = ThreadLocalRandom.current();
        for (int i = puzzles.length - 1; i > 1; i--) {
            final int index = random.nextInt(i);
            final int buffer = puzzles[index];
            puzzles[index] = puzzles[i];
            puzzles[i] = buffer;
        }
        return puzzles;
    }
}
