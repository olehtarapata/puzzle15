package com.puzzle15.puzzles;

import com.puzzle15.puzzles.state.PuzzlesState;

/**
 * Puzzles 15 game engine.
 *
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public interface Puzzles {

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
     * Move empty place in {@see direction}.
     *
     * @param direction direction
     * @return status
     */
    Status move(Direction direction);

    /**
     * Get puzzles state.
     *
     * @return puzzles
     */
    PuzzlesState puzzles();

    /**
     * Check if puzzle state is in win position.
     *
     * @return is win state
     */
    boolean isWin();
}
