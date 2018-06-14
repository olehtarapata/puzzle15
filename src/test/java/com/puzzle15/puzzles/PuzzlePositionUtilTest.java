package com.puzzle15.puzzles;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PuzzlePositionUtilTest {

    @Test
    public void getRaw() {
        assertThat(PuzzlePositionUtil.getRaw(0), is(0));
        assertThat(PuzzlePositionUtil.getRaw(3), is(0));
        assertThat(PuzzlePositionUtil.getRaw(4), is(1));
        assertThat(PuzzlePositionUtil.getRaw(7), is(1));
        assertThat(PuzzlePositionUtil.getRaw(8), is(2));
        assertThat(PuzzlePositionUtil.getRaw(11), is(2));
        assertThat(PuzzlePositionUtil.getRaw(12), is(3));
        assertThat(PuzzlePositionUtil.getRaw(15), is(3));
    }

    @Test
    public void getColumn() {
        assertThat(PuzzlePositionUtil.getColumn(0), is(0));
        assertThat(PuzzlePositionUtil.getColumn(1), is(1));
        assertThat(PuzzlePositionUtil.getColumn(2), is(2));
        assertThat(PuzzlePositionUtil.getColumn(3), is(3));
        assertThat(PuzzlePositionUtil.getColumn(4), is(0));
        assertThat(PuzzlePositionUtil.getColumn(9), is(1));
        assertThat(PuzzlePositionUtil.getColumn(14), is(2));
        assertThat(PuzzlePositionUtil.getColumn(15), is(3));
    }

    @Test
    public void isNeighbors() {
        assertThat(PuzzlePositionUtil.isNeighbors(0, 0), is(false));
        assertThat(PuzzlePositionUtil.isNeighbors(0, 1), is(true));
        assertThat(PuzzlePositionUtil.isNeighbors(0, 4), is(true));
        assertThat(PuzzlePositionUtil.isNeighbors(0, 3), is(false));
        assertThat(PuzzlePositionUtil.isNeighbors(0, 12), is(false));
        assertThat(PuzzlePositionUtil.isNeighbors(0, 5), is(false));
        assertThat(PuzzlePositionUtil.isNeighbors(3, 4), is(false));
        assertThat(PuzzlePositionUtil.isNeighbors(5, 6), is(true));
        assertThat(PuzzlePositionUtil.isNeighbors(5, 9), is(true));
        assertThat(PuzzlePositionUtil.isNeighbors(9, 10), is(true));
        assertThat(PuzzlePositionUtil.isNeighbors(14, 15), is(true));
        assertThat(PuzzlePositionUtil.isNeighbors(15, 10), is(false));
    }
}