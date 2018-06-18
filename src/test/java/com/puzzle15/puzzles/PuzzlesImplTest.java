package com.puzzle15.puzzles;

import com.puzzle15.puzzles.factory.WinPuzzlesFactory;
import com.puzzle15.puzzles.state.ModifiablePuzzlesState;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PuzzlesImplTest {

    @Test
    public void checkIsWin() {
        assertThat(new PuzzlesImpl(new WinPuzzlesFactory().generate()).isWin(), is(true));
    }

    @Test
    public void moveIllegalNumber() {
        final ModifiablePuzzlesState state = new WinPuzzlesFactory().generate();
        final PuzzlesImpl puzzles = new PuzzlesImpl(state);
        assertThat(puzzles.move(-10), is(Status.ILLEGAL_PUZZLE_NUMBER));
        assertThat(puzzles.move(0), is(Status.ILLEGAL_PUZZLE_NUMBER));
        assertThat(puzzles.move(state.cellsCount()), is(Status.ILLEGAL_PUZZLE_NUMBER));
        assertThat(puzzles.move(100), is(Status.ILLEGAL_PUZZLE_NUMBER));
    }

    @Test
    public void moveNotNeighbors() {
        final ModifiablePuzzlesState state = new WinPuzzlesFactory().generate();
        final PuzzlesImpl puzzles = new PuzzlesImpl(state);
        for (int i = 1; i < state.cellsCount(); i++) {
            if (i != 12 && i != 15) {
                assertThat("Puzzle number " + i, puzzles.move(i), is(Status.NOT_NEIGHBORS));
            }
        }
    }

    @Test
    public void moveHorizontallyOk() {
        final ModifiablePuzzlesState state = new WinPuzzlesFactory().generate();
        final PuzzlesImpl puzzles = new PuzzlesImpl(state);
        assertThat(puzzles.move(15), is(Status.OK));
        assertThat(puzzles.isWin(), is(false));
        assertThat(state.get(new Position(14, state.rawsCount(), state.columnsCount())), is(0));
        assertThat(state.get(new Position(15, state.rawsCount(), state.columnsCount())), is(15));
    }

    @Test
    public void moveVerticallyOk() {
        final ModifiablePuzzlesState state = new WinPuzzlesFactory().generate();
        final PuzzlesImpl puzzles = new PuzzlesImpl(state);
        assertThat(puzzles.move(12), is(Status.OK));
        assertThat(puzzles.isWin(), is(false));
        assertThat(state.get(new Position(11, state.rawsCount(), state.columnsCount())), is(0));
        assertThat(state.get(new Position(15, state.rawsCount(), state.columnsCount())), is(12));
    }
}
