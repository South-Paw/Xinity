package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.command.musical.Midi;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Midi command tests.
 *
 * @author adg62
 */
public class MidiTest {
    private Environment env = new Environment();
    private List<String> args;

    @Before
    public void setup() {
        args = new ArrayList<>();
    }

    /**
     * Test with no arguments given.
     */
    @Test
    public void noArgumentsGiven() {
        args.clear();

        new Midi(args).execute(env);

        assertEquals(
            "[ERROR] Invalid arguments. Midi command expects a single note. Eg: midi(C4).",
            env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    /**
     * Test with two arguments given.
     */
    @Test
    public void twoArgumentsGiven() {
        args.clear();
        args.add("C4");
        args.add("D4");

        new Midi(args).execute(env);

        assertEquals(
            "[ERROR] Invalid arguments. Midi command expects a single note. Eg: midi(C4).",
            env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    /**
     * Test with an invalid argument C-2
     */
    @Test
    public void invalidArgumentCNegative2() {
        args.clear();
        args.add("C-2");

        new Midi(args).execute(env);

        assertEquals(
            "[ERROR] Invalid note. Not within the midi range (0 - 127).",
            env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    /**
     * Test that E# is invalid.
     */
    @Test
    public void invalidArgumentESharp() {
        args.clear();
        args.add("E#");

        new Midi(args).execute(env);

        assertEquals(
                "65",
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    /**
     * Test with a valid argument C4.
     */
    @Test
    public void validArgumentC4() {
        args.clear();
        args.add("C4");

        new Midi(args).execute(env);

        assertEquals("60", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /**
     * Test that a note with a sharp and negative is valid.
     */
    @Test
    public void validArgumentDSharpNegative1() {
        args.clear();
        args.add("D#-1");

        new Midi(args).execute(env);

        assertEquals("3", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /**
     * Test that C-1 = 0
     */
    @Test
    public void validArgumentCNegative1() {
        args.clear();
        args.add("C-1");

        new Midi(args).execute(env);

        assertEquals("0", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /**
     * Test that G9 = 127
     */
    @Test
    public void validArgumentG9() {
        args.clear();
        args.add("G9");

        new Midi(args).execute(env);

        assertEquals("127", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /**
     * Test that A4 = 69
     */
    @Test
    public void validArgumentA4() {
        args.clear();
        args.add("A4");

        new Midi(args).execute(env);

        assertEquals("69", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /**
     * Test that G7 = 103
     */
    @Test
    public void validArgumentG7() {
        args.clear();
        args.add("G7");

        new Midi(args).execute(env);

        assertEquals("103", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /**
     * Test that D#7 = 99
     */
    @Test
    public void validArgumentDSharp7() {
        args.clear();
        args.add("D#7");

        new Midi(args).execute(env);

        assertEquals("99", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /**
     * Test that Gb = 66
     */
    @Test
    public void validArgumentCFlat() {
        args.clear();
        args.add("Gb");

        new Midi(args).execute(env);

        assertEquals("66", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /**
     * Test that F4 = 65
     */
    @Test
    public void validArgumentF4() {
        args.clear();
        args.add("F4");

        new Midi(args).execute(env);

        assertEquals("65", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void validArgumentFDoublesharp4() {
        args.clear();
        args.add("F##4");

        new Midi(args).execute(env);

        assertEquals("67", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void invalidOutOfBoundsUpper() {
        args.clear();
        args.add("G#9");

        new Midi(args).execute(env);

        assertEquals("[ERROR] Invalid note. Not within the midi range (0 - 127).", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void validGDoubleSharpWithX7() {
        args.clear();
        args.add("Gx7");

        new Midi(args).execute(env);

        assertEquals("105", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void validGDoubleFlat() {
        args.clear();
        args.add("Gbb");

        new Midi(args).execute(env);

        assertEquals("65", env.getOutput().replaceAll("\\r|\\n", ""));
    }
}