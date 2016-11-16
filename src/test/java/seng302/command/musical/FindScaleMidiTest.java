package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.util.NoteUtil;
import seng302.util.enumerator.Error;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Find Scale Midi tests
 *
 * With the help of the scale logic and manual testing a number of scales that are marked "invalid"
 * have been found. The "invalid" scales are marked this as they cannot exist with just double flats/sharps.
 * The following scales that don't exist are:
 *
 * Major; D##, E##, G##, A##, B##, Fbb
 * Minor; E##, B##, Fbb
 *
 * @author wrs35, ljm163, plr37
 */

public class FindScaleMidiTest {
    private Environment env = new Environment();
    private List<String> args;

    private String errorInvalidScaleNote = "[ERROR] " + Error.INVALIDNOTE.getError();
    private String errorInvalidArguments = "[ERROR] " + Error.INVALIDSCALEARGUMENTS.getError();
    private String errorNoteOutOfRange =   "[ERROR] " + Error.INVALIDMIDINOTE.getError();
    private String errorNoScale =          "[ERROR] " + Error.FINDSCALENOSCALE.getError();

    private String findScaleMidiTestHelper(String notes) throws Exception {
        String midiNotes = "";
        notes = notes.replaceAll("\\s+", "");
        Integer previousMidi = 0;
        for (String s : notes.split(",")) {
            XinityNote note = new XinityNote(s);
            Integer midi = note.getMidi();
            if (previousMidi > midi) {
                note = NoteUtil.incrementedOctaveNote(note, 1);
            }
            midi = note.getMidi();
            midiNotes += midi + ", ";
            previousMidi = midi;
        }
        return midiNotes.substring(0, midiNotes.length() - 2);
    }

    @Before
    public void setup() {
        args = new ArrayList<>();
    }

    //invalid arguments
    @Test
    public void noArgumentsGiven() {
        args.clear();
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testNull() throws Exception {
        args.clear();
        args.add(null);
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // MAJOR SCALE TESTS BEGIN

    //valid
    @Test
    public void testCMajor() throws Exception {
        args.clear();
        args.add("C");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("60, 62, 64, 65, 67, 69, 71, 72", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testC9Major() throws Exception {
        args.clear();
        args.add("C9");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("120, 122, 124, 125, 127 - The rest of the scale cannot be mapped to midi",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCbMajor() throws Exception {
        args.clear();
        args.add("Cb");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("59, 61, 63, 64, 66, 68, 70, 71", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDMajor() throws Exception {
        args.clear();
        args.add("D");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("62, 64, 66, 67, 69, 71, 73, 74", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testB8Major() throws Exception {
        args.clear();
        args.add("B8");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("119, 121, 123, 124, 126 - The rest of the scale cannot be mapped to midi",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testG9Major() throws Exception {
        args.clear();
        args.add("G9");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("127 - The rest of the scale cannot be mapped to midi",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testA9Major() throws Exception {
        args.clear();
        args.add("A9");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorNoteOutOfRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testC4Major() throws Exception {
        args.clear();
        args.add("C4");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("60, 62, 64, 65, 67, 69, 71, 72", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCb4Major() throws Exception {
        args.clear();
        args.add("Cb4");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("59, 61, 63, 64, 66, 68, 70, 71", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFbMajor() throws Exception {
        args.clear();
        args.add("Fb");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("64, 66, 68, 69, 71, 73, 75, 76", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testESharpMajor() throws Exception {
        args.clear();
        args.add("e#");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("65, 67, 69, 70, 72, 74, 76, 77", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testD4Major() throws Exception {
        args.clear();
        args.add("D4");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("62, 64, 66, 67, 69, 71, 73, 74", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testF4Major() throws Exception {
        args.clear();
        args.add("F4");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("65, 67, 69, 70, 72, 74, 76, 77", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCNeg1Major() throws Exception {
        args.clear();
        args.add("C-1");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("0, 2, 4, 5, 7, 9, 11, 12", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testC0Major() throws Exception {
        args.clear();
        args.add("C0");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("12, 14, 16, 17, 19, 21, 23, 24", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFSharpMajor() throws Exception {
        args.clear();
        args.add("F#");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("66, 68, 70, 71, 73, 75, 77, 78", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFSharp3Major() throws Exception {
        args.clear();
        args.add("F#3");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("54, 56, 58, 59, 61, 63, 65, 66", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBDoubleFlatMajor() throws Exception {
        args.clear();
        args.add("Bbb");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("69, 71, 73, 74, 76, 78, 80, 81", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBDoubleSharpMajor() throws Exception {
        args.clear();
        args.add("B##");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //pentatonic scales
    @Test
    public void testCPentaMajor() throws Exception {
        args.clear();
        args.add("C");
        args.add("Penta Major");
        new FindScaleMidi(args).execute(env);
        assertEquals("60, 62, 64, 67, 69, 72", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBSharp6PentaMajor() throws Exception {
        args.clear();
        args.add("B#6");
        args.add("Penta Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }


    @Test
    public void CbMajorPenta() throws Exception {
        args.clear();
        args.add("Cb4");
        args.add("pentatonic major");
        new FindScaleMidi(args).execute(env);
        assertEquals("59, 61, 63, 66, 68, 71", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCPentaMinor() throws Exception {
        args.clear();
        args.add("C4");
        args.add("Penta Minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("60, 63, 65, 67, 70, 72", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFSharpPentaMinor() throws Exception {
        args.clear();
        args.add("F#");
        args.add("Penta Minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("66, 69, 71, 73, 76, 78", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //invalid - scale note

    @Test
    public void testInvalidALowerFlatMajor() throws Exception {
        args.clear();
        args.add("aB");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAAMajor() throws Exception {
        args.clear();
        args.add("AA");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test3Major() throws Exception {
        args.clear();
        args.add("3");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test$Major() throws Exception {
        args.clear();
        args.add("$");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testHMajor() throws Exception {
        args.clear();
        args.add("H");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testUpperXMajor() throws Exception {
        args.clear();
        args.add("X");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testLowerXMajor() throws Exception {
        args.clear();
        args.add("x");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testA$Major() throws Exception {
        args.clear();
        args.add("A$");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAHMajor() throws Exception {
        args.clear();
        args.add("AH");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testOctaveSpaceScaleMajor() throws Exception {
        args.clear();
        args.add(" ");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //invalid - scale doesn't exist

    @Test
    public void testInvalidBxMajor() throws Exception {
        args.clear();
        args.add("Bx");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //invalid - out of range

    @Test
    public void testOctaveOutOfBoundsUpperScaleMajor() throws Exception {
        args.clear();
        args.add("A10");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorNoteOutOfRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testOctaveOutOfBoundsLowerScaleMajor() throws Exception {
        args.clear();
        args.add("A-2");
        args.add("Major");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorNoteOutOfRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // MAJOR SCALE TESTS ENDS

    // MINOR SCALE TESTS BEGIN

    //valid
    @Test
    public void testCMinor() throws Exception {
        args.clear();
        args.add("C");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("60, 62, 63, 65, 67, 68, 70, 72", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testC9Minor() throws Exception {
        args.clear();
        args.add("C9");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("120, 122, 123, 125, 127 - The rest of the scale cannot be mapped to midi",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCbMinor() throws Exception {
        args.clear();
        args.add("Cb");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("59, 61, 62, 64, 66, 67, 69, 71", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDMinor() throws Exception {
        args.clear();
        args.add("D");
        args.add("Minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("62, 64, 65, 67, 69, 70, 72, 74", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFNaturalMinor() throws Exception {
        args.clear();
        args.add("F");
        args.add("natural minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("65, 67, 68, 70, 72, 73, 75, 77", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testB8Minor() throws Exception {
        args.clear();
        args.add("B8");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("119, 121, 122, 124, 126, 127 - The rest of the scale cannot be mapped to midi",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testG9Minor() throws Exception {
        args.clear();
        args.add("G9");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("127 - The rest of the scale cannot be mapped to midi",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testC4Minor() throws Exception {
        args.clear();
        args.add("C4");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("60, 62, 63, 65, 67, 68, 70, 72", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCb4Minor() throws Exception {
        args.clear();
        args.add("Cb4");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("59, 61, 62, 64, 66, 67, 69, 71", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFbMinor() throws Exception {
        args.clear();
        args.add("Fb");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("64, 66, 67, 69, 71, 72, 74, 76", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testESharpMinor() throws Exception {
        args.clear();
        args.add("e#");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("65, 67, 68, 70, 72, 73, 75, 77", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testD4Minor() throws Exception {
        args.clear();
        args.add("D4");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("62, 64, 65, 67, 69, 70, 72, 74", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testF4Minor() throws Exception {
        args.clear();
        args.add("F4");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("65, 67, 68, 70, 72, 73, 75, 77", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCNeg1Minor() throws Exception {
        args.clear();
        args.add("C-1");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("0, 2, 3, 5, 7, 8, 10, 12", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testC0Minor() throws Exception {
        args.clear();
        args.add("C0");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("12, 14, 15, 17, 19, 20, 22, 24", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFSharpMinor() throws Exception {
        args.clear();
        args.add("F#");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("66, 68, 69, 71, 73, 74, 76, 78", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFSharp3Minor() throws Exception {
        args.clear();
        args.add("F#3");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("54, 56, 57, 59, 61, 62, 64, 66", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBDoubleFlatMinor() throws Exception {
        args.clear();
        args.add("Bbb");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals("69, 71, 72, 74, 76, 77, 79, 81", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBDoubleSharpMinor() throws Exception {
        args.clear();
        args.add("B##");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // Scale Modes

    @Test
    public void testCIonian() throws Exception {
        args.clear();
        args.add("C");
        args.add("Ionian");
        new FindScaleMidi(args).execute(env);
        assertEquals("60, 62, 64, 65, 67, 69, 71, 72", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCLocrian() throws Exception {
        args.clear();
        args.add("C");
        args.add("Locrian");
        new FindScaleMidi(args).execute(env);
        assertEquals("60, 61, 63, 65, 66, 68, 70, 72", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testC4Locrian() throws Exception {
        args.clear();
        args.add("C4");
        args.add("Locrian");
        new FindScaleMidi(args).execute(env);
        assertEquals("60, 61, 63, 65, 66, 68, 70, 72", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //invalid - scale note

    @Test
    public void testInvalidALowerFlatMinor() throws Exception {
        args.clear();
        args.add("aB");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAAMinor() throws Exception {
        args.clear();
        args.add("AA");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test3Minor() throws Exception {
        args.clear();
        args.add("3");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void test$Minor() throws Exception {
        args.clear();
        args.add("$");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testHMinor() throws Exception {
        args.clear();
        args.add("H");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testUpperXMinor() throws Exception {
        args.clear();
        args.add("X");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testLowerXMinor() throws Exception {
        args.clear();
        args.add("x");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testA$Minor() throws Exception {
        args.clear();
        args.add("A$");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAHMinor() throws Exception {
        args.clear();
        args.add("AH");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testOctaveSpace() throws Exception {
        args.clear();
        args.add(" ");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorInvalidScaleNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //invalid - scale doesn't exist

    @Test
    public void testInvalidBxMinor() throws Exception {
        args.clear();
        args.add("Bx");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //invalid - out of range

    @Test
    public void testOctaveOutOfBoundsUpper() throws Exception {
        args.clear();
        args.add("A10");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorNoteOutOfRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testOctaveOutOfBoundsLower() throws Exception {
        args.clear();
        args.add("A-2");
        args.add("minor");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorNoteOutOfRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // MINOR SCALE TESTS ENDS

    // MELODIC MINOR SCALE TESTS BEGIN

    @Test
    public void testCMelodicMinor() throws Exception {
        args.add("C");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("C, D, Eb, F, G, A, B, C");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testCSharpMelodicMinor() throws Exception {
        args.add("C#");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("C#, D#, E, F#, G#, A#, B#, C#");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testDbMelodicMinor() throws Exception {
        args.add("Db");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("Db, Eb, Fb, Gb, Ab, Bb, C, Db");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testDMelodicMinor() throws Exception {
        args.add("D");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("D, E, F, G, A, B, C#, D");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testDSharpMelodicMinor() throws Exception {
        args.add("D#");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("D#, E#, F#, G#, A#, B#, Cx, D#");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testEbMelodicMinor() throws Exception {
        args.add("Eb");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("Eb, F, Gb, Ab, Bb, C, D, Eb");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testEMelodicMinor() throws Exception {
        args.add("E");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("E, F#, G, A, B, C#, D#, E");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testESharpMelodicMinor() throws Exception {
        args.add("E#");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("E#, Fx, G#, A#, B#, Cx, Dx, E#");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testFbMelodicMinor() throws Exception {
        args.add("Fb");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("Fb, Gb, Abb, Bbb, Cb, Db, Eb, Fb");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testFMelodicMinor() throws Exception {
        args.add("F");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("F, G, Ab, Bb, C, D, E, F");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testFSharpMelodicMinor() throws Exception {
        args.add("F#");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("F#, G#, A, B, C#, D#, E#, F#");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testGbMelodicMinor() throws Exception {
        args.add("Gb");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("Gb, Ab, Bbb, Cb, Db, Eb, F, Gb");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testGMelodicMinor() throws Exception {
        args.add("G");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("G, A, Bb, C, D, E, F#, G");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testGSharpMelodicMinor() throws Exception {
        args.add("G#");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("G#, A#, B, C#, D#, E#, Fx, G#");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testAbMelodicMinor() throws Exception {
        args.add("Ab");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("Ab, Bb, Cb, Db, Eb, F, G, Ab");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testAMelodicMinor() throws Exception {
        args.add("A");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("A, B, C, D, E, F#, G#, A");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testASharpMelodicMinor() throws Exception {
        args.add("A#");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("A#, B#, C#, D#, E#, Fx, Gx, A#");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testBbMelodicMinor() throws Exception {
        args.add("Bb");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("Bb, C, Db, Eb, F, G, A, Bb");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testBMelodicMinor() throws Exception {
        args.add("B");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("B, C#, D, E, F#, G#, A#, B");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testBSharpMelodicMinor() throws Exception {
        args.add("B#");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("B#, Cx, D#, E#, Fx, Gx, Ax, B#");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testCbMelodicMinor() throws Exception {
        args.add("Cb");
        args.add("Melodic Minor");
        String expected = findScaleMidiTestHelper("Cb, Db, Ebb, Fb, Gb, Ab, Bb, Cb");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void DDorianB2() throws Exception {
        args.clear();
        args.add("D");
        args.add("Dorian b2");
        new FindScaleMidi(args).execute(env);
        assertEquals("62, 63, 65, 67, 69, 71, 72, 74", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void A4Mixolydianb6() throws Exception {
        args.clear();
        args.add("A4");
        args.add("Mixolydian b6");
        new FindScaleMidi(args).execute(env);
        assertEquals("69, 71, 73, 74, 76, 77, 79, 81", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void D4DorianB2() throws Exception {
        args.clear();
        args.add("D4");
        args.add("Dorian b2");
        new FindScaleMidi(args).execute(env);
        assertEquals("62, 63, 65, 67, 69, 71, 72, 74", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void D9DorianB2() throws Exception {
        args.clear();
        args.add("D9");
        args.add("Dorian b2");
        new FindScaleMidi(args).execute(env);
        assertEquals("122, 123, 125, 127 - The rest of the scale cannot be mapped to midi", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void FLydianDominant() throws Exception {
        args.clear();
        args.add("F");
        args.add("Lydian Dominant");
        new FindScaleMidi(args).execute(env);
        assertEquals("65, 67, 69, 71, 72, 74, 75, 77", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void C4Altered() throws Exception {
        args.clear();
        args.add("C4");
        args.add("Altered");
        new FindScaleMidi(args).execute(env);
        assertEquals("60, 61, 63, 64, 66, 68, 70, 72", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void BAltered() throws Exception {
        args.clear();
        args.add("B");
        args.add("Altered");
        new FindScaleMidi(args).execute(env);
        assertEquals("71, 72, 74, 75, 77, 79, 81, 83", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void BbAltered() throws Exception {
        args.clear();
        args.add("Bb");
        args.add("Altered");
        new FindScaleMidi(args).execute(env);
        assertEquals("70, 71, 73, 74, 76, 78, 80, 82", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void AbbAltered() throws Exception {
        args.clear();
        args.add("Abb");
        args.add("Altered");
        new FindScaleMidi(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void AbAltered() throws Exception {
        args.clear();
        args.add("Ab");
        args.add("Altered");
        new FindScaleMidi(args).execute(env);
        assertEquals("68, 69, 71, 72, 74, 76, 78, 80", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void EbbMixolydianb6() throws Exception {
        args.clear();
        args.add("Ebb");
        args.add("Mixolydian b6");
        new FindScaleMidi(args).execute(env);
        assertEquals("62, 64, 66, 67, 69, 70, 72, 74", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCHarmonicMinor() throws Exception {
        args.add("C");
        args.add("Harmonic Minor");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals("60, 62, 63, 65, 67, 68, 71, 72", result);
    }

    @Test
    public void testC9HarmonicMinor() throws Exception {
        args.add("C9");
        args.add("Harmonic Minor");

        new FindScaleMidi(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals("120, 122, 123, 125, 127 - The rest of the scale cannot be mapped to midi", result);
    }
}
