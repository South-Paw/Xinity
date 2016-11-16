package seng302;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import seng302.command.Command;
import seng302.command.NullCommand;
import seng302.command.app.Version;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Invoker class tests.
 *
 * @author adg62
 */
@RunWith(MockitoJUnitRunner.class)
public class InvokerTest {
    @Mock private Environment env;
    private Invoker invoker = new Invoker(env);

    /**
     * Test that a valid commands return the correct object.
     */
    @Test
    public void parserRecognisesValidCommands() throws Exception {
        Command validCommand = invoker.parseCommandString("version()");
        assertThat(validCommand, instanceOf(Version.class));
    }

    /**
     * Test that a invalid commands return the NullCommand object.
     */
    @Test
    public void parserRecognisesInvalidCommand() throws Exception {
        Command invalidCommand = invoker.parseCommandString("thisisnotacommand");
        assertThat(invalidCommand, instanceOf(NullCommand.class));
    }

    /**
     * Test that 'version' returns the Version command.
     */
    @Test
    public void oldVersionCommandIsInvalid() throws Exception {
        Command command = invoker.parseCommandString("version");

        assertThat(command, instanceOf(NullCommand.class));
    }

    /**
     * Test that 'version()' returns the Version command.
     */
    @Test
    public void validVersionCommandIsValid() throws Exception {
        Command command = invoker.parseCommandString("version()");

        assertThat(command, instanceOf(Version.class));
    }

    /**
     * Test that 'version(x)' returns the NullCommand command.
     */
    @Test
    public void invalidVersionCommandIsInvalid() throws Exception {
        Command command = invoker.parseCommandString("version(x)");

        assertThat(command, instanceOf(NullCommand.class));
    }

    /**
     * Test that when an invalid command is parsed, an error is logged to the environment.
     */
    @Test
    public void errorsLogOnFailureTest() throws Exception {
        Environment errorEnv = new Environment();
        Invoker errorInvoker = new Invoker(errorEnv);

        errorInvoker.executeCommand("thisisinvalid");

        assertEquals("[ERROR] Invalid command.", errorEnv.getOutput().replaceAll("\\r|\\n", ""));
    }
}
