package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.EnharmonicUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * HasEnharmonic command returns if a note has a simple enharmonic equivalent.
 *
 * @author wrs35
 */
public class HasEnharmonic implements Command {
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
                "Has Enharmonic",
                CommandCategory.MUSICAL,
                "hasEnharmonic(note)",
                "Returns whether a given note has a simple enharmonic.",
                "note\n    A valid note.",
                "hasEnharmonic(a)\n    Returns if A4 has an enharmonic.\n"
                        + "hasEnharmonic(c)\n    Returns if C4 has an enharmonic",
                "hasEnharmonic(a)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments.
     *
     * @param args Arguments from the command.
     */
    public HasEnharmonic(List<String> args) {
        arguments = args;
    }

    /**
     * Used to execute the enharmonic function.
     *
     * @param env The environment for the function
     */
    public void execute(Environment env) {
        // Get user entered note
        XinityNote inputNote;

        try {
            inputNote = new XinityNote(arguments.get(0));
        } catch (Exception exception) {
            env.error(Error.HASENHARMONICINVALIDARGUMENTS.getError());
            return;
        }

        XinityNote returnNote = EnharmonicUtil.findSimpleEnharmonicEquivalent(inputNote);

        if (returnNote == null) {
            env.println("Note does not have a simple enharmonic.");
        } else {
            env.println("Note has a simple enharmonic.");
        }
    }
}
