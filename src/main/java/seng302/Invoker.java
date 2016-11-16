package seng302;

import seng302.command.Command;
import seng302.command.NullCommand;

import java.io.StringReader;

/**
 * The invoker handles invoking the parser and executor on a command string and returns errors if
 * the parser failed.
 *
 * @author adg62
 */
public class Invoker {
    private Environment environment;

    /**
     * Constructor.
     *
     * @param env The environment.
     */
    public Invoker(Environment env) {
        environment = env;
    }

    /**
     * Parses and then executes a string containing commands.
     *
     * @param commandString The string of commands.
     */
    public Command executeCommand(String commandString) {
        Command toExecute = parseCommandString(commandString);

        executeCommand(toExecute);

        return toExecute;
    }

    /**
     * Executes a command.
     *
     * @param command Command we're calling .execute() on.
     */
    private void executeCommand(Command command) {
        command.execute(environment);
    }

    /**
     * Parses a string of commands.
     *
     * @param commandString The string of commands.
     * @return Returns the command to be executed.
     */
    protected Command parseCommandString(String commandString) {
        DslParser parser = new DslParser(new DslLexer(new StringReader(commandString)));

        Object parseResult = null;

        try {
            parseResult = parser.parse().value;
        } catch (Exception e) {
            //environment.error(String.format("Invalid command: \"%s\"", command_string));
        }

        if (parseResult instanceof Command) {
            return (Command)parseResult;
        } else {
            //environment.error(String.format("Expected command object but got \"%s\"",
            // parseResult));
            return new NullCommand();
        }
    }
}
