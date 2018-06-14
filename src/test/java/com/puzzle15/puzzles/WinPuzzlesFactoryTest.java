package com.puzzle15.puzzles;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WinPuzzlesFactoryTest {

    @Test
    public void checkWinPuzzle() {
        final int[] puzzles = new WinPuzzlesFactory().generate();
        assertThat(puzzles.length, is(Puzzles.CELLS_COUNT));
        assertThat(puzzles[puzzles.length - 1], is(0));
        for (int i = 1; i < Puzzles.CELLS_COUNT; i++) {
            assertThat(puzzles[i - 1], is(i));
        }
    }
}