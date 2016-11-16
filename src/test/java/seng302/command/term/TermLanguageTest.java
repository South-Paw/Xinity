package seng302.command.term;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import seng302.Environment;
import seng302.command.term.TermDefine;
import seng302.command.term.TermLanguage;
import seng302.util.TermsUtil;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Term language tests.
 *
 * @author plr37, adg62
 */
@RunWith(MockitoJUnitRunner.class)
public class TermLanguageTest {
    @Mock
    private Environment mockEnv = new Environment();

    @Before
    public void setUp() {
        TermsUtil.clearAllTerms();
    }

    @Test
    public void languageWithNoArgs() {
        List<String> testArgs = Arrays.asList();
        new TermLanguage(testArgs).execute(mockEnv);
        verify(mockEnv).error("Command expected a single term to look up.");
    }

    @Test
    public void languageWithTwoArgs() {
        List<String> testArgs = Arrays.asList("Term name", "second arg");
        new TermLanguage(testArgs).execute(mockEnv);
        verify(mockEnv).error("Command expected a single term to look up.");
    }

    @Test
    public void languageOfNotDefined() {
        List<String> testArgs = Arrays.asList("Lento");
        new TermLanguage(testArgs).execute(mockEnv);
        verify(mockEnv).error("\"Lento\" is not defined.");
    }

    @Test
    public void languageOfDefined() {
        List<String> testArgs = Arrays.asList("Lento", "Italian", "Slowly", "Tempo");
        new TermDefine(testArgs).execute(mockEnv);
        verify(mockEnv).println("Added \"Lento\" to dictionary.");

        List<String> testArgs1 = Arrays.asList("Lento");
        new TermLanguage(testArgs1).execute(mockEnv);
        verify(mockEnv).println("The source language of \"Lento\" is: \"Italian\"");
    }
}