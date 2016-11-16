package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.command.musical.Signature;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SignatureTest {

    private Environment env = new Environment();
    private List<String> args;

    final private String errorValidScaleType = "[ERROR] " + Error.INVALIDSCALETYPE.getError();
    final private String errorValidScaleNote = "[ERROR] " + Error.INVALIDNOTE.getError();
    final private String errorNoSignature = "[ERROR] " + Error.INVALIDSCALENOSIGNATURE.getError();
    final private String errorValidArguments = "[ERROR] "
        + Error.INVALIDSHOWSIGNATUREARGUMENTS.getError();

    @Before
    public void setup() throws Exception {
        args = new ArrayList<>();
    }

    //valid - without flag

    @Test
    public void testCMajor() throws Exception {
        args.clear();
        args.add("C");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("0#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCMajorOctave() throws Exception {
        args.clear();
        args.add("C4");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("0# (octave ignored)", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCMajorOctave9() throws Exception {
        args.clear();
        args.add("C9");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("0# (octave ignored)", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testGMajor() throws Exception {
        args.clear();
        args.add("G");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("1#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testG9Major() throws Exception {
        args.clear();
        args.add("G9");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("1# (octave ignored)", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDMajor() throws Exception {
        args.clear();
        args.add("D");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("2#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAMajor() throws Exception {
        args.clear();
        args.add("A");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("3#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testEMajor() throws Exception {
        args.clear();
        args.add("E");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("4#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBMajor() throws Exception {
        args.clear();
        args.add("B");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("5#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFSharpMajor() throws Exception {
        args.clear();
        args.add("F#");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("6#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCSharpMajor() throws Exception {
        args.clear();
        args.add("C#");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("7#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFMajor() throws Exception {
        args.clear();
        args.add("F");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("1b", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBbMajor() throws Exception {
        args.clear();
        args.add("Bb");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("2b", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testEbMajor() throws Exception {
        args.clear();
        args.add("Eb");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("3b", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAbMajor() throws Exception {
        args.clear();
        args.add("Ab");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("4b", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDbMajor() throws Exception {
        args.clear();
        args.add("Db");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("5b", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testGbMajor() throws Exception {
        args.clear();
        args.add("Gb");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("6b", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCbMajor() throws Exception {
        args.clear();
        args.add("Cb");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals("7b", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // valid - with flag

    @Test
    public void testCMajorNotes() throws Exception {
        args.clear();
        args.add("C");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("This scale has no key signature notes.",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testGMajorNotes() throws Exception {
        args.clear();
        args.add("G");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("F#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDMajorNotes() throws Exception {
        args.clear();
        args.add("D");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("F#, C#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAMajorNotes() throws Exception {
        args.clear();
        args.add("A");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("F#, C#, G#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testA9MajorNotes() throws Exception {
        args.clear();
        args.add("A9");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("F#, C#, G# (octave ignored)", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testEMajorNotes() throws Exception {
        args.clear();
        args.add("E");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("F#, C#, G#, D#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBMajorNotes() throws Exception {
        args.clear();
        args.add("B");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("F#, C#, G#, D#, A#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFSharpMajorNotes() throws Exception {
        args.clear();
        args.add("F#");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("F#, C#, G#, D#, A#, E#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCSharpMajorNotes() throws Exception {
        args.clear();
        args.add("C#");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("F#, C#, G#, D#, A#, E#, B#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFMajorNotes() throws Exception {
        args.clear();
        args.add("F");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("Bb", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBbMajorNotes() throws Exception {
        args.clear();
        args.add("Bb");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("Bb, Eb", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testEbMajorNotes() throws Exception {
        args.clear();
        args.add("Eb");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("Bb, Eb, Ab", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAbMajorNotes() throws Exception {
        args.clear();
        args.add("Ab");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("Bb, Eb, Ab, Db", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDbMajorNotes() throws Exception {
        args.clear();
        args.add("Db");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("Bb, Eb, Ab, Db, Gb", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testGbMajorNotes() throws Exception {
        args.clear();
        args.add("Gb");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("Bb, Eb, Ab, Db, Gb, Cb", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testGb9MajorNotes() throws Exception {
        args.clear();
        args.add("Gb9");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("Bb, Eb, Ab, Db, Gb, Cb (octave ignored)", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCbMajorNotes() throws Exception {
        args.clear();
        args.add("Cb");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("Bb, Eb, Ab, Db, Gb, Cb, Fb", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // invalid - without flag

    @Test
    public void testDSharpMajor() throws Exception {
        args.clear();
        args.add("D#");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals(errorNoSignature, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testESharpMajor() throws Exception {
        args.clear();
        args.add("E#");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals(errorNoSignature, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testGSharpMajor() throws Exception {
        args.clear();
        args.add("G#");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals(errorNoSignature, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testASharpMajor() throws Exception {
        args.clear();
        args.add("A#");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals(errorNoSignature, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBSharpMajor() throws Exception {
        args.clear();
        args.add("B#");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals(errorNoSignature, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFbMajor() throws Exception {
        args.clear();
        args.add("Fb");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals(errorNoSignature, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidBNotAScaleType() throws Exception {
        args.clear();
        args.add("B");
        args.add("not a scale type");
        new Signature(args).execute(env);
        assertEquals(errorValidScaleType, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidNotANoteMajor() throws Exception {
        args.clear();
        args.add("not a note");
        args.add("Major");
        new Signature(args).execute(env);
        assertEquals(errorValidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // invalid - with flag

    @Test
    public void testGSharpMajorNotes() throws Exception {
        args.clear();
        args.add("G#");
        args.add("Major");
        args.add("Notes");
        new Signature(args).execute(env);
        assertEquals("This scale has no key signature notes.",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidANotAScaleTypeWithFlag() throws Exception {
        args.clear();
        args.add("A");
        args.add("not a scale type");
        args.add("none");
        new Signature(args).execute(env);
        assertEquals(errorValidScaleType, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidNotANoteMajorWithFlag() throws Exception {
        args.clear();
        args.add("not a note");
        args.add("Major");
        args.add("none");
        new Signature(args).execute(env);
        assertEquals(errorValidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidSignatureFlag() throws Exception {
        args.clear();
        args.add("B");
        args.add("Major");
        args.add("not notes flag");
        new Signature(args).execute(env);
        assertEquals(errorValidArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // checkKeySignatureInput METHOD TESTS BEGIN //



}