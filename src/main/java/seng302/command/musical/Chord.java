package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.ChordUtil;
import seng302.util.NoteUtil;
import seng302.util.enumerator.ChordQuality;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.Inversion;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.List;

/**
 * Chord command class.
 *
 * @author mvj14, ljm163
 */
public class Chord implements Command {
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
                "Chord",
                CommandCategory.MUSICAL,
                "chord(note, quality, inversion)",
                "Returns a chord based on a given note and quality.",
                "note\n    A valid note.\n"
                    + "quality\n    Can be one of the following; \"major\", \"minor\" or "
                    + "\"natural minor\".\n"
                    + "inversion\n    A number corresponding to the degree of inversion.",
                "chord(C4, major)\n    Return the chord of C4 Major.\n"
                    + "chord(E4, \"natural minor\")\n    Return the chord of E4 Natural Minor.\n"
                    + "chord(E4, \"natural minor\", 1)\n    Return the first inversion chord of E4 "
                    + "Natural Minor.",
                "chord(C4, major)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor for class.
     *
     * @param args Arguments from the environment.
     */
    public Chord(List<String> args) {
        arguments = args;
    }

    /**
     * Execute the interval command.
     *
     * @param env the environment.
     */
    public void execute(Environment env) {
        // Command can only have 2 or 3 arguments. 3 argument if an inversion is computed.
        if (!(arguments.size() == 2 || arguments.size() == 3)) {
            env.error(Error.CHORDINVALIDNUMARGS.getError());
            return;
        }

        // Check root note is valid
        XinityNote root;
        try {
            root = new XinityNote(arguments.get(0));
            if (!root.isValidMidi()) {
                env.error(Error.INVALIDMIDINOTE.getError());
                return;
            }
        } catch (Exception e) {
            env.error(Error.XINITYNOTECANNOTCREATENOTE.getError()
                    + "\"" + arguments.get(0) + "\".");
            return;
        }

        // Check chord quality is valid
        ChordQuality chordQuality = ChordQuality.fromString(arguments.get(1));
        if (chordQuality == null) {
            env.error(Error.CHORDINVALIDQUALITY.getError());
            return;
        }

        // Produce raw chord array without inversions
        List<XinityNote> chord = ChordUtil.chord(root, chordQuality);
        if (chord == null) {
            env.error(Error.CHORDDOESNOTEXIST.getError());
            return;
        }

        // 3 arguments given, should invert?
        if (arguments.size() == 3) {
            Inversion inversion = Inversion.fromString(arguments.get(2));
            if (inversion == null) {
                env.error(Error.CHORDWRONGINVERSION.getError());
                return;
            } else if (inversion == Inversion.THIRD && chord.size() == 3) {
                // triad cannot have third inversion
                env.error(Error.CHORDWRONGINVERSIONTRIAD.getError());
                return;
            }
            ChordUtil.invertChord(chord, inversion);
        }

        String output = ChordUtil.getChordString(chord, root.hasOctave());

        env.println(output);
    }
}
