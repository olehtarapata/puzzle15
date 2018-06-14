package com.puzzle15.puzzles;

/**
 * Puzzles 15 game engine.
 *
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public interface Puzzles {

    /**
     * Total cells count.
     */
    int CELLS_COUNT = 16;

    /**
     * Total raws count.
     */
    int RAWS_COUNT = 4;

    /**
     * Total columns count.
     */
    int COLUMNS_COUNT = 4;

    /**
     * Empty puzzle number.
     */
    int EMPTY_PUZZLE_NUMBER = 0;

    /**
     * Move {@see puzzleNumber} puzzle to empty place.
     *
     * @param puzzleNumber puzzle number
     * @return status
     */
    Status move(int puzzleNumber);

    /**
     * Get puzzles state.
     *
     * @return puzzles
     */
    int[] puzzles();

    /**
     * Check if puzzle state is in win position.
     *
     * @return is win state
     */
    boolean isWin();
}
