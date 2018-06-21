package com.puzzle15.puzzles;

import com.puzzle15.puzzles.factory.WinPuzzlesFactory;
import com.puzzle15.puzzles.state.ModifiablePuzzlesState;
import org.junit.Test;

import static com.puzzle15.puzzles.TestConstants.SIZE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PuzzlesImplTest {

    @Test
    public void checkIsWin() {
        assertThat(new PuzzlesImpl(new WinPuzzlesFactory(SIZE).generate()).isWin(), is(true));
    }

    @Test
    public void moveIllegalNumber() {
        final ModifiablePuzzlesState state = new WinPuzzlesFactory(SIZE).generate();
        final PuzzlesImpl puzzles = new PuzzlesImpl(state);
        assertThat(puzzles.move(-10), is(Status.ILLEGAL_PUZZLE_NUMBER));
        assertThat(puzzles.move(0), is(Status.ILLEGAL_PUZZLE_NUMBER));
        assertThat(puzzles.move(state.cellsCount()), is(Status.ILLEGAL_PUZZLE_NUMBER));
        assertThat(puzzles.move(100), is(Status.ILLEGAL_PUZZLE_NUMBER));
    }

    @Test
    public void moveNotNeighbors() {
        final ModifiablePuzzlesState state = new WinPuzzlesFactory(SIZE).generate();
        final PuzzlesImpl puzzles = new PuzzlesImpl(state);
        for (int i = 1; i < state.cellsCount(); i++) {
            if (i != state.cellsCount() - state.columnsCount() && i != state.cellsCount() - 1) {
                assertThat("Puzzle number " + i, puzzles.move(i), is(Status.NOT_NEIGHBORS));
            }
        }
    }

    @Test
    public void moveHorizontallyOk() {
        final ModifiablePuzzlesState state = new WinPuzzlesFactory(SIZE).generate();
        final PuzzlesImpl puzzles = new PuzzlesImpl(state);
        assertThat(puzzles.move(state.cellsCount() - 1), is(Status.OK));
        assertThat(puzzles.isWin(), is(false));
        assertThat(state.get(new Position(state.rawsCount() - 1, state.columnsCount() - 2)), is(0));
        assertThat(
                state.get(new Position(state.rawsCount() - 1, state.columnsCount() - 1)),
                is(state.cellsCount() - 1)
        );
    }

    @Test
    public void moveVerticallyOk() {
        final ModifiablePuzzlesState state = new WinPuzzlesFactory(SIZE).generate();
        final PuzzlesImpl puzzles = new PuzzlesImpl(state);
        assertThat(puzzles.move(state.cellsCount() - state.columnsCount()), is(Status.OK));
        assertThat(puzzles.isWin(), is(false));
        assertThat(state.get(new Position(state.rawsCount() - 2, state.columnsCount() - 1)), is(0));
        assertThat(
                state.get(new Position(state.rawsCount() - 1, state.columnsCount() - 1)),
                is(state.cellsCount() - state.columnsCount())
        );
    }
}
