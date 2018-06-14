package com.puzzle15.puzzles;

import static com.puzzle15.puzzles.Puzzles.COLUMNS_COUNT;
import static com.puzzle15.puzzles.Puzzles.RAWS_COUNT;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public final class PuzzlePositionUtil {

    private PuzzlePositionUtil() {
        throw new IllegalAccessError("Should not be instantiated");
    }

    public static boolean isNeighbors(final int position1, final int position2) {
        final int position1Raw = getRaw(position1);
        final int position1Column = getColumn(position1);
        final int position2Raw = getRaw(position2);
        final int position2Column = getColumn(position2);
        if (position1Raw == position2Raw) {
            return Math.abs(position1Column - position2Column) == 1;
        }
        if (position1Column == position2Column) {
            return Math.abs(position1Raw - position2Raw) == 1;
        }
        return false;
    }

    static int getRaw(final int position) {
        return position / RAWS_COUNT;
    }

    static int getColumn(final int position) {
        return position % COLUMNS_COUNT;
    }
}
