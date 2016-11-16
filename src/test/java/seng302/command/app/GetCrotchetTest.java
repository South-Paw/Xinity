package seng302.command.app;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.command.app.GetCrotchet;
import seng302.command.app.SetTempo;
import seng302.util.TempoUtil;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * GetCrotchet command tests.
 *
 * @author ljm163
 */
public class GetCrotchetTest {
    private Environment crotchetEnvironment = new Environment();

    @Before
    public void resetVariables() {
        TempoUtil.resetTempo();
    }

    @Test
    public void crotchetWithDefaults() {
        new GetCrotchet().execute(crotchetEnvironment);

        assertEquals("500 milliseconds", crotchetEnvironment.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void crotchetCalculatesCorrectly() {
        List<String> testArgs = Arrays.asList("300");
        new SetTempo(testArgs).execute(crotchetEnvironment);

        new GetCrotchet().execute(crotchetEnvironment);

        assertEquals("Tempo set to: 300 bpm200 milliseconds", crotchetEnvironment.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void getCrotchetTempo140() {
        List<String> testArgs = Arrays.asList("140");
        new SetTempo(testArgs).execute(crotchetEnvironment);

        new GetCrotchet().execute(crotchetEnvironment);

        assertEquals("Tempo set to: 140 bpm429 milliseconds", crotchetEnvironment.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void getCrotchetSetTempo270() {
        List<String> testArgs = Arrays.asList("270");
        new SetTempo(testArgs).execute(crotchetEnvironment);

        new GetCrotchet().execute(crotchetEnvironment);

        assertEquals("Tempo set to: 270 bpm222 milliseconds", crotchetEnvironment.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void getCrotchetSetTempo45() {
        List<String> testArgs = Arrays.asList("45");
        new SetTempo(testArgs).execute(crotchetEnvironment);

        new GetCrotchet().execute(crotchetEnvironment);

        assertEquals("Tempo set to: 45 bpm1333 milliseconds", crotchetEnvironment.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void getCrotchetSetTempo383() {
        List<String> testArgs = Arrays.asList("383");
        new SetTempo(testArgs).execute(crotchetEnvironment);

        new GetCrotchet().execute(crotchetEnvironment);

        assertEquals("Tempo set to: 383 bpm157 milliseconds", crotchetEnvironment.getOutput().replaceAll("\\r|\\n", ""));
    }
}