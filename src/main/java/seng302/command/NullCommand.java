package seng302.command;

import seng302.Environment;
import seng302.util.enumerator.Error;

/**
 * NullCommand.
 * This 'command' is executed when a command is entered by the user but the DSL doesn't find a
 * matching command. The Environment then executes this to give us the null command error message.
 *
 * @author adg62
 */
public class NullCommand implements Command {

    /**
     * Execute function.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        env.error(Error.NULLCOMMAND.getError());
    }
}
