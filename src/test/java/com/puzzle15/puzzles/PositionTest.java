package com.puzzle15.puzzles;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PositionTest {

    @Test
    public void isNeighbors() {
        assertThat(new Position(2, 2).isNeighbor(new Position(2, 1)), is(true));
        assertThat(new Position(2, 2).isNeighbor(new Position(2, 3)), is(true));
        assertThat(new Position(2, 2).isNeighbor(new Position(1, 2)), is(true));
        assertThat(new Position(2, 2).isNeighbor(new Position(3, 2)), is(true));
        assertThat(new Position(2, 2).isNeighbor(new Position(1, 1)), is(false));
        assertThat(new Position(2, 2).isNeighbor(new Position(1, 3)), is(false));
        assertThat(new Position(2, 2).isNeighbor(new Position(3, 1)), is(false));
        assertThat(new Position(2, 2).isNeighbor(new Position(3, 3)), is(false));
    }

    @Test
    public void checkNext() {
        final Position next1 = new Position(1, 1).next(4);
        assertThat(next1.getRaw(), is(1));
        assertThat(next1.getColumn(), is(2));
        final Position next2 = next1.next(4).next(4);
        assertThat(next2.getRaw(), is(2));
        assertThat(next2.getColumn(), is(0));
    }

    @Test
    public void checkCreationWithIndex() {
        final Position position = new Position(6, 4, 4);
        assertThat(position.getRaw(), is(1));
        assertThat(position.getColumn(), is(2));
    }
}