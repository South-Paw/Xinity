package seng302.command.musical;

import cucumber.api.java.gl.E;
import org.junit.Ignore;
import org.junit.Test;
import seng302.Environment;
import seng302.util.enumerator.Error;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for Find Chord.
 *
 * @author avh17, ljm163
 */
public class FindChordTest {
    private Environment env = new Environment();
    private List<String> args;
    private String invalidArguments = "[ERROR] " + Error.FINDCHORDINVALIDARGUMENTS.getError();
    private String errorNote = "[ERROR] " + Error.INVALIDNOTE.getError();
    private String errorOctaveNote = "[ERROR] " + Error.FINDCHORDNOOCTAVES.getError();

    @Test
    public void findCMajor() {
        args = Arrays.asList("C", "E", "G");

        new FindChord(args).execute(env);

        assertEquals("C Major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findCMajorLowerCase() {
        args = Arrays.asList("c", "e", "g");

        new FindChord(args).execute(env);

        assertEquals("C Major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findCMajorFlag() {
        args = Arrays.asList("C", "E", "G", "inversion");

        new FindChord(args).execute(env);

        assertEquals("C Major Root Position", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findCMinor() {
        args = Arrays.asList("C", "Eb", "G");

        new FindChord(args).execute(env);

        assertEquals("C Minor", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findCMajorFirstInversion() {
        args = Arrays.asList("E", "G", "C");

        new FindChord(args).execute(env);

        assertEquals("C Major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findCMajorSecondInversion() {
        args = Arrays.asList("G", "C", "E");

        new FindChord(args).execute(env);

        assertEquals("C Major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findCMinorFirstInversion() {
        args = Arrays.asList("Eb", "G", "C");

        new FindChord(args).execute(env);

        assertEquals("C Minor", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findCMinorSecondInversion() {
        args = Arrays.asList("G", "C", "Eb");

        new FindChord(args).execute(env);

        assertEquals("C Minor", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findCMajorFirstInversionFlag() {
        args = Arrays.asList("E", "G", "C", "inversion");

        new FindChord(args).execute(env);

        assertEquals("C Major 1st Inversion", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findCMajorSecondInversionFlag() {
        args = Arrays.asList("G", "C", "E", "inversion");

        new FindChord(args).execute(env);

        assertEquals("C Major 2nd Inversion", env.getOutput().replaceAll("\\r|\\n", ""));
    }


    // find chord enhanced (4 note and enharmonic)

    @Test
    public void findEm6() {
        args = Arrays.asList("E", "G", "B", "C#");

        new FindChord(args).execute(env);

        assertEquals("E Minor 6th, C# Half Diminished", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findEm6Inversion1() {
        args = Arrays.asList("G", "B", "C#", "E");

        new FindChord(args).execute(env);

        assertEquals("C# Half Diminished, E Minor 6th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findEm6Inversion1Flag() {
        args = Arrays.asList("G", "B", "C#", "E", "inversion");

        new FindChord(args).execute(env);

        assertEquals("C# Half Diminished 2nd Inversion, E Minor 6th 1st Inversion",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findInvalid4Note() {
        args = Arrays.asList("A", "B", "C", "D", "inversion");

        new FindChord(args).execute(env);

        assertEquals(invalidArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findInvalid4NoteOctave() {
        args = Arrays.asList("E9", "G9", "B9", "C#10");

        new FindChord(args).execute(env);

        assertEquals(errorOctaveNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findMultipleChords() {
        args = Arrays.asList("C", "E", "G", "A");

        new FindChord(args).execute(env);

        assertEquals("C Major 6th, A Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findMultipleChordsNoEnharmonic() {
        args = Arrays.asList("C", "E", "G", "A", "noEnharmonic");

        new FindChord(args).execute(env);

        assertEquals("C Major 6th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findMultipleChords2() {
        args = Arrays.asList("C", "Eb", "Gb", "Bb");

        new FindChord(args).execute(env);

        assertEquals("C Half Diminished, Eb Minor 6th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findMultipleChords2NoEnharmonic() {
        args = Arrays.asList("C", "Eb", "Gb", "Bb", "noEnharmonic");

        new FindChord(args).execute(env);

        assertEquals("C Half Diminished", env.getOutput().replaceAll("\\r|\\n", ""));
    }


    @Test
    public void findMultipleChords3() {
        args = Arrays.asList("C", "E", "G#");

        new FindChord(args).execute(env);

        assertEquals("C Augmented Triad, E Augmented Triad, G# Augmented Triad",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findMultipleChords3InversionNoEnharmonic() {
        args = Arrays.asList("C", "E", "G#", "inversion", "noEnharmonic");

        new FindChord(args).execute(env);

        assertEquals("C Augmented Triad Root Position",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findMultipleChords3NoEnharmonic() {
        args = Arrays.asList("C", "E", "G#", "noEnharmonic");

        new FindChord(args).execute(env);

        assertEquals("C Augmented Triad", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findMultipleChords3NoEnharmonicFlag() {
        args = Arrays.asList("C", "E", "G#", "noEnharmonic", "inversion");

        new FindChord(args).execute(env);

        assertEquals("C Augmented Triad Root Position", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findFACSharp() {
        args = Arrays.asList("F", "A", "C#");

        new FindChord(args).execute(env);

        assertEquals("F Augmented Triad, A Augmented Triad, C# Augmented Triad",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findFACSharpInversion() {
        args = Arrays.asList("F", "A", "C#", "Inversion");

        new FindChord(args).execute(env);

        assertEquals("F Augmented Triad Root Position, "
                        + "A Augmented Triad 2nd Inversion, "
                        + "C# Augmented Triad 1st Inversion",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findACSharpF() {
        args = Arrays.asList("A", "C#", "F");

        new FindChord(args).execute(env);

        assertEquals("F Augmented Triad, A Augmented Triad, C# Augmented Triad",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findACFSharpInversion() {
        args = Arrays.asList("A", "C#", "F", "Inversion");

        new FindChord(args).execute(env);

        assertEquals("F Augmented Triad 1st Inversion, "
                        + "A Augmented Triad Root Position, "
                        + "C# Augmented Triad 2nd Inversion",
                env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findACFSharpInversionNoEnharmonic() {
        args = Arrays.asList("A", "C#", "F", "Inversion", "noEnharmonic");

        new FindChord(args).execute(env);

        assertEquals("F Augmented Triad 1st Inversion", env.getOutput().replaceAll("\\r|\\n", ""));
    }




    //-------------------------------------------------------------


    @Test
    public void invalidChordOne() {
        args = Arrays.asList("A", "B", "C");

        new FindChord(args).execute(env);

        assertEquals(invalidArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void invalidChordTwo() {
        args = Arrays.asList("C", "E", "G", "inversion");

        new FindChord(args).execute(env);

        assertEquals("C Major Root Position", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findCMajorWithOctave() {
        args = Arrays.asList("C4", "E4", "G4");

        new FindChord(args).execute(env);

        assertEquals(errorOctaveNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void invalidChordThree() {
        args = Arrays.asList("G0", "B10", "D10");

        new FindChord(args).execute(env);

        assertEquals(errorOctaveNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findDSharpMajorSecondInversion() {
        args = Arrays.asList("A#", "D#", "Fx");

        new FindChord(args).execute(env);

        assertEquals("D# Major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findDSharpMajorSecondInversionFlag() {
        args = Arrays.asList("A#", "D#", "Fx", "inversion");

        new FindChord(args).execute(env);

        assertEquals("D# Major 2nd Inversion", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findFFlatMinorFirstInversion() {
        args = Arrays.asList("Abb", "Cb", "Fb");

        new FindChord(args).execute(env);

        assertEquals("Fb Minor", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findFFlatMinorFirstInversionFlag() {
        args = Arrays.asList("Abb", "Cb", "Fb", "inversion");

        new FindChord(args).execute(env);

        assertEquals("Fb Minor 1st Inversion", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void notEnoughArgumentsGiven() {
        args = Arrays.asList("Abb", "Cb");

        new FindChord(args).execute(env);

        assertEquals(invalidArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void notEnoughNotesGiven() {
        args = Arrays.asList("Abb", "Cb", "inversion");

        new FindChord(args).execute(env);

        assertEquals(errorNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void invalidChordGiven() {
        args = Arrays.asList("this", "is not", "a chord", "inversion");

        new FindChord(args).execute(env);

        assertEquals(errorNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findCMajorMidi() {
        args = Arrays.asList("60", "64", "67");

        new FindChord(args).execute(env);

        assertEquals(errorNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findCMajorFirstInversionFlagMidi() {
        args = Arrays.asList("64", "67", "60", "inversion");

        new FindChord(args).execute(env);

        assertEquals(errorNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findBMajorOctave() {
        args = Arrays.asList("B4", "D#5", "F#5");

        new FindChord(args).execute(env);

        assertEquals(errorOctaveNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findBMajorFirstInversionOctave() {
        args = Arrays.asList("D#", "F#", "B");

        new FindChord(args).execute(env);

        assertEquals("B Major", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void findBMajorFirstInversionFlagOctave() {
        args = Arrays.asList("D#", "F#", "B", "inversion");

        new FindChord(args).execute(env);

        assertEquals("B Major 1st Inversion", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void invalidInversionFlag() {
        args = Arrays.asList("C", "E", "g", "notAFlag");

        new FindChord(args).execute(env);

        assertEquals(errorNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void tooManyArguments() {
        args = Arrays.asList("C", "E", "g", "inversion", "anotherArgument");

        new FindChord(args).execute(env);

        assertEquals(invalidArguments, env.getOutput().replaceAll("\\r|\\n", ""));
    }
}