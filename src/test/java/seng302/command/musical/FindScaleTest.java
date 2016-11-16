package seng302.command.musical;

import cucumber.api.java.gl.E;
import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.command.musical.FindScale;
import seng302.util.NoteUtil;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static seng302.util.enumerator.Error.*;

/**
 * Find Scale tests
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

public class FindScaleTest {
    private Environment env = new Environment();
    private List<String> args;
    final private String errorValidNote = "[ERROR] " + INVALIDNOTE.getError();
    final private String errorValidArguments = "[ERROR] " + INVALIDSCALEARGUMENTS.getError();
    final private String errorNoScale = "[ERROR] " + FINDSCALENOSCALE.getError();
    final private String errorMidiRange = "[ERROR] " + INVALIDMIDINOTE.getError();
    
    @Before
    public void setup() throws Exception {
        args = new ArrayList<>();
    }

    //invalid args

    @Test
    public void testInvalidNull() throws Exception {
        args.clear();
        args.add(null);
        new FindScale(args).execute(env);
        assertEquals(errorValidArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //Major Scale Tests

    //valid
    @Test
    public void testCMajor() throws Exception {
        args.clear();
        args.add("C4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("C4, D4, E4, F4, G4, A4, B4, C5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDMajor() throws Exception {
        args.clear();
        args.add("d4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("D4, E4, F#4, G4, A4, B4, C#5, D5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testEMajor() throws Exception {
        args.clear();
        args.add("E4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("E4, F#4, G#4, A4, B4, C#5, D#5, E5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFMajor() throws Exception {
        args.clear();
        args.add("F4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("F4, G4, A4, Bb4, C5, D5, E5, F5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testGMajor() throws Exception {
        args.clear();
        args.add("G4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("G4, A4, B4, C5, D5, E5, F#5, G5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAMajor() throws Exception {
        args.clear();
        args.add("A4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("A4, B4, C#5, D5, E5, F#5, G#5, A5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBMajor() throws Exception {
        args.clear();
        args.add("B4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("B4, C#5, D#5, E5, F#5, G#5, A#5, B5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAbbMajor() throws Exception {
        args.clear();
        args.add("Abb4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("Abb4, Bbb4, Cb5, Dbb5, Ebb5, Fb5, Gb5, Abb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBbbMajor() throws Exception {
        args.clear();
        args.add("Bbb4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("Bbb4, Cb5, Db5, Ebb5, Fb5, Gb5, Ab5, Bbb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCDoubleSharpMajor() throws Exception {
        args.clear();
        args.add("C##4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("Cx4, Dx4, Ex4, Fx4, Gx4, Ax4, Bx4, Cx5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCbbMajor() throws Exception {
        args.clear();
        args.add("Cbb4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("Cbb4, Dbb4, Ebb4, Fbb4, Gbb4, Abb4, Bbb4, Cbb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDbbMajor() throws Exception {
        args.clear();
        args.add("Dbb4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("Dbb4, Ebb4, Fb4, Gbb4, Abb4, Bbb4, Cb5, Dbb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testEbbMajor() throws Exception {
        args.clear();
        args.add("Ebb4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("Ebb4, Fb4, Gb4, Abb4, Bbb4, Cb5, Db5, Ebb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFDoubleSharpMajor() throws Exception {
        args.clear();
        args.add("F##4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("Fx4, Gx4, Ax4, B#4, Cx5, Dx5, Ex5, Fx5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testGbbMajor() throws Exception {
        args.clear();
        args.add("Gbb4");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("Gbb4, Abb4, Bbb4, Cbb5, Dbb5, Ebb5, Fb5, Gbb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //invalid - scale doesn't exist

    @Test
    public void testInvalidADoubleSharpMajor() throws Exception {
        args.clear();
        args.add("A##");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidBDoubleSharpMajor() throws Exception {
        args.clear();
        args.add("B##");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidDDoubleSharpMajor() throws Exception {
        args.clear();
        args.add("D##");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidEDoubleSharpMajor() throws Exception {
        args.clear();
        args.add("E##");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidFbbMajor() throws Exception {
        args.clear();
        args.add("Fbb");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidGDoubleSharpMajor() throws Exception {
        args.clear();
        args.add("G##");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //Minor Scale Tests

    //valid

    @Test
    public void testADoubleSharpMinor() throws Exception {
        args.clear();
        args.add("A##4");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals("Ax4, Bx4, Cx5, Dx5, Ex5, Fx5, Gx5, Ax5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDDoubleSharpMinor() throws Exception {
        args.clear();
        args.add("D##4");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals("Dx4, Ex4, Fx4, Gx4, Ax4, B#4, Cx5, Dx5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testEbbMinor() throws Exception {
        args.clear();
        args.add("Ebb4");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals("Ebb4, Fb4, Gbb4, Abb4, Bbb4, Cbb5, Dbb5, Ebb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFDoubleSharpMinor() throws Exception {
        args.clear();
        args.add("F##4");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals("Fx4, Gx4, A#4, B#4, Cx5, D#5, E#5, Fx5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCNaturalMinor() throws Exception {
        args.clear();
        args.add("C4");
        args.add("Natural Minor");
        new FindScale(args).execute(env);
        assertEquals("C4, D4, Eb4, F4, G4, Ab4, Bb4, C5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testASharp5Minor() throws Exception {
        args.clear();
        args.add("A#5");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals("A#5, B#5, C#6, D#6, E#6, F#6, G#6, A#6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testGSharp5NaturalMinor() throws Exception {
        args.clear();
        args.add("G#5");
        args.add("Natural Minor");
        new FindScale(args).execute(env);
        assertEquals("G#5, A#5, B5, C#6, D#6, E6, F#6, G#6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCbMinor() throws Exception {
        args.clear();
        args.add("Cb4");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals("Cb4, Db4, Ebb4, Fb4, Gb4, Abb4, Bbb4, Cb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testC4NaturalMinor() throws Exception {
        args.clear();
        args.add("C4");
        args.add("natural minor");
        new FindScale(args).execute(env);
        assertEquals("C4, D4, Eb4, F4, G4, Ab4, Bb4, C5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCFlat4NaturalMinor() throws Exception {
        args.clear();
        args.add("Cb4");
        args.add("natural minor");
        new FindScale(args).execute(env);
        assertEquals("Cb4, Db4, Ebb4, Fb4, Gb4, Abb4, Bbb4, Cb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBSharp4NaturalMinor() throws Exception {
        args.clear();
        args.add("B#4");
        args.add("natural minor");
        new FindScale(args).execute(env);
        assertEquals("B#4, Cx5, D#5, E#5, Fx5, G#5, A#5, B#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCNeg1Minor() throws Exception {
        args.clear();
        args.add("C-1");
        args.add("minor");
        new FindScale(args).execute(env);
        assertEquals("C-1, D-1, Eb-1, F-1, G-1, Ab-1, Bb-1, C0", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCDoubleSharpMinor() throws Exception {
        args.clear();
        args.add("C##4");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals("Cx4, Dx4, E#4, Fx4, Gx4, A#4, B#4, Cx5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCxMinor() throws Exception {
        args.clear();
        args.add("Cx4");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals("Cx4, Dx4, E#4, Fx4, Gx4, A#4, B#4, Cx5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testAbbMinor() throws Exception {
        args.clear();
        args.add("Abb4");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals("Abb4, Bbb4, Cbb5, Dbb5, Ebb5, Fbb5, Gbb5, Abb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBbbMinor() throws Exception {
        args.clear();
        args.add("Bbb4");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals("Bbb4, Cb5, Dbb5, Ebb5, Fb5, Gbb5, Abb5, Bbb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // Scale Modes
    @Test
    public void testCIonian() throws Exception {
        args.clear();
        args.add("C");
        args.add("Ionian");
        new FindScale(args).execute(env);
        assertEquals("C, D, E, F, G, A, B, C", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCDorian() throws Exception {
        args.clear();
        args.add("C");
        args.add("Dorian");
        new FindScale(args).execute(env);
        assertEquals("C, D, Eb, F, G, A, Bb, C", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCPhrygian() throws Exception {
        args.clear();
        args.add("C");
        args.add("Phrygian");
        new FindScale(args).execute(env);
        assertEquals("C, Db, Eb, F, G, Ab, Bb, C", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCLydian() throws Exception {
        args.clear();
        args.add("C");
        args.add("Lydian");
        new FindScale(args).execute(env);
        assertEquals("C, D, E, F#, G, A, B, C", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCSharpLydian() throws Exception {
        args.clear();
        args.add("C#");
        args.add("Lydian");
        new FindScale(args).execute(env);
        assertEquals("C#, D#, E#, Fx, G#, A#, B#, C#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCMixolydian() throws Exception {
        args.clear();
        args.add("C");
        args.add("Mixolydian");
        new FindScale(args).execute(env);
        assertEquals("C, D, E, F, G, A, Bb, C", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCAeolian() throws Exception {
        args.clear();
        args.add("C");
        args.add("Aeolian");
        new FindScale(args).execute(env);
        assertEquals("C, D, Eb, F, G, Ab, Bb, C", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCLocrian() throws Exception {
        args.clear();
        args.add("C");
        args.add("Locrian");
        new FindScale(args).execute(env);
        assertEquals("C, Db, Eb, F, Gb, Ab, Bb, C", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDIonian() throws Exception {
        args.clear();
        args.add("D");
        args.add("Ionian");
        new FindScale(args).execute(env);
        assertEquals("D, E, F#, G, A, B, C#, D", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testD5Dorian() throws Exception {
        args.clear();
        args.add("D5");
        args.add("Dorian");
        new FindScale(args).execute(env);
        assertEquals("D5, E5, F5, G5, A5, B5, C6, D6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDPhrygian() throws Exception {
        args.clear();
        args.add("D");
        args.add("Phrygian");
        new FindScale(args).execute(env);
        assertEquals("D, Eb, F, G, A, Bb, C, D", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDLydian() throws Exception {
        args.clear();
        args.add("D");
        args.add("Lydian");
        new FindScale(args).execute(env);
        assertEquals("D, E, F#, G#, A, B, C#, D", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDMixolydian() throws Exception {
        args.clear();
        args.add("D");
        args.add("Mixolydian");
        new FindScale(args).execute(env);
        assertEquals("D, E, F#, G, A, B, C, D", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDAeolian() throws Exception {
        args.clear();
        args.add("D");
        args.add("Aeolian");
        new FindScale(args).execute(env);
        assertEquals("D, E, F, G, A, Bb, C, D", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testDLocrian() throws Exception {
        args.clear();
        args.add("D");
        args.add("Locrian");
        new FindScale(args).execute(env);
        assertEquals("D, Eb, F, G, Ab, Bb, C, D", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testD4Locrian() throws Exception {
        args.clear();
        args.add("D4");
        args.add("Locrian");
        new FindScale(args).execute(env);
        assertEquals("D4, Eb4, F4, G4, Ab4, Bb4, C5, D5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testC4Locrian() throws Exception {
        args.clear();
        args.add("C4");
        args.add("Locrian");
        new FindScale(args).execute(env);
        assertEquals("C4, Db4, Eb4, F4, Gb4, Ab4, Bb4, C5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testGSharpLydian() throws Exception {
        args.clear();
        args.add("G#");
        args.add("Lydian");
        new FindScale(args).execute(env);
        assertEquals("G#, A#, B#, Cx, D#, E#, Fx, G#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //Melodic Minor Scales
    @Test
    public void testFMelodicMinorScale() throws Exception {
        args.clear();
        args.add("F");
        args.add("Melodic Minor");
        new FindScale(args).execute(env);
        assertEquals("F, G, Ab, Bb, C, D, E, F", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFSharpMelodicMinorScale() throws Exception {
        args.clear();
        args.add("F#");
        args.add("Melodic Minor");
        new FindScale(args).execute(env);
        assertEquals("F#, G#, A, B, C#, D#, E#, F#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testGSharp4MelodicMinorScale() throws Exception {
        args.clear();
        args.add("G#4");
        args.add("Melodic Minor");
        new FindScale(args).execute(env);
        assertEquals("G#4, A#4, B4, C#5, D#5, E#5, Fx5, G#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //valid - scale going out of midi range

    @Test
    public void testG9NaturalMinor() throws Exception {
        args.clear();
        args.add("G9");
        args.add("Natural minor");
        new FindScale(args).execute(env);
        assertEquals("G9 - The rest of the scale cannot be mapped to midi", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testC9NaturalMinor() throws Exception {
        args.clear();
        args.add("C9");
        args.add("natural Minor");
        new FindScale(args).execute(env);
        assertEquals("C9, D9, Eb9, F9, G9 - The rest of the scale cannot be mapped to midi", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testGSharp8Minor() throws Exception {
        args.clear();
        args.add("G#8");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals("G#8, A#8, B8, C#9, D#9, E9, F#9 - The rest of the scale cannot be mapped to midi", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // MELODIC MINOR SCALE TESTS


    @Test
    public void testCMelodicMinor() throws Exception {
        args.add("C");
        args.add("Melodic Minor");
        String expected = "C, D, Eb, F, G, A, B, C";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testCSharpMelodicMinor() throws Exception {
        args.add("C#");
        args.add("Melodic Minor");
        String expected = "C#, D#, E, F#, G#, A#, B#, C#";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testDbMelodicMinor() throws Exception {
        args.add("Db");
        args.add("Melodic Minor");
        String expected = "Db, Eb, Fb, Gb, Ab, Bb, C, Db";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testDMelodicMinor() throws Exception {
        args.add("D");
        args.add("Melodic Minor");
        String expected = "D, E, F, G, A, B, C#, D";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testDSharpMelodicMinor() throws Exception {
        args.add("D#");
        args.add("Melodic Minor");
        String expected = "D#, E#, F#, G#, A#, B#, Cx, D#";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testEbMelodicMinor() throws Exception {
        args.add("Eb");
        args.add("Melodic Minor");
        String expected = "Eb, F, Gb, Ab, Bb, C, D, Eb";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testEMelodicMinor() throws Exception {
        args.add("E");
        args.add("Melodic Minor");
        String expected = "E, F#, G, A, B, C#, D#, E";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testESharpMelodicMinor() throws Exception {
        args.add("E#");
        args.add("Melodic Minor");
        String expected = "E#, Fx, G#, A#, B#, Cx, Dx, E#";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testFbMelodicMinor() throws Exception {
        args.add("Fb");
        args.add("Melodic Minor");
        String expected = "Fb, Gb, Abb, Bbb, Cb, Db, Eb, Fb";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testFMelodicMinor() throws Exception {
        args.add("F");
        args.add("Melodic Minor");
        String expected = "F, G, Ab, Bb, C, D, E, F";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testFSharpMelodicMinor() throws Exception {
        args.add("F#");
        args.add("Melodic Minor");
        String expected = "F#, G#, A, B, C#, D#, E#, F#";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testGbMelodicMinor() throws Exception {
        args.add("Gb");
        args.add("Melodic Minor");
        String expected = "Gb, Ab, Bbb, Cb, Db, Eb, F, Gb";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testGMelodicMinor() throws Exception {
        args.add("G");
        args.add("Melodic Minor");
        String expected = "G, A, Bb, C, D, E, F#, G";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testGSharpMelodicMinor() throws Exception {
        args.add("G#");
        args.add("Melodic Minor");
        String expected = "G#, A#, B, C#, D#, E#, Fx, G#";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testAbMelodicMinor() throws Exception {
        args.add("Ab");
        args.add("Melodic Minor");
        String expected = "Ab, Bb, Cb, Db, Eb, F, G, Ab";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testAMelodicMinor() throws Exception {
        args.add("A");
        args.add("Melodic Minor");
        String expected = "A, B, C, D, E, F#, G#, A";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testASharpMelodicMinor() throws Exception {
        args.add("A#");
        args.add("Melodic Minor");
        String expected = "A#, B#, C#, D#, E#, Fx, Gx, A#";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testBbMelodicMinor() throws Exception {
        args.add("Bb");
        args.add("Melodic Minor");
        String expected = "Bb, C, Db, Eb, F, G, A, Bb";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testBMelodicMinor() throws Exception {
        args.add("B");
        args.add("Melodic Minor");
        String expected = "B, C#, D, E, F#, G#, A#, B";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testBSharpMelodicMinor() throws Exception {
        args.add("B#");
        args.add("Melodic Minor");
        String expected = "B#, Cx, D#, E#, Fx, Gx, Ax, B#";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testCbMelodicMinor() throws Exception {
        args.add("Cb");
        args.add("Melodic Minor");
        String expected = "Cb, Db, Ebb, Fb, Gb, Ab, Bb, Cb";

        new FindScale(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testGSharp8MelodicMinor() throws Exception {
        args.clear();
        args.add("G#8");
        args.add("Melodic Minor");
        new FindScale(args).execute(env);
        assertEquals("G#8, A#8, B8, C#9, D#9, E#9, Fx9 - The rest of the scale cannot be mapped to midi", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //invalid - scale doesn't exist

    @Test
    public void testInvalidCbbMinor() throws Exception {
        args.clear();
        args.add("Cbb");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidDbbMinor() throws Exception {
        args.clear();
        args.add("Dbb");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidEDoubleSharpMinor() throws Exception {
        args.clear();
        args.add("E##");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidFbbMinor() throws Exception {
        args.clear();
        args.add("Fbb");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidGbbMinor() throws Exception {
        args.clear();
        args.add("Gbb");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidBDoubleSharpMinor() throws Exception {
        args.clear();
        args.add("B##");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    //invalid - note

    @Test
    public void testInvalidAUpperFlatMinor() throws Exception {
        args.clear();
        args.add("aB");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorValidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidGDoubleSharpMinor() throws Exception {
        args.clear();
        args.add("G##9");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorMidiRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidCNeg10Minor() throws Exception {
        args.clear();
        args.add("C-10");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorMidiRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidGbNeg1() throws Exception {
        args.clear();
        args.add("Gb-1");
        args.add("Major");
        new FindScale(args).execute(env);
        assertEquals("Gb-1, Ab-1, Bb-1, Cb0, Db0, Eb0, F0, Gb0", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DDorianB2() throws Exception {
        args.clear();
        args.add("D");
        args.add("Dorian b2");
        new FindScale(args).execute(env);
        assertEquals("D, Eb, F, G, A, B, C, D", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void A4Mixolydianb6() throws Exception {
        args.clear();
        args.add("A4");
        args.add("Mixolydian b6");
        new FindScale(args).execute(env);
        assertEquals("A4, B4, C#5, D5, E5, F5, G5, A5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void D4DorianB2() throws Exception {
        args.clear();
        args.add("D4");
        args.add("Dorian b2");
        new FindScale(args).execute(env);
        assertEquals("D4, Eb4, F4, G4, A4, B4, C5, D5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void D9DorianB2() throws Exception {
        args.clear();
        args.add("D9");
        args.add("Dorian b2");
        new FindScale(args).execute(env);
        assertEquals("D9, Eb9, F9, G9 - The rest of the scale cannot be mapped to midi", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void FLydianDominant() throws Exception {
        args.clear();
        args.add("F");
        args.add("Lydian Dominant");
        new FindScale(args).execute(env);
        assertEquals("F, G, A, B, C, D, Eb, F", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void C4Altered() throws Exception {
        args.clear();
        args.add("C4");
        args.add("Altered");
        new FindScale(args).execute(env);
        assertEquals("C4, Db4, Eb4, Fb4, Gb4, Ab4, Bb4, C5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void BAltered() throws Exception {
        args.clear();
        args.add("B");
        args.add("Altered");
        new FindScale(args).execute(env);
        assertEquals("B, C, D, Eb, F, G, A, B", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void BbAltered() throws Exception {
        args.clear();
        args.add("Bb");
        args.add("Altered");
        new FindScale(args).execute(env);
        assertEquals("Bb, Cb, Db, Ebb, Fb, Gb, Ab, Bb", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void AbbAltered() throws Exception {
        args.clear();
        args.add("Abb");
        args.add("Altered");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void AbAltered() throws Exception {
        args.clear();
        args.add("Ab");
        args.add("Altered");
        new FindScale(args).execute(env);
        assertEquals("Ab, Bbb, Cb, Dbb, Ebb, Fb, Gb, Ab", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void EbbMixolydianb6() throws Exception {
        args.clear();
        args.add("Ebb");
        args.add("Mixolydian b6");
        new FindScale(args).execute(env);
        assertEquals("Ebb, Fb, Gb, Abb, Bbb, Cbb, Dbb, Ebb", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidC10Minor() throws Exception {
        args.clear();
        args.add("C10");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorMidiRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidAAMinor() throws Exception {
        args.clear();
        args.add("AA");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorValidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalid3Minor() throws Exception {
        args.clear();
        args.add("3");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorValidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalid$Minor() throws Exception {
        args.clear();
        args.add("$");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorValidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidHMinor() throws Exception {
        args.clear();
        args.add("H");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorValidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidUpperXMinor() throws Exception {
        args.clear();
        args.add("X");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorValidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidLowerXMinor() throws Exception {
        args.clear();
        args.add("x");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorValidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidA$Minor() throws Exception {
        args.clear();
        args.add("A$");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorValidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidAHMinor() throws Exception {
        args.clear();
        args.add("AH");
        args.add("Minor");
        new FindScale(args).execute(env);
        assertEquals(errorValidNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }
}
