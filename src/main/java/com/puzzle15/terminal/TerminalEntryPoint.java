package com.puzzle15.terminal;

import com.puzzle15.puzzles.PuzzlesImpl;
import com.puzzle15.puzzles.factory.ShuffledPuzzlesFactory;
import com.puzzle15.puzzles.factory.SolvedPuzzlesFactory;
import com.puzzle15.puzzles.factory.WinPuzzlesFactory;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public class TerminalEntryPoint implements Runnable {

    public static void main(String[] args) {
        new TerminalEntryPoint().run();
    }

    @Override
    public void run() {
        new TerminalUI(
                new PuzzlesImpl(
                        new SolvedPuzzlesFactory(
                                new ShuffledPuzzlesFactory(
                                        new WinPuzzlesFactory()
                                )
                        ).generate()
                )
        ).handleInput();
    }
}
