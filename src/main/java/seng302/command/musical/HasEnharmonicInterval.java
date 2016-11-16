package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.IntervalUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;

import java.util.List;

/**
 * Determines if given interval has an enharmonic equivalent
 * and returns true or false.
 *
 * @author ljm163, plr37
 */

public class HasEnharmonicInterval implements Command {

    List<String> arguments;

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Has Enharmonic Interval",
                CommandCategory.MUSICAL,
                "hasEnharmonicInterval(interval)",
                "Displays whether the given interval has a simple enharmonic equivalent.",
                "interval\n    An interval from the interval list.",
                "hasEnharmonicInterval(\"Perfect Unison\")\n    Returns if there is an "
                    + "enharmonic equivalent for this interval.",
                "hasEnharmonicInterval(\"Perfect Unison\")"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments.
     *
     * @param args Arguments from the command.
     */
    public HasEnharmonicInterval(List<String> args) {
        arguments = args;
    }

    /**
     * Execute function.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        if (arguments.size() != 1) {
            env.error(String.format(Error.HASENHARMONICINTERVALINVALIDNUMBEROFARGUMENTS.getError(),
                    arguments.size()));
            return;
        }
        seng302.util.enumerator.Interval interval;
        interval = seng302.util.enumerator.Interval.fromString(arguments.get(0));
        if (interval == null) {
            env.error(Error.HASEHARMONICINTERVALINTERVALDOESNOTEXIST.getError());
            return;
        }

        if (IntervalUtil.hasEnharmonic(interval)) {
            env.println("Interval has an enharmonic equivalent.");
        } else {
            env.println("Interval does not have an enharmonic equivalent.");
        }
    }
}
