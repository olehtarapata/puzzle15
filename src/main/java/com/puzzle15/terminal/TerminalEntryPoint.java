package com.puzzle15.terminal;

import com.puzzle15.puzzles.PuzzlesImpl;
import com.puzzle15.puzzles.factory.ShuffledPuzzlesFactory;
import com.puzzle15.puzzles.factory.SolvablePuzzlesFactory;
import com.puzzle15.puzzles.factory.WinPuzzlesFactory;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public class TerminalEntryPoint implements Runnable {

    public static final int SIZE = 4;

    public static void main(String[] args) {
        new TerminalEntryPoint().run();
    }

    @Override
    public void run() {
        new TerminalUI(
                new PuzzlesImpl(
                        new SolvablePuzzlesFactory(
                                new ShuffledPuzzlesFactory(
                                        new WinPuzzlesFactory(SIZE, SIZE)
                                )
                        ).generate()
                )
        ).handleInput();
    }
}
