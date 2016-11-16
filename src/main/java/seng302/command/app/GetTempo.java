package seng302.command.app;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.TempoUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;

/**
 * GetTempo command retrieves the persistent tempo from the AppVariables and displays the tempo
 * in the transcript.
 *
 * @author ljm163
 */
public class GetTempo implements Command {

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Get Tempo",
                CommandCategory.PROJECT,
                "getTempo()",
                "Returns the current Project's tempo.",
                "",
                "getTempo()\n    Return the current tempo of the project.",
                "getTempo()"
            );
        }

        return commandManual;
    }

    /**
     * Execute command.
     *
     * @param env The Environment.
     */
    public void execute(Environment env) {
        try {
            env.println(TempoUtil.getTempo() + " bpm");
        } catch (Exception e) {
            env.error(Error.GETTEMPOFAILED.getError());
        }
    }
}
