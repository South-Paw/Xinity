package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the bluesScale() command.
 *
 * @author plr37
 */
public class BluesScaleTest {
    private Environment env = new Environment();
    private List<String> args;

    final private String errorScaleNote = "[ERROR] " + Error.INVALIDNOTE.getError();
    final private String errorMidiNote = "[ERROR] " + Error.INVALIDMIDINOTE.getError();
    final private String errorArgs = "[ERROR] "
            + Error.INVALIDSCALEARGUMENTS.getError();
    final private String errorNoScale = "[ERROR] " + Error.FINDSCALENOSCALE.getError();

    @Before
    public void setup() throws Exception {
        args = new ArrayList<>();
    }

    @Test
    public void testX() {
        args.add("X");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals(errorScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testE() {
        args.add("E4");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("E4, G4, A4, Bb4, B4, D5, E5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testC() {
        args.add("C4");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("C4, Eb4, F4, Gb4, G4, Bb4, C5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testD() {
        args.add("D4");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("D4, F4, G4, Ab4, A4, C5, D5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDSharp() {
        args.add("D#4");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("D#4, F#4, G#4, A4, A#4, C#5, D#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testEb() {
        args.add("Eb4");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("Eb4, Gb4, Ab4, A4, Bb4, Db5, Eb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testF() {
        args.add("F4");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("F4, Ab4, Bb4, B4, C5, Eb5, F5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFSharp() {
        args.add("F#4");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("F#4, A4, B4, C5, C#5, E5, F#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testG() {
        args.add("G4");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("G4, Bb4, C5, Db5, D5, F5, G5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testGSharp() {
        args.add("G#4");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("G#4, B4, C#5, D5, D#5, F#5, G#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testA() {
        args.add("A4");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("A4, C5, D5, Eb5, E5, G5, A5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAb() {
        args.add("Ab4");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("Ab4, B4, Db5, D5, Eb5, Gb5, Ab5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testASharp() {
        args.add("A#4");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("A#4, C#5, D#5, E5, F5, G#5, A#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBSharp() {
        args.add("B#");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testB() {
        args.add("B4");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("B4, D5, E5, F5, F#5, A5, B5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBb() {
        args.add("Bb4");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("Bb4, Db5, Eb5, E5, F5, Ab5, Bb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testG9() {
        args.add("G9");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("G9 - The rest of the scale cannot be mapped to midi", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //invalid
    @Test
    public void testNull() {
        new FindScale(args).execute(env);
        assertEquals(errorArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidNote() {
        args.add("invalid");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals(errorScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidScale() {
        args.add("C");
        args.add("invalid");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals(errorArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testOutofRange() {
        args.add("C10");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals(errorMidiNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

}