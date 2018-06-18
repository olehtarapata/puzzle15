package com.puzzle15.terminal;

import com.puzzle15.puzzles.Position;
import com.puzzle15.puzzles.Puzzles;
import com.puzzle15.puzzles.Status;
import com.puzzle15.puzzles.state.PuzzlesState;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public class TerminalUI {

    static final String EXIT_COMMAND = "q";

    static final String EXIT_MESSAGE = "Bye!";

    static final String WIN_MESSAGE = "Congratulations! You win the game!";

    static final String IMPOSSIBLE_TO_PARSE_MESSAGE = "Impossible to parse puzzles number: ";

    static final String ILLEGAL_PUZZLE_MESSAGE = "Illegal puzzle number: ";

    static final String PUZZLE_NOT_NEIGHBOR_MESSAGE = "Puzzle is not neighbor to empty place: ";

    static final String PROMPT_MESSAGE = String.format("Please enter a puzzle number to move or '%s' to exit: ", EXIT_COMMAND);

    static final String WELCOME_MESSAGE = "Welcome to Puzzles 15!";

    private final Puzzles puzzles;

    private final InputStream input;

    private final PrintStream output;

    public TerminalUI(final Puzzles puzzles) {
        this(puzzles, System.in, System.out);
    }

    public TerminalUI(final Puzzles puzzles, PrintStream output) {
        this(puzzles, System.in, output);
    }

    public TerminalUI(final Puzzles puzzles, final InputStream input, final PrintStream output) {
        this.puzzles = puzzles;
        this.input = input;
        this.output = output;
    }

    public void handleInput() {
        output.println(WELCOME_MESSAGE);
        try (final Scanner scanner = new Scanner(input)) {
            while (true) {
                output.println();
                printPuzzles();
                output.println();
                if (puzzles.isWin()) {
                    output.println(WIN_MESSAGE);
                    return;
                }
                output.print(PROMPT_MESSAGE);
                final String command = scanner.nextLine().trim();
                if (EXIT_COMMAND.equalsIgnoreCase(command)) {
                    output.println(EXIT_MESSAGE);
                    return;
                }
                final int puzzleNumber;
                try {
                    puzzleNumber = Integer.parseInt(command);
                    final Status status = puzzles.move(puzzleNumber);
                    if (Status.ILLEGAL_PUZZLE_NUMBER == status) {
                        output.println(ILLEGAL_PUZZLE_MESSAGE + puzzleNumber);
                    }
                    if (Status.NOT_NEIGHBORS == status) {
                        output.println(PUZZLE_NOT_NEIGHBOR_MESSAGE + puzzleNumber);
                    }
                } catch (final NumberFormatException e) {
                    output.println(IMPOSSIBLE_TO_PARSE_MESSAGE + command);
                }
            }
        }
    }

    void printPuzzles() {
        final PuzzlesState state = puzzles.puzzles();
        for (int i = 0; i < state.rawsCount(); i++) {
            for (int j = 0; j < state.columnsCount(); j++) {
                final int puzzleNumber = state.get(new Position(i, j));
                if (puzzleNumber == Puzzles.EMPTY_PUZZLE_NUMBER) {
                    output.print("    ");
                } else {
                    output.printf("%1$4s", puzzleNumber);
                }
            }
            output.println();
        }
    }
}
