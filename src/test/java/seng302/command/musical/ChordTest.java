package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.command.musical.Chord;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author mvj14
 */
public class ChordTest {
    private Environment env = new Environment();
    private List<String> args;
    private String errorInvalidQuality = "[ERROR] " + Error.CHORDINVALIDQUALITY.getError();
    private String errorInvalidNumArgs = "[ERROR] " + Error.CHORDINVALIDNUMARGS.getError();
    private String errorOutOfMidiRange = "[ERROR] " + Error.INVALIDMIDINOTE.getError();
    private String errorChordNotExists = "[ERROR] " + Error.CHORDDOESNOTEXIST.getError();
    private String errorInvalidNote = "[ERROR] " + Error.XINITYNOTECANNOTCREATENOTE.getError();
    private String errorInversionTriad = "[ERROR] " + Error.CHORDWRONGINVERSIONTRIAD.getError();
    private String errorInversion = "[ERROR] " + Error.CHORDWRONGINVERSION.getError();

    @Before
    public void setUp() {
        args = new ArrayList<>();
    }

    @Test
    public void chordTriadMinorG() {
        args.clear();

        args.add("G");
        args.add("Minor Triad");

        new Chord(args).execute(env);

        assertEquals("G, Bb, D",env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordTriadMajorG() {
        args.clear();

        args.add("G");
        args.add("Major Triad");

        new Chord(args).execute(env);

        assertEquals(
                "G, B, D",
                env.getOutput().replaceAll("\\r|\\n", "")
        );
    }

    @Test
    public void chordTriadMajorCShortForm() {
        args.clear();

        args.add("C");
        args.add("Major");

        new Chord(args).execute(env);

        assertEquals("C, E, G", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordTriadMinorCShortForm() {
        args.clear();

        args.add("C");
        args.add("Minor");

        new Chord(args).execute(env);

        assertEquals("C, Eb, G", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordTriadMajorC4() {
        args.clear();

        args.add("C4");
        args.add("Major Triad");

        new Chord(args).execute(env);

        assertEquals("C4, E4, G4", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordTriadMajorG9() {
        args.clear();

        args.add("G9");
        args.add("Major Triad");

        new Chord(args).execute(env);
        assertEquals("G9 - The rest of the chord cannot be mapped to midi", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordTriadMajorCSharp3() {
        args.clear();

        args.add("C#3");
        args.add("Major Triad");

        new Chord(args).execute(env);

        assertEquals("C#3, E#3, G#3", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordTriadMinorDb3() {
        args.clear();

        args.add("Db3");
        args.add("Minor Triad");

        new Chord(args).execute(env);

        assertEquals("Db3, Fb3, Ab3", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordTriadMajorDSharpSharp3() {
        args.clear();

        args.add("D##3");
        args.add("Major Triad");

        new Chord(args).execute(env);

        assertEquals(errorChordNotExists, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordTriadMajorCSharpSharp3() {
        args.clear();

        args.add("C##3");
        args.add("Major Triad");

        new Chord(args).execute(env);

        assertEquals("Cx3, Ex3, Gx3", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordTriadBadQuality() {
        args.clear();

        args.add("C");
        args.add("Mager Traid");

        new Chord(args).execute(env);

        assertEquals(errorInvalidQuality, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordTriadBadNote() {
        args.clear();

        args.add("Ceesharp");
        args.add("Major Triad");

        new Chord(args).execute(env);

        assertEquals(errorInvalidNote + "\"Ceesharp\".", env.getOutput().replaceAll("\\r|\\n", ""));
    }
    @Test
    public void chordTriadOneArg() {
        args.clear();

        args.add("Major Triad");

        new Chord(args).execute(env);

        assertEquals(errorInvalidNumArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }
    @Test
    public void chordTriadThreeArgs() {
        args.clear();

        args.add("C");
        args.add("Major Triad");
        args.add("Minor Triad");

        new Chord(args).execute(env);

        assertEquals(errorInversion, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordOutOfBoundsLower() {
        args.clear();

        args.add("B-2");
        args.add("Minor Triad");

        new Chord(args).execute(env);

        assertEquals(errorOutOfMidiRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordOutOfBoundsUpper() {
        args.clear();

        args.add("G10");
        args.add("Minor Triad");

        new Chord(args).execute(env);

        assertEquals(errorOutOfMidiRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordC4Major7th() {
        args.clear();

        args.add("C4");
        args.add("Major Seventh");

        new Chord(args).execute(env);

        assertEquals("C4, E4, G4, B4", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordD3Minor7th() {
        args.clear();

        args.add("D3");
        args.add("Minor Seventh");

        new Chord(args).execute(env);

        assertEquals("D3, F3, A3, C4", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordGSeventh() {
        args.clear();

        args.add("G");
        args.add("Seventh");

        new Chord(args).execute(env);

        assertEquals("G, B, D, F", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordBSharp5HalfDiminished() {
        args.clear();

        args.add("B#5");
        args.add("Half Diminished");

        new Chord(args).execute(env);

        assertEquals("B#5, D#6, F#6, A#6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordCMajorSixth() {
        args.clear();

        args.add("C");
        args.add("Major sixth");

        new Chord(args).execute(env);

        assertEquals("C, E, G, A", env.getOutput().replaceAll("\\r|\\n", ""));
    }
    @Test
    public void chordCSharpDiminished7th() {
        args.clear();

        args.add("C#");
        args.add("Diminished 7th");

        new Chord(args).execute(env);

        assertEquals("C#, E, G, Bb", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void chordOutOfBoundsUpper4note() {
        args.clear();

        args.add("C9");
        args.add("Major 7th");

        new Chord(args).execute(env);

        assertEquals("C9, E9, G9 - The rest of the chord cannot be mapped to midi", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // tests for inversions

    @Test
    public void C4MajorInversion1() {
        args.clear();

        args.add("C4");
        args.add("Major");
        args.add("1");

        new Chord(args).execute(env);

        assertEquals("E4, G4, C5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void C4MajorInversion2() {
        args.clear();

        args.add("C4");
        args.add("Major");
        args.add("2");

        new Chord(args).execute(env);

        assertEquals("G4, C5, E5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void C4MajorInversion3() {
        args.clear();

        args.add("C4");
        args.add("Major");
        args.add("3");

        new Chord(args).execute(env);

        assertEquals(errorInversionTriad, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void C4Major7thInversion1() {
        args.clear();

        args.add("C4");
        args.add("Major 7th");
        args.add("1");

        new Chord(args).execute(env);

        assertEquals("E4, G4, B4, C5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void C4Major7thInversion2() {
        args.clear();

        args.add("C4");
        args.add("Major 7th");
        args.add("2");

        new Chord(args).execute(env);

        assertEquals("G4, B4, C5, E5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void C4Major7thInversion3() {
        args.clear();

        args.add("C4");
        args.add("Major 7th");
        args.add("3");

        new Chord(args).execute(env);

        assertEquals("B4, C5, E5, G5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void C4Major7thInversion4() {
        args.clear();

        args.add("C4");
        args.add("Major 7th");
        args.add("4");

        new Chord(args).execute(env);

        assertEquals(errorInversion, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void G4MajorInversion1() {
        args.clear();

        args.add("G4");
        args.add("Major");
        args.add("1");

        new Chord(args).execute(env);

        assertEquals("B4, D5, G5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void G9MajorInversion1() {
        args.clear();

        args.add("G9");
        args.add("Major");
        args.add("1");

        new Chord(args).execute(env);

        assertEquals("No notes could be mapped to midi", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void A9Major() {
        args.clear();

        args.add("A9");
        args.add("Major");

        new Chord(args).execute(env);

        assertEquals(errorOutOfMidiRange, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void D9MajorInversion1() {
        args.clear();

        args.add("D9");
        args.add("Major");
        args.add("1");

        new Chord(args).execute(env);

        assertEquals("F#9 - The rest of the chord cannot be mapped to midi", env.getOutput().replaceAll("\\r|\\n", ""));
    }
}
