package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.ScalesUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * Given starting note and scale type, returns the desired scale in midi note numbers.
 * Input and output clamped to 0 - 127.
 *
 * @author wrs35, ljm163
 */
public class FindScaleMidi implements Command {
    private List<String> arguments;
    private String output;
    private List<XinityNote> scale;

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Find Scale Midi",
                CommandCategory.MUSICAL,
                "findScaleMidi(note, scale)",
                "Returns a list of the midi notes in the given scale type, starting from the root "
                        + "note.",
                "note\n    A valid note.\n"
                        + "scale\n    One of the following; \"major\", \"minor\", \"natural "
                        + "minor\", \"pentatonic major\", \"pentatonic minor\", \"blues\""
                        + ", \"melodic minor\", \"harmonic minor\" or a scale mode.",
                "findScaleMidi(bb, minor)\n    Returns the minor scale of Bb4 in midi notes."
                        + " \nfindScaleMidi(g, major)\n    Returns the major scale of G4 in midi "
                        + "notes.",
                "findScaleMidi(bb, minor)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor for class.
     *
     * @param args Given arguments from the invoker.
     */
    public FindScaleMidi(List<String> args) {
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

        List<Integer> midiScale = ScalesUtil.convertToMidiScale(scale);
        output = ScalesUtil.getScaleString(midiScale);
        env.println(output);
    }
}
