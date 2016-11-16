package seng302.command.tutor;

import seng302.Environment;
import seng302.command.Command;
import seng302.tutor.ChordSpellingTutorTestGenerator;
import seng302.util.enumerator.ChordQuality;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Command class for the Chord Spelling Tutor.
 *
 * @author avh17.
 */
public class ChordSpellingTutor implements Command {

    private static ChordSpellingTutor chordSpellingTutor;

    private List<String> arguments;
    private List<ChordQuality> chordTypes = new ArrayList<>();
    private Boolean noEnharmonic = false;
    private Boolean randomNotes = false;

    private static CommandManual commandManual;
    private ChordSpellingTutorTestGenerator tutor = new ChordSpellingTutorTestGenerator();

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Chord Spelling Tutor",
                CommandCategory.TUTOR,
                "spellingTutor([number][, constraints][, noEnharmonic][, randomNotes]) ",
                "Run a Chord Spelling Test in the Spelling Tutor tab.",
                "number\n    Optional param, defaults to 1. The \"x\" character followed by the "
                        + "number of tests to run (see examples if unclear).\n"
                        + "constraints\n    Optional param. The styles of chords to be included.\n"
                        + "noEnharmonic\n    Optional param. Flag for allowing enharmonic chords.\n"
                        + "randomNotes\n    Optional param. Flag for allowing random note chords.",
                "spellingTutor(x10)\n    Runs 10 Chord Spelling Test questions.\n"
                        + "spellingTutor(x20, Major)\n    Runs 20 Chord Spelling Test questions "
                        + "with only major chords included.",
                "spellingTutor(x10)"
            );
        }

        return commandManual;
    }

    /**
     * The constructor for the class with no parameters.
     */
    public ChordSpellingTutor() {
        chordSpellingTutor = this;
        arguments = new ArrayList<>();
    }

    /**
     * The constructor for the class with parameters.
     *
     * @param args The arguments list.
     */
    public ChordSpellingTutor(List<String> args) {
        chordSpellingTutor = this;
        arguments = args;
    }

    /**
     * Used to get the instance of the chord spelling tutor class.
     *
     * @return The instance of the chord spelling tutor class.
     */
    public static ChordSpellingTutor getInstance() {
        return chordSpellingTutor;
    }

    /**
     * The function that executes the commands.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        Integer numberOfQuestions;

        if (arguments.size() == 0) {
            // Handles no arguments given
            numberOfQuestions = 1;
            chordTypes.addAll(Arrays.asList(ChordQuality.values()));
            noEnharmonic = false;
            randomNotes = false;
        } else {
            // Checks the first argument
            String firstArg = arguments.get(0);
            if (firstArg.substring(0, 1).equals("x")) {
                try {
                    numberOfQuestions = Integer.parseInt(firstArg.substring(1));
                    arguments.remove(firstArg);
                    if (numberOfQuestions <= 0) {
                        env.error(Error.SPELLINGTUTORINVALIDARGUMENTS.getError());
                        return;
                    }
                } catch (Exception e) {
                    env.error(Error.SPELLINGTUTORINVALIDARGUMENTS.getError());
                    return;
                }
            } else {
                numberOfQuestions = 1;
            }

            // Handle any arguments that relate to a chord constraint
            if (arguments.size() > 0) {
                List<String> argumentsLeft = new ArrayList<>();
                argumentsLeft.addAll(arguments);
                for (String argument: argumentsLeft) {
                    ChordQuality convertedArgument = ChordQuality.fromString(argument);
                    if (convertedArgument != null) {
                        chordTypes.add(convertedArgument);
                        arguments.remove(argument);
                    }
                }
            }

            // Handles any arguments that are flags
            if (arguments.size() > 0) {
                List<String> argumentsLeft = new ArrayList<>();
                argumentsLeft.addAll(arguments);
                for (String argument: argumentsLeft) {
                    if (argument.equalsIgnoreCase("noEnharmonic") && !noEnharmonic) {
                        noEnharmonic = true;
                        arguments.remove(argument);
                    } else if (argument.equalsIgnoreCase("randomNotes") && !randomNotes) {
                        randomNotes = true;
                        arguments.remove(argument);
                    }
                }
            }

            // Throws an error if there are any arguments left
            if (arguments.size() > 0) {
                env.error(Error.SPELLINGTUTORINVALIDARGUMENTS.getError());
                return;
            }

            // Adds all chord types if none where specified
            if (chordTypes.size() == 0) {
                chordTypes.addAll(Arrays.asList(ChordQuality.values()));
            }
        }

        env.println("Starting Chord Spelling Tutor...");
        tutor.startTutorTest(numberOfQuestions);
    }

    /**
     * Used to get the tutor test generator.
     *
     * @return The tutor test generator.
     */
    public ChordSpellingTutorTestGenerator getTutor() {
        return tutor;
    }

    /**
     * Used to get the no enharmonic boolean.
     *
     * @return The no enharmonic boolean.
     */
    public Boolean getNoEnharmonic() {
        return noEnharmonic;
    }

    /**
     * Used to get the random notes boolean.
     *
     * @return The random notes boolean.
     */
    public Boolean getRandomNotes() {
        return randomNotes;
    }

    /**
     * Used to get the chord constraints.
     *
     * @return The chord constraints.
     */
    public List<ChordQuality> getChordTypes() {
        return chordTypes;
    }
}
