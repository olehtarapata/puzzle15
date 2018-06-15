package com.puzzle15.terminal;

import com.puzzle15.puzzles.PuzzlePositionUtil;
import com.puzzle15.puzzles.Puzzles;
import com.puzzle15.puzzles.PuzzlesImpl;
import com.puzzle15.puzzles.ShuffledPuzzlesFactory;
import com.puzzle15.puzzles.WinPuzzlesFactory;
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

    @Test
    public void checkPrintPuzzlesFormat() throws IOException {
        final PipedInputStream output = new PipedInputStream();
        final PrintStream terminalOutput = new PrintStream(new PipedOutputStream(output));
        new TerminalUI(new PuzzlesImpl(new WinPuzzlesFactory()), terminalOutput).printPuzzles();
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
        new TerminalUI(new PuzzlesImpl(new WinPuzzlesFactory()), terminalOutput).handleInput();
        terminalOutput.close();
        assertThat(readLastLine(output), is(WIN_MESSAGE));
    }

    @Test
    public void checkPrintExitMessage() throws IOException {
        final InputStream terminalInput = new ByteArrayInputStream(EXIT_COMMAND.getBytes(StandardCharsets.UTF_8));
        final PipedInputStream output = new PipedInputStream();
        final PrintStream terminalOutput = new PrintStream(new PipedOutputStream(output));
        new TerminalUI(new PuzzlesImpl(new ShuffledPuzzlesFactory(new WinPuzzlesFactory())), terminalInput, terminalOutput).handleInput();
        terminalOutput.close();
        assertThat(readLastLine(output), is(PROMPT_MESSAGE + EXIT_MESSAGE));
    }

    @Test
    public void impossibleToParsePuzzleNumber() throws IOException {
        final String puzzleNumber = "abc123";
        final String inputString = puzzleNumber + "\n" + EXIT_COMMAND;
        final InputStream terminalInput = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        final PipedInputStream output = new PipedInputStream();
        final PrintStream terminalOutput = new PrintStream(new PipedOutputStream(output));
        new TerminalUI(new PuzzlesImpl(new ShuffledPuzzlesFactory(new WinPuzzlesFactory())), terminalInput, terminalOutput).handleInput();
        terminalOutput.close();
        final List<String> allLines = readAllLines(output);
        assertThat(allLines.contains(WELCOME_MESSAGE), is(true));
        assertThat(allLines.contains(PROMPT_MESSAGE + IMPOSSIBLE_TO_PARSE_MESSAGE + puzzleNumber), is(true));
        assertThat(allLines.contains(PROMPT_MESSAGE + EXIT_MESSAGE), is(true));
    }

    @Test
    public void illegalPuzzleNumber() throws IOException {
        final String puzzleNumber = "16";
        final String inputString = puzzleNumber + "\n" + EXIT_COMMAND;
        final InputStream terminalInput = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        final PipedInputStream output = new PipedInputStream();
        final PrintStream terminalOutput = new PrintStream(new PipedOutputStream(output));
        new TerminalUI(new PuzzlesImpl(new ShuffledPuzzlesFactory(new WinPuzzlesFactory())), terminalInput, terminalOutput).handleInput();
        terminalOutput.close();
        final List<String> allLines = readAllLines(output);
        assertThat(allLines.contains(WELCOME_MESSAGE), is(true));
        assertThat(allLines.contains(PROMPT_MESSAGE + ILLEGAL_PUZZLE_MESSAGE + puzzleNumber), is(true));
        assertThat(allLines.contains(PROMPT_MESSAGE + EXIT_MESSAGE), is(true));
    }

    @Test
    public void puzzlesNotNeighbors() throws IOException {
        final Puzzles puzzles = new PuzzlesImpl(new ShuffledPuzzlesFactory(new WinPuzzlesFactory()));
        final int emptyPosition = findEmptyPosition(puzzles);
        final int[] puzzlesArray = puzzles.puzzles();
        int puzzleNumber = 0;
        for (int i = 0; i < Puzzles.CELLS_COUNT; i++) {
            if (!PuzzlePositionUtil.isNeighbors(emptyPosition, i)) {
                if (puzzlesArray[i] != Puzzles.EMPTY_PUZZLE_NUMBER) {
                    puzzleNumber = puzzlesArray[i];
                    break;
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
        assertThat(allLines.contains(WELCOME_MESSAGE), is(true));
        assertThat(allLines.contains(PROMPT_MESSAGE + PUZZLE_NOT_NEIGHBOR_MESSAGE + puzzleNumber), is(true));
        assertThat(allLines.contains(PROMPT_MESSAGE + EXIT_MESSAGE), is(true));
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

    private int findEmptyPosition(final Puzzles puzzles) {
        final int[] puzzlesArray = puzzles.puzzles();
        for (int i = 0; i < puzzlesArray.length; i++) {
            if (puzzlesArray[i] == Puzzles.EMPTY_PUZZLE_NUMBER) {
                return i;
            }
        }
        throw new IllegalArgumentException("Puzzles do not contain empty place");
    }
}