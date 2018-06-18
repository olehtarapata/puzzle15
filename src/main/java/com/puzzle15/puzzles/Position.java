package com.puzzle15.puzzles;

public class Position {

    private final int raw;

    private final int column;

    public Position(final int index, final int rawsCount, final int columsCount) {
        this(index / columsCount % rawsCount, index % columsCount);
    }

    public Position(final int raw, final int column) {
        this.raw = raw;
        this.column = column;
    }

    public int getRaw() {
        return raw;
    }

    public int getColumn() {
        return column;
    }

    public boolean isNeighbor(Position position) {
        if (raw == position.raw) {
            return Math.abs(column - position.column) == 1;
        }
        if (column == position.column) {
            return Math.abs(raw - position.raw) == 1;
        }
        return false;
    }

    public Position next(final int columnsCount) {
        int newRaw = raw;
        int newColumn = column + 1;
        if (newColumn == columnsCount) {
            newRaw++;
            newColumn = 0;
        }
        return new Position(newRaw, newColumn);
    }
}
