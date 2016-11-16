package seng302.command.term;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import seng302.Environment;
import seng302.command.term.TermDefine;
import seng302.workspace.Project;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Term define tests.
 *
 * @author plr37, adg62
 */
@RunWith(MockitoJUnitRunner.class)
public class TermDefineTest {
    @Mock
    private Environment mockEnv = new Environment();

    @Before
    public void setUp() {
        Project.getInstance().resetTerms();
    }

    @Test
    public void defineWithNoArgs() {
        List<String> testArgs = Arrays.asList();
        new TermDefine(testArgs).execute(mockEnv);
        verify(mockEnv).error("Command expected 4 arguments. (Term, Language, Meaning, Category).");
    }

    @Test
    public void defineWithTwoArgs() {
        List<String> testArgs = Arrays.asList("term name", "language");
        new TermDefine(testArgs).execute(mockEnv);
        verify(mockEnv).error("Command expected 4 arguments. (Term, Language, Meaning, Category).");
    }

    @Test
    public void defineNewTerm() {
        List<String> testArgs = Arrays.asList("Lento", "Italian", "Slowly", "Tempo");
        new TermDefine(testArgs).execute(mockEnv);
        verify(mockEnv).println("Added \"Lento\" to dictionary.");
    }

    @Test
    public void updateTerm() {
        List<String> testArgs = Arrays.asList("Lento", "Italian", "Slowly", "Tempo");
        new TermDefine(testArgs).execute(mockEnv);

        verify(mockEnv).println("Added \"Lento\" to dictionary.");

        List<String> testArgs2 = Arrays.asList("Lento", "Italian", "Slowly writing tests", "Tempo");
        new TermDefine(testArgs2).execute(mockEnv);

        verify(mockEnv).println("Updated \"Lento\" in dictionary.");
    }
}
