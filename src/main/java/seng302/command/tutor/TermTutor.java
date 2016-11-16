package seng302.command.tutor;

import seng302.Environment;
import seng302.command.Command;
import seng302.controllers.MainSceneController;
import seng302.controllers.termtutor.TermQuestionController;
import seng302.controllers.termtutor.TermTutorSceneController;
import seng302.util.TermsUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;
import seng302.util.object.Term;
import seng302.workspace.Project;
import seng302.workspace.TutorRecords;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * The command class for the Term Tutor.
 *
 * @author avh17.
 */
@SuppressWarnings({"Duplicates", "unchecked"})
public class TermTutor implements Command {

    private static TermTutor termTutorInstance;

    private List<String> arguments;
    private Integer numberOfTerms;
    private Integer questionsLeftToRun;
    private Integer questionsCompleted;
    private Integer questionsAnsweredCorrectly;
    private Integer runningQuestionsIndex = 0;
    private Boolean noParams = false;
    private Boolean throwError = false;
    private Boolean noTerms = false;
    private String errorString = "";
    private String correctAnswer = "";
    private String questionText = "";
    private String questionType = "";
    private ArrayList<List<String>> currentIncorrectQuestionsArray = new ArrayList<>();
    private ArrayList<List<String>> questionsToRunArray = new ArrayList<>();
    private ArrayList<List<String>> testRecords = new ArrayList<>();

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Term Tutor",
                CommandCategory.TUTOR,
                "termTutor([number])",
                "Run a Term Recognition Test in the Term Tutor tab using terms defined in the "
                    + "users term dictionary.",
                "number\n    Optional param, defaults to 1. The \"x\" character followed by the "
                    + "number of tests to run (see examples if unclear).\n",
                "termTutor(x5)",
                "termTutor(x5)"
            );
        }

        return commandManual;
    }


    /**
     * The constructor for the class with no parameters.
     */
    public TermTutor() {
        noParams = true;
        numberOfTerms = 1;
        termTutorInstance = this;
    }

    /**
     * The constructor for the class with parameters.
     *
     * @param args The arguments list.
     */
    public TermTutor(List<String> args) {
        arguments = args;
        termTutorInstance = this;
    }

    /**
     * Used to get the instance of the term tutor class.
     *
     * @return The instance of the term tutor class.
     */
    public static TermTutor getTermTutorInstance() {
        if (termTutorInstance == null) {
            termTutorInstance = new TermTutor();
        }
        return termTutorInstance;
    }

    /**
     * The function that executes the commands.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        //Handles parameters if given.
        if (!noParams) {
            handleParameters();
        }

        //Throws an error if needed.
        if (throwError) {
            env.error(errorString);
            return;
        }

        //Reset to default values
        resetToDefaults();

        //Set the tests left to run variable
        questionsLeftToRun = numberOfTerms;

        //Switch tabs and toggle buttons
        env.println("Starting Term Tutor...");
        MainSceneController.getInstance().switchTabToTermTutor();
        TermTutorSceneController.getInstance().getCreateButton().setDisable(true);
        TermTutorSceneController.getInstance().getResetButton().setDisable(false);
        TermTutorSceneController.getInstance().getEndTestButton().setDisable(false);

        //Append text to the status field
        setStatusField();

        //Generate the tests to run
        populateTermTestsToRunArray();

        //Start a single terms test
        singleTermQuestion();
    }

    /**
     * This checks the first parameters. It will also check whether an error needs to be thrown.
     */
    private void handleParameters() {
        //The first parameter.
        String parameter = arguments.get(0);

        //This checks whether too many arguments were given.
        if (arguments.size() > 1) {
            errorString = Error.TERMTUTORTOOMANYARGUMENTS.getError();
            throwError = true;
            return;
        }

        //This checks whether the parameter is in the right format.
        if (!parameter.substring(0, 1).equals("x")) {
            errorString = Error.TERMTUTORNOIDENTIFIER.getError();
            throwError = true;
            return;
        } else {
            parameter = parameter.substring(1);
        }

        //This checks whether the parameter is an integer
        try {
            numberOfTerms = Integer.parseInt(parameter);
        } catch (Exception ex) {
            errorString = Error.TERMTUTORNOTANUMBER.getError();
            throwError = true;
            return;
        }

        //This checks whether the parameter is a valid integer (must be greater than 0)
        if (numberOfTerms < 1) {
            errorString = Error.TERMTUTORINVALIDNUMBER.getError();
            throwError = true;
        }
    }

    /**
     * Used to set the text in the status field.
     */
    private void setStatusField() {
        //Update status field
        Integer singleTest = 1;
        String status = "Running " + numberOfTerms + " term question";
        if (numberOfTerms > singleTest) {
            TermTutorSceneController.getInstance().getStatusTextField().setText(status + "s.");
        } else {
            TermTutorSceneController.getInstance().getStatusTextField().setText(status + ".");
        }
    }

    /**
     * Used to generate all the test questions and add them to an array.
     */
    private void populateTermTestsToRunArray() {
        //List of term names
        ArrayList<String> termNames = new ArrayList<>();
        Set<String> tempTermNames = Project.getInstance().viewTermDictionary().keySet();

        termNames.addAll(tempTermNames);

        Integer numberOfTermNames = termNames.size();
        Random rand = new Random();

        //Handles little or no terms being defined.
        if (numberOfTermNames <= 0) {
            questionsLeftToRun = 0;
            noTerms = true;
            String noTermsWarning = "Warning! No terms could be found. Test aborted.";
            TermTutorSceneController.getInstance().getStatusTextField().setText(noTermsWarning);
            return;
        } else if (numberOfTermNames < 3) {
            String currentStatusText =
                    TermTutorSceneController.getInstance().getStatusTextField().getText();
            String noTermsWarning = "  (Warning! There are less than 3 terms to test on)";
            TermTutorSceneController.getInstance().getStatusTextField().setText(
                    currentStatusText + noTermsWarning);
        }

        //Used to stop 2 of the same questions appearing in a row
        String previousQuestionType = "";

        for (Integer i = 0; i < questionsLeftToRun; i++) {
            //Generate the random term and attributes
            Integer randomTermIndex = rand.nextInt(termNames.size());
            String termToBeTestedOn = termNames.get(randomTermIndex);
            Term termAttributes = TermsUtil.readTerm(termToBeTestedOn);

            //Variables for the question and answer
            String questionToAsk = "";
            String answerToGive = "";
            String questionType = "";

            //While the previous question is the same or there is no question type yet
            while (questionType.equals(previousQuestionType) || questionType.equals("")) {
                //Randomly chooses a test type
                Integer randomTestTypeNumber = rand.nextInt(3);
                if (randomTestTypeNumber == 0) {
                    //The meaning is shown and the user types in the correct term.
                    questionToAsk = "What is the Term for the following Meaning \""
                            + termAttributes.getDescription() + "\"?";
                    answerToGive = termToBeTestedOn;
                    questionType = "Term";
                } else if (randomTestTypeNumber == 1) {
                    //The term is shown and the user types in the source language.
                    questionToAsk = "What is the Language of \"" + termToBeTestedOn + "\"?";
                    answerToGive = termAttributes.getLanguage();
                    questionType = "Language";
                } else {
                    //The term is shown and the user types in the meaning
                    questionToAsk = "What is the Meaning of \"" + termToBeTestedOn + "\"?";
                    answerToGive = termAttributes.getDescription();
                    questionType = "Description";
                }
            }
            previousQuestionType = questionType;
            questionsToRunArray.add(Arrays.asList(questionToAsk, answerToGive, questionType));
        }
    }

    /**
     * This function runs a single question.
     */
    public void singleTermQuestion() {
        Integer noQuestions = 0;
        if (questionsLeftToRun > noQuestions) {
            //Disable the previous question
            if (questionsCompleted > noQuestions) {
                TermQuestionController.getInstance().disableQuestion();
            }

            //Gets the current question
            List<String> currentQuestion = questionsToRunArray.get(runningQuestionsIndex);
            questionText = currentQuestion.get(0);
            correctAnswer = currentQuestion.get(1);
            questionType = currentQuestion.get(2);
            runningQuestionsIndex++;

            //Builds a question and adds it to the scene
            buildNewQuestion();

            //Sets the text on the current question
            TermQuestionController.getInstance().setQuestionText(questionText, questionType);

            //Decrement tests left to run
            questionsLeftToRun--;
        } else {
            handleEndOfTest();
        }
    }

    /**
     * This function creates a test object and adds it to the scroll pane.
     */
    private void buildNewQuestion() {
        //Builds a new question and adds it to the pane
        TermTutorSceneController.getInstance().addCurrentQuestionToScene();

        //Sets the question number
        Integer questionNumber = (numberOfTerms - questionsLeftToRun) + 1;
        TermQuestionController.getInstance().setQuestionNumberText(questionNumber.toString());
    }

    /**
     * This function is called when the end of the test is reached.
     * Also called when the end test button is pressed.
     */
    public void handleEndOfTest() {
        questionsLeftToRun = 0;

        //Disables previous question
        if (!noTerms) {
            TermQuestionController.getInstance().disableQuestion();
        }

        //Only calculates results if 1 or more question has been answered.
        if (questionsCompleted > 0) {
            //Calculates the results
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);

            Double correctTests = (double) questionsAnsweredCorrectly;
            Double totalTests = (double) numberOfTerms;

            Double scorePercentage = (correctTests / totalTests) * 100.0;

            Integer incorrectTests = questionsCompleted - questionsAnsweredCorrectly;

            //Create a result string and append to the results text field
            String resultsFieldString = "";

            resultsFieldString = resultsFieldString.concat(
                    "Your score was " + questionsAnsweredCorrectly + "/" + questionsCompleted);

            resultsFieldString = resultsFieldString.concat(
                    " (" + df.format(scorePercentage) + "%) ");

            resultsFieldString = resultsFieldString.concat(
                    "You had " + incorrectTests + " incorrect question");

            if (questionsCompleted > 1) {
                resultsFieldString += "s";
            }

            TermTutorSceneController.getInstance().getTestResultsTextField().setText(
                    resultsFieldString);
            TutorRecords.getInstance().testRecordSaving("Term Tutor", testRecords);
        }

        //Toggles Buttons
        TermTutorSceneController.getInstance().getCreateButton().setDisable(false);
        TermTutorSceneController.getInstance().getEndTestButton().setDisable(true);
        TermTutorSceneController.getInstance().getResetButton().setDisable(false);

        if (questionsAnsweredCorrectly < questionsCompleted) {
            TermTutorSceneController.getInstance().getRetryTestsButton().setDisable(false);
        }
    }

    /**
     * Called by the GUI to check the current answer.
     *
     * @param answer The answer received from the GUI.
     * @return A boolean indicating whether the answer is correct or false.
     */
    public Boolean handleCheckAnswer(String answer) {
        Boolean answerCorrect = false;
        questionsCompleted++;
        String answerText;

        if (answer.equals(correctAnswer)) {
            questionsAnsweredCorrectly++;
            answerCorrect = true;
            answerText = "Correct";
        } else {
            currentIncorrectQuestionsArray.add(Arrays.asList(
                    questionText, correctAnswer, questionType));
            answerText = "Incorrect";
        }

        //Create a question record and add it to the test records
        List<String> questionRecord = Arrays.asList(questionText, answer, answerText);
        testRecords.add(questionRecord);

        return answerCorrect;
    }

    /**
     * Called by the GUI to handle retrying tests.
     */
    public void handleRetryTests() {
        questionsToRunArray = currentIncorrectQuestionsArray;
        resetToDefaults();
        numberOfTerms = questionsToRunArray.size();
        questionsLeftToRun = questionsToRunArray.size();
        setStatusField();
        singleTermQuestion();
    }

    /**
     * Used to reset values to defaults.
     * Also used by the GUI when the reset button is pressed.
     */
    public void resetToDefaults() {
        questionsLeftToRun = 0;
        questionsCompleted = 0;
        questionsAnsweredCorrectly = 0;
        noParams = false;
        throwError = false;
        noTerms = false;
        errorString = "";
        correctAnswer = "";
        questionType = "";
        runningQuestionsIndex = 0;
        currentIncorrectQuestionsArray = new ArrayList<>();
        testRecords = new ArrayList<>();

        //Resets the GUI
        TermTutorSceneController.getInstance().resetToInitialState();
    }

    /**
     * Getter for the current answer.
     *
     * @return The answer.
     */
    public String getAnswer() {
        return correctAnswer;
    }

}
