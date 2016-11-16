package seng302.command.tutor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import seng302.Environment;
import seng302.command.tutor.ScaleTutor;
import seng302.util.enumerator.Error;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for the Scale Tutor command.
 *
 * @author ljm163
 */
@RunWith(MockitoJUnitRunner.class)
public class ScaleTutorTest {
    @Mock
    private Environment mockEnv;

    private String invalidArgs = Error.SCALETUTORINVALIDARGS.getError();

    @Test
    public void executeInvalidScaleNumber() {
        mockEnv = mock(Environment.class);
        new ScaleTutor(Arrays.asList("xtest", "1", "up")).execute(mockEnv);
        verify(mockEnv).error(invalidArgs);
    }

    @Test
    public void executeInvalidScaleNumberTwo() {
        mockEnv = mock(Environment.class);
        new ScaleTutor(Arrays.asList("xest", "1", "up")).execute(mockEnv);
        verify(mockEnv).error(invalidArgs);
    }

    @Test
    public void executeInvalidScaleNumberThree() {
        mockEnv = mock(Environment.class);
        new ScaleTutor(Arrays.asList("test", "1", "up")).execute(mockEnv);
        verify(mockEnv).error(invalidArgs);
    }

    @Test
    public void executeInvalidScaleNumberOnly() {
        mockEnv = mock(Environment.class);
        new ScaleTutor(Arrays.asList("xest")).execute(mockEnv);
        verify(mockEnv).error(invalidArgs);
    }

    @Test
    public void executeInvalidScaleNumberFour() {
        mockEnv = mock(Environment.class);
        new ScaleTutor(Arrays.asList("10", "2", "updown")).execute(mockEnv);
        verify(mockEnv).error(invalidArgs);
    }


    @Test
    public void executeInvalidOneParameter() {
        mockEnv = mock(Environment.class);
        new ScaleTutor(Arrays.asList("Z")).execute(mockEnv);
        verify(mockEnv).error(invalidArgs);
    }

    @Test
    public void executeInvalidTwoParameters() {
        mockEnv = mock(Environment.class);
        new ScaleTutor(Arrays.asList("y", "updow")).execute(mockEnv);
        verify(mockEnv).error(invalidArgs);
    }

    @Test
    public void executeInvalidDirection() {
        mockEnv = mock(Environment.class);
        new ScaleTutor(Arrays.asList("x4", "u", "1")).execute(mockEnv);
        verify(mockEnv).error(invalidArgs);
    }

    @Test
    public void executeInvalidOctaves() {
        mockEnv = mock(Environment.class);
        new ScaleTutor(Arrays.asList("x5", "down", "a")).execute(mockEnv);
        verify(mockEnv).error(invalidArgs);
    }

    @Test
    public void moreThan4Args() {
        mockEnv = mock(Environment.class);
        new ScaleTutor(Arrays.asList("x5", "down", "5", "swing", "fail")).execute(mockEnv);
        verify(mockEnv).error(invalidArgs);
    }

    @Test
    public void incorrectPlayStyle() {
        mockEnv = mock(Environment.class);
        new ScaleTutor(Arrays.asList("x5", "down", "5", "fail")).execute(mockEnv);
        verify(mockEnv).error(invalidArgs);
    }
}

