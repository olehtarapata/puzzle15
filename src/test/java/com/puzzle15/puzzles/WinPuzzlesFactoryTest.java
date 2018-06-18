package com.puzzle15.puzzles;

import com.puzzle15.puzzles.factory.WinPuzzlesFactory;
import com.puzzle15.puzzles.state.ModifiablePuzzlesState;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WinPuzzlesFactoryTest {

    @Test
    public void checkWinPuzzle() {
        final ModifiablePuzzlesState state = new WinPuzzlesFactory().generate();
        for (int i = 0; i < state.rawsCount(); i++) {
            for (int j = 0; j < state.columnsCount(); j++) {
                assertThat(state.get(new Position(i, j)), is((i * state.columnsCount() + j + 1) % state.cellsCount()));
            }
        }
    }
}