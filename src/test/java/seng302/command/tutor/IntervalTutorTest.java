package seng302.command.tutor;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mock;
import seng302.Environment;
import seng302.command.tutor.IntervalTutor;

import java.util.ArrayList;

/**
 * Created by avh17 on 11/05/16.
 */
public class IntervalTutorTest {

    @Mock
    private Environment mockEnv;

    private ArrayList argsList = new ArrayList();

    private String invalidArgumentError = "Invalid argument!";
    private String argumentExplanation = "The Command only accepts arguments in the form 'x' followed by a number";

    @Test
    @SuppressWarnings("unchecked")
    public void executeNumberWithoutIdentifier() {
        argsList.clear();
        argsList.add("10");
        mockEnv = mock(Environment.class);
        new IntervalTutor(argsList).execute(mockEnv);
        verify(mockEnv).error(invalidArgumentError + "\n" + argumentExplanation);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void executeNegativeNumber() {
        argsList.clear();
        argsList.add("-10");
        mockEnv = mock(Environment.class);
        new IntervalTutor(argsList).execute(mockEnv);
        verify(mockEnv).error(invalidArgumentError + "\n" + argumentExplanation);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void executeStringArgument() {
        argsList.clear();
        argsList.add("plzFail");
        mockEnv = mock(Environment.class);
        new IntervalTutor(argsList).execute(mockEnv);
        verify(mockEnv).error(invalidArgumentError + "\n" + argumentExplanation);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void executeStringArgumentTwo() {
        argsList.clear();
        argsList.add("test");
        mockEnv = mock(Environment.class);
        new IntervalTutor(argsList).execute(mockEnv);
        verify(mockEnv).error(invalidArgumentError + "\n" + argumentExplanation);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void executeStringArgumentWithIdentifierX() {
        argsList.clear();
        argsList.add("xtest");
        mockEnv = mock(Environment.class);
        new IntervalTutor(argsList).execute(mockEnv);
        verify(mockEnv).error(invalidArgumentError + "\n" + argumentExplanation);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void executeStringArgumentWithIdentifierXTwo() {
        argsList.clear();
        argsList.add("x10x");
        mockEnv = mock(Environment.class);
        new IntervalTutor(argsList).execute(mockEnv);
        verify(mockEnv).error(invalidArgumentError + "\n" + argumentExplanation);
    }
}