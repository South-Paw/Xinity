package seng302.controllers.intervaltutor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import seng302.util.IntervalUtil;
import seng302.util.PlayServiceUtil;
import seng302.util.TempoUtil;

/**
 * Controller class for an individual interval test.
 *
 * @author avh17
 */
@SuppressWarnings("unused")
public class IntervalQuestionController {

    private static IntervalQuestionController intervalQuestionController;

    @FXML private Text testNumberText;
    @FXML private ChoiceBox intervalChoiceDropDown;
    @FXML private Button nextButton;
    @FXML private Button checkAnswerButton;
    @FXML private Button playTestButton;
    @FXML private Text answerText;
    @FXML private Text giveAnswerText;

    /**
     * Constructor for the class.
     */
    public IntervalQuestionController() {
        intervalQuestionController = this;
    }

    /**
     * Used to get the instance of the Interval Question Controller.
     *
     * @return The Interval Question Controller.
     */
    public static IntervalQuestionController getInstance() {
        return intervalQuestionController;
    }

    /**
     * Handles the play button being pressed.
     *
     * @param event The button event.
     */
    @FXML private void playButtonPressed(ActionEvent event) {
        intervalChoiceDropDown.setDisable(false);
        checkAnswerButton.setDisable(false);

        try {
            playTestNotes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the check button being pressed.
     *
     * @param event The button event.
     */
    @FXML private void checkButtonPressed(ActionEvent event) {
        IntervalTutorSceneController intervalScene = IntervalTutorSceneController.getInstance();
        if (intervalChoiceDropDown.getSelectionModel().getSelectedItem() != null) {
            checkAnswerButton.setDisable(true);
            intervalChoiceDropDown.setDisable(true);
            nextButton.setDisable(false);
            String selectedAnswer =
                    intervalChoiceDropDown.getSelectionModel().getSelectedItem().toString();
            Boolean answerCorrect = intervalScene.checkAnswer(selectedAnswer);
            if (answerCorrect) {
                answerText.setFill(Color.GREEN);
                answerText.setText("Correct");
            } else {
                answerText.setFill(Color.RED);
                answerText.setText("Incorrect");
                String correctAnswer = intervalScene.getCorrectAnswer();
                giveAnswerText.setText("Answer: " + correctAnswer);
            }
            playTestButton.setDisable(true);
        }
    }

    /**
     * Handles the next button being pressed.
     *
     * @param event The button event.
     */
    @FXML private void nextButtonPressed(ActionEvent event) {
        IntervalTutorSceneController.getInstance().runASingleTest();
    }

    /**
     * This function plays the notes of the individual test.
     *
     * @throws Exception Can't play note.
     */
    @SuppressWarnings("Duplicates")
    public void playTestNotes() throws Exception {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                playTestButton.setDisable(true);
                //Calculate the pause between notes (3 crotchets)
                Integer pauseBetweenNotes = TempoUtil.getCrotchet() * 3;

                //Play the first note
                Integer noteOne;
                noteOne = IntervalTutorSceneController.getInstance().getFirstPlayedMidiNote();
                PlayServiceUtil.playMidi(noteOne, -1);

                //Wait 3 crotchets
                try {
                    Thread.sleep(pauseBetweenNotes);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //Play the second note
                Integer noteTwo;
                noteTwo = IntervalTutorSceneController.getInstance().getSecondPlayedMidiNote();
                PlayServiceUtil.playMidi(noteTwo, -1);
                playTestButton.setDisable(false);
            }
        });
        thread.start();


    }

    /**
     * This sets the test number on the individual test.
     *
     * @param number The number to set.
     */
    public void setTestNumber(Integer number) {
        testNumberText.setText("Test #" + number);
    }

    /**
     * This adds the options to the drop down menu in an individual test.
     */
    @SuppressWarnings("unchecked")
    public void setPitchChoiceDropDown() {
        ObservableList<String> dropDownList =
                FXCollections.observableArrayList(IntervalUtil.getIntervals());
        intervalChoiceDropDown.setItems(dropDownList);
    }

    /**
     * Used to disable the last test.
     */
    public void disablePreviousTest() {
        nextButton.setDisable(true);
        intervalChoiceDropDown.setDisable(true);
        checkAnswerButton.setDisable(true);
        playTestButton.setDisable(true);
    }
}
