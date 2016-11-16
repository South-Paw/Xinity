package seng302.command.term;

import org.junit.Test;
import org.mockito.Mock;
import seng302.Environment;
import seng302.command.tutor.TermTutor;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * The test class for the term tutor.
 */
public class TermTutorTest {

    @Mock
    private Environment mockEnv;

    @Mock
    private TermTutor termTutor;

    @Test
    public void noNumberIdentifier() {
        mockEnv = mock(Environment.class);
        new TermTutor(Arrays.asList("10")).execute(mockEnv);
        verify(mockEnv).error("The parameter needs to be in the form 'x' followed by a number.");
    }

    @Test
    public void noNumberIdentifierWithWord() {
        mockEnv = mock(Environment.class);
        new TermTutor(Arrays.asList("fail")).execute(mockEnv);
        verify(mockEnv).error("The parameter needs to be in the form 'x' followed by a number.");
    }

    @Test
    public void wordAfterIdentifier() {
        mockEnv = mock(Environment.class);
        new TermTutor(Arrays.asList("xfail")).execute(mockEnv);
        verify(mockEnv).error("The value given for parameter was not a number.");
    }

    @Test
    public void invalidNumberAfterIdentifier() {
        mockEnv = mock(Environment.class);
        new TermTutor(Arrays.asList("x0")).execute(mockEnv);
        verify(mockEnv).error("The number of tests to run must be greater than 10.");
    }

    @Test
    public void invalidNumberAfterIdentifierTwo() {
        mockEnv = mock(Environment.class);
        new TermTutor(Arrays.asList("x-1")).execute(mockEnv);
        verify(mockEnv).error("The number of tests to run must be greater than 10.");
    }

    @Test
    public void tooManyArguments() {
        mockEnv = mock(Environment.class);
        new TermTutor(Arrays.asList("x10", "C", "B")).execute(mockEnv);
        verify(mockEnv).error("Too many arguments! The command will only accept 1.");
    }

    @Test
    public void tooManyArgumentsTwo() {
        mockEnv = mock(Environment.class);
        new TermTutor(Arrays.asList("10", "x10", "x10")).execute(mockEnv);
        verify(mockEnv).error("Too many arguments! The command will only accept 1.");
    }
}
