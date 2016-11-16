package seng302.controllers.termtutor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import seng302.command.tutor.TermTutor;

/**
 * Controller class for an individual term question.
 *
 * @author avh17, adg62
 */
@SuppressWarnings("unused")
public class TermQuestionController {
    private static TermQuestionController termQuestionController;

    @FXML private Text testNumberText;
    @FXML private Text questionText;
    @FXML private Text answerText;
    @FXML private Text giveAnswerText;
    @FXML private Button checkAnswerButton;
    @FXML private Button nextButton;
    @FXML private TextField answerInput;

    /**
     * Constructor for the term question scene controller.
     */
    public TermQuestionController() {
        termQuestionController = this;
    }

    /**
     * Used to get the instance if the term question controller.
     *
     * @return The term question controller.
     */
    public static TermQuestionController getInstance() {
        return termQuestionController;
    }

    /**
     * Handles the check answer button being pressed.
     *
     * @param event The button event.
     */
    @FXML
    private void checkAnswerButtonPressed(ActionEvent event) {
        String givenAnswer = answerInput.getText();

        if (TermTutor.getTermTutorInstance().handleCheckAnswer(givenAnswer)) {
            answerText.setText("Correct!");
            answerText.setFill(Color.GREEN);
        } else {
            answerText.setText("Incorrect");
            answerText.setFill(Color.RED);
            String actualAnswer = TermTutor.getTermTutorInstance().getAnswer();
            giveAnswerText.setText("Answer: " + actualAnswer);
        }

        nextButton.setDisable(false);
        checkAnswerButton.setDisable(true);
        answerInput.setDisable(true);
    }

    /**
     * Handles the next button being pressed.
     *
     * @param event The button event.
     */
    @FXML
    private void nextButtonPressed(ActionEvent event) {
        TermTutor.getTermTutorInstance().singleTermQuestion();
    }

    /**
     * If enter is pressed with the input in focus - it will submit.
     *
     * @param event The key press event.
     */
    @FXML
    private void enterToSubmit(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            checkAnswerButtonPressed(null);
        }
    }

    /**
     * If enter is pressed with the next button in focus - do the next question action.
     *
     * @param event The key press event.
     */
    @FXML
    private void enterOnNextButtonPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            nextButtonPressed(null);
        }
    }

    /**
     * Used to set the question number.
     *
     * @param number The question number.
     */
    public void setQuestionNumberText(String number) {
        testNumberText.setText("Test #" + number);
    }

    /**
     * Used to set the question text.
     *
     * @param question The question to ask.
     * @param type The type of question.
     */
    public void setQuestionText(String question, String type) {
        questionText.setText(question);

        answerInput.requestFocus();
    }

    /**
     * Disables the GUI elements of the question.
     */
    public void disableQuestion() {
        checkAnswerButton.setDisable(true);
        nextButton.setDisable(true);
        answerInput.setDisable(true);
    }
}
