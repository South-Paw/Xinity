package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.util.enumerator.Error;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntervalTest {
    private Environment env = new Environment();
    private List<String> args;
    private String errorInvalidArgs = "[ERROR] " + Error.INTERVALINVALIDARGUMENTS.getError();
    private String errorDoesNotExist = "[ERROR] " + Error.INTERVALDOESNOTEXIST.getError();
    private String errorOutOfMidiRange = "[ERROR] " + Error.INVALIDMIDINOTE.getError();
    private String errorCannotCreateNote = "[ERROR] " + Error.XINITYNOTECANNOTCREATENOTE.getError();

    @Before
    public void setUp() {
        args = new ArrayList<>();
    }

    @Test
    public void intervalArgsWrongWay() {
        args.clear();

        args.add("Major 6th");
        args.add("C");

        new Interval(args).execute(env);

        assertEquals(errorInvalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalMissingNote() {
        args.clear();

        args.add("Major 6th");

        new Interval(args).execute(env);

        assertEquals(errorInvalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalCPerfectUnison() {
        args.clear();

        args.add("C");
        args.add("Perfect Unison");

        new Interval(args).execute(env);

        assertEquals("C", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalCNegative1PerfectUnison() {
        args.clear();

        args.add("C-1");
        args.add("Perfect Unison");

        new Interval(args).execute(env);

        assertEquals("C-1", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalNoNotePerfectUnison() {
        args.clear();

        args.add("");
        args.add("Perfect Unison");

        new Interval(args).execute(env);

        assertEquals(errorInvalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalPerfectUnisonTypo() {
        args.clear();

        args.add("A");
        args.add("Perfect Unizon");

        new Interval(args).execute(env);

        assertEquals(errorDoesNotExist, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalPerfect4thA6() {
        args.clear();

        args.add("a6");
        args.add("Perfect 4th");

        new Interval(args).execute(env);

        assertEquals("D7", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalAllLowercase() {
        args.clear();

        args.add("a");
        args.add("perfect unison");

        new Interval(args).execute(env);

        assertEquals("A", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalCNeg2MajorSecond() {
        args.clear();

        args.add("C-2");
        args.add("major second");

        new Interval(args).execute(env);

        assertEquals(errorOutOfMidiRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalCNeg1Major3rd() {
        args.clear();

        args.add("C-1");
        args.add("Major 3rd");

        new Interval(args).execute(env);

        assertEquals("E-1", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalG9Major3rd() {
        args.clear();

        args.add("G9");
        args.add("Major 3rd");

        new Interval(args).execute(env);

        assertEquals(errorOutOfMidiRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalA9Major3rd() {
        args.clear();

        args.add("A9");
        args.add("Major 3rd");

        new Interval(args).execute(env);

        assertEquals(errorOutOfMidiRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalF9Minor3rd() {
        args.clear();

        args.add("F9");
        args.add("Minor 3rd");

        new Interval(args).execute(env);

        assertEquals(errorOutOfMidiRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalA8PerfectOctave() {
        args.clear();

        args.add("A8");
        args.add("Perfect Octave");

        new Interval(args).execute(env);

        assertEquals(errorOutOfMidiRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalDMajor2nd() {
        args.clear();

        args.add("D");
        args.add("Major 2nd");

        new Interval(args).execute(env);

        assertEquals("E", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalEMajor3rd() {
        args.clear();

        args.add("E");
        args.add("Major 3rd");

        new Interval(args).execute(env);

        assertEquals("G#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalFPerfect4th() {
        args.clear();

        args.add("F");
        args.add("Perfect 4th");

        new Interval(args).execute(env);

        assertEquals("Bb", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalGPerfect5th() {
        args.clear();

        args.add("G");
        args.add("Perfect 5th");

        new Interval(args).execute(env);

        assertEquals("D", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalAMajor6th() {
        args.clear();

        args.add("A");
        args.add("Major 6th");

        new Interval(args).execute(env);

        assertEquals("F#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalOutOfBoundsUpper() {
        args.clear();

        args.add("A10");
        args.add("Major 6th");

        new Interval(args).execute(env);

        assertEquals(errorOutOfMidiRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalDSharp9Major3rd() {
        args.clear();

        args.add("D#9");
        args.add("Major 3rd");

        new Interval(args).execute(env);

        assertEquals("Fx9", env.getOutput().replaceAll("\\r|\\n", ""));
    }


    @Test
    public void intervalBMajor7th() {
        args.clear();

        args.add("B");
        args.add("Major 7th");

        new Interval(args).execute(env);

        assertEquals("A#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalCPerfectOctave() {
        args.clear();

        args.add("C");
        args.add("Perfect Octave");

        new Interval(args).execute(env);

        assertEquals("C", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalC8PerfectOctave() {
        args.clear();

        args.add("C8");
        args.add("Perfect Octave");

        new Interval(args).execute(env);

        assertEquals("C9", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalC4Major2nd() {
        args.clear();

        args.add("C4");
        args.add("Major 2nd");

        new Interval(args).execute(env);

        assertEquals("D4", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalASharpPerfectOctave() {
        args.clear();

        args.add("A#");
        args.add("Perfect Octave");

        new Interval(args).execute(env);

        assertEquals("A#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalCSharpMinor2nd() {
        args.clear();

        args.add("C#");
        args.add("Minor Second");

        new Interval(args).execute(env);

        assertEquals("D", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalEFlat3Major6th() {
        args.clear();

        args.add("Eb3");
        args.add("Major Sixth");

        new Interval(args).execute(env);

        assertEquals("C4", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalEUnison() {
        args.clear();

        args.add("E");
        args.add("Unison");

        new Interval(args).execute(env);

        assertEquals("E", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalE6Octave() {
        args.clear();

        args.add("E6");
        args.add("Octave");

        new Interval(args).execute(env);

        assertEquals("E7", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalC4DiminishedFifth() {
        args.clear();

        args.add("C4");
        args.add("Diminished 5th");

        new Interval(args).execute(env);

        assertEquals("Gb4", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalD3AugmentedFourth() {
        args.clear();

        args.add("D3");
        args.add("Augmented Fourth");

        new Interval(args).execute(env);

        assertEquals("G#3", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalDSharpAugmentedThird() {
        args.clear();

        args.add("D#");
        args.add("Augmented Third");

        new Interval(args).execute(env);

        assertEquals(errorDoesNotExist, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalDSharp3AugmentedFifteenth() {
        args.clear();

        args.add("D#3");
        args.add("Augmented fifteenth");

        new Interval(args).execute(env);

        assertEquals(errorDoesNotExist, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void intervalG7AugmentedFifteenth() {
        args.clear();

        args.add("G7");
        args.add("Augmented 15th");

        new Interval(args).execute(env);

        assertEquals(errorDoesNotExist, env.getOutput().replaceAll("\\r|\\n", ""));
    }
}