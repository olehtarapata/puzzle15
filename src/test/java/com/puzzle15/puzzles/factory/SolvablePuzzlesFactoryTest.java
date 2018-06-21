package com.puzzle15.puzzles.factory;

import com.puzzle15.puzzles.functions.IsSolvableStateImpl;
import com.puzzle15.puzzles.functions.IsWinStateImpl;
import com.puzzle15.puzzles.state.ModifiablePuzzlesState;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static com.puzzle15.puzzles.TestConstants.SIZE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SolvablePuzzlesFactoryTest {

    @Test
    public void generateSolvableAndCheck() {
        final ModifiablePuzzlesState state = new WinPuzzlesFactory(SIZE).generate();
        state.swap(0, state.cellsCount() - 1);
        final AtomicInteger callCount = new AtomicInteger();
        final ModifiablePuzzlesState generatedState = new SolvablePuzzlesFactory(() -> {
            callCount.incrementAndGet();
            return state;
        }).generate();
        assertThat(new IsWinStateImpl(generatedState).isWin(), is(false));
        assertThat(new IsSolvableStateImpl(generatedState).isSolvable(), is(true));
        assertThat(callCount.get(), is(1));
    }
}