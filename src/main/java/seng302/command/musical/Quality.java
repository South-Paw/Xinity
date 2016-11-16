package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.enumerator.ChordFunctionEnum;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;

import java.util.List;

/**
 * Command class for the quality command.
 *
 * @author avh17.
 */
public class Quality implements Command {

    private List<String> arguments;

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                    "Quality",
                    CommandCategory.MUSICAL,
                    "quality(function)",
                    "Returns the chord quality relating to the given function",
                    "function\n    A roman numeral corresponding to a function/degree.",
                    "quality(IV)\n    Returns the chord quality \'Major Seventh\'.\n"
                            + "quality(VI)\n    "
                            + "Returns the chord quality 'Minor Seventh'.",
                    "quality(IV)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments.
     *
     * @param args Arguments from the command.
     */
    public Quality(List<String> args) {
        arguments = args;
    }

    /**
     * Execute function.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        if (arguments.size() < 1 || arguments.size() > 1) {
            env.error(Error.QUALITYINVALIDARGS.getError());
            return;
        }

        ChordFunctionEnum function = ChordFunctionEnum.fromString(arguments.get(0));
        if (function != null) {
            env.println(function.getQuality().toString());
        } else {
            env.error(Error.QUALITYINVALIDARGS.getError());
        }
    }
}
