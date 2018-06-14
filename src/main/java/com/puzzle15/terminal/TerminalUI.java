package com.puzzle15.terminal;

import com.puzzle15.puzzles.Puzzles;
import com.puzzle15.puzzles.Status;

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
        output.println("Welcome to Puzzles 15!");
        try (final Scanner scanner = new Scanner(input)) {
            while (true) {
                output.println();
                printPuzzles();
                output.println();
                if (puzzles.isWin()) {
                    output.println(WIN_MESSAGE);
                    return;
                }
                output.print(String.format("Please enter a puzzle number to move or '%s': ", EXIT_COMMAND));
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
                        output.println("Illegal puzzle number: " + puzzleNumber);
                    }
                    if (Status.NOT_NEIGHBORS == status) {
                        output.println("Puzzle not neighbor to empty place: " + puzzleNumber);
                    }
                } catch (final NumberFormatException e) {
                    output.println("Impossible to parse puzzles number: " + command);
                }
            }
        }
    }

    void printPuzzles() {
        final int[] puzzlesArray = puzzles.puzzles();
        for (int i = 0; i < Puzzles.CELLS_COUNT; i++) {
            if (puzzlesArray[i] == Puzzles.EMPTY_PUZZLE_NUMBER) {
                output.print("    ");
            } else {
                output.printf("%1$4s", puzzlesArray[i]);
            }
            if ((i + 1) % Puzzles.COLUMNS_COUNT == 0) {
                output.println();
            }
        }
    }
}
