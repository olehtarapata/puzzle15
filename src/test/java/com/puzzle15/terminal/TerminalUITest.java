package com.puzzle15.terminal;

import com.puzzle15.puzzles.Position;
import com.puzzle15.puzzles.Puzzles;
import com.puzzle15.puzzles.PuzzlesImpl;
import com.puzzle15.puzzles.factory.ShuffledPuzzlesFactory;
import com.puzzle15.puzzles.factory.WinPuzzlesFactory;
import com.puzzle15.puzzles.state.ModifiablePuzzlesState;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.puzzle15.terminal.TerminalUI.CLEAR_LINE;
import static com.puzzle15.terminal.TerminalUI.EXIT_COMMAND;
import static com.puzzle15.terminal.TerminalUI.EXIT_MESSAGE;
import static com.puzzle15.terminal.TerminalUI.ILLEGAL_PUZZLE_MESSAGE;
import static com.puzzle15.terminal.TerminalUI.IMPOSSIBLE_TO_PARSE_MESSAGE;
import static com.puzzle15.terminal.TerminalUI.PROMPT_MESSAGE;
import static com.puzzle15.terminal.TerminalUI.PUZZLE_NOT_NEIGHBOR_MESSAGE;
import static com.puzzle15.terminal.TerminalUI.WELCOME_MESSAGE;
import static com.puzzle15.terminal.TerminalUI.WIN_MESSAGE;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class TerminalUITest {

    private static final int SIZE = 4;

    @Test
    public void checkPrintPuzzlesFormat() throws IOException {
        final PipedInputStream output = new PipedInputStream();
        final PrintStream terminalOutput = new PrintStream(new PipedOutputStream(output));
        new TerminalUI(new PuzzlesImpl(new WinPuzzlesFactory(SIZE)), terminalOutput).printPuzzles();
        terminalOutput.close();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(output));
        assertThat(reader.readLine(), is("   1   2   3   4"));
        assertThat(reader.readLine(), is("   5   6   7   8"));
        assertThat(reader.readLine(), is("   9  10  11  12"));
        assertThat(reader.readLine(), is("  13  14  15    "));
        assertThat(reader.readLine(), is(nullValue()));
    }

    @Test
    public void checkPrintWinMessage() throws IOException {
        final PipedInputStream output = new PipedInputStream();
        final PrintStream terminalOutput = new PrintStream(new PipedOutputStream(output));
        new TerminalUI(new PuzzlesImpl(new WinPuzzlesFactory(SIZE)), terminalOutput).handleInput();
        terminalOutput.close();
        assertThat(readLastLine(output), is(CLEAR_LINE + WIN_MESSAGE));
    }

    @Test
    public void checkPrintExitMessage() throws IOException {
        final InputStream terminalInput = new ByteArrayInputStream(EXIT_COMMAND.getBytes(StandardCharsets.UTF_8));
        final PipedInputStream output = new PipedInputStream();
        final PrintStream terminalOutput = new PrintStream(new PipedOutputStream(output));
        new TerminalUI(new PuzzlesImpl(new ShuffledPuzzlesFactory(new WinPuzzlesFactory(SIZE))), terminalInput, terminalOutput).handleInput();
        terminalOutput.close();
        assertThat(readLastLine(output), is(CLEAR_LINE + PROMPT_MESSAGE + CLEAR_LINE + EXIT_MESSAGE));
    }

    @Test
    public void impossibleToParsePuzzleNumber() throws IOException {
        final String puzzleNumber = "abc123";
        final String inputString = puzzleNumber + "\n" + EXIT_COMMAND;
        final InputStream terminalInput = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        final PipedInputStream output = new PipedInputStream();
        final PrintStream terminalOutput = new PrintStream(new PipedOutputStream(output));
        new TerminalUI(new PuzzlesImpl(new ShuffledPuzzlesFactory(new WinPuzzlesFactory(SIZE))), terminalInput, terminalOutput).handleInput();
        terminalOutput.close();
        final List<String> allLines = readAllLines(output);
        assertThat(allLines.contains(String.format(WELCOME_MESSAGE, 15)), is(true));
        assertThat(allLines.contains(CLEAR_LINE + PROMPT_MESSAGE + CLEAR_LINE + IMPOSSIBLE_TO_PARSE_MESSAGE + puzzleNumber), is(true));
        assertThat(allLines.contains(CLEAR_LINE + PROMPT_MESSAGE + CLEAR_LINE + EXIT_MESSAGE), is(true));
    }

    @Test
    public void illegalPuzzleNumber() throws IOException {
        final String puzzleNumber = "16";
        final String inputString = puzzleNumber + "\n" + EXIT_COMMAND;
        final InputStream terminalInput = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        final PipedInputStream output = new PipedInputStream();
        final PrintStream terminalOutput = new PrintStream(new PipedOutputStream(output));
        new TerminalUI(new PuzzlesImpl(new ShuffledPuzzlesFactory(new WinPuzzlesFactory(SIZE))), terminalInput, terminalOutput).handleInput();
        terminalOutput.close();
        final List<String> allLines = readAllLines(output);
        assertThat(allLines.contains(String.format(WELCOME_MESSAGE, 15)), is(true));
        assertThat(allLines.contains(CLEAR_LINE + PROMPT_MESSAGE + CLEAR_LINE + ILLEGAL_PUZZLE_MESSAGE + puzzleNumber), is(true));
        assertThat(allLines.contains(CLEAR_LINE + PROMPT_MESSAGE + CLEAR_LINE + EXIT_MESSAGE), is(true));
    }

    @Test
    public void puzzlesNotNeighbors() throws IOException {
        final ModifiablePuzzlesState state = new ShuffledPuzzlesFactory(new WinPuzzlesFactory(SIZE)).generate();
        final Puzzles puzzles = new PuzzlesImpl(state);
        final Position emptyPosition = state.getPosition(Puzzles.EMPTY_PUZZLE_NUMBER);
        int puzzleNumber = 0;
        for (int i = 0; i < state.rawsCount(); i++) {
            for (int j = 0; j < state.columnsCount(); j++) {
                final Position position = new Position(i, j);
                if (!emptyPosition.isNeighbor(position)) {
                    puzzleNumber = state.get(position);
                    if (puzzleNumber != Puzzles.EMPTY_PUZZLE_NUMBER) {
                        break;
                    }
                }
            }
        }
        final String inputString = puzzleNumber + "\n" + EXIT_COMMAND;
        final InputStream terminalInput = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        final PipedInputStream output = new PipedInputStream();
        final PrintStream terminalOutput = new PrintStream(new PipedOutputStream(output));
        new TerminalUI(puzzles, terminalInput, terminalOutput).handleInput();
        terminalOutput.close();
        final List<String> allLines = readAllLines(output);
        System.out.println(allLines);
        System.out.println(String.format(WELCOME_MESSAGE, 15).length());
        System.out.println(allLines.get(0).length());
        assertThat(allLines.contains(String.format(WELCOME_MESSAGE, 15)), is(true));
        assertThat(allLines.contains(CLEAR_LINE + PROMPT_MESSAGE + CLEAR_LINE + PUZZLE_NOT_NEIGHBOR_MESSAGE + puzzleNumber), is(true));
        assertThat(allLines.contains(CLEAR_LINE + PROMPT_MESSAGE + CLEAR_LINE + EXIT_MESSAGE), is(true));
    }

    private String readLastLine(final PipedInputStream output) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(output));
        String lastLine = null;
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            lastLine = currentLine;
        }
        return lastLine;
    }

    private List<String> readAllLines(final PipedInputStream output) throws IOException {
        return readAllLines(new BufferedReader(new InputStreamReader(output)));
    }

    private List<String> readAllLines(final BufferedReader reader) throws IOException {
        final List<String> result = new ArrayList<>();
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            result.add(currentLine);
        }
        return result;
    }
}