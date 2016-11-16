package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.NoteUtil;
import seng302.util.ScalesUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * Given starting note and scale type, returns the desired scale.
 * Input and output clamped to 0 - 127.
 *
 * @author wrs35, ljm163
 */
public class FindScale implements Command {
    private List<String> arguments;
    private List<XinityNote> scale;
    private Boolean hasOctave;
    private String scaleString;

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                    "Find Scale",
                    CommandCategory.MUSICAL,
                    "findScale(note, scale)",
                    "Returns a list of the notes in the given scale type, "
                            + "starting from the root note.",
                    "note\n    A valid note.\n"
                            + "scale\n    One of the following; \"major\", \"minor\", \"natural "
                            + "minor\", \"pentatonic major\", \"pentatonic minor\", \"blues\""
                            + ", \"melodic minor\", \"harmonic minor\" or a scale mode.",
                    "findScale(a, major)\n    Returns the major scale of A4."
                            + " \nfindScale(e, minor)\n    Returns the minor scale of E4.",
                    "findScale(a, major)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor for class.
     *
     * @param args Given arguments from the invoker.
     */
    public FindScale(List<String> args) {
        arguments = args;
    }


    /**
     * Execute the find scale command.
     *
     * @param env The Environment.
     */
    public void execute(Environment env) {
        if (arguments.size() != 2) {
            env.error(Error.INVALIDSCALEARGUMENTS.getError());
            return;
        }

        XinityNote note;
        try {
            note = new XinityNote(arguments.get(0));
        } catch (Exception e) {
            env.error(Error.INVALIDNOTE.getError());
            return;
        }
        if (!note.isValidMidi()) {
            env.error(Error.INVALIDMIDINOTE.getError());
            return;
        }

        ScaleType scaleType = ScaleType.fromString(arguments.get(1));
        if (scaleType == null) {
            env.error(Error.INVALIDSCALETYPE.getError());
            return;
        }

        scale = ScalesUtil.findScale(note, scaleType);
        if (scale == null) {
            env.error(Error.FINDSCALENOSCALE.getError());
            return;
        }
        hasOctave = note.hasOctave();
        scaleString = ScalesUtil.getScaleString(scale, hasOctave);
        env.println(scaleString);
    }
}



