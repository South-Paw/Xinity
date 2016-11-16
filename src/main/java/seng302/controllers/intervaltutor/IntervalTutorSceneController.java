package seng302.controllers.intervaltutor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import seng302.util.IntervalUtil;
import seng302.util.NoteUtil;
import seng302.util.SemitoneUtil;
import seng302.util.enumerator.Interval;
import seng302.util.map.XinityIntervalMap;
import seng302.util.object.XinityNote;
import seng302.workspace.TutorRecords;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Controller for the Interval Tutor Scene Controller.
 *
 * @author avh17
 */
@SuppressWarnings({"unchecked", "unused"})
public class IntervalTutorSceneController {

    private static IntervalTutorSceneController intervalTutorSceneController;

    @FXML private Button createTestButton;
    @FXML private Button endTestButton;
    @FXML private Button retryTestsButton;
    @FXML private Button resetButton;
    @FXML private Text testStatusTextField;
    @FXML private Text testResultsTextField;
    @FXML private VBox contentBox = new VBox();
    @FXML private ScrollPane testScrollPane;

    private Integer totalTestsToRun = 0;
    private Integer testsLeftToRun = 0;
    private String correctAnswer;
    private Integer testsCompleted = 0;
    private Integer testsAnsweredCorrectly = 0;
    private Integer firstPlayedMidiNote = 0;
    private Integer secondPlayedMidiNote = 0;
    private Integer reRunTestsIndex = 0;
    private ArrayList<List<String>> incorrectTests = new ArrayList<>();
    private ArrayList<List<String>> incorrectTestsToReRun;
    private Boolean runningIncorrectTests = false;
    private Boolean startIntervalRecognitionTest = false;
    private ArrayList<List<String>> testRecords = new ArrayList<>();

    /**
     * Constructor for the Interval tutor scene controller.
     */
    public IntervalTutorSceneController() {
        intervalTutorSceneController = this;
    }

    /**
     * Use to get the instance of the Interval Tutor Scene Controller.
     *
     * @return The Interval Tutor Scene Controller.
     */
    public static IntervalTutorSceneController getInstance() {
        return intervalTutorSceneController;
    }

    /**
     * Deals with the create test button being pressed.
     *
     * @param event The button event.
     * @throws Exception Unable to open fxml file.
     */
    @FXML
    private void createTestButtonPressed(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        Parent root;

        if (event.getSource() == createTestButton) {
            //Loads the popup
            root = FXMLLoader.load(getClass().getResource(
                    "/scenes/intervaltutor/IntervalTutorPopUp.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("New Interval Recognition Test");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(createTestButton.getScene().getWindow());
            stage.showAndWait();
            if (startIntervalRecognitionTest) {
                resetToDefaults();
                initiateIntervalTestFromPopUp();
            }
        } else {
            stage = (Stage) createTestButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * This handles the end button being pressed.
     *
     * @param event The button event.
     */
    @FXML
    private void endTestButtonPressed(ActionEvent event) {
        testsLeftToRun = 0;
        endTestButton.setDisable(true);
        if (incorrectTests.size() > 0) {
            retryTestsButton.setDisable(false);
        }
        runASingleTest();
    }

    /**
     * Handles the retry tests button being pressed.
     *
     * @param event The button event.
     */
    @FXML
    private void retryTestsButtonPressed(ActionEvent event) {
        incorrectTestsToReRun = incorrectTests;
        resetToDefaults();
        runningIncorrectTests = true;
        intervalRecognitionTest(incorrectTestsToReRun.size());
    }

    /**
     * Handles the reset button being pressed.
     *
     * @param event The button event.
     */
    @FXML
    private void resetButtonPressed(ActionEvent event) {
        resetToDefaults();
    }

    /**
     * Called when an interval test is started from the GUI.
     */
    public void initiateIntervalTestFromPopUp() {
        //Get the values from the pop up
        IntervalTutorPopUpController intervalPopup = IntervalTutorPopUpController.getInstance();
        Integer amountOfTestsToRun = intervalPopup.getNumberOfIntervalTests();

        //Start the interval recognition test
        intervalRecognitionTest(amountOfTestsToRun);
    }

    /**
     * This is the starting point for any interval recognition test.
     *
     * @param numberOfTests The number of tests to be run.
     */
    public void intervalRecognitionTest(Integer numberOfTests) {
        //Toggle Buttons
        retryTestsButton.setDisable(true);
        createTestButton.setDisable(true);
        endTestButton.setDisable(false);
        resetButton.setDisable(false);

        //Assign values
        totalTestsToRun = numberOfTests;
        testsLeftToRun = numberOfTests;

        //Update status field
        Integer singleTest = 1;
        if (numberOfTests > singleTest) {
            testStatusTextField.setText("Running " + numberOfTests + " Interval questions");
        } else {
            testStatusTextField.setText("Running " + numberOfTests + " Interval question");
        }

        //Start the the first test
        runASingleTest();
    }

    /**
     * This function is called each time an individual test is run.
     */
    public void runASingleTest() {
        if (testsLeftToRun > 0) {
            //Disables the next button of the previous test
            if (IntervalQuestionController.getInstance() != null) {
                IntervalQuestionController.getInstance().disablePreviousTest();
            }

            //Creates a new individual test and adds it to the scroll pane
            buildNewTest();

            //Generate the random interval for the test
            try {
                generateRandomInterval();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Decrements the tests left to run variable
            testsLeftToRun--;
        } else {
            //Handles the end of the test
            handleEndOfTest();
        }
    }

    /**
     * This function creates a test object and adds it to the scroll pane.
     */
    private void buildNewTest() {
        Pane newPane = new Pane();
        //Loads the test fxml
        try {
            newPane = FXMLLoader.load(getClass().getResource(
                    "/scenes/intervaltutor/IntervalTutorQuestion.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Sets the test number and drop down menu on the test
        IntervalQuestionController question = IntervalQuestionController.getInstance();
        question.setTestNumber((totalTestsToRun - testsLeftToRun) + 1);
        question.setPitchChoiceDropDown();

        //Adds the test to the scroll pane
        contentBox.getChildren().addAll(newPane);
        newPane.prefWidthProperty().bind(testScrollPane.widthProperty());
        testScrollPane.setContent(contentBox);
        testScrollPane.setVvalue(1);
    }

    /**
     * This function generates and assigns the random interval and notes, it also deals with
     * re-running incorrect tests.
     *
     * @throws Exception Values cannot be assigned.
     */
    private void generateRandomInterval() throws Exception {
        if (runningIncorrectTests) {
            //Read from incorrect tests
            List<String> incorrectTest = incorrectTestsToReRun.get(reRunTestsIndex);
            firstPlayedMidiNote = Integer.parseInt((incorrectTest).get(0));
            secondPlayedMidiNote = Integer.parseInt((incorrectTest).get(1));
            correctAnswer = incorrectTest.get(2);
            reRunTestsIndex++;
        } else {
            try {
                //Generate a random midi note and a random interval index
                Random rand = new Random();
                Integer minMidi = 60;
                Integer maxMidi = 71;
                firstPlayedMidiNote = rand.nextInt(maxMidi + 1 - minMidi) + minMidi;
                Integer randomGeneratedIntervalIndex =
                        rand.nextInt(IntervalUtil.getIntervals().size());


                //Find a random interval
                correctAnswer = Interval.toString(
                        IntervalUtil.getInterval(randomGeneratedIntervalIndex));

                //Convert the midi note to a note name
                String firstNoteName = NoteUtil.convertToNote(firstPlayedMidiNote);

                //Get the second note relating to the interval
                Integer semitones = IntervalUtil.getSemitones(Interval.fromString(correctAnswer));
                XinityNote secondNote = SemitoneUtil.neighbouringNote(
                        semitones,
                        new XinityNote(firstNoteName));

                secondPlayedMidiNote = NoteUtil.convertToMidi(secondNote);
            } catch (Exception ex) {
                // On the off chance the interval command returns a value that
                // is not on the midi scale
                generateRandomInterval();
            }
        }
    }

    /**
     * Handles what happens all the tests are finished.
     */
    private void handleEndOfTest() {
        //Disables the last test
        IntervalQuestionController.getInstance().disablePreviousTest();

        //Only calculates results if at least one test has been completed
        if (testsCompleted > 0) {
            //Calculates the results
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            Double correctTests = (double) testsAnsweredCorrectly;
            Double totalTests = (double) totalTestsToRun;
            Double scorePercentage = (correctTests / totalTests) * 100.0;
            Integer incorrectPairs = testsCompleted - testsAnsweredCorrectly;

            //Create a result string and append to the results text field
            String resultsFieldString = "";

            resultsFieldString = resultsFieldString.concat("Your score was "
                    + testsAnsweredCorrectly + "/" + testsCompleted);

            resultsFieldString = resultsFieldString.concat(" (" + df.format(scorePercentage)
                    + "%) ");

            resultsFieldString = resultsFieldString.concat("You had " + incorrectPairs
                    + " incorrect question");

            if (testsCompleted > 1) {
                resultsFieldString += "s";
            }

            testResultsTextField.setText(resultsFieldString);
            TutorRecords.getInstance().testRecordSaving("Interval Tutor", testRecords);


        }

        //Toggle buttons
        resetButton.setDisable(false);
        endTestButton.setDisable(true);
        createTestButton.setDisable(false);
        if (incorrectTests.size() > 0) {
            retryTestsButton.setDisable(false);
        }


    }

    /**
     * Used to reset all values to default.
     */
    public void resetToDefaults() {
        //Toggle Buttons
        createTestButton.setDisable(false);
        endTestButton.setDisable(true);
        retryTestsButton.setDisable(true);
        resetButton.setDisable(true);

        //Reset values
        startIntervalRecognitionTest = false;
        totalTestsToRun = 0;
        testsLeftToRun = 0;
        testsCompleted = 0;
        firstPlayedMidiNote = 0;
        secondPlayedMidiNote = 0;
        testsAnsweredCorrectly = 0;
        reRunTestsIndex = 0;
        testStatusTextField.setText("");
        testResultsTextField.setText("");
        incorrectTests = new ArrayList<>();
        contentBox = new VBox();
        testScrollPane.setContent(contentBox);
        runningIncorrectTests = false;
        testRecords = new ArrayList<>();
    }

    /**
     * This is used by the individual interval test controller to determine whether the given
     * answer is correct.
     *
     * @param answer The answer String.
     * @return A boolean value indicating whether the answer is correct or incorrect.
     */
    public boolean checkAnswer(String answer) {
        testsCompleted++;
        Boolean returnBool;
        String answerText;
        String firstStringNote = firstPlayedMidiNote.toString();
        String secondStringNote = secondPlayedMidiNote.toString();

        if (answer.equals(correctAnswer)) {
            testsAnsweredCorrectly++;
            returnBool = true;
            answerText = "Correct";
        } else {
            incorrectTests.add(Arrays.asList(firstStringNote, secondStringNote, correctAnswer));
            returnBool = false;
            answerText = "Incorrect";
        }

        //Create a question record and add it to the test records
        String question = "Which interval do the notes "
                + NoteUtil.convertToNote(firstPlayedMidiNote) + " and "
                + NoteUtil.convertToNote(secondPlayedMidiNote) + " belong to?";

        List<String> questionRecord = Arrays.asList(question, answer, answerText);
        testRecords.add(questionRecord);

        return returnBool;
    }

    /**
     * Getter for the correct answer.
     *
     * @return The correct answer.
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Getter for the firstPlayedMidiNote.
     *
     * @return the firstPlayedMidiNote.
     */
    public Integer getFirstPlayedMidiNote() {
        return firstPlayedMidiNote;
    }

    /**
     * Getter for the secondPlayedMidiNote.
     *
     * @return The secondPlayedMidiNote.
     */
    public Integer getSecondPlayedMidiNote() {
        return secondPlayedMidiNote;
    }

    /**
     * Used by the Pop Up Controller to start an interval recognition test.
     */
    public void startTest() {
        startIntervalRecognitionTest = true;
    }
}
