package seng302.command.musical;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import seng302.Environment;
import seng302.command.musical.SimpleEnharmonic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Simple Enharmonic Command tests.
 *
 * @author avh17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleEnharmonicTest {
    @Mock private Environment mockEnv;
    @Mock private SimpleEnharmonic enharmonicSimple;

    @Test
    public void testAb() throws Exception {
        String expectedOutput =  "G#4";
        List<String> testArgs = Collections.singletonList("Ab");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testASharp() throws Exception {
        String expectedOutput =  "Bb4";
        List<String> testArgs = Collections.singletonList("A#");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testB() throws Exception {
        String expectedOutput =  "Cb5";
        List<String> testArgs = Collections.singletonList("B");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testBb() throws Exception {
        String expectedOutput =  "A#4";
        List<String> testArgs = Collections.singletonList("Bb");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testBSharp() throws Exception {
        String expectedOutput =  "C5";
        List<String> testArgs = Collections.singletonList("B#");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testC() throws Exception {
        String expectedOutput =  "B#3";
        List<String> testArgs = Collections.singletonList("C");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testCb() throws Exception {
        String expectedOutput =  "B3";
        List<String> testArgs = Collections.singletonList("Cb");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testCSharp() throws Exception {
        String expectedOutput =  "Db4";
        List<String> testArgs = Collections.singletonList("C#");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testDb() throws Exception {
        String expectedOutput =  "C#4";
        List<String> testArgs = Collections.singletonList("Db");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testDSharp() throws Exception {
        String expectedOutput =  "Eb4";
        List<String> testArgs = Collections.singletonList("D#");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testE() throws Exception {
        String expectedOutput =  "Fb4";
        List<String> testArgs = Collections.singletonList("E");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testEb() throws Exception {
        String expectedOutput =  "D#4";
        List<String> testArgs = Collections.singletonList("Eb");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testESharp() throws Exception {
        String expectedOutput =  "F4";
        List<String> testArgs = Collections.singletonList("E#");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testF() throws Exception {
        String expectedOutput =  "E#4";
        List<String> testArgs = Collections.singletonList("F");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testFb() throws Exception {
        String expectedOutput =  "E4";
        List<String> testArgs = Collections.singletonList("Fb");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testFSharp() throws Exception {
        String expectedOutput =  "Gb4";
        List<String> testArgs = Collections.singletonList("F#");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testGb() throws Exception {
        String expectedOutput =  "F#4";
        List<String> testArgs = Collections.singletonList("Gb");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testGSharp() throws Exception {
        String expectedOutput =  "Ab4";
        List<String> testArgs = Collections.singletonList("G#");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testA() throws Exception {
        String expectedOutput =  "There is no simple enharmonic equivalent for this note";
        List<String> testArgs = Collections.singletonList("A");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testD() throws Exception {
        String expectedOutput =  "There is no simple enharmonic equivalent for this note";
        List<String> testArgs = Collections.singletonList("D");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testG() throws Exception {
        String expectedOutput =  "There is no simple enharmonic equivalent for this note";
        List<String> testArgs = Collections.singletonList("G");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testA5() throws Exception {
        String expectedOutput =  "There is no simple enharmonic equivalent for this note";
        List<String> testArgs = Collections.singletonList("A5");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testD4() throws Exception {
        String expectedOutput =  "There is no simple enharmonic equivalent for this note";
        List<String> testArgs = Collections.singletonList("D4");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void testGNegative1() throws Exception {
        String expectedOutput =  "There is no simple enharmonic equivalent for this note";
        List<String> testArgs = Collections.singletonList("G-1");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).println(expectedOutput);
    }

    @Test
    public void test$$$() throws Exception {
        String expectedOutput =  "No valid arguments given\nMust be in the form 'enharmonicSimple(note)'";
        List<String> testArgs = Collections.singletonList("$$$");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).error(expectedOutput);
    }

    @Test
    public void testx() throws Exception {
        String expectedOutput =  "No valid arguments given\nMust be in the form 'enharmonicSimple(note)'";
        List<String> testArgs = Collections.singletonList("x");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).error(expectedOutput);
    }

    @Test
    public void testa$() throws Exception {
        String expectedOutput =  "No valid arguments given\nMust be in the form 'enharmonicSimple(note)'";
        List<String> testArgs = Collections.singletonList("a$");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).error(expectedOutput);
    }

    @Test
    public void testy5() throws Exception {
        String expectedOutput =  "No valid arguments given\nMust be in the form 'enharmonicSimple(note)'";
        List<String> testArgs = Collections.singletonList("y5");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).error(expectedOutput);
    }

    @Test
    public void testSpace () throws Exception {
        String expectedOutput =  "No valid arguments given\nMust be in the form 'enharmonicSimple(note)'";
        List<String> testArgs = Collections.singletonList(" ");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).error(expectedOutput);
    }

    @Test
    public void testNothing() throws Exception {
        String expectedOutput =  "No valid arguments given\nMust be in the form 'enharmonicSimple(note)'";
        List<String> testArgs = Collections.singletonList("");
        new SimpleEnharmonic(testArgs).execute(mockEnv);
        verify(mockEnv).error(expectedOutput);
    }
}
