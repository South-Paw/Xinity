package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.ScalesUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.ScaleGroup;
import seng302.util.enumerator.ScaleMode;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * Parent command class.
 *
 * @author plr37
 */
public class Parent implements Command {

    private List<String> arguments;
    private XinityNote note;
    private ScaleMode mode;
    private XinityNote parentNote;

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                    "Parent",
                    CommandCategory.MUSICAL,
                    "parent(note, mode)",
                    "Returns the corresponding parent scale of a mode.",
                    "note\n    A valid note.\n"
                            + "mode\n    One of the following; \"Ionian\", \"Dorian\","
                            + " \"Phrygian\", \"Lydian\", \"Mixolydian\", \"Aeolian\","
                            + " or \"Locrain\". Or a valid Melodic Minor mode.",
                    "parent(D, Dorian)\n    Returns \"C Major\"."
                            + " \nparent(A, Mixolydian)\n    Returns \"D Major\"",
                    "parent(D, Dorian)"
            );
        }
        return commandManual;
    }

    /**
     * Constructor for class.
     *
     * @param args Arguments from the environment.
     */
    public Parent(List<String> args) {
        arguments = args;
    }

    /**
     * Execute Parent Command.
     *
     * @param env The environment
     */
    public void execute(Environment env) {
        if (arguments.size() != 2) {
            env.error(Error.PARENTINVALIDARGS.getError());
            return;
        }

        try {
            note = new XinityNote(arguments.get(0));
        } catch (Exception e) {
            env.error(Error.PARENTINVALIDNOTE.getError());
            return;
        }

        if (note.getMidi() < 0 || note.getMidi() > 127) {
            env.error(Error.PARENTMIDIRANGE.getError());
            return;
        }

        mode = ScaleMode.fromString(arguments.get(1));
        if (mode == null) {
            env.error(Error.PARENTINVALIDMODE.getError());
            return;
        }

        parentNote = ScalesUtil.getParentNote(note, mode);
        ScaleGroup scaleGroup = ScaleGroup.fromScaleType(ScaleType.fromString(mode.toString()));
        String parentScaleTypeString;
        if (scaleGroup == ScaleGroup.MELODICMINORMODES) {
            parentScaleTypeString = ScaleType.MELODICMINOR.toString();
        } else if (scaleGroup == ScaleGroup.MAJORMODES) {
            parentScaleTypeString = ScaleType.MAJOR.toString();
        } else if (scaleGroup == ScaleGroup.HARMONICMINORMODES) {
            parentScaleTypeString = ScaleType.HARMONICMINOR.toString();
        } else {
            env.error(Error.PARENTINVALIDMODE.getError());
            return;
        }
        if (note.hasOctave()) {
            env.println(parentNote.getNote() + " " + parentScaleTypeString);
        } else {
            env.println(parentNote.getLetterAndAccidental() + " " + parentScaleTypeString);
        }

    }
}
