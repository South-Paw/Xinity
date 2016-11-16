package seng302.tutor;

import seng302.controllers.MainSceneController;
import seng302.controllers.commontutor.TutorSceneController;
import seng302.workspace.TutorRecords;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used to handle the tutor logic for all tutors.
 */
public abstract class Tutor {

    /**
     * Enum for tutor types.
     */
    public enum TutorType {
        // Order must match enum MainSceneController.TabList
        // REALLY, IT HAS TO
        PITCH("pitch"),
        INTERVAL("interval"),
        TERM("term"),
        SCALE("scale"),
        CHORD("chord"),
        SIGNATURE("signature"),
        SPELLING("spelling");

        private String text;

        TutorType(String text) {
            this.text = text;
        }

        public String toString() {
            return this.text;
        }

        /**
         * Tries to get a valid tutor type.
         * @param inputType The given tutortype as string.
         * @return The valid tutor type or null.
         */
        public static TutorType fromString(String inputType) {
            if (inputType != null) {
                for (TutorType tutorType : TutorType.values()) {
                    if (inputType.equalsIgnoreCase(tutorType.text)) {
                        return tutorType;
                    }
                }
            }
            return null;
        }
    }

    protected String tutorName;
    protected Integer numberOfQuestions;
    protected Integer questionsLeftToRun;
    protected Integer questionsCompleted;
    protected Integer questionsAnsweredCorrectly;
    protected Integer runningQuestionsIndex = 0;
    protected String correctAnswer = "";
    protected Boolean doNotRunTest = false;
    protected ArrayList<List<String>> currentIncorrectQuestionsArray = new ArrayList<>();
    protected ArrayList<List<String>> questionsToRunArray = new ArrayList<>();
    protected ArrayList<List<String>> testRecords = new ArrayList<>();
    protected List<String> currentQuestion;
    protected TutorSceneController tutorSceneController;
    protected TutorType tutorType;

    /**
     * The function called to start a tutor test.
     */
    public void startTutorTest(Integer questionsToRun) {
        resetToDefaults();
        //Set the amount of questions to run
        questionsLeftToRun = questionsToRun;
        numberOfQuestions = questionsToRun;
        //Switch tab, reset to defaults, get questions and toggle buttons
        MainSceneController.getInstance().switchTabToTutor(tutorType);
        tutorSceneController.resetToInitialState();
        questionsToRunArray = generateQuestions(numberOfQuestions);
        tutorSceneController.startTestButtonToggle();
        setStatusField();
        //Start the first question
        runSingleTutorQuestion();
    }

    /**
     * Used to set the text in the status field.
     */
    private void setStatusField() {
        //Creates the status string
        String status = "Running " + numberOfQuestions + " " + tutorName + " Question";
        if (numberOfQuestions > 1) {
            status += "s";
        }
        //Sets the status field on the desired controller
        tutorSceneController.getStatusTextField().setText(status);
    }

    /**
     * This function runs a single question.
     */
    public void runSingleTutorQuestion() {
        if (questionsLeftToRun > 0) {
            //Disable the previous question
            if (questionsCompleted > 0) {
                tutorSceneController.getQuestion().disableQuestion();
            }

            //Gets the current question
            currentQuestion = questionsToRunArray.get(runningQuestionsIndex);
            correctAnswer = currentQuestion.get(0);
            runningQuestionsIndex++;

            //Builds a question and adds it to the scene
            buildNewQuestion();

            //Decrement tests left to run
            questionsLeftToRun--;
        } else {
            handleEndOfTest();
        }
    }


    /**
     * This function creates a question object and adds it to the scroll pane.
     */
    private void buildNewQuestion() {
        Integer questionNumber = (numberOfQuestions - questionsLeftToRun) + 1;
        tutorSceneController.addQuestionToScene();
        tutorSceneController.getQuestion().getQuestionNumberText()
                .setText("Test #" + questionNumber);
    }

    /**
     * This function is called when the end of the test is reached.
     * Also called when the end test button is pressed.
     */
    public void handleEndOfTest() {
        buttonToggle();

        if (questionsCompleted < 1) {
            return;
        }

        tutorSceneController.getQuestion().disableQuestion();

        //Calculates the results
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        Double correctTests = (double) questionsAnsweredCorrectly;
        Double totalTests = (double) questionsCompleted;
        Double scorePercentage = (correctTests / totalTests) * 100.f;
        Integer incorrectTests = questionsCompleted - questionsAnsweredCorrectly;

        //Create a result string and append to the results text field
        String resultsFieldString = "";

        resultsFieldString = resultsFieldString.concat(
                "Your score was " + questionsAnsweredCorrectly + "/" + questionsCompleted);

        resultsFieldString = resultsFieldString.concat(
                " (" + df.format(scorePercentage) + "%) ");

        resultsFieldString = resultsFieldString.concat(
                "You had " + incorrectTests + " incorrect question");
        if (incorrectTests > 1) {
            resultsFieldString += "s";
        }

        //Set the results field on the appropriate tutor.
        tutorSceneController.getTestResultsTextField().setText(resultsFieldString);
        TutorRecords.getInstance().testRecordSaving(tutorName, testRecords);
    }

    /**
     * Used to toggle the buttons on various tutors.
     */
    private void buttonToggle() {
        Boolean retryTests = false;
        if (questionsAnsweredCorrectly < questionsCompleted) {
            retryTests = true;
        }
        tutorSceneController.endTestButtonToggle(retryTests);
    }

    /**
     * Called by the GUI to handle retrying tests.
     */
    public void handleRetryTests() {
        questionsToRunArray = currentIncorrectQuestionsArray;
        resetToDefaults();
        numberOfQuestions = questionsToRunArray.size();
        questionsLeftToRun = questionsToRunArray.size();
        setStatusField();
        runSingleTutorQuestion();
    }

    /**
     * Used to reset values to defaults.
     */
    private void resetToDefaults() {
        questionsLeftToRun = 0;
        questionsCompleted = 0;
        questionsAnsweredCorrectly = 0;
        doNotRunTest = false;
        correctAnswer = "";
        runningQuestionsIndex = 0;
        currentIncorrectQuestionsArray = new ArrayList<>();
        testRecords = new ArrayList<>();

        //Resets the GUI
        tutorSceneController.resetToInitialState();
    }

    public List<String> getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     * Handles answer checking. Returns true if correct.
     *
     * @param answer The answer given.
     * @return A boolean.
     */
    public Boolean checkAnswer(String answer) {
        Boolean answerCorrect;
        String answerText;
        questionsCompleted++;

        if (answer.equalsIgnoreCase(correctAnswer)) {
            answerCorrect = true;
            answerText = "Correct";
            questionsAnsweredCorrectly++;
        } else {
            currentIncorrectQuestionsArray.add(currentQuestion);
            answerText = "Incorrect";
            answerCorrect = false;
        }

        //Create a question record and add it to the test records
        List<String> questionRecord = Arrays.asList(getQuestionString(), answer, answerText);
        testRecords.add(questionRecord);

        return answerCorrect;
    }

    /**
     * Generates the question string used in recording scores.
     *
     * @return the question string.
     */
    protected abstract String getQuestionString();

    /**
     * Used to get the correct answer for the current question.
     *
     * @return The correct answer.
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Used to populate the  questions array.
     *
     * @param numberOfQuestions The amount of questions to add.
     * @return The question list.
     */
    protected abstract ArrayList<List<String>> generateQuestions(Integer numberOfQuestions);

}
