package com.puzzle15.puzzles;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public class PuzzlesModel implements Model {

    private final int[] puzzles;

    private final Status status;

    public PuzzlesModel(final int[] puzzles, final Status status) {
        this.puzzles = puzzles;
        this.status = status;
    }

    @Override
    public int[][] cells() {
        return new int[0][];
    }

    @Override
    public Status status() {
        return this.status;
    }
}
