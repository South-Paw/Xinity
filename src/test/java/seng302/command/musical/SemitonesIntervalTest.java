package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.command.musical.SemitonesInterval;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class SemitonesIntervalTest {
    private Environment env = new Environment();
    private List<String> args;
    private final String invalidArgs = "[ERROR] Must pass one argument, the type of interval desired to find the number of semitones for.";
    private final String invalidInterval = "[ERROR] Invalid Interval.";

    @Before
    public void setup() {
        args = new ArrayList<>();
    }


    @Test
    public void noArgumentsGiven() {
        args.clear();

        new SemitonesInterval(args).execute(env);

        assertEquals(
                invalidArgs,
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }


    @Test
    public void InvalidArgumentsGiven() {
        args.clear();
        args.add("mejor ferth");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                invalidInterval,
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void TooManyArgumentsGiven() {
        args.clear();
        args.add("mejor ferth");
        args.add("mejor ferth");
        args.add("mejor ferth");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                invalidArgs,
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void NullArgumentGiven() {
        args.clear();
        args.add("null");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                invalidInterval,
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void Unison() {
        args.clear();
        args.add("Unison");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                "Unison contains 0 semitones.",
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void MajorSecond() {
        args.clear();
        args.add("Major Second");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                "Major Second contains 2 semitones.",
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void Major2nd() {
        args.clear();
        args.add("Major 2nd");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                "Major 2nd contains 2 semitones.",
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void Major2ndLowercaseM() {
        args.clear();
        args.add("major 2nd");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                "major 2nd contains 2 semitones.",
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void Major3rd() {
        args.clear();
        args.add("Major 3rd");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                "Major 3rd contains 4 semitones.",
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void PerfectFourth() {
        args.clear();
        args.add("Perfect Fourth");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                "Perfect Fourth contains 5 semitones.",
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void Perfect5th() {
        args.clear();
        args.add("perfect 5th");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                "perfect 5th contains 7 semitones.",
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void MajorSixth() {
        args.clear();
        args.add("major sixth");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                "major sixth contains 9 semitones.",
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void Minor7th() {
        args.clear();
        args.add("minor seventh");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                "minor seventh contains 10 semitones.",
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void Major7th() {
        args.clear();
        args.add("major 7th");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                "major 7th contains 11 semitones.",
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void PerfectOctave() {
        args.clear();
        args.add("Perfect Octave");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                "Perfect Octave contains 12 semitones.",
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void Octave() {
        args.clear();
        args.add("Octave");

        new SemitonesInterval(args).execute(env);

        assertEquals(
                "Octave contains 12 semitones.",
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }
}