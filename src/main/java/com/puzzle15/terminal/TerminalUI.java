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

    static final String WELCOME_MESSAGE = "Welcome to Puzzles %s!";

    static final String CLEAR_LINE = "\033[K";

    static final String CURSOR_UP = Character.toString((char) 27) + "[A";

    static final String CURSOR_DOWN = "\027[B";

    static final String CURSOR_FORWARD = "\027[C";

    static final String CURSOR_BACK = "\027[D";

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
        // clear the console
        output.println("\033[H\033[2J");
        output.printf(WELCOME_MESSAGE, puzzles.puzzles().cellsCount() - 1);
        output.println();
        final String upCommand = "\033[" + (puzzles.puzzles().rawsCount() + 4) + "A";
        try (final Scanner scanner = new Scanner(input)) {
            while (true) {
                output.println();
                printPuzzles();
                output.println();
                if (puzzles.isWin()) {
                    output.println(CLEAR_LINE + WIN_MESSAGE);
                    return;
                }
                output.print(CLEAR_LINE + PROMPT_MESSAGE);
                final String command = scanner.nextLine();
                if (EXIT_COMMAND.equalsIgnoreCase(command)) {
                    output.println(CLEAR_LINE + EXIT_MESSAGE);
                    return;
                }
                final int puzzleNumber;
                try {
                    puzzleNumber = Integer.parseInt(command);
                    final Status status = puzzles.move(puzzleNumber);
                    if (Status.ILLEGAL_PUZZLE_NUMBER == status) {
                        output.println(CLEAR_LINE + ILLEGAL_PUZZLE_MESSAGE + puzzleNumber);
                    }
                    if (Status.NOT_NEIGHBORS == status) {
                        output.println(CLEAR_LINE + PUZZLE_NOT_NEIGHBOR_MESSAGE + puzzleNumber);
                    }
                    if (Status.OK == status) {
                        output.println(CLEAR_LINE);
                    }
                } catch (final NumberFormatException e) {
                    String commandToWrite = Character.toString(command.charAt(0)) + " " + command.charAt(1) + " " + command.charAt(2);
                    if (command.equals(CURSOR_UP)) {
                        commandToWrite = "cursor up ";
                    }
                    if (command.equals(CURSOR_DOWN)) {
                        commandToWrite = "cursor down";
                    }
                    if (command.equals(CURSOR_BACK)) {
                        commandToWrite = "cursor back";
                    }
                    if (command.equals(CURSOR_FORWARD)) {
                        commandToWrite = "cursor forward";
                    }
                    output.println(CLEAR_LINE + IMPOSSIBLE_TO_PARSE_MESSAGE + commandToWrite);
                }
                output.print(upCommand);
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
