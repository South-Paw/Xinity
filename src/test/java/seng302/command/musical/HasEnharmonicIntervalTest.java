package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.command.musical.HasEnharmonicInterval;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for hasEnharmonicInterval command.
 */

public class HasEnharmonicIntervalTest {

    private Environment env = new Environment();
    private List<String> args;
    final private String hasEquivalent = "Interval has an enharmonic equivalent.";
    final private String noEquivalent = "Interval does not have an enharmonic equivalent.";
    final private String invalidNumberOfArgs = "[ERROR] "
            + Error.HASENHARMONICINTERVALINVALIDNUMBEROFARGUMENTS.getError();
    final private String invalidInterval = "[ERROR] "
            + Error.HASEHARMONICINTERVALINTERVALDOESNOTEXIST.getError();

    @Before
    public void setup() throws Exception {
        args = new ArrayList<>();
    }


    // Invalid Arguments
    @Test
    public void invalidInterval() throws Exception {
        args.clear();
        args.add("Not an interval");
        new HasEnharmonicInterval(args).execute(env);
        assertEquals(invalidInterval, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void tooManyArgs() throws Exception {
        args.clear();
        args.add("Unison");
        args.add("Major Second");
        new HasEnharmonicInterval(args).execute(env);
        assertEquals(String.format(invalidNumberOfArgs,args.size()), env.getOutput().replaceAll("\\r|\\n", ""));
    }


    // These interval names HAVE an enharmonic equivalent

    @Test
    public void perfectUnison() throws Exception {
        args.clear();
        args.add("Perfect Unison");
        new HasEnharmonicInterval(args).execute(env);
        assertEquals(hasEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void unison() throws Exception {
        args.clear();
        args.add("unison");
        new HasEnharmonicInterval(args).execute(env);
        assertEquals(hasEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void diminishedSecond() throws Exception {
        args.clear();
        args.add("Diminished Second");
        new HasEnharmonicInterval(args).execute(env);
        assertEquals(hasEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void majorThird() throws Exception {
        args.clear();
        args.add("Major Third");
        new HasEnharmonicInterval(args).execute(env);
        assertEquals(hasEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void diminishedTenth() throws Exception {
        args.clear();
        args.add("Diminished Tenth");
        new HasEnharmonicInterval(args).execute(env);
        assertEquals(hasEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void diminishedFifteenth() throws Exception {
        args.clear();
        args.add("diminished fifteenth");
        new HasEnharmonicInterval(args).execute(env);
        assertEquals(hasEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }



    // These interval names DO NOT HAVE an enharmonic equivalent

    @Test
    public void diminishedFifth() throws Exception {
        args.clear();
        args.add("Diminished Fifth");
        new HasEnharmonicInterval(args).execute(env);
        assertEquals(noEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void augmentedSecond() throws Exception {
        args.clear();
        args.add("Augmented Second");
        new HasEnharmonicInterval(args).execute(env);
        assertEquals(noEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void augmentedFourth() throws Exception {
        args.clear();
        args.add("Augmented Fourth");
        new HasEnharmonicInterval(args).execute(env);
        assertEquals(noEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void minorNinth() throws Exception {
        args.clear();
        args.add("Minor Ninth");
        new HasEnharmonicInterval(args).execute(env);
        assertEquals(noEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void minorTenth() throws Exception {
        args.clear();
        args.add("Minor Tenth");
        new HasEnharmonicInterval(args).execute(env);
        assertEquals(noEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void minorThirteenth() throws Exception {
        args.clear();
        args.add("Minor Thirteenth");
        new HasEnharmonicInterval(args).execute(env);
        assertEquals(noEquivalent, env.getOutput().replaceAll("\\r|\\n", ""));
    }

}
