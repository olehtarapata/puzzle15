package com.puzzle15.puzzles;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PositionTest {

    @Test
    public void isNeighbors() {
        assertThat(new Position(0, 0).isNeighbor(new Position(0, 1)), is(true));
    }
}