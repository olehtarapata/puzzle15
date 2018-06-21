package com.puzzle15.puzzles.functions;

import com.puzzle15.puzzles.factory.WinPuzzlesFactory;
import com.puzzle15.puzzles.state.ModifiablePuzzlesState;
import org.junit.Test;

import static com.puzzle15.puzzles.TestConstants.SIZE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class InversionCountImplTest {

    @Test
    public void calculateInversionsCount() {
        final ModifiablePuzzlesState state = new WinPuzzlesFactory(SIZE).generate();
        for (int i = 0; i < state.cellsCount(); i++) {
            assertThat(new InversionCountImpl(state, i).value(), is(0));
        }
        state.swap(14, 15);
        assertThat(new InversionCountImpl(state, 14).value(), is(0));
        assertThat(new InversionCountImpl(state, 15).value(), is(1));
    }
}