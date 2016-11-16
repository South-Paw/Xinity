package seng302.command.term;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import seng302.Environment;
import seng302.command.term.TermDefine;
import seng302.command.term.TermRemove;
import seng302.util.TermsUtil;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Term remove tests.
 *
 * @author plr37, adg62
 */
@RunWith(MockitoJUnitRunner.class)
public class TermRemoveTest {
    @Mock
    private Environment mockEnv = new Environment();

    @Before
    public void setUp() {
        TermsUtil.clearAllTerms();
    }

    @Test
    public void meaningWithNoArgs() {
        List<String> testArgs = Arrays.asList();
        new TermRemove(testArgs).execute(mockEnv);
        verify(mockEnv).error("Command expected a single term to remove.");
    }

    @Test
    public void meaningWithTwoArgs() {
        List<String> testArgs = Arrays.asList("Term name", "second arg");
        new TermRemove(testArgs).execute(mockEnv);
        verify(mockEnv).error("Command expected a single term to remove.");
    }

    @Test
    public void meaningOfNotDefined() {
        List<String> testArgs = Arrays.asList("Lento");
        new TermRemove(testArgs).execute(mockEnv);
        verify(mockEnv).error("\"Lento\" is not defined. Nothing removed.");
    }

    @Test
    public void meaningOfDefined() {
        List<String> testArgs = Arrays.asList("Lento", "Italian", "Slowly", "Tempo");
        new TermDefine(testArgs).execute(mockEnv);
        verify(mockEnv).println("Added \"Lento\" to dictionary.");

        List<String> testArgs1 = Arrays.asList("Lento");
        new TermRemove(testArgs1).execute(mockEnv);
        verify(mockEnv).println("\"Lento\" was removed.");
    }
}
