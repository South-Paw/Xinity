package seng302.controllers.commontutor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import seng302.tutor.Tutor;

/**
 * Common controller class for an individual test.
 *
 * @author wrs35, avh17
 */
public abstract class TutorQuestionController {

    @FXML protected Text testNumberText;
    @FXML protected Button nextButton;
    @FXML protected Button checkAnswerButton;
    @FXML protected Text answerText;
    @FXML protected Text giveAnswerText;

    protected TutorSceneController sceneController;
    protected Tutor tutor;

    /**
     * Handles the check button being pressed.
     *
     * @param event The button event.
     */
    @FXML protected abstract void checkButtonPressed(ActionEvent event);

    /**
     * Handles the next button being pressed.
     *
     * @param event The button event.
     */
    @FXML protected void nextButtonPressed(ActionEvent event) {
        tutor.runSingleTutorQuestion();
        nextButton.setDisable(true);
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
     * Used to disable the last test.
     */
    public void disableQuestion() {
        nextButton.setDisable(true);
        checkAnswerButton.setDisable(true);
    }

    /**
     * Returns the question number text.
     * @return Returns the question number text.
     */
    public Text getQuestionNumberText() {
        return testNumberText;
    }

}
