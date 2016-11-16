package seng302.command.play;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.command.play.PlayInterval;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * PlayInterval Test.
 *
 * @author plr37
 */
public class PlayIntervalTest {
    private Environment env = new Environment();
    private List<String> args;
    private String errorInvalidArgs = "[ERROR] " + Error.INVALIDINTERVALARGUMENTS.getError();
    private String errorOutOfMidiRange = "[ERROR] " + Error.INVALIDMIDINOTE.getError();
    private String errorInvalidNote = "[ERROR] " + Error.INVALIDNOTE.getError();
    private String errorInvalidInterval = "[ERROR] " + Error.INTERVALDOESNOTEXIST.getError();
    private String errorOutOfSemitoneRange = "[ERROR] " +
            Error.PLAYINTERVALINVALIDNUMSEMITONES.getError();

    // executes before each test
    @Before
    public void setUpBefore() {
        args = new ArrayList<>();
    }

    // PERFECT INTERVAL TESTS BEGIN

    // valid
    @Test
    public void testPerfectUnison() throws Exception {
        args.add("C");
        args.add("Perfect Unison");

        new PlayInterval(args).execute(env);
        assertEquals("C4 and C4", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBadInterval() throws Exception {
        args.add("C#");
        args.add("not a real interval");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidInterval, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBadArgs() throws Exception {
        args.add("Beeeeflat");
        args.add("not a real interval");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test11CSharp() throws Exception {
        args.add("C#");
        args.add("11");

        new PlayInterval(args).execute(env);
        assertEquals("C#4 and C5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test25C() throws Exception {
        args.add("C");
        args.add("25");

        new PlayInterval(args).execute(env);
        assertEquals(errorOutOfSemitoneRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testNeg1CSharp() throws Exception {
        args.add("E#");
        args.add("-1");

        new PlayInterval(args).execute(env);
        assertEquals(errorOutOfSemitoneRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testEmptyInterval() throws Exception {
        args.add("C#");
        args.add("");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidInterval, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testPerfectFourth() throws Exception {
        args.add("60");
        args.add("Perfect Fourth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testPerfectFifth() throws Exception {
        args.add("G");
        args.add("Perfect Fifth");

        new PlayInterval(args).execute(env);
        assertEquals("G4 and D5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testPerfectOctave() throws Exception {
        args.add("70");
        args.add("Perfect Octave");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testPerfectEleventh() throws Exception {
        args.add("A");
        args.add("Perfect Eleventh");

        new PlayInterval(args).execute(env);
        assertEquals("A4 and D6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testPerfectTwelfth() throws Exception {
        args.add("B");
        args.add("Perfect Twelfth");

        new PlayInterval(args).execute(env);
        assertEquals("B4 and F#6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testPerfectFifteenth() throws Exception {
        args.add("B");
        args.add("Perfect Fifteenth");

        new PlayInterval(args).execute(env);
        assertEquals("B4 and B6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // INVALID
    @Test
    public void testPerfectUnisonBadNote() throws Exception {
        args.add("X");
        args.add("Perfect Unison");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test$PerfectFifth() throws Exception {
        args.add("$");
        args.add("Perfect Fifth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testPerfectOctaveThreeArgs() throws Exception {
        args.add("18");
        args.add("06");
        args.add("Perfect Octave");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testPerfectEleventhOneArg() throws Exception {
        args.add("Perfect Eleventh");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testPerfectTwelfthSpace() throws Exception {
        args.add(" ");
        args.add("Perfect Twelfth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // PERFECT INTERVAL TESTS END

    // MAJOR INTERVAL TESTS BEGIN

    // valid
    @Test
    public void testMajorSecond() throws Exception {
        args.add("B");
        args.add("Major Second");

        new PlayInterval(args).execute(env);
        assertEquals("B4 and C#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testMajorSixth() throws Exception {
        args.add("G");
        args.add("Major Sixth");

        new PlayInterval(args).execute(env);
        assertEquals("G4 and E5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testMajorNinth() throws Exception {
        args.add("70");
        args.add("Major Ninth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testMajorTenth() throws Exception {
        args.add("B");
        args.add("Major 10th");

        new PlayInterval(args).execute(env);
        assertEquals("B4 and D#6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testMajorThirteenth() throws Exception {
        args.add("E");
        args.add("Major thirteenth");

        new PlayInterval(args).execute(env);
        assertEquals("E4 and C#6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // invalid
    @Test
    public void testMajorSecondBadNote() throws Exception {
        args.add("X");
        args.add("Major 2nd");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testMajorThirdMidiOutOfRange() throws Exception {
        args.add("1989");
        args.add("Major Third");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test$MajorSixth() throws Exception {
        args.add("$");
        args.add("Major Sixth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testMajorNinthThreeArgs() throws Exception {
        args.add("18");
        args.add("06");
        args.add("Major 9th");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testMajorTenthOneArg() throws Exception {
        args.add("Major tenth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testMajorThirteenthSpace() throws Exception {
        args.add(" ");
        args.add("Major Thirteenth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // MAJOR INTERVAL TESTS END

    // MINOR INTERVAL TESTS BEGIN

    // valid
    @Test
    public void testMinorSecond() throws Exception {
        args.add("B");
        args.add("Minor Second");

        new PlayInterval(args).execute(env);
        assertEquals("B4 and C5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testMinorSixth() throws Exception {
        args.add("G");
        args.add("Minor Sixth");

        new PlayInterval(args).execute(env);
        assertEquals("G4 and Eb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testMinorTenth() throws Exception {
        args.add("B");
        args.add("Minor 10th");

        new PlayInterval(args).execute(env);
        assertEquals("B4 and D6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testMinorThirteenth() throws Exception {
        args.add("B");
        args.add("Minor thirteenth");

        new PlayInterval(args).execute(env);
        assertEquals("B4 and G6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // INVALID
    @Test
    public void testMinorSecondBadNote() throws Exception {
        args.add("X");
        args.add("Minor 2nd");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test$MinorSixth() throws Exception {
        args.add("$");
        args.add("Minor Sixth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testMinorNinthThreeArgs() throws Exception {
        args.add("18");
        args.add("Minor 9th");
        args.add("65");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testMinorTenthOneArg() throws Exception {
        args.add("Minor tenth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testMinorThirteenthSpace() throws Exception {
        args.add(" ");
        args.add("Minor Thirteenth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // MINOR INTERVAL TESTS END

    // DIMINISHED INTERVAL TESTS BEGIN

    // valid
    @Test
    public void testDiminishedSecond() throws Exception {
        args.add("B");
        args.add("Diminished Second");

        new PlayInterval(args).execute(env);
        assertEquals("B4 and Cb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDiminishedSixth() throws Exception {
        args.add("G");
        args.add("Diminished Sixth");

        new PlayInterval(args).execute(env);
        assertEquals("G4 and Ebb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDiminishedTenth() throws Exception {
        args.add("B");
        args.add("Diminished 10th");

        new PlayInterval(args).execute(env);
        assertEquals("B4 and Db6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDiminishedThirteenth() throws Exception {
        args.add("B");
        args.add("Diminished thirteenth");

        new PlayInterval(args).execute(env);
        assertEquals("B4 and Gb6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // invalid
    @Test
    public void testDiminishedSecondBadNote() throws Exception {
        args.add("X");
        args.add("Diminished 2nd");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDiminishedThirdMidiOutOfRange() throws Exception {
        args.add("1939");
        args.add("Diminished Third");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test$DiminishedSixth() throws Exception {
        args.add("$");
        args.add("Diminished Sixth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDiminishedTenthOneArg() throws Exception {
        args.add("Diminished tenth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDiminishedThirteenthSpace() throws Exception {
        args.add(" ");
        args.add("Diminished Thirteenth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // DIMINISHED INTERVAL TESTS END
    
    // AUGMENTED INTERVAL TESTS BEGIN
    
    // valid
    @Test
    public void testAugmentedSecond() throws Exception {
        args.add("B");
        args.add("Augmented Second");

        new PlayInterval(args).execute(env);
        assertEquals("B4 and Cx5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAugmentedThird() throws Exception {
        args.add("65");
        args.add("Augmented Third");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAugmentedSixth() throws Exception {
        args.add("G");
        args.add("Augmented Sixth");

        new PlayInterval(args).execute(env);
        assertEquals("G4 and E#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAugmentedNinth() throws Exception {
        args.add("70");
        args.add("Augmented Ninth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAugmentedTenth() throws Exception {
        args.add("B");
        args.add("Augmented 10th");

        new PlayInterval(args).execute(env);
        assertEquals("B4 and Dx6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAugmentedThirteenth() throws Exception {
        args.add("B");
        args.add("Augmented thirteenth");

        new PlayInterval(args).execute(env);
        assertEquals("B4 and Gx6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // invalid
    @Test
    public void testAugmentedSecondBadNote() throws Exception {
        args.add("X");
        args.add("Augmented 2nd");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test$AugmentedSixth() throws Exception {
        args.add("$");
        args.add("Augmented Sixth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAugmentedNinthThreeArgs() throws Exception {
        args.add("18");
        args.add("65");
        args.add("Augmented 9th");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAugmentedTenthOneArg() throws Exception {
        args.add("Augmented tenth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAugmentedThirteenthSpace() throws Exception {
        args.add(" ");
        args.add("Augmented Thirteenth");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // AUGMENTED INTERVAL TESTS END

    // OCTAVE INTERVAL TESTS BEGIN

    // valid
    @Test
    public void testOctave() throws Exception {
        args.add("B");
        args.add("Octave");

        new PlayInterval(args).execute(env);
        assertEquals("B4 and B5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // invalid
    @Test
    public void testOctaveOneArg() throws Exception {
        args.add("Octave");

        new PlayInterval(args).execute(env);
        assertEquals(errorInvalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // OCTAVE INTERVAL TESTS END
}