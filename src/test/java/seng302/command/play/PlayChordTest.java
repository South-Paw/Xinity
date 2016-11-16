package seng302.command.play;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import seng302.Environment;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * @author mvj14
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayChordTest {
    private List<String> args;
    private String errorInvalidQuality = Error.CHORDINVALIDQUALITY.getError();
    private String errorInvalidNumArgs = Error.CHORDINVALIDNUMARGS.getError();
    private String errorOutOfMidiRange = Error.INVALIDMIDINOTE.getError();
    private String errorChordNotExists = Error.CHORDDOESNOTEXIST.getError();
    private String errorInvalidNote = Error.XINITYNOTECANNOTCREATENOTE.getError();

    @Mock
    private Environment mockEnv = new Environment();

    @Before
    public void setUp() {
        args = new ArrayList<>();
    }

    @Test
    public void playChordTriadMinorG() {
        args.clear();

        args.add("G");
        args.add("Minor Triad");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).println("G, Bb, D");
    }

    @Test
    public void playChordTriadMajorG() {
        args.clear();

        args.add("G");
        args.add("Major Triad");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).println("G, B, D");
    }


    @Test
    public void playChordMajorG() {
        args.clear();

        args.add("G");
        args.add("Major");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).println("G, B, D");
    }

    @Test
    public void playChordMajG() {
        args.clear();

        args.add("G");
        args.add("Maj");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).println("G, B, D");
    }

    @Test
    public void playChordMaj7thG() {
        args.clear();

        args.add("G");
        args.add("Maj 7th");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).println("G, B, D, F#");
    }

    @Test
    public void playChordTriadMajorC4() {
        args.clear();

        args.add("C4");
        args.add("Major Triad");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).println("C4, E4, G4");
    }

    @Test
    public void playChordTriadMajorG9() {
        args.clear();

        args.add("G9");
        args.add("Major Triad");

        new PlayChord(args).execute(mockEnv);
        verify(mockEnv).println("G9 - The rest of the chord cannot be mapped to midi");
    }

    @Test
    public void playChordMajorSeventhC9() {
        args.clear();

        args.add("C9");
        args.add("Major Seventh");

        new PlayChord(args).execute(mockEnv);
        verify(mockEnv).println("C9, E9, G9 - The rest of the chord cannot be mapped to midi");
    }

    @Test
    public void playChordTriadMajorCSharp3() {
        args.clear();

        args.add("C#3");
        args.add("Major Triad");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).println("C#3, E#3, G#3");
    }

    @Test
    public void playChordTriadMinorDb3() {
        args.clear();

        args.add("Db3");
        args.add("Minor Triad");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).println("Db3, Fb3, Ab3");
    }

    @Test
    public void playChordMajorSeventh() {
        args.clear();

        args.add("Db2");
        args.add("maj 7th");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).println("Db2, F2, Ab2, C3");
    }

    @Test
    public void playChordMaj6th() {
        args.clear();

        args.add("eb");
        args.add("maj 6th");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).println("Eb, G, Bb, C");
    }

    @Test
    public void playChordTriadMajorDSharpSharp3() {
        args.clear();

        args.add("D##3");
        args.add("Major Triad");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).error(errorChordNotExists);
    }

    @Test
    public void playChordTriadBadQuality() {
        args.clear();

        args.add("C");
        args.add("Mager Tired");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).error(errorInvalidQuality);
    }

    @Test
    public void playChordTriadBadNote() {
        args.clear();

        args.add("Ceesharp");
        args.add("Major Triad");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).error(errorInvalidNote + "\"Ceesharp\".");
    }

    @Test
    public void playChordOneArgs() {
        args.clear();

        args.add("Minor Triad");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).error(errorInvalidNumArgs);
    }

    @Test
    public void playChordFourArgs() {
        args.clear();

        args.add("C");
        args.add("C");
        args.add("Minor Triad");
        args.add("Major Triad");

        new PlayChord(args).execute(mockEnv);

        verify(mockEnv).error(errorInvalidNumArgs);
    }
}
