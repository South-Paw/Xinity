package seng302.command.tutor;

import seng302.Environment;
import seng302.command.Command;
import seng302.tutor.SignatureTutorTestGenerator;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;

import java.util.Arrays;
import java.util.List;

/**
 * Command class for the signature tutor.
 */
public class SignatureTutor implements Command {
    private static SignatureTutor signatureTutor;

    private List<String> arguments;
    private Integer numberOfQuestions;
    private String signatureSpecifier;
    private String scaleNameType;
    private Boolean throwError = false;
    private String errorString;
    private List<String> acceptedSignatureSpecifiers = Arrays.asList("number", "notes");
    private List<String> acceptedScaleNameType = Arrays.asList("major", "minor", "both");

    private SignatureTutorTestGenerator tutor = new SignatureTutorTestGenerator();

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Signature Tutor",
                CommandCategory.TUTOR,
                "signatureTutor([number][, signatureSpecifier][, scaleType])",
                "Run a Signature Recognition Test in the Signature Tutor tab.",
                "number\n    Optional param, defaults to 1. The \"x\" character followed by the "
                    + "number of tests to run (see examples if unclear).\n"
                    + "signatureSpecifier\n    \"numbers\" or \"notes\" are accepted "
                    + "and specifies how the signature must be represented.\n"
                    + "scaleType\n    Accepts \"major\", \"minor\", or \"both\""
                    + ", scale types to be quizzed on.",
                "signatureTutor(x10, number, major)\n"
                    + "    Runs tutor with 10 tests, signatures "
                    + " as numbers and uses the major scale\n"
                    + "signatureTutor(number, minor)\n"
                    + "    Runs tutor with 1 test, signatures "
                    + " as numbers and uses the minor scale\n"
                    + "signatureTutor(x5, notes, both)\n"
                    + "    Runs tutor with 5 tests, signatures "
                    + " as notes and uses both the major and minor scales\n",
                "signatureTutor(x10, number, major)"
            );
        }

        return commandManual;
    }

    /**
     * The constructor for the class with no parameters.
     */
    public SignatureTutor() {
        signatureTutor = this;
        throwError = true;
        errorString = Error.SIGNATURETUTORNOARGSGIVEN.getError();
    }

    /**
     * The constructor for the class with parameters.
     *
     * @param args The arguments list.
     */
    public SignatureTutor(List<String> args) {
        signatureTutor = this;
        arguments = args;
    }

    /**
     * Used to get the instance of the signature tutor class.
     *
     * @return The instance of the signature tutor class.
     */
    public static SignatureTutor getInstance() {
        return signatureTutor;
    }

    /**
     * The function that executes the commands.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {

        // Handles not enough arguments
        if (arguments != null && arguments.size() < 2 && !throwError) {
            throwError = true;
            errorString = Error.SIGNATURETUTORNOARGSGIVEN.getError();
        }

        // Handles too many arguments
        if (arguments != null && arguments.size() > 3 && !throwError) {
            throwError = true;
            errorString = Error.SIGNATURETUTORTOOMANYARGSGIVEN.getError();
        }

        // Handles the correct number of arguments given
        if (arguments != null && arguments.size() >= 2 && arguments.size() <= 3 && !throwError) {
            handleArguments();
        }

        if (throwError) {
            env.error(errorString);
        } else {
            env.println("Starting Signature Tutor...");
            tutor.startTutorTest(numberOfQuestions);
        }
    }

    /**
     * Used to handle the given arguments.
     */
    private void handleArguments() {
        // Handles all three arguments
        if (arguments.size() == 3) {
            String arg1 = arguments.get(0);
            String arg2 = arguments.get(1);
            String arg3 = arguments.get(2);

            Boolean argOneCheck = arg1.substring(0,1).equals("x");
            Boolean argTwoCheck = acceptedSignatureSpecifiers.contains(arg2.toLowerCase());
            Boolean argThreeCheck = acceptedScaleNameType.contains(arg3.toLowerCase());

            if (!argOneCheck || !argTwoCheck || !argThreeCheck) {
                throwError = true;
                errorString = Error.SIGNATURETUTORINVALIDARGUMENTS.getError();
                return;
            }

            try {
                numberOfQuestions = Integer.parseInt(arg1.substring(1));
                signatureSpecifier = arg2;
                scaleNameType = arg3;
            } catch (Exception e) {
                throwError = true;
                errorString = Error.SIGNATURETUTORINVALIDARGUMENTS.getError();
            }

            // Handles a number less than or equal to 0
            if (numberOfQuestions <= 0) {
                throwError = true;
                errorString = Error.SIGNATURETUTORINVALIDARGUMENTS.getError();
            }
        } else {
            String arg1 = arguments.get(0);
            String arg2 = arguments.get(1);

            Boolean argOneCheck = acceptedSignatureSpecifiers.contains(arg1.toLowerCase());
            Boolean argTwoCheck = acceptedScaleNameType.contains(arg2.toLowerCase());

            if (!argOneCheck || !argTwoCheck) {
                throwError = true;
                errorString = Error.SIGNATURETUTORINVALIDARGUMENTS.getError();
                return;
            }

            // Assigns variables (number of questions default is 1)
            numberOfQuestions = 1;
            signatureSpecifier = arg1;
            scaleNameType = arg2;
        }
    }

    /**
     * Get the tutor.
     *
     * @return The tutor object.
     */
    public SignatureTutorTestGenerator getTutor() {
        return tutor;
    }

    /**
     * Used to get the signature specifier.
     *
     * @return The signature specifier.
     */
    public String getSignatureSpecifier() {
        return signatureSpecifier;
    }

    /**
     * Used to get the scale name type.
     *
     * @return The scale name type.
     */
    public String getScaleNameType() {
        return scaleNameType;
    }
}
