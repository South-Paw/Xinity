package seng302.command.app;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.ConfigUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.object.CommandManual;

/**
 * Version command.
 * This command accesses the programs version from the app config and then echo's is back in a
 * formatted string.
 *
 * @author adg62
 */
public class Version implements Command {

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Version",
                CommandCategory.PROJECT,
                "version()",
                "Returns the application version.",
                "",
                "version()\n    Return the application's version.",
                "version()"
            );
        }

        return commandManual;
    }

    /**
     * Execute function.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        env.println("Version: " + ConfigUtil.retrieveVersion());
    }
}
