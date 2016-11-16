package seng302.command.term;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import seng302.Environment;
import seng302.command.term.TermDefine;
import seng302.command.term.TermLookup;
import seng302.util.TermsUtil;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Term lookup tests.
 *
 * @author plr37, adg62
 */
@RunWith(MockitoJUnitRunner.class)
public class TermLookupTest {
    @Mock
    private Environment mockEnv = new Environment();

    @Before
    public void setUp() {
        TermsUtil.clearAllTerms();
    }

    @Test
    public void languageWithNoArgs() {
        List<String> testArgs = Arrays.asList();
        new TermLookup(testArgs).execute(mockEnv);
        verify(mockEnv).error("Command expected a single term to look up.");
    }

    @Test
    public void languageWithTwoArgs() {
        List<String> testArgs = Arrays.asList("Term name", "second arg");
        new TermLookup(testArgs).execute(mockEnv);
        verify(mockEnv).error("Command expected a single term to look up.");
    }

    @Test
    public void languageOfNotDefined() {
        List<String> testArgs = Arrays.asList("Lento");
        new TermLookup(testArgs).execute(mockEnv);
        verify(mockEnv).error("\"Lento\" is not defined.");
    }

    @Test
    public void languageOfDefined() {
        List<String> testArgs = Arrays.asList("Lento", "Italian", "Slowly", "Tempo");
        new TermDefine(testArgs).execute(mockEnv);
        verify(mockEnv).println("Added \"Lento\" to dictionary.");

        List<String> testArgs1 = Arrays.asList("Lento");
        new TermLookup(testArgs1).execute(mockEnv);
        verify(mockEnv).println("Term: Lento\nLanguage: Italian\nMeaning: Slowly\nCategory: Tempo");
    }
}