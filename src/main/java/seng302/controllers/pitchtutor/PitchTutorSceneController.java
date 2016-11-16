package seng302.controllers.pitchtutor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import seng302.util.NoteUtil;
import seng302.workspace.TutorRecords;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * The controller class for the Pitch Tutor Scene.
 *
 * @author avh17.
 */
@SuppressWarnings({"unused", "unchecked"})
public class PitchTutorSceneController {

    private static PitchTutorSceneController pitchTutorSceneController;

    //All the GUI elements
    @FXML private Button createTestButton;
    @FXML private Button endTestButton;
    @FXML private Button retryTestsButton;
    @FXML private Button resetButton;
    @FXML private Text testStatusTextField;
    @FXML private ScrollPane testScrollPane;
    @FXML private VBox contentBox = new VBox();
    @FXML private Text testResultsTextField;

    //Fields relating to running the tests
    private Integer testLeftToRun;
    private Integer randomNoteOne;
    private Integer randomNoteTwo;
    private Integer testsCompleted = 0;
    private Integer lowerRangeMidiNote;
    private Integer higherRangeMidiNote;
    private Integer resetTestsIndex = 0;
    private Double totalTests;
    private Double numberOfCorrectAnswers = 0.0;
    private Boolean sameNote = false;
    private Boolean randomNoteTwoHigher;
    private Boolean runningResetTests = false;
    private Boolean startPitchComparisonTest = false;
    private String correctAnswer = "";
    private ArrayList<Integer> noteRangePool = new ArrayList<>();
    private ArrayList<List> incorrectTests = new ArrayList<>();
    private ArrayList<List> resetTestsToRun;

    //Test records
    private ArrayList<List<String>> testRecords = new ArrayList<>();

    /**
     * Constructor for the Pitch Tutor Scene.
     */
    public PitchTutorSceneController() {
        pitchTutorSceneController = this;
    }

    /**
     * Used to get the instance of the Pitch Tutor Scene Controller.
     *
     * @return The Pitch Tutor Scene Controller.
     */
    public static PitchTutorSceneController getInstance() {
        return pitchTutorSceneController;
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
                    "/scenes/pitchtutor/PitchTutorPopUp.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("New Pitch Comparison Test");
            stage.setResizable(false);
            stage.initModality(Modality.NONE);
            stage.initOwner(createTestButton.getScene().getWindow());
            PitchTutorPopUpController.getInstance().setDefaults();
            stage.showAndWait();

            if (startPitchComparisonTest) {
                resetToDefaults();
                initiatePitchTestFromPopup();
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
     * @throws Exception The fxml file won't open.
     */
    @FXML
    private void endTestButtonPressed(ActionEvent event) throws Exception {
        testLeftToRun = 0;
        endTestButton.setDisable(true);
        if (incorrectTests.size() > 0) {
            retryTestsButton.setDisable(false);
        }
        runASingleTest();
    }

    /**
     * Used to handle the retry test button being pressed.
     *
     * @param event The button event.
     */
    @FXML
    private void retryTestsButtonPressed(ActionEvent event) {
        //Stores values before they are reset
        resetTestsToRun = incorrectTests;
        Integer lowerRangeNoteTemp = lowerRangeMidiNote;
        Integer higherRangeNoteTemp = higherRangeMidiNote;

        //Reset all values
        resetToDefaults();

        //Run a pitch comparison test with incorrect values
        runningResetTests = true;
        pitchComparisonTest(resetTestsToRun.size(), lowerRangeNoteTemp, higherRangeNoteTemp);
    }

    /**
     * This handles the reset button being pressed.
     */
    @FXML
    private void resetButtonPressed(ActionEvent event) {
        //Reset all values to default
        resetToDefaults();
    }

    /**
     * Initiates a pitch comparison test when started from the GUI pop up.
     */
    private void initiatePitchTestFromPopup() {
        //Get the values from the pop up controller
        PitchTutorPopUpController popUpController = PitchTutorPopUpController.getInstance();
        Integer notePairs = popUpController.getNotePairsNumber();
        Integer lowerMidiNote = popUpController.getMidiNoteRangeOne();
        Integer higherMidiNote = popUpController.getMidiNoteRangeTwo();

        //Make sure the midi notes are assigned to the right variable
        if (lowerMidiNote > higherMidiNote) {
            Integer tempNote = lowerMidiNote;
            lowerMidiNote = higherMidiNote;
            higherMidiNote = tempNote;
        }

        //Go to the test function
        resetToDefaults();
        pitchComparisonTest(notePairs, lowerMidiNote, higherMidiNote);
    }

    /**
     * This initiates a pitch comparison test and runs the first individual test.
     *
     * @param notePairs The value for the note pairs.
     * @param lowerMidiNote The lower range midi note value.
     * @param higherMidiNote the higher range midi note value.
     */
    public void pitchComparisonTest(Integer notePairs, Integer lowerMidiNote,
                                    Integer higherMidiNote) {
        //Toggle buttons
        endTestButton.setDisable(false);
        resetButton.setDisable(false);
        createTestButton.setDisable(true);

        //Assign values to the fields
        testLeftToRun = notePairs;
        totalTests = notePairs.doubleValue();
        lowerRangeMidiNote = lowerMidiNote;
        higherRangeMidiNote = higherMidiNote;

        //Convert midi notes to proper note names
        String properNoteOne = NoteUtil.convertToNote(lowerMidiNote);
        String properNoteTwo = NoteUtil.convertToNote(higherMidiNote);

        //Appends text to the status field
        String rangeString = properNoteOne + " to " + properNoteTwo;
        if (notePairs > 1) {
            testStatusTextField.setText("Running " + notePairs + " questions withing the range of "
                    + rangeString);
        } else {
            testStatusTextField.setText("Running 1 question withing the range of " + rangeString);
        }

        //Try to start a test
        try {
            runASingleTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This creates and runs a single pitch comparison test.
     *
     * @throws Exception The fxml document could not be loaded.
     */
    public void runASingleTest() throws Exception {
        if (testLeftToRun > 0) {
            //Disables the next button of the previous test
            if (PitchQuestionController.getInstance() != null) {
                PitchQuestionController.getInstance().disablePreviousTest();
            }

            //Creates a new individual test and adds it to the scroll pane
            buildNewTest();

            //Generate the random notes
            generateRandomNotes();

            //Decrements the tests left to run variable
            testLeftToRun--;
        } else {
            //Handles the end of the test
            handleEndOfTest();
        }
    }

    /**
     * This function creates a test object and adds it to the scroll pane.
     *
     * @throws Exception The fxml file could not be loaded.
     */
    private void buildNewTest() throws Exception {
        //Loads the test fxml
        Pane newPane = FXMLLoader.load(getClass().getResource(
                "/scenes/pitchtutor/PitchTutorQuestion.fxml"));

        //Sets the test number and drop down menu on the test
        PitchQuestionController.getInstance().setTestNumber((totalTests - testLeftToRun) + 1.0);
        PitchQuestionController.getInstance().setPitchChoiceDropDown();

        //Adds the test to the scroll pane
        contentBox.getChildren().addAll(newPane);
        newPane.prefWidthProperty().bind(testScrollPane.widthProperty());
        testScrollPane.setContent(contentBox);
        testScrollPane.setVvalue(100.0);
    }

    /**
     * Used to generate the random notes between the given range.
     */
    private void generateRandomNotes() {
        sameNote = false;

        //Add the notes within the range to the range pool (notes are stored as midi numbers)
        for (Integer i = lowerRangeMidiNote; i <= higherRangeMidiNote; i++) {
            noteRangePool.add(i);
        }

        //Check whether to run incorrect tests or generate new ones
        if (runningResetTests) {
            List<Integer> currentTestPair = resetTestsToRun.get(resetTestsIndex);
            randomNoteOne = currentTestPair.get(0);
            randomNoteTwo = currentTestPair.get(1);
            resetTestsIndex++;
        } else {
            //Generate two random notes within the range
            Random rand = new Random();
            randomNoteOne = noteRangePool.get(rand.nextInt(noteRangePool.size()));
            randomNoteTwo = noteRangePool.get(rand.nextInt(noteRangePool.size()));
        }

        //Determine the higher and lower pitch notes out of the two randomly selected
        if (randomNoteOne.equals(randomNoteTwo)) {
            sameNote = true;
        } else if (randomNoteTwo > randomNoteOne) {
            randomNoteTwoHigher = true;
        } else {
            randomNoteTwoHigher = false;
        }
    }

    /**
     * handles what happens all tests are finished.
     */
    private void handleEndOfTest() {
        //Disables the last test
        PitchQuestionController.getInstance().disablePreviousTest();

        //Only calculate score if at least one test has been completed
        if (testsCompleted > 0) {
            //Calculates the results
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            Double scorePercentage = (numberOfCorrectAnswers / testsCompleted) * 100;
            Integer correctPairs = numberOfCorrectAnswers.intValue();
            Integer incorrectPairs = testsCompleted - correctPairs;

            //Create a result string and append to the results text field
            String resultsFieldString = "";

            resultsFieldString = resultsFieldString.concat("Your score was " + correctPairs
                    + "/" + testsCompleted);

            resultsFieldString = resultsFieldString.concat(" (" + df.format(scorePercentage)
                    + "%) ");

            resultsFieldString = resultsFieldString.concat("You had " + incorrectPairs
                    + " incorrect question");

            if (testsCompleted > 1) {
                resultsFieldString += "s";
            }

            testResultsTextField.setText(resultsFieldString);
            TutorRecords.getInstance().testRecordSaving("Pitch Tutor", testRecords);
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
     * This function is used to reset all the fields to their defaults.
     */
    public void resetToDefaults() {
        //Toggle buttons
        endTestButton.setDisable(true);
        resetButton.setDisable(true);
        retryTestsButton.setDisable(true);
        createTestButton.setDisable(false);

        //Reset text fields
        testStatusTextField.setText("");
        testResultsTextField.setText("");

        //Reset booleans
        startPitchComparisonTest = false;
        sameNote = false;
        runningResetTests = false;

        //Reset counts
        numberOfCorrectAnswers = 0.0;
        testsCompleted = 0;
        resetTestsIndex = 0;

        //Reset scroll pane, correct answer and incorrect tests array
        correctAnswer = "";
        contentBox = new VBox();
        testScrollPane.setContent(contentBox);
        incorrectTests = new ArrayList<>();
        noteRangePool = new ArrayList<>();
        testRecords = new ArrayList<>();
    }

    /**
     * Used byt the individual test controller to check whether the result entered is correct.
     *
     * @param result The result entered.
     * @return A boolean value indicating whether the answer is correct.
     */
    public boolean checkResults(String result) {
        testsCompleted++;
        Boolean answerCorrect;
        String answer;

        //Sets the correct answer
        if (sameNote) {
            correctAnswer = "Same";
        } else {
            if (randomNoteTwoHigher) {
                correctAnswer = "Higher";
            } else {
                correctAnswer = "Lower";
            }
        }

        //Checks whether the answer is correct
        if (result.equals(correctAnswer)) {
            numberOfCorrectAnswers++;
            answerCorrect = true;
            answer = "Correct";
        } else {
            incorrectTests.add(Arrays.asList(randomNoteOne, randomNoteTwo));
            answerCorrect = false;
            answer = "Incorrect";
        }

        //Create a question record and add it to the test records
        String question = "Does " + NoteUtil.convertToNote(randomNoteTwo)
                + " have a higher or lower pitch than "
                + NoteUtil.convertToNote(randomNoteOne) + "?";

        List<String> questionRecord = Arrays.asList(question, result, answer);
        testRecords.add(questionRecord);

        return answerCorrect;
    }

    /**
     * Used to get the correct answer.
     *
     * @return The correct answer string.
     */
    public String getAnswer() {
        return correctAnswer;
    }

    /**
     * Getter for the first random note.
     *
     * @return The first random note.
     */
    public Integer getRandomNoteOne() {
        return randomNoteOne;
    }

    /**
     * Getter for the second random note.
     *
     * @return The second random note.
     */
    public Integer getRandomNoteTwo() {
        return randomNoteTwo;
    }

    /**
     * Used by the pop up controller to start the pitch comparison test.
     */
    public void startTest() {
        startPitchComparisonTest = true;
    }
}
