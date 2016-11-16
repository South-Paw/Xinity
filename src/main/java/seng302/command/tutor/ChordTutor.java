package seng302.command.tutor;

import seng302.Environment;
import seng302.command.Command;
import seng302.tutor.ChordTutorTestGenerator;
import seng302.tutor.Tutor;
import seng302.util.enumerator.ChordStyle;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;

import java.util.Arrays;
import java.util.List;

/**
 * Chord tutor command class.
 */
public class ChordTutor implements Command {

    private static ChordTutor chordTutor;

    private List<String> arguments;
    private Integer numberOfQuestions;
    private ChordStyle chordStyle;

    private ChordTutorTestGenerator tutor = new ChordTutorTestGenerator();

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Chord Tutor",
                CommandCategory.TUTOR,
                "chordTutor([number][, chordStyle])",
                "Run a Chord Recognition Test in the Chord Tutor tab.",
                "number\n    Optional param, defaults to 1. The \"x\" character followed by the "
                    + "number of tests to run (see examples if unclear).\n"
                    + "chordStyle\n    Optional param. The style of chords to be played.",
                "chordTutor(x10)\n    Runs 10 Chord Recognition Test questions.\n"
                    + "chordTutor(x20, arpeggio)\n    Runs 20 Chord Recognition Test questions "
                    + "with chords played as arpeggio.",
                "chordTutor(x10)"
            );
        }

        return commandManual;
    }

    /**
     * The constructor for the class with no parameters.
     */
    public ChordTutor() {
        chordTutor = this;
        numberOfQuestions = 1; // AC: Default number of tests is 1
        chordStyle = ChordStyle.UNISON; // AC: Default style is unison
    }

    /**
     * The constructor for the class with parameters.
     *
     * @param args The arguments list.
     */
    public ChordTutor(List<String> args) {
        chordTutor = this;
        arguments = args;
    }

    /**
     * Used to get the instance of the chord tutor class.
     *
     * @return The instance of the chord tutor class.
     */
    public static ChordTutor getInstance() {
        return chordTutor;
    }

    /**
     * The function that executes the commands.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        String arg0;
        String arg1;

        if (arguments != null) {
            // Splits up the arguments and assigns the numberOfQuestions variable a number
            List<String> argumentList = setArguments(arguments);
            arg0 = argumentList.get(0);
            arg1 = argumentList.get(1);

            // Displays an error if the numberOfQuestions is in the wrong format
            if (arg0 == null) {
                env.error(Error.CHORDTUTORINVALIDCHORDNUMBER.getError());
                return;
            }

            // Displays an error if the number of tests parameter is in the right format but is not
            // a number. Returns -1 if value of arg0 cannot be made integer.
            numberOfQuestions = calculateNumberOfTests(arg0);
            if (numberOfQuestions <= 0) {
                env.error(Error.CHORDTUTORINVALIDCHORDNUMBER.getError());
                return;
            }

            // Ensure chord is not null
            if (arg1 != null) {
                if (ChordStyle.fromString(arg1) == null) {
                    env.error(Error.CHORDTUTORINVALIDCHORDSTYLE.getError());
                    return;
                } else {
                    chordStyle = ChordStyle.fromString(arg1);
                }
            }
        }

        // Start chord tutor
        env.println("Starting Chord Tutor...");
        tutor.startTutorTest(numberOfQuestions);

    }

    /**
     * A helper function for finding the number of chords to test.
     *
     * @param numberOfTests the argument given in the command
     * @return an integer for the notePairs variable
     */
    private int calculateNumberOfTests(String numberOfTests) {
        try {
            return Integer.parseInt(numberOfTests);
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
        String arg0; // Typically the number of tests
        String arg1; // Typically the chord style
        Boolean arg0RelatesToNumberOfTests = false;

        try {
            arg0 = arguments.get(0);
            arg0RelatesToNumberOfTests = arg0.substring(0, 1).equals("x");
        } catch (Exception e) {
            arg0 = null;
        }

        // Try set the chord style argument
        try {
            arg1 = arguments.get(1);
        } catch (Exception ex) {
            arg1 = null;
        }

        // Deals with the arguments if the numberOfQuestions value was given
        if (arg0RelatesToNumberOfTests) {
            if (arg0.length() > 1) {
                return Arrays.asList(arg0.substring(1), arg1);
            } else {
                return Arrays.asList("1", arg1);
            }
            // Deals with the arguments numberOfQuestions value if it was omitted
        } else if (arg1 == null) {
            // Moves the chord style argument
            // into the correct position and sets the numberOfQuestions argument to default
            arg1 = arg0;
            arg0 = "1";
            return Arrays.asList(arg0, arg1);
        } else {
            return Arrays.asList(null, arg1);
        }
    }

    /**
     * Used to get the chord style.
     *
     * @return The chordStyle.
     */
    public ChordStyle getChordStyle() {
        return chordStyle;
    }

    /**
     * Get the tutor.
     *
     * @return The tutor object.
     */
    public Tutor getTutor() {
        return tutor;
    }
}
