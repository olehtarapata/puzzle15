package com.puzzle15.puzzles.state;

import com.puzzle15.puzzles.Position;

public interface PuzzlesState {

    int rawsCount();

    int columnsCount();

    int get(Position position);

    default int cellsCount() {
        return rawsCount() * columnsCount();
    }
}
