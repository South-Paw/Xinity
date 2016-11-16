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
 * This class handles the enharmonic notes full command.
 *
 * @author avh17
 */
public class Enharmonic implements Command {
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
                "Enharmonic",
                CommandCategory.MUSICAL,
                "enharmonic(direction, note)",
                "Returns a note based on a direction and a given note.",
                "direction\n    Either \"+\" for above or \"-\" for below.\n"
                        + "note\n    A valid note.",
                "enharmonic(+, C3)\n    Return the enharmonic equivalent above C3, which is Dbb3.\n"
                        + "enharmonic(-, B5)\n    Return the enharmonic equivalent below C3, "
                        + "which is Ax5.",
                "enharmonic(+, C3)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments entered by the user.
     *
     * @param args Arguments from the command entered by the user.
     */
    public Enharmonic(List<String> args) {
        arguments = args;
    }

    /**
     * Used to execute the enharmonic function.
     *
     * @param env The environment for the function
     */
    public void execute(Environment env) {
        String noNoteNotice = "There is no enharmonic equivalent for this note";
        String direction;
        XinityNote note;

        try {
            direction = arguments.get(0);
            note = new XinityNote(arguments.get(1));
            if (!direction.equals("+") && !direction.equals("-")) {
                env.error(Error.ENHARMONICINVALIDARGUMENTS.getError());
                return;
            }
        } catch (Exception ex) {
            env.error(Error.ENHARMONICINVALIDARGUMENTS.getError());
            return;
        }

        XinityNote enharmonic = EnharmonicUtil.findEnharmonicEquivalent(direction, note);

        if (enharmonic == null) {
            env.println(noNoteNotice);
        } else {
            env.println(enharmonic.getNote());
        }
    }
}