package com.puzzle15.puzzles.functions;

import com.puzzle15.puzzles.factory.WinPuzzlesFactory;
import com.puzzle15.puzzles.state.ModifiablePuzzlesState;
import org.junit.Test;

import static com.puzzle15.puzzles.TestConstants.SIZE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IsSolvableStateImplTest {

    @Test
    public void generateAndCheck() {
        final ModifiablePuzzlesState state = new WinPuzzlesFactory(SIZE).generate();
        final IsSolvableState isSolvableState = new IsSolvableStateImpl(state);
        assertThat(isSolvableState.isSolvable(), is(true));
        state.swap(state.cellsCount() - 2, state.columnsCount() - 1);
        assertThat(isSolvableState.isSolvable(), is(false));
    }
}