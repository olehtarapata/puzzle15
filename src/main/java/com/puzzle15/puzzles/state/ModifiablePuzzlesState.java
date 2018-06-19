package com.puzzle15.puzzles.state;

import com.puzzle15.puzzles.Position;

public interface ModifiablePuzzlesState extends PuzzlesState {

    void swap(Position position1, Position position2);
}
