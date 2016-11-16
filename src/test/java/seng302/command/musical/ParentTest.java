package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Parent command test.
 *
 * @author plr37
 */
public class ParentTest {

    private Environment env = new Environment();
    private List<String> args;

    private String numArgsError = "[ERROR] " + Error.PARENTINVALIDARGS.getError();
    private String noteError = "[ERROR] " + Error.PARENTINVALIDNOTE.getError();
    private String scaleError = "[ERROR] " + Error.PARENTINVALIDMODE.getError();
    private String midiError = "[ERROR] " + Error.PARENTMIDIRANGE.getError();

    @Before
    public void setUp() {
        args = new ArrayList<>();
    }

    //Invalid
    @Test
    public void OneArg() throws Exception {
        args.add("C");
        String expected = numArgsError;
        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void ThreeArgs() throws Exception {
        args.add("C");
        args.add("Dorian");
        args.add("Mixolydian");
        String expected = numArgsError;
        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void InvalidKeyX() throws Exception {
        args.add("X");
        args.add("Mixolydian");

        String expected = noteError;
        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void InvalidKeySharp() throws Exception {
        args.add("#");
        args.add("Mixolydian");

        String expected = noteError;
        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void InvalidKeyGG() throws Exception {
        args.add("GG");
        args.add("Mixolydian");

        String expected = noteError;
        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void InvalidScale() throws Exception {
        args.add("C");
        args.add("Invalid");

        String expected = scaleError;
        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void InvalidScaleMode() throws Exception {
        args.add("C");
        args.add("Major");

        String expected = scaleError;
        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void InvalidNoteHighMidi() throws Exception {
        args.add("G#9");
        args.add("Ionian");

        String expected = midiError;
        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void InvalidNoteLowMidi() throws Exception {
        args.add("Cb-1");
        args.add("Ionian");

        String expected = midiError;

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    //Valid
    @Test
    public void testParentCIonian() throws Exception {
        args.add("C");
        args.add("Ionian");

        String expected = "C Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentDDorian() throws Exception {
        args.add("D");
        args.add("Dorian");

        String expected = "C Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentEPhrygian() throws Exception {
        args.add("E");
        args.add("Phrygian");

        String expected = "C Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentFLydian() throws Exception {
        args.add("F");
        args.add("Lydian");

        String expected = "C Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentGMixolydian() throws Exception {
        args.add("G");
        args.add("Mixolydian");

        String expected = "C Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentAAeolian() throws Exception {
        args.add("A");
        args.add("Aeolian");

        String expected = "C Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentBLocrian() throws Exception {
        args.add("B");
        args.add("Locrian");

        String expected = "C Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentEDorian() throws Exception {
        args.add("E");
        args.add("Dorian");

        String expected = "D Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentFSharpPhrygian() throws Exception {
        args.add("F#4");
        args.add("Phrygian");

        String expected = "D4 Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentGLydian() throws Exception {
        args.add("G");
        args.add("Lydian");

        String expected = "D Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentAMixolydian() throws Exception {
        args.add("A");
        args.add("Mixolydian");

        String expected = "D Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentBAeolian() throws Exception {
        args.add("B4");
        args.add("Aeolian");

        String expected = "D4 Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentCSharpLocrian() throws Exception {
        args.add("C#5");
        args.add("Locrian");

        String expected = "D4 Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentBFlatIonian() throws Exception {
        args.add("Bb");
        args.add("Ionian");

        String expected = "Bb Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentCDorian() throws Exception {
        args.add("C");
        args.add("Dorian");

        String expected = "Bb Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentDPhrygian() throws Exception {
        args.add("D");
        args.add("Phrygian");

        String expected = "Bb Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentEbLydian() throws Exception {
        args.add("Eb");
        args.add("Lydian");

        String expected = "Bb Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentFMixolydian() throws Exception {
        args.add("F");
        args.add("Mixolydian");

        String expected = "Bb Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentGAeolian() throws Exception {
        args.add("G");
        args.add("Aeolian");

        String expected = "Bb Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentALocrian() throws Exception {
        args.add("A");
        args.add("Locrian");

        String expected = "Bb Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentDDorianb2() throws Exception {
        args.add("D");
        args.add("Dorian b2");

        String expected = "C Melodic Minor";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentAMixolydianb6() throws Exception {
        args.add("A");
        args.add("Mixolydian b6");

        String expected = "D Melodic Minor";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentD4Dorianb2() throws Exception {
        args.add("D4");
        args.add("Dorian b2");

        String expected = "C4 Melodic Minor";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentA4Mixolydianb6() throws Exception {
        args.add("A4");
        args.add("Mixolydian b6");

        String expected = "D4 Melodic Minor";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentD4Locrian2() throws Exception {
        args.add("D4");
        args.add("Locrian #2");

        String expected = "F3 Melodic Minor";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentBDoubleSharpAltered() throws Exception {
        args.add("B##");
        args.add("Altered");

        String expected = "Cx Melodic Minor";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentBDoubleSharpLocrian() throws Exception {
        args.add("B##");
        args.add("Locrian");

        String expected = "Cx Major";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentBDoubleFlatLydianFive() throws Exception {
        args.add("Bbb");
        args.add("Lydian #5");

        String expected = "Gb Melodic Minor";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentEbbMixolydianb6() throws Exception {
        args.add("Ebb");
        args.add("Mixolydian b6");

        String expected = "Abb Melodic Minor";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentCx4Minormajor() throws Exception {
        args.add("Cx4");
        args.add("Minormajor");

        String expected = "Cx4 Melodic Minor";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testParentDbb4LydianDominant() throws Exception {
        args.add("Dbb4");
        args.add("Lydian Dominant");

        String expected = "Abb3 Melodic Minor";

        new Parent(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

}
