package com.puzzle15.terminal;

import com.puzzle15.puzzles.PuzzlesImpl;
import com.puzzle15.puzzles.ShuffledPuzzlesFactory;
import com.puzzle15.puzzles.WinPuzzlesFactory;

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
                        new ShuffledPuzzlesFactory(
                                new WinPuzzlesFactory()
                        )
                )
        ).handleInput();
    }
}
