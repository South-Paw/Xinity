package seng302.command.app;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.command.app.GetTempo;
import seng302.command.app.SetTempo;
import seng302.util.TempoUtil;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * GetTempo command tests.
 *
 * @author ljm163
 */
public class GetTempoTest {
    private Environment tempoTestEnv = new Environment();

    @Before
    public void resetVariables() {
        TempoUtil.resetTempo();
    }

    @Test
    public void tempoOutputsCorrectly() {
        new GetTempo().execute(tempoTestEnv);

        assertEquals("120 bpm", tempoTestEnv.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void tempoReportsChanges() {
        new GetTempo().execute(tempoTestEnv);
        assertEquals("120 bpm", tempoTestEnv.getOutput().replaceAll("\\r|\\n", ""));

        List<String> testArgs = Arrays.asList("100");
        new SetTempo(testArgs).execute(tempoTestEnv);

        new GetTempo().execute(tempoTestEnv);
        assertEquals("120 bpmTempo set to: 100 bpm100 bpm", tempoTestEnv.getOutput().replaceAll("\\r|\\n", ""));
    }
}