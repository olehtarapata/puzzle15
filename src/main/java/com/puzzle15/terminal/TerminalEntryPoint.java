package com.puzzle15.terminal;

import com.puzzle15.puzzles.PuzzlesImpl;
import com.puzzle15.puzzles.factory.ShuffledPuzzlesFactory;
import com.puzzle15.puzzles.factory.SolvablePuzzlesFactory;
import com.puzzle15.puzzles.factory.WinPuzzlesFactory;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public class TerminalEntryPoint {

    public static void main(String[] args) {
        if (args.length == 0 || args.length > 1) {
            printUsage();
            return;
        }
        final int size;
        try {
            size = Integer.parseInt(args[0]);
        } catch (final NumberFormatException e) {
            System.out.println("Impossible to parse: " + args[0]);
            printUsage();
            return;
        }
        if (size < 2 || size > 10) {
            System.out.println("Invalid size: " + size);
            printUsage();
            return;
        }
        new TerminalEntryPoint().run(size);
    }

    private static void printUsage() {
        System.out.println("Usage: <size> (from 2 to 10)");
    }

    private void run(final int size) {
        new TerminalUI(
                new PuzzlesImpl(
                        new SolvablePuzzlesFactory(
                                new ShuffledPuzzlesFactory(
                                        new WinPuzzlesFactory(size)
                                )
                        ).generate()
                )
        ).handleInput();
    }
}
