package seng302.command.play;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import seng302.Environment;
import seng302.util.enumerator.Error;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

/**
 * Tests for the PlayNote command class.
 *
 * @author jps100
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayNoteTest {
    @Mock
    private Environment mockEnv = new Environment();

    final private String notNote = Error.INVALIDNOTE.getError();
    final private String notMidi = Error.INVALIDMIDINOTE.getError();

    @Mock
    private PlayNote playNote;

    @Test
    public void testNoArgs() {
        List<String> testArgs = Arrays.asList();
        new PlayNote(testArgs).execute(mockEnv);
        verify(mockEnv).error("No parameters given. Requires at least one Note value to play");
    }

    @Test
    public void testTooManyArgs() {
        List<String> testArgs = Arrays.asList("60", "100", "100");
        new PlayNote(testArgs).execute(mockEnv);
        verify(mockEnv).error("Incorrect parameters. Requires 1 or 2 parameters");
    }

    @Test
    public void testNoteInvalidMidi() {
        List<String> testArgs = Arrays.asList("130", "100");
        new PlayNote(testArgs).execute(mockEnv);
        verify(mockEnv).error(notMidi);
    }

    @Test
    public void testNoteInvalidClassic() {
        List<String> testArgs = Arrays.asList("x4", "100");
        new PlayNote(testArgs).execute(mockEnv);
        verify(mockEnv).error(notNote);
    }

    @Test
    public void testDurationInvalid() {
        List<String> testArgs = Arrays.asList("60", "-1");
        new PlayNote(testArgs).execute(mockEnv);
        verify(mockEnv).error("Time duration must be between 0-10000 milliseconds");
    }

    @Test
    public void testValidDataPasses() {
        List<String> testArgs = Arrays.asList("60", "100");
        new PlayNote(testArgs).execute(mockEnv);
        verify(mockEnv).println("Playing: 60");
    }
}