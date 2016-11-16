package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.ChordUtil;
import seng302.util.ScalesUtil;
import seng302.util.enumerator.ChordFunctionEnum;
import seng302.util.enumerator.ChordQuality;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.ScaleDirection;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.List;

/**
 * Command class for the Function command.
 *
 * @author avh17.
 */
public class Function implements Command {

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
                    "Function",
                    CommandCategory.MUSICAL,
                    "function(chordNote, quality, scaleNote)",
                    "Returns the function for the given chord and scale.",
                    "chordNote\n    A valid root note of a chord.\n"
                    + "quality\n    A valid chord quality.\n"
                    + "scaleNote\n    A valid root note of a scale.",
                    "function(F, Seventh, Bb)\n    Returns the function \'V\'.\n"
                            + "function(B, Half Diminished, C)\n    "
                            + "Returns the function \'VII\'.",
                    "function(F, Seventh, Bb)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments.
     *
     * @param args Arguments from the command.
     */
    public Function(List<String> args) {
        arguments = args;
    }

    /**
     * Execute function.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        if (arguments.size() < 1 || arguments.size() > 3) {
            env.error(Error.FUNCTIONINVALIDARGS.getError());
            return;
        }

        XinityNote chordNote;
        XinityNote scaleNote;
        ChordQuality chordQuality;
        try {
            chordNote = new XinityNote(arguments.get(0));
            scaleNote = new XinityNote(arguments.get(2));
            chordQuality = ChordQuality.fromString(arguments.get(1));
        } catch (Exception e) {
            env.error(Error.FUNCTIONINVALIDARGS.getError());
            return;
        }

        if (chordQuality == null) {
            env.error(Error.FUNCTIONINVALIDARGS.getError());
            return;
        }

        Integer functionNumber = ChordUtil.functionNumberFinder(scaleNote,
                                                                chordNote,
                                                                chordQuality);

        if (functionNumber == -1) {
            env.error(Error.FUNCTIONINVALIDARGS.getError());
            return;
        }

        // Hacky but comes from ChordUtil to tell error messages apart
        if (functionNumber == -2) {
            env.println("Non Functional.");
            return;
        }

        env.println(ChordFunctionEnum.fromString(functionNumber.toString()).toString());
    }
}
