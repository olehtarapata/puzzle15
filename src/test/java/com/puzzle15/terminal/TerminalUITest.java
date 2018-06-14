package com.puzzle15.terminal;

import com.puzzle15.puzzles.PuzzlesImpl;
import com.puzzle15.puzzles.ShuffledPuzzlesFactory;
import com.puzzle15.puzzles.WinPuzzlesFactory;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.Writer;

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
        assertThat(readLastLine(output), is(TerminalUI.WIN_MESSAGE));
    }

    @Test
    public void checkPrintExitMessage() throws IOException {
        final PipedOutputStream pipedOutputStream = new PipedOutputStream();
        final PipedInputStream terminalInput = new PipedInputStream(pipedOutputStream);
        final BufferedWriter input = new BufferedWriter(new OutputStreamWriter(pipedOutputStream));
        final PipedInputStream output = new PipedInputStream();
        final PrintStream terminalOutput = new PrintStream(new PipedOutputStream(output));
        new TerminalUI(new PuzzlesImpl(new ShuffledPuzzlesFactory(new WinPuzzlesFactory())), terminalInput, terminalOutput).handleInput();
        input.write("q");
        input.newLine();
        input.flush();
        terminalOutput.close();
        assertThat(readLastLine(output), is(TerminalUI.EXIT_MESSAGE));
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
}