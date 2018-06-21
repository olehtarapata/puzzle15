package com.puzzle15.puzzles.state;

import com.puzzle15.puzzles.Position;

public interface ModifiablePuzzlesState extends PuzzlesState {

    void swap(Position position1, Position position2);

    default void swap(int puzzleNumber1, int puzzleNumber2) {
        swap(getPosition(puzzleNumber1), getPosition(puzzleNumber2));
    }
}
