package seng302.command.tutor;

import seng302.Environment;
import seng302.command.Command;
import seng302.controllers.MainSceneController;
import seng302.controllers.intervaltutor.IntervalTutorSceneController;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;

import java.util.List;

/**
 * The tutor class for musical terms.
 *
 * @author avh17
 */
public class IntervalTutor implements Command {
    List<String> arguments;
    private Integer intervalTests;
    private Boolean validArguments;
    private Integer intervalTestsLowerLimit = 0;

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Interval Tutor",
                CommandCategory.TUTOR,
                "intervalTutor([number])",
                "Run a Interval Recognition Test in the Interval Tutor tab.",
                "number\n    Optional param, defaults to 1. The \"x\" character followed by the "
                    + "number of tests to run (see examples if unclear).",
                "intervalTutor(x10)\n    Run an interval tutor with 10 tests.",
                "intervalTutor(x10)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor for the interval tutor with no parameters.
     */
    public IntervalTutor() {
        intervalTests = 1;
        validArguments = true;
    }

    /**
     * Constructor for the interval tutor with parameters.
     *
     * @param args The parameters
     */
    public IntervalTutor(List<String> args) {
        arguments = args;
    }

    /**
     * The execute function.
     *
     * @param env The environment
     */
    public void execute(Environment env) {
        if (arguments != null) {
            validArguments = setArguments();
        }

        if (!validArguments) {
            env.error(Error.INTERVALTTUTORINVALIDARGUMENTS.getError()
                    + "\n" + Error.INTERVALTUTORINVALIDEXPLANATION.getError());
            return;
        }

        //Initiate the interval tests
        env.println("Starting Interval Tutor...");
        MainSceneController.getInstance().switchTabToIntervalTutor();
        IntervalTutorSceneController.getInstance().resetToDefaults();
        IntervalTutorSceneController.getInstance().intervalRecognitionTest(intervalTests);
    }

    /**
     * Used to determine whether the argument given is valid.
     *
     * @return A boolean value indicating whether the argument is valid
     */
    private boolean setArguments() {
        String givenArgument;

        //Try get an argument from the arguments list
        try {
            givenArgument = arguments.get(0);
        } catch (Exception ex) {
            return false;
        }

        //Check whether the argument has the 'x' identifier in front of it
        if (givenArgument.substring(0, 1).equals("x")) {
            //Check if the value after the identifier is a valid integer and assign it
            try {
                intervalTests = Integer.parseInt(givenArgument.substring(1));
                if (intervalTests <= intervalTestsLowerLimit) {
                    return false;
                }
            } catch (Exception ex) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
