package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.ChordUtil;
import seng302.util.ScalesUtil;
import seng302.util.enumerator.ChordFunctionEnum;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.ScaleDirection;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * Command class for the Chord Function command.
 *
 * @author avh17.
 */
public class ChordFunction implements Command {

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
                    "Chord Function",
                    CommandCategory.MUSICAL,
                    "chordFunction(function, note)",
                    "Returns the diatonic chord based off the major scale of the given note.",
                    "function\n    A roman numeral corresponding to a function/degree.\n"
                    + "note\n    A valid root note of a major scale.",
                    "chordFunction(IV, D)\n    Returns the chord \'G Major Seventh\'.\n"
                            + "chordFunction(VII, F)\n    "
                            + "Returns the chord \'E Half Diminished\'.",
                    "chordFunction(IV, D)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments.
     *
     * @param args Arguments from the command.
     */
    public ChordFunction(List<String> args) {
        arguments = args;
    }

    /**
     * Execute function.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        if (arguments.size() < 1 || arguments.size() > 2) {
            env.error(Error.CHORDFUNCTIONINVALIDARGS.getError());
            return;
        }

        ChordFunctionEnum function;
        XinityNote rootNote;
        try {
            function = ChordFunctionEnum.fromString(arguments.get(0));
            rootNote = new XinityNote(arguments.get(1));
        } catch (Exception e) {
            env.error(Error.CHORDFUNCTIONINVALIDARGS.getError());
            return;
        }

        if (function == null) {
            env.error(Error.CHORDFUNCTIONINVALIDARGS.getError());
            return;
        }

        // Print the chord name
        env.println(ChordUtil.getDiatonicChord(function, rootNote));
    }
}
