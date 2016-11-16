package seng302.command.app;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import seng302.Environment;
import seng302.command.app.SetTempo;
import seng302.util.TempoUtil;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * SetTempo command tests.
 *
 * @author ljm163
 */
@RunWith(MockitoJUnitRunner.class)
public class SetTempoTest {
    @Mock
    private Environment mockEnv = new Environment();

    /** Reset the AppVariables for these tests. */
    @Before
    public void resetVariables() {
        TempoUtil.resetTempo();
    }

    @Test
    public void testValid20() throws Exception {
        List<String> testArgs = Arrays.asList("20");
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).println("Tempo set to: 20 bpm");
    }

    @Test
    public void testValid400() throws Exception {
        List<String> testArgs = Arrays.asList("400");
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).println("Tempo set to: 400 bpm");
    }

    @Test
    public void testValid200() throws Exception {
        List<String> testArgs = Arrays.asList("200");
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).println("Tempo set to: 200 bpm");
    }

    @Test
    public void testInvalid2Args() throws Exception {
        List<String> testArgs = Arrays.asList("200", "300");
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).error("Invalid arguments. Command expects a single positive integer.");
    }

    @Test
    public void testValid200LowerF() throws Exception {
        List<String> testArgs = Arrays.asList("200", "f");
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).println("Tempo set to: 200 bpm");
    }

    @Test
    public void testValid1000LowerF() throws Exception {
        List<String> testArgs = Arrays.asList("1000", "f");
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).println("Tempo set to: 1000 bpm");
    }

    @Test
    public void testValid10LowerF() throws Exception {
        List<String> testArgs = Arrays.asList("10", "f");
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).println("Tempo set to: 10 bpm");
    }

    @Test
    public void testInvalid10LowerF() throws Exception {
        List<String> testArgs = Arrays.asList("-1", "f");
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).error("Invalid arguments. Command expects a single positive integer.");
    }

    @Test
    public void testInvalidNoArgs() throws Exception {
        List<String> testArgs = Arrays.asList();
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).error("Invalid arguments. Command expects a single positive integer.");
    }

    @Test
    public void testInvalid19() throws Exception {
        List<String> testArgs = Arrays.asList("19");
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).error("Input lies outside of tempo range (20 - 400 bpm). Tempo did not change.");
    }

    @Test
    public void testInvalid401() throws Exception {
        List<String> testArgs = Arrays.asList("401");
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).error
                ("Input lies outside of tempo range (20 - 400 bpm). Tempo did not change.");
    }

    @Test
    public void testInvalidNeg1() throws Exception {
        List<String> testArgs = Arrays.asList("-1");
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).error("Input lies outside of tempo range (20 - 400 bpm). Tempo did not change.");
    }

    @Test
    public void testInvalid1000() throws Exception {
        List<String> testArgs = Arrays.asList("1000");
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).error("Input lies outside of tempo range (20 - 400 bpm). Tempo did not change.");
    }

    @Test
    public void testInvalidString() throws Exception {
        List<String> testArgs = Arrays.asList("asdfdgdsd");
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).error("Invalid tempo. Tempo was not changed.");
    }

    @Test
    public void testValidStringLowerF() throws Exception {
        List<String> testArgs = Arrays.asList("asdsafghj", "f");
        new SetTempo(testArgs).execute(mockEnv);
        verify(mockEnv).error("Invalid tempo. Tempo was not changed.");
    }
}