package com.puzzle15.terminal;

import com.puzzle15.puzzles.Puzzles;
import com.puzzle15.puzzles.Status;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Oleg Tarapata (oleh.tarapata@gmail.com)
 */
public class TerminalUI {

    private static final String QUIT_COMMAND = "quit";

    private final Puzzles puzzles = null;

    public void handleInput() {
        System.out.println("Welcome to Puzzles 15!");
        try (final Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println();
                printPuzzles(puzzles, System.out);
                if (puzzles.isWin()) {
                    System.out.println("Congratulations! You win the game!");
                    return;
                }
                System.out.println(String.format("Please enter a puzzle number to move or '%s': ", QUIT_COMMAND));
                final String command = scanner.nextLine().trim();
                if (QUIT_COMMAND.equalsIgnoreCase(command)) {
                    System.out.println("Bye!");
                    return;
                }
                final int puzzleNumber;
                try {
                    puzzleNumber = Integer.parseInt(command);
                    final Status status = puzzles.move(puzzleNumber);
                    if (Status.ILLEGAL_PUZZLE_NUMBER == status) {
                        System.out.println("Illegal puzzle number: " + puzzleNumber);
                    }
                    if (Status.NOT_NEIGHBORS == status) {
                        System.out.println("Puzzle not neighbor to empty place");
                    }
                } catch (final NumberFormatException e) {
                    System.out.println("Impossible to parse puzzles number: " + command);
                }
            }
        }
    }

    public void printPuzzles(final Puzzles puzzles, final PrintStream writer) {
        final int[] puzzlesArray = puzzles.puzzles();
        for (int i = 0; i < Puzzles.CELLS_COUNT; i++) {
            if (puzzlesArray[i] == Puzzles.EMPTY_PUZZLE_NUMBER) {
                writer.println("    ");
            } else {
                writer.printf("%1$4s", puzzlesArray[i]);
            }
            if ((i + 1) % Puzzles.COLUMNS_COUNT == 0) {
                writer.println();
            }
        }
    }
}
