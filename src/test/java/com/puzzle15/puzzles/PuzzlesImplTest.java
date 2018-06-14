package com.puzzle15.puzzles;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PuzzlesImplTest {

    @Test
    public void checkIsWin() {
        assertThat(new PuzzlesImpl(new WinPuzzlesFactory()).isWin(), is(true));
    }

    @Test
    public void checkInitialization() {
        final PuzzlesImpl puzzles = new PuzzlesImpl(new WinPuzzlesFactory());
        final int[] puzzleToPosition = puzzles.puzzleToPosition();
        assertThat(puzzleToPosition.length, is(Puzzles.CELLS_COUNT));
        assertThat(puzzleToPosition[0], is(Puzzles.CELLS_COUNT - 1));
        for (int i = 1; i < Puzzles.CELLS_COUNT; i++) {
            assertThat(puzzleToPosition[i], is(i - 1));
        }
    }

    @Test
    public void moveIllegalNumber() {
        final PuzzlesImpl puzzles = new PuzzlesImpl(new WinPuzzlesFactory());
        assertThat(puzzles.move(-10), is(Status.ILLEGAL_PUZZLE_NUMBER));
        assertThat(puzzles.move(0), is(Status.ILLEGAL_PUZZLE_NUMBER));
        assertThat(puzzles.move(Puzzles.CELLS_COUNT), is(Status.ILLEGAL_PUZZLE_NUMBER));
        assertThat(puzzles.move(100), is(Status.ILLEGAL_PUZZLE_NUMBER));
    }

    @Test
    public void moveNotNeighbors() {
        final PuzzlesImpl puzzles = new PuzzlesImpl(new WinPuzzlesFactory());
        for (int i = 1; i < Puzzles.CELLS_COUNT; i++) {
            if (i != 12 && i != 15) {
                assertThat("Puzzle number " + i, puzzles.move(i), is(Status.NOT_NEIGHBORS));
            }
        }
    }

    @Test
    public void moveHorizontallyOk() {
        final PuzzlesImpl puzzles = new PuzzlesImpl(new WinPuzzlesFactory());
        assertThat(puzzles.move(15), is(Status.OK));
        assertThat(puzzles.isWin(), is(false));
        assertThat(puzzles.puzzles()[14], is(0));
        assertThat(puzzles.puzzles()[15], is(15));
        assertThat(puzzles.puzzleToPosition()[0], is(14));
        assertThat(puzzles.puzzleToPosition()[15], is(15));
    }

    @Test
    public void moveVerticallyOk() {
        final PuzzlesImpl puzzles = new PuzzlesImpl(new WinPuzzlesFactory());
        assertThat(puzzles.move(12), is(Status.OK));
        assertThat(puzzles.isWin(), is(false));
        assertThat(puzzles.puzzles()[11], is(0));
        assertThat(puzzles.puzzles()[15], is(12));
        assertThat(puzzles.puzzleToPosition()[0], is(11));
        assertThat(puzzles.puzzleToPosition()[12], is(15));
    }
}
