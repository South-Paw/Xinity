package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.command.musical.FindInterval;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.Interval;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for find interval command.
 *
 * @author mvj14
 */
public class FindIntervalTest {
    private Environment env = new Environment();
    private List<String> args;
    private String errorMidiRange = "[ERROR] " + Error.INVALIDMIDINOTE.getError();

    @Before
    public void setUp() {
        args = new ArrayList<>();
    }

    @Test
    public void findIntervalCC() {
        args.clear();

        args.add("C");
        args.add("C");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.PERFECTUNISON),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalCD() {
        args.clear();

        args.add("C");
        args.add("D");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.MAJORSECOND),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalCB() {
        args.clear();

        args.add("C");
        args.add("B");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.MAJORSEVENTH),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalBC() {
        args.clear();

        args.add("B");
        args.add("C");

        new FindInterval(args).execute(env);

        assertEquals("[ERROR] Note 1 cannot be higher than Note 2.",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalCSharpD() {
        args.clear();

        args.add("C#");
        args.add("D");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.MINORSECOND),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalC4C5() {
        args.clear();

        args.add("C4");
        args.add("C5");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.PERFECTOCTAVE),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalB4C5() {
        args.clear();

        args.add("B4");
        args.add("C5");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.MINORSECOND),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }
    @Test
    public void findIntervalC5B4() {
        args.clear();

        args.add("C5");
        args.add("B4");

        new FindInterval(args).execute(env);

        assertEquals("[ERROR] Note 1 cannot be higher than Note 2.",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalC3C5() {
        args.clear();

        args.add("C3");
        args.add("C5");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.PERFECTFIFTEENTH),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalC3Gb3() {
        args.clear();

        args.add("C3");
        args.add("Gb3");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.DIMINISHEDFIFTH),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalC3FSharp3() {
        args.clear();

        args.add("C3");
        args.add("F#3");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.AUGMENTEDFOURTH),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalDGSharp() {
        args.clear();

        args.add("D");
        args.add("G#");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.AUGMENTEDFOURTH),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalDAb() {
        args.clear();

        args.add("D");
        args.add("Ab");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.DIMINISHEDFIFTH),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalDEb() {
        args.clear();

        args.add("D");
        args.add("Eb");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.MINORSECOND),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalDDSharp() {
        args.clear();

        args.add("D");
        args.add("D#");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.AUGMENTEDUNISON),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalDESharp() {
        args.clear();

        args.add("D");
        args.add("E#");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.AUGMENTEDSECOND),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalDF() {
        args.clear();

        args.add("D");
        args.add("F");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.MINORTHIRD),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }


    @Test
    public void findIntervalC10C11() {
        args.clear();

        args.add("C10");
        args.add("C11");

        new FindInterval(args).execute(env);

        assertEquals(errorMidiRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findIntervalC4D() {
        args.clear();

        args.add("C4");
        args.add("D");

        new FindInterval(args).execute(env);

        assertEquals(Interval.toString(Interval.MAJORSECOND),
                env.getOutput().replaceAll("\\r|\\n", ""));
    }
}
