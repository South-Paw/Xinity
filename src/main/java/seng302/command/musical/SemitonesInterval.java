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
 * SemitoneInterval command.
 * Given a valid interval, return the number of semitones it contains.
 *
 * @author mvj14
 */
public class SemitonesInterval implements Command {
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
                "Semitones in Interval",
                CommandCategory.MUSICAL,
                "semitonesInterval(interval)",
                "Calculates the number of semitones within a given interval.",
                "interval\n    An interval from the interval list.",
                "semitonesInterval(\"augmented second\")\n    Returns the number of semitones "
                        + "within the augmented second interval.",
                "semitonesInterval(\"major 2nd\")"
            );
        }

        return commandManual;
    }

    /**
     * Constructor
     *
     * @param args Arguments for the command.
     */
    public SemitonesInterval(List<String> args) {
        arguments = args;
    }

    /**
     * Execute the semitone interval command.
     *
     * @param env The Environment.
     */
    public void execute(Environment env) {
        if (arguments.size() != 1) {
            env.error(Error.SEMITONESINTERVALNOARGS.getError());
            return;
        }
        Interval interval = Interval.fromString(arguments.get(0));
        if (interval == null) {
            env.error(Error.SEMITONESINTERVALINVALIDINTERVAL.getError());
            return;
        }

        Integer semitones = IntervalUtil.getSemitones(interval);
        if (semitones == null) {
            env.error(Error.SEMITONESINTERVALUNKNOWN.getError());
            return;
        }
        env.println(arguments.get(0) +  " contains " + semitones + " semitones.");
    }
}
