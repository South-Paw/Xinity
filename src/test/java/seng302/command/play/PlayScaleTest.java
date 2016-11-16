package seng302.command.play;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import seng302.Environment;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Tests for the PlayScale command class.
 *
 * With the help of the scale logic and manual testing a number of scales that are marked "invalid"
 * have been found. The "invalid" scales are marked this as they cannot exist with just double flats/sharps.
 * The following scales that don't exist are:
 *
 * Major; D##4, E##4, G##4, A##4, B##4, Fbb
 * Minor; E##4, B##4, Fbb
 *
 * @author wrs35, ljm163, plr37
 */

@RunWith(MockitoJUnitRunner.class)
public class PlayScaleTest {

    @Mock
    private Environment mockEnv = new Environment();
    private final String notNote = Error.INVALIDNOTE.getError();
    private final String notScale = Error.FINDSCALENOSCALE.getError();
    private final String notScalePentatonic = Error.INVALIDNOCORRESPONDINGPENTATONIC.getError();
    private final String notScaleBlues = Error.INVALIDNOCORRESPONDINGBLUES.getError();
    private final String noMode = Error.NOMODE.getError();
    List<String> testArgs = new ArrayList();

    //Invalid arguments
    @Test
    public void testNoArgs() {
        List<String> testArgs = Arrays.asList();
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error("Invalid number of arguments! (got 0)");
    }

    @Test
    public void testOneArg() {
        List<String> testArgs = Arrays.asList("C");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error("Invalid number of arguments! (got 1)");
    }

    @Test
    public void testSixArgs() {
        List<String> testArgs = Arrays.asList("C","C","C","C","C","C");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error("Invalid number of arguments! (got 6)");
    }

    @Test
    public void testInvalidStartNote() {
        List<String> testArgs = Arrays.asList("K","Major");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notNote);
    }

    @Test
    public void testInvalidScale() {
        List<String> testArgs = Arrays.asList("C","invalid");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error("Enter a valid scale type; e.g. 'major' or 'minor'.");
    }

    @Test
    public void testThreeArgsWithInvalidThirdArg() {
        List<String> testArgs = Arrays.asList("C","Major","invalid");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error("Third argument must be a direction or the number of octaves (got invalid).");
    }

    @Test
    public void testFourArgsWithInvalidThirdArg() {
        List<String> testArgs = Arrays.asList("C","Major","invalid","2");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error("Third argument must be a direction (got invalid).");
    }

    @Test
    public void testFourArgsWithInvalidFourthArg() {
        List<String> testArgs = Arrays.asList("C","Major","up","invalid");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error("Fourth argument must be the number of octaves (got invalid).");
    }

    @Test
    public void testFourArgsWithInvalidThirdArgFourthArg() {
        List<String> testArgs = Arrays.asList("C","Minor","2","down");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error("Third argument must be a direction (got 2).");
    }

    //Major Scale Tests

    //valid
    @Test
    public void testCMajorUp() {
        List<String> testArgs = Arrays.asList("C","Major","up");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C, D, E, F, G, A, B, C");
    }

    @Test
    public void testCMajor1() {
        List<String> testArgs = Arrays.asList("C4","Major","1");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, E4, F4, G4, A4, B4, C5");
    }

    @Test
    public void testCMajorUp1() {
        List<String> testArgs = Arrays.asList("C4","Major","up","1");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, E4, F4, G4, A4, B4, C5");
    }

    @Test
    public void testCMajor2() {
        List<String> testArgs = Arrays.asList("C4","Major","2");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, E4, F4, G4, A4, B4, C5, D5, E5, F5, G5, A5, B5, C6");
    }

    @Test
    public void testCMajorBoth2() {
        List<String> testArgs = Arrays.asList("C4","Major","Both","2");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, E4, F4, G4, A4, B4, C5, D5, E5, F5, G5, A5, B5, C6, " +
                "C6, B5, A5, G5, F5, E5, D5, C5, B4, A4, G4, F4, E4, D4, C4");
    }

    //Pentatonic Scales Test
    @Test
    public void testCPentaMajorUp() {
        List<String> testArgs = Arrays.asList("C4","Pentatonic Major","up");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, E4, G4, A4, C5");
    }

    @Test
    public void testC5PentaMajorUp() {
        List<String> testArgs = Arrays.asList("C5","Pentatonic Major","up");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C5, D5, E5, G5, A5, C6");
    }

    @Test
    public void testEbPentaMajorUp() {
        List<String> testArgs = Arrays.asList("Eb4","Pentatonic Major","up");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Eb4, F4, G4, Bb4, C5, Eb5");
    }

    @Test
    public void testEbPentaMajorDown() {
        List<String> testArgs = Arrays.asList("Eb4","Pentatonic Major","down");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Eb5, C5, Bb4, G4, F4, Eb4");
    }

    @Test
    public void testEbPentaMajorBoth() {
        List<String> testArgs = Arrays.asList("Eb4","Pentatonic Major","Both");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Eb4, F4, G4, Bb4, C5, Eb5, C5, Bb4, G4, F4, Eb4");
    }

    @Test
    public void testCSharpPentaMajorBoth2() {
        List<String> testArgs = Arrays.asList("C#4","Pentatonic Major","Both","2");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C#4, D#4, F4, G#4, A#4, C#5, D#5, F5, G#5, A#5, C#6, A#5, G#5,"
                + " F5, D#5, C#5, A#4, G#4, F4, D#4, C#4");
    }

    @Test
    public void testD6PentaMinorUp2() {
        List<String> testArgs = Arrays.asList("D6","Pentatonic Minor","up", "2");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("D6, F6, G6, A6, C7, D7, F7, G7, A7, C8, D8");
    }

    @Test
    public void testC4PentaMinorUp2() {
        List<String> testArgs = Arrays.asList("C4","Pentatonic Major","up", "2");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, E4, G4, A4, C5, D5, E5, G5, A5, C6");
    }

    @Test
    public void testC9PentaMajor() {
        List<String> testArgs = Arrays.asList("C9","Pentatonic Major");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C9, D9, E9, G9 - The rest of the scale cannot be mapped to midi");
    }

    @Test
    public void testBSharp6PentaMajor() throws Exception {
        List<String> testArgs = Arrays.asList("B#6", "Penta Major");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScalePentatonic);
    }


    @Test
    public void CbMajorPenta() throws Exception {
        List<String> testArgs = Arrays.asList("Cb", "pentatonic major");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Cb, Db, Eb, Gb, Ab, Cb");
    }


    // blues scale - invalid
    @Test
    public void testBSharpBlues() {
        List<String> testArgs = Arrays.asList("B#","Blues");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScaleBlues);
    }

    @Test
    public void testCbBlues() {
        List<String> testArgs = Arrays.asList("Cb","Blues");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Cb, D, E, F, Gb, A, Cb");
    }

    @Test
    public void testESharpBlues() {
        List<String> testArgs = Arrays.asList("E#","Blues");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScaleBlues);
    }

    @Test
    public void testBbbSharpBlues() {
        List<String> testArgs = Arrays.asList("Bbb","Blue");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScaleBlues);
    }

    @Test
    public void testGxBlues() {
        List<String> testArgs = Arrays.asList("Gx","Blues");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScaleBlues);
    }

    @Test
    public void testC9Blues() {
        List<String> testArgs = Arrays.asList("C9","blues");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C9, Eb9, F9, Gb9, G9 - The rest of the scale cannot be mapped to midi");
    }


    //valid - going out of range
    @Test
    public void testC9Major() {
        List<String> testArgs = Arrays.asList("C9","Major");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C9, D9, E9, F9, G9 - The rest of the scale cannot be mapped to midi");
    }

    @Test
    public void testC9MajorBoth2() {
        List<String> testArgs = Arrays.asList("C9","Major","Both","2");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println(
                "C9, D9, E9, F9, G9, G9, F9, E9, D9, C9 - The rest of the scale cannot be mapped to midi");
    }

    @Test
    public void testCMajor() {
        List<String> testArgs = Arrays.asList("C4","Major");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, E4, F4, G4, A4, B4, C5");
    }

    //invalid
    @Test
    public void testInvalidGNeg3Major() {
        List<String> testArgs = Arrays.asList("G-3","Major");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error("Invalid note. Not within the midi range (0 - 127).");
    }

    @Test
    public void testInvalidC10Major() {
        List<String> testArgs = Arrays.asList("C10","Major");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error("Invalid note. Not within the midi range (0 - 127).");
    }

    //invalid - scale doesn't exist
    @Test
    public void testInvalidDDoubleSharpMajor() {
        List<String> testArgs = Arrays.asList("D##","Major");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScale);
    }

    @Test
    public void testInvalidDxMajor() {
        List<String> testArgs = Arrays.asList("Dx","Major");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScale);
    }

    @Test
    public void testInvalidGDoubleSharpMajor() {
        List<String> testArgs = Arrays.asList("G##","Major");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScale);
    }

    @Test
    public void testInvalidGxMajor() {
        List<String> testArgs = Arrays.asList("Gx","Major");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScale);
    }

    @Test
    public void testInvalidFbbMajor() {
        List<String> testArgs = Arrays.asList("Fbb","Major");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScale);
    }

    // Natural Minor Scale Tests (Minor = 2 1 2 2 1 2 2)

    //valid
    @Test
    public void testCNaturalMinor() {
        List<String> testArgs = Arrays.asList("C4", "Natural Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, Eb4, F4, G4, Ab4, Bb4, C5");
    }

    @Test
    public void testCMinor() {
        List<String> testArgs = Arrays.asList("C4", "Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, Eb4, F4, G4, Ab4, Bb4, C5");
    }

    @Test
    public void testCx4Minor() {
        List<String> testArgs = Arrays.asList("Cx4", "Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Cx4, Dx4, E#4, Fx4, Gx4, A#4, B#4, Cx5");
    }

    @Test
    public void testCDoubleSharpMinor() {
        List<String> testArgs = Arrays.asList("C##4", "Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Cx4, Dx4, E#4, Fx4, Gx4, A#4, B#4, Cx5");
    }

    @Test
    public void testCNegative1NaturalMinor() {
        List<String> testArgs = Arrays.asList("C-1", "Natural Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C-1, D-1, Eb-1, F-1, G-1, Ab-1, Bb-1, C0");
    }

    @Test
    public void testCMinorBoth1() {
        List<String> testArgs = Arrays.asList("C4","Minor","both","1");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, Eb4, F4, G4, Ab4, Bb4, C5, C5, Bb4, Ab4, G4, F4, Eb4, D4, C4");
    }

    @Test
    public void testDMinor2() {
        List<String> testArgs = Arrays.asList("D4","Minor","2");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("D4, E4, F4, G4, A4, Bb4, C5, D5, E5, F5, G5, A5, Bb5, C6, D6");
    }

    @Test
    public void testF5MinorDown() {
        List<String> testArgs = Arrays.asList("F5","Minor","down");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("F6, Eb6, Db6, C6, Bb5, Ab5, G5, F5");
    }

    @Test
    public void testAMinorBoth2() {
        List<String> testArgs = Arrays.asList("A4","Minor","both","2");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("A4, B4, C5, D5, E5, F5, G5, A5, B5, C6, D6, E6, F6, G6, A6, " +
                "A6, G6, F6, E6, D6, C6, B5, A5, G5, F5, E5, D5, C5, B4, A4");
    }

    //valid - going out of range
    @Test
    public void testE9MinorBoth() {
        List<String> testArgs = Arrays.asList("E9","Minor","both");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("E9, F#9, G9, G9, F#9, E9 - The rest of the scale cannot be mapped to midi");
    }

    @Test
    public void testE9Minor() {
        List<String> testArgs = Arrays.asList("E9","Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("E9, F#9, G9 - The rest of the scale cannot be mapped to midi");
    }

    @Test
    public void testG9Minor() {
        List<String> testArgs = Arrays.asList("G9", "Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("G9 - The rest of the scale cannot be mapped to midi");
    }

    //invalid
    @Test
    public void testInvalidCNeg2Minor() {
        List<String> testArgs = Arrays.asList("C-2", "Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error("Invalid note. Not within the midi range (0 - 127).");
    }

    @Test
    public void testInvalidG10Minor() {
        List<String> testArgs = Arrays.asList("G10", "Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error("Invalid note. Not within the midi range (0 - 127).");
    }

    //invalid - scale doesn't exist
    @Test
    public void testInvalidEDoubleSharpMinor() {
        List<String> testArgs = Arrays.asList("E##","Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScale);
    }

    @Test
    public void testInvalidExMinor() {
        List<String> testArgs = Arrays.asList("Ex","Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScale);
    }

    @Test
    public void testInvalidBDoubleSharpMinor() {
        List<String> testArgs = Arrays.asList("B##","Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScale);
    }

    @Test
    public void testInvalidBxMinor() {
        List<String> testArgs = Arrays.asList("Bx","Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScale);
    }

    @Test
    public void testInvalidFbbMinor() {
        List<String> testArgs = Arrays.asList("Fbb","Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(notScale);
    }

    @Test
    public void playSwingScale3Args() {
        List<String> testArgs = Arrays.asList("c4","major","swing");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, E4, F4, G4, A4, B4, C5");
    }

    @Test
    public void playStraightScale3Args() {
        List<String> testArgs = Arrays.asList("c4","major","straight");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, E4, F4, G4, A4, B4, C5");
    }
    @Test
    public void playSwingScale3ArgsCapital() {
        List<String> testArgs = Arrays.asList("c4","major","Swing");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, E4, F4, G4, A4, B4, C5");
    }

    @Test
    public void playStraightScale3ArgsCapital() {
        List<String> testArgs = Arrays.asList("c4","major","Straight");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, E4, F4, G4, A4, B4, C5");
    }

    @Test
    public void playSwingScale4Args() {
        List<String> testArgs = Arrays.asList("d4","minor","up","swing");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("D4, E4, F4, G4, A4, Bb4, C5, D5");
    }

    @Test
    public void playStraightScale4Args() {
        List<String> testArgs = Arrays.asList("d4","minor","up","straight");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("D4, E4, F4, G4, A4, Bb4, C5, D5");
    }

    @Test
    public void playSwingScale5Args() {
        List<String> testArgs = Arrays.asList("a4","major","down","1","swing");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("A5, G#5, F#5, E5, D5, C#5, B4, A4");
    }

    @Test
    public void playStraightScale5Args() {
        List<String> testArgs = Arrays.asList("a4","major","down","1","straight");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("A5, G#5, F#5, E5, D5, C#5, B4, A4");
    }

    @Test
    public void playSwingInvalidArg() {
        List<String> testArgs = Arrays.asList("a","major","down","1","invalid");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(String.format(Error.PLAYSCALEFIFTHARG.getError(), "invalid"));
    }

    // modes
    @Test
    public void playCIonian() {
        List<String> testArgs = Arrays.asList("C4","Ionian");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, E4, F4, G4, A4, B4, C5");
    }

    // MELODIC MINOR SCALES

    @Test
    public void testCMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("C4");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, Eb4, F4, G4, A4, B4, C5");
    }

    @Test
    public void testCSharpMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("C#");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C#, D#, E, F#, G#, A#, B#, C#");
    }

    @Test
    public void testDbMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("Db");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Db, Eb, Fb, Gb, Ab, Bb, C, Db");
    }

    @Test
    public void testDMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("D4");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("D4, E4, F4, G4, A4, B4, C#5, D5");
    }

    @Test
    public void testDSharpMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("D#");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("D#, E#, F#, G#, A#, B#, Cx, D#");
    }

    @Test
    public void testEbMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("Eb");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Eb, F, Gb, Ab, Bb, C, D, Eb");
    }

    @Test
    public void testEMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("E");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("E, F#, G, A, B, C#, D#, E");
    }

    @Test
    public void testESharpMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("E#4");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("E#4, Fx4, G#4, A#4, B#4, Cx5, Dx5, E#5");
    }

    @Test
    public void testFbMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("Fb4");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Fb4, Gb4, Abb4, Bbb4, Cb5, Db5, Eb5, Fb5");
    }

    @Test
    public void testFMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("F");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("F, G, Ab, Bb, C, D, E, F");
    }

    @Test
    public void testFSharpMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("F#4");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("F#4, G#4, A4, B4, C#5, D#5, E#5, F#5");
    }

    @Test
    public void testGbMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("Gb4");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Gb4, Ab4, Bbb4, Cb5, Db5, Eb5, F5, Gb5");
    }

    @Test
    public void testGMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("G");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("G, A, Bb, C, D, E, F#, G");
    }

    @Test
    public void testGSharpMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("G#");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("G#, A#, B, C#, D#, E#, Fx, G#");
    }

    @Test
    public void testAbMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("Ab");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Ab, Bb, Cb, Db, Eb, F, G, Ab");
    }

    @Test
    public void testAMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("A");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("A, B, C, D, E, F#, G#, A");
    }

    @Test
    public void testASharpMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("A#4");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("A#4, B#4, C#5, D#5, E#5, Fx5, Gx5, A#5");
    }

    @Test
    public void testBbMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("Bb");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Bb, C, Db, Eb, F, G, A, Bb");
    }

    @Test
    public void testBMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("B4");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("B4, C#5, D5, E5, F#5, G#5, A#5, B5");
    }

    @Test
    public void testBSharpMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("B#4");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("B#4, Cx5, D#5, E#5, Fx5, Gx5, Ax5, B#5");
    }

    @Test
    public void testCbMelodicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("Cb");
        testArgs.add("Melodic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Cb, Db, Ebb, Fb, Gb, Ab, Bb, Cb");
    }

    // harmonic minor scales

    @Test
    public void testC4HarmonicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("C4");
        testArgs.add("Harmonic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, D4, Eb4, F4, G4, Ab4, B4, C5");
    }

    @Test
    public void testC9HarmonicMinor() throws Exception {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("C9");
        testArgs.add("Harmonic Minor");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C9, D9, Eb9, F9, G9 - The rest of the scale cannot be mapped to midi");
    }

    @Test
    public void DDorianB2() throws Exception {
        testArgs.clear();
        testArgs.add("D");
        testArgs.add("Dorian b2");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("D, Eb, F, G, A, B, C, D");
    }

    @Test
    public void A4Mixolydianb6() throws Exception {
        testArgs.clear();
        testArgs.add("A4");
        testArgs.add("Mixolydian b6");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("A4, B4, C#5, D5, E5, F5, G5, A5");
    }

    @Test
    public void D4DorianB2() throws Exception {
        testArgs.clear();
        testArgs.add("D4");
        testArgs.add("Dorian b2");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("D4, Eb4, F4, G4, A4, B4, C5, D5");
    }

    @Test
    public void D9DorianB2() throws Exception {
        testArgs.clear();
        testArgs.add("D9");
        testArgs.add("Dorian b2");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("D9, Eb9, F9, G9 - The rest of the scale cannot be mapped to midi");
    }

    @Test
    public void FLydianDominant() throws Exception {
        testArgs.clear();
        testArgs.add("F");
        testArgs.add("Lydian Dominant");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("F, G, A, B, C, D, Eb, F");
    }

    @Test
    public void C4Altered() throws Exception {
        testArgs.clear();
        testArgs.add("C4");
        testArgs.add("Altered");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4, Db4, Eb4, Fb4, Gb4, Ab4, Bb4, C5");
    }

    @Test
    public void BAltered() throws Exception {
        testArgs.clear();
        testArgs.add("B");
        testArgs.add("Altered");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("B, C, D, Eb, F, G, A, B");
    }

    @Test
    public void BbAltered() throws Exception {
        testArgs.clear();
        testArgs.add("Bb");
        testArgs.add("Altered");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Bb, Cb, Db, Ebb, Fb, Gb, Ab, Bb");
    }

    @Test
    public void AbbAltered() throws Exception {
        testArgs.clear();
        testArgs.add("Abb");
        testArgs.add("Altered");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).error(noMode);
    }

    @Test
    public void AbAltered() throws Exception {
        testArgs.clear();
        testArgs.add("Ab");
        testArgs.add("Altered");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Ab, Bbb, Cb, Dbb, Ebb, Fb, Gb, Ab");
    }

    @Test
    public void EbbMixolydianb6() throws Exception {
        testArgs.clear();
        testArgs.add("Ebb");
        testArgs.add("Mixolydian b6");
        new PlayScale(testArgs).execute(mockEnv);
        verify(mockEnv).println("Ebb, Fb, Gb, Abb, Bbb, Cbb, Dbb, Ebb");
    }

}
