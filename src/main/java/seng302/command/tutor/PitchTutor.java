package seng302.command.tutor;

import seng302.Environment;
import seng302.command.Command;
import seng302.controllers.MainSceneController;
import seng302.controllers.pitchtutor.PitchTutorSceneController;
import seng302.util.NoteUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.Arrays;
import java.util.List;

/**
 * Pitch tutor command class.
 *
 * @author avh17
 */
public class PitchTutor implements Command {
    private List<String> arguments;
    private Integer notePairs;
    private Integer lowerRangeMidiNote;
    private Integer upperRangeMidiNote;

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Pitch Tutor",
                CommandCategory.TUTOR,
                "pitchTutor([number][, noteA, noteB])",
                "Run a Pitch Recognition Test in the Pitch Tutor tab.",
                "number\n    Optional param, defaults to 1. The \"x\" character followed by the "
                    + "number of tests to run (see examples if unclear).\n"
                    + "noteA\n    Optional param, defaults to C4. The lower end of the note "
                    + "range for the tests. Must be a lower note than noteB\n"
                    + "noteB\n    Optional param, defaults to C5. The higher end of the note "
                    + "range for the tests. Must be a higher note than noteA.",
                "pitchTutor()\n    Run the pitch tutor with default arguments.\n"
                        + "pitchTutor(x10, C4, D7)\n    Run the pitch tutor with 10 tests between "
                    + "the notes of C4 and D7.",
                    "pitchTutor(x10, C4, D7)"
            );
        }

        return commandManual;
    }

    /**
     * The constructor for the class with no passed arguments.
     */
    public PitchTutor() {
        notePairs = 1;
        lowerRangeMidiNote = 60;
        upperRangeMidiNote = 72;
    }

    /**
     * The constructor for the class with passed arguments.
     *
     * @param args The arguments given in the command
     */
    public PitchTutor(List<String> args) {
        arguments = args;
    }

    /**
     * The execute function.
     *
     * @param env The environment
     */
    public void execute(Environment env) {
        String notePairsArgument;
        String rangeArgumentOne;
        String rangeArgumentTwo;

        if (arguments != null) {
            //Splits up the arguments and assigns the notePair variable a number
            List<String> argumentList = setArguments(arguments);
            notePairsArgument = argumentList.get(0);
            rangeArgumentOne = argumentList.get(1);
            rangeArgumentTwo = argumentList.get(2);

            //Displays an error if the note pair is in the wrong format
            if (notePairsArgument == null) {
                env.error(Error.PITCHTUTORINVALIDNOTEPAIRS.getError());
                return;
            }

            // Ensure 0 or 2 values given for range
            if (rangeArgumentOne == null ^ rangeArgumentTwo == null) {
                env.error(Error.PITCHTUTORONENOTE.getError());
                return;
            }

            // Displays an error if the note pairs parameter is in the right format but is not
            // a number. Returns -1 if value of arg1 cannot be made integer.
            notePairs = calculateNotePairs(notePairsArgument);
            if (notePairs.equals(-1)) {
                env.error(Error.PITCHTUTORNOTEPAIRSNOTNUMBER.getError());
                return;
            }

            // Displays an error if one of the supplied notes in the range is not valid.
            boolean correctNoteRange = setNoteRange(rangeArgumentOne, rangeArgumentTwo);
            if (!correctNoteRange) {
                env.error(Error.PITCHTUTORINVALIDNOTE.getError());
                return;
            }
        }

        //Switch to pitch tutor tab, reset to defaults, start pitch comparison test
        env.println("Starting Pitch Tutor...");
        MainSceneController.getInstance().switchTabToPitchTutor();
        PitchTutorSceneController.getInstance().resetToDefaults();
        PitchTutorSceneController.getInstance().pitchComparisonTest(notePairs, lowerRangeMidiNote,
                upperRangeMidiNote);
    }

    /**
     * A helper function to set the upper and lower notes in the range.
     *
     * @param noteOne The first note
     * @param noteTwo The second note
     * @return A boolean stating used to determine if the note range is valid
     */
    private boolean setNoteRange(String noteOne, String noteTwo) {
        boolean validNoteRange = true;
        //This sets the note range
        if (noteOne == null && noteTwo == null) {
            //sets the default note range as Midi numbers
            lowerRangeMidiNote = 60;
            upperRangeMidiNote = 72;
        } else {
            Integer midiNoteOne = setNote(noteOne);
            Integer midiNoteTwo = setNote(noteTwo);

            if (midiNoteOne == -1 || midiNoteTwo == -1) {
                validNoteRange = false;
            }

            if (midiNoteOne > midiNoteTwo) {
                lowerRangeMidiNote = midiNoteTwo;
                upperRangeMidiNote = midiNoteOne;
            } else {
                lowerRangeMidiNote = midiNoteOne;
                upperRangeMidiNote = midiNoteTwo;
            }
        }
        return validNoteRange;
    }

    /**
     * Helper function to deal with the note arguments and convert them to midi note numbers.
     *
     * @param note the argument the be converted to a midi note
     * @return the midi note number
     */
    private Integer setNote(String note) {
        Integer returnNote;
        try {
            //Try process the note as a midi number
            Integer midiNote = NoteUtil.convertToMidi(new XinityNote(note));
            try {
                returnNote = Integer.parseInt(note);
            } catch (Exception ex) {
                returnNote = -1;
            }

            //Try process the note as a note name value object if midi number failed
            if (returnNote == -1) {
                try {
                    returnNote = midiNote;
                } catch (Exception ex) {
                    returnNote = -1;
                }
            }

            if (!NoteUtil.isValidMidiRange(returnNote)) {
                returnNote = -1;
            }
        } catch (Exception ex) {
            returnNote = -1;
        }
        return returnNote;
    }

    /**
     * A helper function for finding the number of note pairs to test.
     *
     * @param notePairsArgument the argument given in the command
     * @return an integer for the notePairs variable
     */
    private int calculateNotePairs(String notePairsArgument) {
        try {
            return Integer.parseInt(notePairsArgument);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * A helper used to divide up the arguments and check which parameters have been used.
     *
     * @param arguments The arguments list received from the command
     * @return  a list of the divided arguments
     */
    private List<String> setArguments(List<String> arguments) {
        String arg1;
        String arg2;
        String arg3;
        Boolean argOneRelatesToNotePairs = false;

        //Try set the note pairs argument
        try {
            arg1 = arguments.get(0);
            argOneRelatesToNotePairs = arg1.substring(0, 1).equals("x");
        } catch (Exception ex) {
            arg1 = null;
        }

        //Try set the first note argument
        try {
            arg2 = arguments.get(1);
        } catch (Exception ex) {
            arg2 = null;
        }

        //Try set the second note argument
        try {
            arg3 = arguments.get(2);
        } catch (Exception ex) {
            arg3 = null;
        }

        //Deals with the arguments if the note pair value was given
        if (argOneRelatesToNotePairs) {
            if (arg1.length() > 1) {
                return Arrays.asList(arg1.substring(1), arg2, arg3);
            } else {
                return Arrays.asList("1", arg2, arg3);
            }
        //Deals with the arguments note pair value if it was omitted
        } else if (arg3 == null) {
            //Moves the notes into the correct position and sets the note pair argument to default
            arg3 = arg2;
            arg2 = arg1;
            arg1 = "1";
            return Arrays.asList(arg1, arg2, arg3);
        } else {
            return Arrays.asList(null, arg2, arg3);
        }
    }
}
