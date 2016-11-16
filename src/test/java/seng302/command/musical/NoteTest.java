package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.util.enumerator.Error;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for the Note command.
 *
 * @author adg62
 */

public class NoteTest {
    private Environment env = new Environment();
    private List<String> args;
    private String invalidArgs = "[ERROR] " + Error.INVALIDNOTEARGUMENTS.getError();
    private String invalidMidi = "[ERROR] " + Error.INVALIDMIDINOTE.getError();

    @Before
    public void setUp() {
        args = new ArrayList<>();
    }

    /**
     * Test that 2 arguments are invalid.
     */
    @Test
    public void twoArgumentsInvalid() {
        args.clear();
        args.add("30");
        args.add("40");

        new Note(args).execute(env);

        assertEquals(invalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /**
     * Test that negative 1 is out of range.
     */
    @Test
    public void invalidArgumentNegative1() {
        args.clear();
        args.add("-1");

        new Note(args).execute(env);

        assertEquals(invalidMidi, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /**
     * Test that 128 is out of range.
     */
    @Test
    public void invalidArgument128() {
        args.clear();
        args.add("128");

        new Note(args).execute(env);

        assertEquals(invalidMidi, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /**
     * Test that note(60) returns C4.
     */
    @Test
    public void validArgument60() {
        args.clear();
        args.add("60");

        new Note(args).execute(env);

        assertEquals("C4", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /**
     * Test that note(0) returns C-1.
     */
    @Test
    public void validArgument0() {
        args.clear();
        args.add("0");

        new Note(args).execute(env);

        assertEquals("C-1", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /**
     * Test that note(127) returns G9.
     */
    @Test
    public void validArgument127() {
        args.clear();
        args.add("127");

        new Note(args).execute(env);

        assertEquals("G9", env.getOutput().replaceAll("\\r|\\n", ""));
    }
}