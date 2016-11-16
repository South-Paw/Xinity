package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.NoteUtil;
import seng302.util.ScalesUtil;
import seng302.util.SignatureUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;
import java.util.Set;

/**
 * Signature command. Takes in valid scale eg. 'C minor' and returns the sharps/flats
 * to the transcript in the correct order.
 *
 * @author ljm163
 *
 */

public class Signature implements Command {

    private List<String> arguments;
    private Boolean notesFlag;
    private Boolean octaveInput;
    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                    "Signature",
                    CommandCategory.MUSICAL,
                    "signature(note, scale, override)",
                    "Returns the key signature for the given scale, "
                            + "in a form determined by the override.",
                    "note\n    A valid note.\n"
                            + "scale\n    A valid scale type such as major or minor.\n"
                            + "override\n    "
                            + "The override is \"notes\". When the override is used, the output "
                            + "will be given with the actual notes, "
                            + "rather than the number of flats or sharps.",
                    "signature(c, minor)\n    Return the key signature for c minor.\n"
                            + "signature(c, minor, notes)\n    "
                            + "Return the key signature for c minor in the form of notes.",
                    "signature(C, minor)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments.
     *
     * @param args Arguments from the command.
     */
    public Signature(List<String> args) {
        arguments = args;
        notesFlag = false; //is notesFlag 'notes' given
        octaveInput = false;
    }

    /**
     * Execute function. At least two arguments (C, major) are required to execute the command.
     * The user has the option of using the flag 'notes' for the third parameter if they wish to
     * display the notes for the key signature. The default is the number of flats/sharps '3#'.
     * i.e showSignature(C, minor, notes) will give 'Bb, Eb, Ab'
     *     showSignature(A, major) will give '3#'
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        if (arguments.size() == 2 || arguments.size() == 3) {
            //argument checking for argument1 and argument2
            XinityNote note;
            try {
                note = new XinityNote(arguments.get(0));
                if (note.hasOctave()) {
                    octaveInput = true;
                }
                note = new XinityNote(note.getNote().replaceAll("\\d",""));

                if (!note.isValidMidi()) {
                    env.error(Error.INVALIDMIDINOTE.getError());
                    return;
                }
                ScaleType scaleType = ScaleType.fromString(arguments.get(1));
                if (scaleType != null) {
                    List<XinityNote> scale = ScalesUtil.findScale(note, scaleType);
                    if (scale == null) {
                        env.error(Error.FINDSCALENOSCALE.getError());
                        return;
                    }

                } else {
                    env.error(Error.INVALIDSCALETYPE.getError());
                    return;
                }
            } catch (Exception e) {
                env.error(Error.INVALIDNOTE.getError());
                return;
            }

            //if notesFlag is given
            if (arguments.size() == 3) {
                if (arguments.get(2).equalsIgnoreCase("notes")) {
                    notesFlag = true;
                    //display key signature as notes e.g. 'Bb, Eb, Ab'
                } else {
                    env.error(Error.INVALIDSHOWSIGNATUREARGUMENTS.getError());
                    return;
                }
            }

            if (notesFlag) {
                Set<XinityNote> signature = SignatureUtil.getSignatureNotes(
                        note.getNote(),
                        arguments.get(1));

                String scaleString = "";
                if (signature == null || signature.size() == 0) {
                    scaleString = "This scale has no key signature notes.";
                } else {

                    for (XinityNote scaleNote : signature) {
                        scaleString += scaleNote.getLetterAndAccidental() + ", ";
                    }

                    // strip last comma and space
                    scaleString = scaleString.substring(0, scaleString.length() - 2);

                    // is octave given
                    if (octaveInput) {
                        scaleString += " (octave ignored)";
                    }
                }
                env.println(scaleString);

            } else {
                String scaleString = SignatureUtil.getKeySignatureFromMap(
                        note.getNote(),
                        arguments.get(1));

                if (scaleString == null) {
                    env.error(Error.INVALIDSCALENOSIGNATURE.getError());
                    return;
                }
                // is octave given
                if (octaveInput) {
                    scaleString += " (octave ignored)";
                }
                env.println(scaleString);
            }

        } else {
            env.error(Error.INVALIDSHOWSIGNATUREARGUMENTS.getError());
        }
    }
}
