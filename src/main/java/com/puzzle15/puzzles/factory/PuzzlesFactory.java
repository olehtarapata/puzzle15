package com.puzzle15.puzzles.factory;

import com.puzzle15.puzzles.state.ModifiablePuzzlesState;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public interface PuzzlesFactory {

    ModifiablePuzzlesState generate();
}
