package com.puzzle15.puzzles;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public interface Puzzles {

    int CELLS_COUNT = 16;

    int RAWS_COUNT = 4;

    int COLUMNS_COUNT = 4;

    int EMPTY_PUZZLE_NUMBER = 0;

    Status move(int puzzleNumber);

    int[] puzzles();

    boolean isWin();
}
