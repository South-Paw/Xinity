package seng302;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Environment tests.
 *
 * @author adg62
 */
public class EnvironmentTest {

    /**
     * Test that the environment starts out empty.
     */
    @Test public void testOutputBeginsEmpty() throws Exception {
        Environment env = new Environment();

        assertEquals("", env.getOutput());
    }

    /**
     * Test that println() appends to the output and that multiple are broken by '\n'.
     */
    @Test public void testPrintlnAppendsToOutput() throws Exception {
        Environment env = new Environment();

        env.println("Hello world.");
        assertEquals("Hello world.", env.getOutput().replaceAll("\\r|\\n", ""));

        env.println("Goodbye.");
        assertEquals("Hello world.Goodbye.", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /**
     * Test that error() appends to the output.
     */
    @Test public void testError() throws Exception {
        Environment env = new Environment();

        env.error("Error!");
        assertEquals("[ERROR] Error!", env.getOutput().replaceAll("\\r|\\n", ""));
    }
}
