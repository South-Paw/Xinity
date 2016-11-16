package seng302.command.tutor;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import seng302.Environment;
import seng302.command.tutor.PitchTutor;

import java.util.Arrays;

/**
 * Created by Adam on 13/04/2016.
 *
 * @author avh17
 */
@RunWith(MockitoJUnitRunner.class)
public class PitchTutorTest {
    @Mock
    private Environment mockEnv;

    @Test
    public void executeInvalidNoteNumber() {
        mockEnv = mock(Environment.class);
        new PitchTutor(Arrays.asList("xtest", "D5", "B")).execute(mockEnv);
        verify(mockEnv).error("The value given for the note pairs parameter was not a number.");
    }

    @Test
    public void executeInvalidNoteNumberTwo() {
        mockEnv = mock(Environment.class);
        new PitchTutor(Arrays.asList("xest", "D5", "B")).execute(mockEnv);
        verify(mockEnv).error("The value given for the note pairs parameter was not a number.");
    }

    @Test
    public void executeInvalidNoteNumberThree() {
        mockEnv = mock(Environment.class);
        new PitchTutor(Arrays.asList("test", "D5", "B")).execute(mockEnv);
        verify(mockEnv).error("The note pairs parameter needs to be in the form 'x' followed by a number.");
    }

    @Test
    public void executeInvalidNoteNumberNoRange() {
        mockEnv = mock(Environment.class);
        new PitchTutor(Arrays.asList("xest")).execute(mockEnv);
        verify(mockEnv).error("The value given for the note pairs parameter was not a number.");
    }

    @Test
    public void executeInvalidNoteNumberFour() {
        mockEnv = mock(Environment.class);
        new PitchTutor(Arrays.asList("10", "c4", "b4")).execute(mockEnv);
        verify(mockEnv).error("The note pairs parameter needs to be in the form 'x' followed by a number.");
    }


    @Test
    public void executeOnlyOneNoteGivenForRange() {
        mockEnv = mock(Environment.class);
        new PitchTutor(Arrays.asList("C4")).execute(mockEnv);
        verify(mockEnv).error("You need to give two notes in the parameters to create a range.");
    }

    @Test
    public void executeInvalidNotePairNumberWithMidiRange() {
        mockEnv = mock(Environment.class);
        new PitchTutor(Arrays.asList("50", "60", "70")).execute(mockEnv);
        verify(mockEnv).error("The note pairs parameter needs to be in the form 'x' followed by a number.");
    }

    @Test
    public void executeInvalidRangeWithMidiNumbers() {
        mockEnv = mock(Environment.class);
        new PitchTutor(Arrays.asList("60")).execute(mockEnv);
        verify(mockEnv).error("You need to give two notes in the parameters to create a range.");
    }

    @Test
    public void executeInvalidNotesInRange() {
        mockEnv = mock(Environment.class);
        new PitchTutor(Arrays.asList("x5", "abc", "dfg")).execute(mockEnv);
        verify(mockEnv).error("One or more of the given notes were not valid.");
    }

    @Test
    public void executeInvalidMidiNotesInRange() {
        mockEnv = mock(Environment.class);
        new PitchTutor(Arrays.asList("x5", "200", "300")).execute(mockEnv);
        verify(mockEnv).error("One or more of the given notes were not valid.");
    }

    @Test
    public void executeOneInvalidMidiInRange() {
        mockEnv = mock(Environment.class);
        new PitchTutor(Arrays.asList("x5", "400", "c4")).execute(mockEnv);
        verify(mockEnv).error("One or more of the given notes were not valid.");
    }

    @Test
    public void executeOneInvalidMidiInRangeTwo() {
        mockEnv = mock(Environment.class);
        new PitchTutor(Arrays.asList("x5", "c5", "500")).execute(mockEnv);
        verify(mockEnv).error("One or more of the given notes were not valid.");
    }

    @Test
    public void executeOneInvalidNoteInRange() {
        mockEnv = mock(Environment.class);
        new PitchTutor(Arrays.asList("x5", "x5", "50")).execute(mockEnv);
        verify(mockEnv).error("One or more of the given notes were not valid.");
    }

    @Test
    public void executeOneInvalidMidiInRangeThree() {
        mockEnv = mock(Environment.class);
        new PitchTutor(Arrays.asList("x5", "c5", "-50")).execute(mockEnv);
        verify(mockEnv).error("One or more of the given notes were not valid.");
    }

}
