package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;

import seng302.util.ScalesUtil;
import seng302.util.SignatureUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.NoteAccidental;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * ScaleSignature command. Given a key signature eg. 'sig 2#' the command returns a scale
 * eg. 'D major'
 *
 * @author ljm163, plr37, wrs35
 */
public class ScaleSignature implements Command {

    private List<String> arguments;

    private Boolean typeGiven;
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
                    "Scale Signature",
                    CommandCategory.MUSICAL,
                    "scaleSignature(signature)",
                    "Returns the scale name for the given key signature.",
                    "signature\n    A valid key signature.\n"
                            + "    This could be in the form of a count of flats and sharps, "
                            + "or in the form of the actual notes.\n",
                    "signature(2#)\n    Return the scale corresponding containing two sharps.\n"
                            + "signature(F#, C#)\n    "
                            + "Return the scale corresponding containing the two notes.",
                    "scaleSignature(2#)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments.
     *
     * @param args Arguments from the command.
     */
    public ScaleSignature(List<String> args) {
        arguments = args;
        typeGiven = false;
        octaveInput = false;
    }

    /**
     * Execute function. The findSignature command will except one argument in the form '#2',
     * or multiple arguments given it is in the form of a valid key signature eg. 'C#, F#'.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        // Early return if no arguments given
        if (arguments.size() == 0) {
            env.error("No arguments passed");
            return;
        }

        // check if last argument is 'major', 'minor' or 'both'
        String lastArgument = arguments.get(arguments.size() - 1);
        // Did not use enum because if unknown is passed it is valid causing errors
        // also prevents errors in the future if more chord types are added.
        // Perhaps this should be handled differently, but prevents unwanted behavior. Will(wrs35)
        if (lastArgument.equalsIgnoreCase("major")
                || lastArgument.equalsIgnoreCase("minor")
                || lastArgument.equalsIgnoreCase("both")) {
            typeGiven = true;
        }

        // check first argument is a note or key signature
        String arg = arguments.get(0);
        try {
            XinityNote note = new XinityNote(arg);
            if (note.hasOctave()) {
                octaveInput = true;
            }
            if (NoteAccidental.isSimpleAccidental(note.getAccidental())) {
                handleNoteInput(env);
            } else {
                env.error(Error.SCALESIGNATUREWRONGARGUMENTS.getError());
            }
        } catch (Exception e) {
            if (!arg.substring(0, 1).matches("\\d+")) {
                env.error(Error.SCALESIGNATUREWRONGARGUMENTS.getError());
                return;
            }
            if (NoteAccidental.isSimpleAccidental(
                    NoteAccidental.fromMusicalRepresentation(arg.substring(arg.length() - 1)))) {
                handleKeySignatureInput(env);
            } else {
                env.error(Error.SCALESIGNATUREWRONGARGUMENTS.getError());
            }
        }
    }

    private void handleKeySignatureInput(Environment env) {
        String scaleType;

        if (typeGiven) {
            scaleType = arguments.get(arguments.size() - 1);
            if (arguments.size() > 2) {
                env.error(Error.SCALESIGNATUREWRONGTYPE.getError());
                return;
            }
        } else {
            scaleType = "major";
            if (arguments.size() > 1) {
                env.error(Error.SCALESIGNATUREWRONGTYPE.getError());
                return;
            }
        }

        String signature = arguments.get(0);

        //Extract integer 10 from '10#' or 2 from '2b' etc
        Integer noOfAccidentals = Integer.parseInt(signature.substring(0, signature.length() - 1));

        if (noOfAccidentals >= 0 && noOfAccidentals <= 7) {
            if (scaleType.equals("both")) { // print major than minor
                env.println(SignatureUtil.determineScaleFromKeySignature(arguments.get(0),
                        ScaleType.MAJOR) + " major");
                env.println(SignatureUtil.determineScaleFromKeySignature(arguments.get(0),
                        ScaleType.MINOR) + " minor");
            } else {
                env.println(SignatureUtil.determineScaleFromKeySignature(arguments.get(0),
                        ScaleType.fromString(scaleType)) + " "
                        + scaleType.toLowerCase());
            }
        } else {
            env.error(Error.SCALESIGNATUREWRONGFLATSHARPS.getError());
        }
    }

    private void handleNoteInput(Environment env) {
        String scaleType;
        Integer numberOfNotes;
        List<String> keySignatureOrder;

        if (typeGiven) {
            scaleType = arguments.get(arguments.size() - 1);
            numberOfNotes = arguments.size() - 1;
        } else {
            scaleType = "major";
            numberOfNotes = arguments.size();

        }

        if (numberOfNotes > 7) {
            env.error(Error.SCALESIGNATUREWRONGNUMBEROFNOTES.getError());
            return;
        }

        // determine if argument(s) are key signature notes or simply a key signature
        char accidental = arguments.get(0).charAt(1); // get accidental of first note

        keySignatureOrder = SignatureUtil.getKeySignatureOrder(Character.toString(accidental));
        if (keySignatureOrder != null) {
            keySignatureOrder = keySignatureOrder.subList(0, numberOfNotes);
            for (Integer i = 0; i < numberOfNotes; i++) {
                if (!keySignatureOrder.get(i).equalsIgnoreCase(arguments.get(i).substring(0, 1))) {
                    env.error(Error.SCALESIGNATURENOSCALE.getError());
                    return;
                } else if (arguments.get(i).charAt(1) != accidental) {
                    env.error(Error.SCALESIGNATUREMIXEDFLATSHARPS.getError());
                    return;
                }
            }

            String signature = numberOfNotes + Character.toString(accidental);

            if (scaleType.equals("both")) { // print major than minor
                if (octaveInput) {
                    env.println(SignatureUtil.determineScaleFromKeySignature(signature,
                            ScaleType.MAJOR) + " major" + " (octave ignored)");
                    env.println(SignatureUtil.determineScaleFromKeySignature(signature,
                            ScaleType.MINOR) + " minor" + " (octave ignored)");
                } else {
                    env.println(SignatureUtil.determineScaleFromKeySignature(signature,
                            ScaleType.MAJOR) + " major");
                    env.println(SignatureUtil.determineScaleFromKeySignature(signature,
                            ScaleType.MINOR) + " minor");

                }
            } else {
                if (octaveInput) {
                    env.println(SignatureUtil.determineScaleFromKeySignature(signature,
                            ScaleType.fromString(scaleType)) + " "
                            + scaleType.toLowerCase() + " (octave ignored)");
                } else {
                    env.println(SignatureUtil.determineScaleFromKeySignature(signature,
                            ScaleType.fromString(scaleType)) + " "
                            + scaleType.toLowerCase());
                }
            }
        } else {
            env.error(Error.SCALESIGNATURENOTNOTES.getError());
        }
    }
}
