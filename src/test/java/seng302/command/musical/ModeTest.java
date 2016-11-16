package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Mode command test.
 *
 * @author plr37
 */
public class ModeTest {

    private Environment env = new Environment();
    private List<String> args;

    private String numArgsError = "[ERROR] " + Error.MODEINVALIDARGS.getError();
    private String keyError = "[ERROR] " + Error.MODEINVALIDKEY.getError();
    private String degreeError = "[ERROR] " + Error.MODEINVALIDDEGREE.getError();
    private String midiError = "[ERROR] " + Error.MODEMIDIRANGE.getError();
    private String modeTypeError = "[ERROR] " + Error.MODEINVALIDMODETYPE.getError();
    String outOfMidiRange = "The resulting mode is out of midi range.";

    @Before
    public void setUp() {
        args = new ArrayList<>();
    }


    //Invalid
    @Test
    public void OneArg() throws Exception {
        args.add("C");
        String expected = numArgsError;
        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void InvalidKeyX() throws Exception {
        args.add("X");
        args.add("2");

        String expected = keyError;
        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void InvalidKeySharp() throws Exception {
        args.add("#");
        args.add("2");

        String expected = keyError;
        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void InvalidKeyGG() throws Exception {
        args.add("GG");
        args.add("2");

        String expected = keyError;
        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void InvalidDegree0() throws Exception {
        args.add("C");
        args.add("0");

        String expected = degreeError;
        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void InvalidDegree8() throws Exception {
        args.add("C");
        args.add("8");

        String expected = degreeError;
        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void InvalidNoteHighMidi() throws Exception {
        args.add("G#9");
        args.add("2");

        String expected = midiError;
        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void InvalidNoteLowMidi() throws Exception {
        args.add("Cb-1");
        args.add("2");

        String expected = midiError;

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }


    //Valid
    @Test
    public void testModeC1() throws Exception {
        args.add("C");
        args.add("1");
        String expected = "C Ionian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeC2() throws Exception {
        args.add("C");
        args.add("2");
        String expected = "D Dorian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeC3() throws Exception {
        args.add("C");
        args.add("3");
        String expected = "E Phrygian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeC4() throws Exception {
        args.add("C");
        args.add("4");
        String expected = "F Lydian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeC5() throws Exception {
        args.add("C");
        args.add("5");
        String expected = "G Mixolydian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeC6() throws Exception {
        args.add("C");
        args.add("6");
        String expected = "A Aeolian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeC7() throws Exception {
        args.add("C");
        args.add("7");
        String expected = "B Locrian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeD1() throws Exception {
        args.add("D");
        args.add("1");
        String expected = "D Ionian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeD2() throws Exception {
        args.add("D");
        args.add("2");
        String expected = "E Dorian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeD3() throws Exception {
        args.add("D");
        args.add("3");
        String expected = "F# Phrygian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeD4() throws Exception {
        args.add("D");
        args.add("4");
        String expected = "G Lydian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeD5() throws Exception {
        args.add("D");
        args.add("5");
        String expected = "A Mixolydian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeD95() throws Exception {
        args.add("D9");
        args.add("5");
        String expected = "The resulting mode is out of midi range.";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeD6() throws Exception {
        args.add("D");
        args.add("6");
        String expected = "B Aeolian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeD7() throws Exception {
        args.add("D");
        args.add("7");
        String expected = "C# Locrian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    //Valid - other
    @Test
    public void testModeC41() throws Exception {
        args.add("C4");
        args.add("1");
        String expected = "C4 Ionian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);

    }

    @Test
    public void testModeC81() throws Exception {
        args.add("C8");
        args.add("1");
        String expected = "C8 Ionian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeC81Major() throws Exception {
        args.add("C8");
        args.add("1");
        args.add("Major");
        String expected = "C8 Ionian";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeInvalidModeType() throws Exception {
        args.add("C");
        args.add("1");
        args.add("Invalid");

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(modeTypeError, result);
    }

    @Test
    public void testModeC2MelodicMinor() throws Exception {
        args.add("C");
        args.add("2");
        args.add("Melodic Minor");
        String expected = "D Dorian b2";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeD5MelodicMinor() throws Exception {
        args.add("D");
        args.add("5");
        args.add("Melodic Minor");
        String expected = "A Mixolydian b6";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeC7MelodicMinor() throws Exception {
        args.add("C");
        args.add("7");
        args.add("Melodic Minor");
        String expected = "B Altered";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeC8MelodicMinor() throws Exception {
        args.add("C");
        args.add("8");
        args.add("Melodic Minor");

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(degreeError, result);
    }

    @Test
    public void testModeCNeg1MelodicMinor() throws Exception {
        args.add("C");
        args.add("-1");
        args.add("Melodic Minor");

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(degreeError, result);
    }

    @Test
    public void testModeD2MelodicMinor() throws Exception {
        args.add("D");
        args.add("2");
        args.add("Melodic Minor");
        String expected = "E Dorian b2";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeBSharp53MelodicMinor() throws Exception {
        args.add("B#5");
        args.add("3");
        args.add("Melodic Minor");
        String expected = "D#6 Lydian #5";

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(expected, result);
    }

    @Test
    public void testModeInvalidModeTypeButValidScaleGroup() throws Exception {
        args.add("C");
        args.add("3");
        args.add("Jazz");

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(modeTypeError, result);
    }

    @Test
    public void testModeOutOfBoundsUpperNote() throws Exception {
        args.add("D10");
        args.add("2");
        args.add("Melodic Minor");

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(midiError, result);
    }

    @Test
    public void testModeOutOfBoundsLowerNote() throws Exception {
        args.add("D-2");
        args.add("2");
        args.add("Melodic Minor");

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(midiError, result);
    }

    @Test
    public void testModeReturnedModeOutOfRangeMelodicMinor() throws Exception {
        args.add("C#9");
        args.add("6");

        new Mode(args).execute(env);
        String result = env.getOutput().replaceAll("\\r|\\n", "");
        assertEquals(outOfMidiRange, result);
    }

}