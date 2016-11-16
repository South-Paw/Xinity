package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import seng302.command.musical.ScaleSignature;
import seng302.util.enumerator.Error;

/**
 * @author plr37
 */
public class ScaleSignatureTest {
    private Environment env = new Environment();
    private List<String> args;

    private final String wrongArguments = "[ERROR] " + Error.SCALESIGNATUREWRONGARGUMENTS.getError();
    private final String wrongNumberOfNotes = "[ERROR] " + Error.SCALESIGNATUREWRONGNUMBEROFNOTES.getError();
    private final String wrongType = "[ERROR] " + Error.SCALESIGNATUREWRONGTYPE.getError();
    private final String noScale = "[ERROR] " + Error.SCALESIGNATURENOSCALE.getError();
    private final String wrongFlatSharps = "[ERROR] " + Error.SCALESIGNATUREWRONGFLATSHARPS.getError();
    private final String mixedFlatSharps = "[ERROR] " + Error.SCALESIGNATUREMIXEDFLATSHARPS.getError();
    private final String notNotes = "[ERROR] " + Error.SCALESIGNATURENOTNOTES.getError();

    @Before
    public void setup() throws Exception {
        args = new ArrayList<>();
    }

    // valid key signatures - major

    @Test
    public void test2Sharps() {
        args.add("2#");

        new ScaleSignature(args).execute(env);
        assertEquals("D major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test4Sharps() {
        args.add("4#");

        new ScaleSignature(args).execute(env);
        assertEquals("E major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test6Sharps() {
        args.add("6#");

        new ScaleSignature(args).execute(env);
        assertEquals("F# major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test7Sharps() {
        args.add("7#");

        new ScaleSignature(args).execute(env);
        assertEquals("C# major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // valid key signatures - major, with scale type

    @Test
    public void test2SharpsMajor() {
        args.add("2#");
        args.add("Major");

        new ScaleSignature(args).execute(env);
        assertEquals("D major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test4SharpsMajor() {
        args.add("4#");
        args.add("Major");

        new ScaleSignature(args).execute(env);
        assertEquals("E major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test6SharpsMajor() {
        args.add("6#");
        args.add("Major");

        new ScaleSignature(args).execute(env);
        assertEquals("F# major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test7SharpsMajor() {
        args.add("7#");
        args.add("Major");

        new ScaleSignature(args).execute(env);
        assertEquals("C# major", env.getOutput().replaceAll("\\r|\\n", ""));
    }


    // valid key signature notes - major

    @Test
    public void testDMajorNotesOctave() {
        args.add("F#4");
        args.add("C#5");

        new ScaleSignature(args).execute(env);
        assertEquals("D major (octave ignored)", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDMajorNotes() {
        args.add("F#");
        args.add("C#");

        new ScaleSignature(args).execute(env);
        assertEquals("D major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBMajorNotes() {
        args.add("F#");
        args.add("C#");
        args.add("G#");
        args.add("D#");
        args.add("A#");

        new ScaleSignature(args).execute(env);
        assertEquals("B major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBMajorNotesOctave() {
        args.add("F#1");
        args.add("C#2");
        args.add("G#3");
        args.add("D#4");
        args.add("A#5");

        new ScaleSignature(args).execute(env);
        assertEquals("B major (octave ignored)", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCSharpMajorNotes() {
        args.add("F#");
        args.add("C#");
        args.add("G#");
        args.add("D#");
        args.add("A#");
        args.add("E#");
        args.add("B#");

        new ScaleSignature(args).execute(env);
        assertEquals("C# major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCSharpMajorNotesOctave() {
        args.add("F#4");
        args.add("C#4");
        args.add("G#4");
        args.add("D#4");
        args.add("A#4");
        args.add("E#4");
        args.add("B#4");

        new ScaleSignature(args).execute(env);
        assertEquals("C# major (octave ignored)", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // valid key signature notes - major with scale type

    @Test
    public void testDMajorNotesMajor() {
        args.add("F#");
        args.add("C#");
        args.add("Major");

        new ScaleSignature(args).execute(env);
        assertEquals("D major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBMajorNotesMajor() {
        args.add("F#");
        args.add("C#");
        args.add("G#");
        args.add("D#");
        args.add("A#");
        args.add("Major");

        new ScaleSignature(args).execute(env);
        assertEquals("B major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCSharpMajorNotesMajor() {
        args.add("F#");
        args.add("C#");
        args.add("G#");
        args.add("D#");
        args.add("A#");
        args.add("E#");
        args.add("B#");
        args.add("Major");

        new ScaleSignature(args).execute(env);
        assertEquals("C# major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // invalid

    @Test
    public void testInvalidKeySig1() {
        args.add("2");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidKeySig2() {
        args.add("2B");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidKeySig4() {
        args.add("b");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidKeySig5() {
        args.add("3B");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidKeySig6() {
        args.add("8#");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongFlatSharps, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidKeySig7() {
        args.add("88#");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongFlatSharps, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidKeySig8() {
        args.add("invalid");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidKeySig9() {
        args.add("C5");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidKeySig10() {
        args.add("5C");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidKeySig11() {
        args.add("2#");
        args.add("C#");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongType, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidKeySig12() {
        args.add("C#");
        args.add("3b");

        new ScaleSignature(args).execute(env);
        assertEquals(noScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidKeySig13() {
        args.add("2#");
        args.add("3b");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongType, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidScale1() {
        args.add("2#");
        args.add("not a scale");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongType, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidScale2() {
        args.add("2#");
        args.add("invalid");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongType, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidScale3() {
        args.add("2#");
        args.add("Minor");
        args.add("invalid");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongType, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidScale4() {
        args.add("2#");
        args.add("Minor");
        args.add("invalid");
        args.add("Major");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongType, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidScale5() {
        args.add("F#");
        args.add("C#");
        args.add("not a scale");

        new ScaleSignature(args).execute(env);
        assertEquals(noScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidScale6() {
        args.add("G#");
        args.add("C#");
        args.add("not a scale");

        new ScaleSignature(args).execute(env);
        assertEquals(noScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testTooManyNotes() {
        args.add("F#");
        args.add("C#");
        args.add("G#");
        args.add("D#");
        args.add("A#");
        args.add("E#");
        args.add("B#");
        args.add("C#");

        new ScaleSignature(args).execute(env);
        assertEquals(wrongNumberOfNotes, env.getOutput().replaceAll("\\r|\\n", ""));
    }
}