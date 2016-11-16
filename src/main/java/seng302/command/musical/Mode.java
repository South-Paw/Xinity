package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.ScalesUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.ScaleGroup;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * The mode command class.
 *
 * @author plr37
 */
public class Mode implements Command {

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
                    "Mode",
                    CommandCategory.MUSICAL,
                    "mode(key, degree)",
                    "Returns the corresponding mode of the major scale.",
                    "key\n    A valid note.\n"
                            + "degree\n    An integer between 1 and 7.",
                    "mode(C, 2)\n    Returns \"D Dorian\"."
                            + " \nmode(D, 5)\n    Returns \"A Mixolydian\"",
                    "mode(D, 5)"
            );
        }
        return commandManual;
    }

    /**
     * Constructor for class.
     *
     * @param args Arguments from the environment.
     */
    public Mode(List<String> args) {
        arguments = args;
    }

    /**
     * Execute Mode Command.
     *
     * @param env The environment
     */
    public void execute(Environment env) {
        XinityNote key;
        Integer degree;
        String outOfMidiRange = "The resulting mode is out of midi range.";
        ScaleGroup scaleGroup = ScaleGroup.MAJORMODES;

        if (arguments.size() < 2 || arguments.size() > 3) {
            env.error(Error.MODEINVALIDARGS.getError());
            return;
        }

        try {
            key = new XinityNote(arguments.get(0));
        } catch (Exception e) {
            env.error(Error.MODEINVALIDKEY.getError());
            return;
        }

        if (key.getMidi() < 0 || key.getMidi() > 127) {
            env.error(Error.MODEMIDIRANGE.getError());
            return;
        }

        try {
            degree = Integer.parseInt(arguments.get(1));
        } catch (Exception e) {
            env.error(Error.MODEINVALIDDEGREE.getError());
            return;
        }

        if (degree < 1 || degree > 7) {
            env.error(Error.MODEINVALIDDEGREE.getError());
            return;
        }

        if (arguments.size() > 2) {
            String inputModeType = arguments.get(2);

            if (inputModeType.equalsIgnoreCase("major")) {
                scaleGroup = ScaleGroup.MAJORMODES;
            } else if (inputModeType.equalsIgnoreCase("melodic minor")) {
                scaleGroup = ScaleGroup.MELODICMINORMODES;
            } else {
                env.error(Error.MODEINVALIDMODETYPE.getError());
                return;
            }
        }

        String output = ScalesUtil.getMode(key, degree, scaleGroup);
        if (output != null) {
            env.println(output);
        } else {
            env.println(outOfMidiRange);
        }
    }

}
