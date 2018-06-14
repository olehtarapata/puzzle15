package com.puzzle15.puzzles;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ShuffledPuzzlesFactoryTest {

    @Test
    public void checkShuffledFactory() {
        final int[] puzzles = new ShuffledPuzzlesFactory(new WinPuzzlesFactory()).generate();
        assertThat(puzzles.length, is(Puzzles.CELLS_COUNT));
        assertThat(puzzles[puzzles.length - 1], is(not(0)));
        for (int i = 0; i < puzzles.length; i++) {
            assertThat(contains(puzzles, i), is(true));
        }
    }

    private boolean contains(int[] array, int element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == element) {
                return true;
            }
        }
        return false;
    }


}