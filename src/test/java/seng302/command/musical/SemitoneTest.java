package seng302.command.musical;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import seng302.Environment;
import seng302.command.musical.Semitone;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;


/**
 * Set tempo tests.
 *
 * @author ljm163, adg62
 */
@RunWith(MockitoJUnitRunner.class)
public class SemitoneTest {
    @Mock
    private Environment mockEnv = new Environment();

    @Mock
    private Semitone semitone;

    @Test
    public void test1C() throws Exception {
        List<String> testArgs = Arrays.asList("1", "C");
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).println("C#4");
    }

    @Test
    public void testNeg1C() throws Exception {
        List<String> testArgs = Arrays.asList("-1", "C");
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).println("B3");
    }

    @Test
    public void test2G() throws Exception {
        List<String> testArgs = Arrays.asList("2", "G");
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).println("A4");
    }

    @Test
    public void testNeg2G() throws Exception {
        List<String> testArgs = Arrays.asList("-2", "G");
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).println("F4");
    }

    @Test
    public void test3FSharp() throws Exception {
        List<String> testArgs = Arrays.asList("3", "F#");
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).println("A4");
    }

    @Test
    public void testNeg3FSharp() throws Exception {
        List<String> testArgs = Arrays.asList("-3", "F#");
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).println("Eb4");
    }

    @Test
    public void test0E() throws Exception {
        List<String> testArgs = Arrays.asList("0", "E");
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).println("E4");
    }

    @Test
    public void test0ASharp() throws Exception {
        List<String> testArgs = Arrays.asList("0", "A#");
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).println("A#4");
    }

    @Test
    public void test1LowerA() throws Exception {
        List<String> testArgs = Arrays.asList("1", "a");
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).println("A#4");
    }

    @Test
    public void test1LowerB() throws Exception {
        List<String> testArgs = Arrays.asList("1", "b");
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).println("C5");
    }

    @Test
    public void test4G5() throws Exception {
        List<String> testArgs = Arrays.asList("4", "G#5");
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).println("C6");
    }

    @Test
    public void testNeg4G5() throws Exception {
        List<String> testArgs = Arrays.asList("-4", "G5");
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).println("Eb5");
    }

    @Test
    public void testInvalidIncrementLowerA() throws Exception {
        List<String> testArgs = Arrays.asList("a", "a");
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).error(
                "Invalid arguments. Semitone expects input in the form semitone(1, C4).");
    }

    @Test
    public void testInvalidNoteLowerGG() throws Exception {
        List<String> testArgs = Arrays.asList("1", "gg");
        new Semitone(testArgs).execute(mockEnv);

        verify(mockEnv).error(
                "Invalid arguments. Semitone expects input in the form semitone(1, C4).");
    }

    @Test
    public void testInvalidNote$() throws Exception {
        List<String> testArgs = Arrays.asList("1", "$");
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).error(
                "Invalid arguments. Semitone expects input in the form semitone(1, C4).");
    }

    @Test
    public void testEmptyArgs() throws Exception {
        List<String> testArgs = Collections.emptyList();
        new Semitone(testArgs).execute(mockEnv);
        verify(mockEnv).error(
                "No arguments given. Semitone expects input in the form semitone(1, C4).");
    }


}