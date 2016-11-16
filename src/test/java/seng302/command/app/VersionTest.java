package seng302.command.app;

import org.junit.Test;
import seng302.Environment;
import seng302.command.app.Version;

import static org.junit.Assert.*;

/**
 * Version Command tests.
 *
 * @author adg62
 */
public class VersionTest {
    private Environment env = new Environment();

    /**
     * Tests that execute() returns a string with "Version: " in it.
     */
    @Test
    public void testVersionResult() {
        new Version().execute(env);

        assertTrue(env.getOutput().contains("Version: "));
    }
}