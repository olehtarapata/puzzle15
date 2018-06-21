package com.puzzle15.puzzles.factory;

import com.puzzle15.puzzles.Position;
import com.puzzle15.puzzles.functions.IsWinStateImpl;
import com.puzzle15.puzzles.state.ModifiablePuzzlesState;
import org.junit.Test;

import static com.puzzle15.puzzles.TestConstants.SIZE;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ShuffledPuzzlesFactoryTest {

    @Test
    public void checkShuffledFactory() {
        final ModifiablePuzzlesState state = new ShuffledPuzzlesFactory(new WinPuzzlesFactory(SIZE)).generate();
        assertThat(state.get(new Position(state.rawsCount() - 1, state.columnsCount() - 1)), is(not(0)));
        assertThat(new IsWinStateImpl(state).isWin(), is(false));
        for (int i = 0; i < state.cellsCount(); i++) {
            assertThat(state.getPosition(i), is(notNullValue()));
        }
    }
}