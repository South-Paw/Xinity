package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.IntervalUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.Interval;
import seng302.util.object.CommandManual;

import java.util.List;

/**
 * This class handles the enharmonic intervals command.
 *
 * @author plr37
 */
public class EnharmonicInterval implements Command {

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
                "Enharmonic Interval",
                CommandCategory.MUSICAL,
                "enharmonicInterval(interval)",
                "Displays an enharmonic equivalent for the given interval.",
                "interval\n    An interval from the interval list.",
                "enharmonicInterval(\"Perfect Unison\")\n    Returns the enharmonic equivalent "
                    + "for a \"Perfect Unison\".",
                "enharmonicInterval(\"Perfect Unison\")"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments.
     *
     * @param args Arguments from the command.
     */
    public EnharmonicInterval(List<String> args) {
        arguments = args;
    }

    /**
     * Used to execute the enharmonic function.
     *
     * @param env The environment for the function
     */
    public void execute(Environment env) {
        if (arguments.size() != 1) {
            env.error(String.format(Error.ENHARMONICINTERVALINVALIDNUMBEROFARGUMENTS.getError(),
                    arguments.size()));
        } else {
            Interval interval = Interval.fromString(arguments.get(0));
            if (interval == null) {
                env.error(Error.ENHARMONICINTERVALINTERVALDOESNOTEXIST.getError());
                return;
            }
            Interval enharmonic = IntervalUtil.getEnharmonic(interval);
            if (enharmonic == null) {
                env.println("Interval does not have an enharmonic equivalent.");
            } else {
                env.println(Interval.toString(enharmonic));
            }
        }
    }
}
