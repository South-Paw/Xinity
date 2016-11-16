package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.command.musical.EnharmonicInterval;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * Tests for the Enharmonic Interval command class.
 *
 * @author plr37
 */
public class EnharmonicIntervalTest {

    private Environment env = new Environment();
    private List<String> args;
    final private String noEquivalent = "Interval does not have an enharmonic equivalent.";
    final private String invalidNumberOfArgs = "[ERROR] "
            + Error.ENHARMONICINTERVALINVALIDNUMBEROFARGUMENTS.getError();
    final private String invalidInterval = "[ERROR] "
            + Error.ENHARMONICINTERVALINTERVALDOESNOTEXIST.getError();

    @Before
    public void setup() throws Exception {
        args = new ArrayList<>();
    }

    // Tests Invalid Args

    @Test
    public void NotAnInterval() throws Exception {
        args.add("Not An Interval");
        new EnharmonicInterval(args).execute(env);
        assertEquals(invalidInterval, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void TooManyArgs() throws Exception {
        args.add("Perfect Unison");
        args.add("Diminished Second");
        new EnharmonicInterval(args).execute(env);
        assertEquals(String.format(invalidNumberOfArgs, args.size()), env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // Tests Intervals WITH enharmonic equivalents
    @Test
    public void PerfectUnison() throws Exception {
        args.add("Perfect Unison");
        new EnharmonicInterval(args).execute(env);
        assertEquals("Diminished Second", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DiminishedSecond() throws Exception {
        args.add("Diminished Second");
        new EnharmonicInterval(args).execute(env);
        assertEquals("Perfect Unison", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void MajorSecond() throws Exception {
        args.add("Major Second");
        new EnharmonicInterval(args).execute(env);
        assertEquals("Diminished Third", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DiminishedThird() throws Exception {
        args.add("Diminished Third");
        new EnharmonicInterval(args).execute(env);
        assertEquals("Major Second", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DiminishedSixth() throws Exception {
        args.add("Diminished Sixth");
        new EnharmonicInterval(args).execute(env);
        assertEquals("Perfect Fifth", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void PerfectTwelfth() throws Exception {
        args.add("Perfect Twelfth");
        new EnharmonicInterval(args).execute(env);
        assertEquals("Diminished Thirteenth", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void MajorFourteenth() throws Exception {
        args.add("Major Fourteenth");
        new EnharmonicInterval(args).execute(env);
        assertEquals("Diminished Fifteenth", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // Tests Intervals WITHOUT enharmonic equivalents
    @Test
    public void MinorSecond() throws Exception {
        args.add("Minor Second");
        new EnharmonicInterval(args).execute(env);
        assertEquals(noEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void MinorThird() throws Exception {
        args.add("Minor Third");
        new EnharmonicInterval(args).execute(env);
        assertEquals(noEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void PerfectFourth() throws Exception {
        args.add("Perfect Fourth");
        new EnharmonicInterval(args).execute(env);
        assertEquals(noEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void PerfectEleventh() throws Exception {
        args.add("Perfect Eleventh");
        new EnharmonicInterval(args).execute(env);
        assertEquals(noEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void MinorFourteenth() throws Exception {
        args.add("Minor Fourteenth");
        new EnharmonicInterval(args).execute(env);
        assertEquals(noEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }
}