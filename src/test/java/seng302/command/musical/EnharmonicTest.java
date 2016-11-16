package seng302.command.musical;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import seng302.Environment;
import seng302.command.musical.Enharmonic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Tests for the Enharmonic command class.
 *
 * @author wrs35
 */
@RunWith(MockitoJUnitRunner.class)
public class EnharmonicTest {
    @Mock
    private Environment mockEnv = new Environment();

    @Mock
    private Enharmonic enharmonic;

    @Test
    public void testCDown() throws Exception {
        List<String> testArgs = Arrays.asList("-", "C2");
        new Enharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println("B#1");
    }

    @Test
    public void testCUp() throws Exception {
        List<String> testArgs = Arrays.asList("+", "C2");
        new Enharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println("Dbb2");
    }

    @Test
    public void testBdown() throws Exception {
        List<String> testArgs = Arrays.asList("-", "B5");
        new Enharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println("Ax5");
    }

    @Test
    public void testBUp() throws Exception {
        List<String> testArgs = Arrays.asList("+", "B4");
        new Enharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println("Cb5");
    }

    @Test
    public void testFlatUp() throws Exception {
        List<String> testArgs = Arrays.asList("+", "Ab4");
        new Enharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println("There is no enharmonic equivalent for this note");
    }

    @Test
    public void testDoubleFlatUp() throws Exception {
        List<String> testArgs = Arrays.asList("+", "Dbb");
        new Enharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println("There is no enharmonic equivalent for this note");
    }

    @Test
    public void testDoubleDown() throws Exception {
        List<String> testArgs = Arrays.asList("-", "Dbb");
        new Enharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println("C4");
    }

    @Test
    public void testSharpUp() throws Exception {
        List<String> testArgs = Arrays.asList("+", "C#4");
        new Enharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println("Db4");
    }

    @Test
    public void testDoubleSharpUp() throws Exception {
        List<String> testArgs = Arrays.asList("+", "Cx4");
        new Enharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println("D4");
    }

    @Test
    public void testLowerCaseNote() throws Exception {
        List<String> testArgs = Arrays.asList("+", "a");
        new Enharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println("Bbb4");
    }

    @Test
    public void testInvalidDirection() throws Exception {
        List<String> testArgs = Arrays.asList("a", "a");
        new Enharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).error("Arguments are invalid!\nMust be in the form 'enharmonic(+ or -, note)'");
    }

    @Test
    public void testInvalidNote() throws Exception {
        List<String> testArgs = Arrays.asList("+", "$");
        new Enharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).error("Arguments are invalid!\nMust be in the form 'enharmonic(+ or -, note)'");
    }

    @Test
    public void testEmptyArgs() throws Exception {
        List<String> testArgs = Collections.emptyList();
        new Enharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).error("Arguments are invalid!\nMust be in the form 'enharmonic(+ or -, note)'");
    }

}