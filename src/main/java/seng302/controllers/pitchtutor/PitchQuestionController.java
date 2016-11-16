package seng302.controllers.pitchtutor;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import seng302.util.PlayServiceUtil;
import seng302.util.TempoUtil;

/**
 * The controller for an individual pitch test.
 *
 * @author avh17
 */
@SuppressWarnings({"unused", "unchecked"})
public class PitchQuestionController {

    private static PitchQuestionController pitchQuestionController;

    @FXML private Button nextButton;
    @FXML private Button checkAnswerButton;
    @FXML private Text testNumberText;
    @FXML private Text answerText;
    @FXML private ChoiceBox pitchChoiceDropDown;
    @FXML private Text giveAnswerText;
    @FXML private Button playTestButton;

    /**
     * Constructor for an individual test.
     */
    public PitchQuestionController() {
        pitchQuestionController = this;
    }

    /**
     * Used to get the instance of the Pitch Question Controller.
     *
     * @return The Pitch Question Controller.
     */
    public static PitchQuestionController getInstance() {
        return pitchQuestionController;
    }

    /**
     * Handles the check button being pressed.
     *
     * @param event The button event
     */
    @FXML
    private void checkButtonPressed(ActionEvent event) {
        if (!pitchChoiceDropDown.getSelectionModel().isEmpty()) {
            pitchChoiceDropDown.setDisable(true);
            checkAnswerButton.setDisable(true);
            nextButton.setDisable(false);
            String answer = pitchChoiceDropDown.getSelectionModel().getSelectedItem().toString();
            if (PitchTutorSceneController.getInstance().checkResults(answer)) {
                //Deals with a correct answer
                answerText.setFill(Color.GREEN);
                answerText.setText("Correct!");
            } else {
                //deals with an incorrect answer
                answerText.setFill(Color.RED);
                answerText.setText("Incorrect");
                String correctAnswer = PitchTutorSceneController.getInstance().getAnswer();
                giveAnswerText.setText("Answer: " + correctAnswer);
            }
            playTestButton.setDisable(true);
        }
    }

    /**
     * Handles the next button being pressed.
     *
     * @param event The button event
     * @throws Exception Individual test fxml not loading
     */
    @FXML
    private void nextButtonPressed(ActionEvent event) throws Exception {
        PitchTutorSceneController.getInstance().runASingleTest();
    }

    /**
     * Handles the play button being pressed.
     * @param event The button event
     */
    @FXML
    private void playButtonPressed(ActionEvent event) {
        pitchChoiceDropDown.setDisable(false);
        checkAnswerButton.setDisable(false);

        try {
            playTestNotes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to play the two current notes.
     *
     * @throws Exception Note can't be played
     */
    private void playTestNotes() throws Exception {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                playTestButton.setDisable(true);
                //Calculate the pause between notes (3 crotchets)
                Integer pauseBetweenNotes = TempoUtil.getCrotchet() * 3;

                //Play the first note
                Integer noteOne = PitchTutorSceneController.getInstance().getRandomNoteOne();
                PlayServiceUtil.playMidi(noteOne, -1);

                //Wait 3 crotchets
                try {
                    Thread.sleep(pauseBetweenNotes);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

                //Play the second note
                Integer noteTwo = PitchTutorSceneController.getInstance().getRandomNoteTwo();
                PlayServiceUtil.playMidi(noteTwo, -1);
                playTestButton.setDisable(false);
            }
        });
        thread.start();

    }

    /**
     * This sets the test number on the individual test.
     *
     * @param number The number to set
     */
    public void setTestNumber(Double number) {
        Integer testNumber = number.intValue();
        testNumberText.setText("Test #" + testNumber);
    }

    /**
     * This adds the options to the drop down menu in an individual test.
     */
    public void setPitchChoiceDropDown() {
        pitchChoiceDropDown.setItems(FXCollections.observableArrayList("Higher", "Lower", "Same"));
    }

    /**
     * Used to disable the next button on the individual event gui.
     */
    public void disablePreviousTest() {
        nextButton.setDisable(true);
        pitchChoiceDropDown.setDisable(true);
        checkAnswerButton.setDisable(true);
        playTestButton.setDisable(true);
    }
}
