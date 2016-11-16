package seng302.command.app;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.TempoUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;

/**
 * GetCrotchet command calculates the crotchet duration (given the tempo in bpm) and displays
 * it in seconds.
 *
 * @author ljm163
*/
public class GetCrotchet implements Command {

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Get Crotchet",
                CommandCategory.PROJECT,
                "getCrotchet()",
                "Returns the current Project's crochet.",
                "",
                "getCrotchet()\n    Return the current crochet of the project.",
                "getCrotchet()"
            );
        }

        return commandManual;
    }

    /**
     * Execute command.
     *
     * @param env The Environment
     */
    public void execute(Environment env) {
        try {
            env.println(TempoUtil.getCrotchet() + " milliseconds");
        } catch (Exception e) {
            env.error(Error.GETCROTCHETFAILED.getError());
        }
    }
}
