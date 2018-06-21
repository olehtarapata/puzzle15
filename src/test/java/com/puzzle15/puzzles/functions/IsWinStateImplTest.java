package com.puzzle15.puzzles.functions;

import com.puzzle15.puzzles.factory.WinPuzzlesFactory;
import com.puzzle15.puzzles.state.ModifiablePuzzlesState;
import org.junit.Test;

import static com.puzzle15.puzzles.TestConstants.SIZE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IsWinStateImplTest {

    @Test
    public void generateWinPuzzlesAndCheck() {
        final ModifiablePuzzlesState state = new WinPuzzlesFactory(SIZE).generate();
        final IsWinStateImpl isWinState = new IsWinStateImpl(state);
        assertThat(isWinState.isWin(), is(true));
        state.swap(0, 1);
        assertThat(isWinState.isWin(), is(false));
    }
}