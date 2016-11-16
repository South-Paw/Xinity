package seng302.controllers.signaturetutor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seng302.command.tutor.SignatureTutor;
import seng302.controllers.commontutor.TutorQuestionController;
import seng302.controllers.keyboard.KeyboardSceneController;


/**
 * Controller class for the signature tutor question.
 */
public class SignatureQuestionController extends TutorQuestionController {

    private static SignatureQuestionController signatureQuestionController;

    @FXML private TextField answerInput;
    @FXML private Text questionText;

    /**
     * Constructor for class.
     */
    public SignatureQuestionController() {
        signatureQuestionController = this;
        tutor = SignatureTutor.getInstance().getTutor();
        sceneController = SignatureTutorSceneController.getInstance();
    }

    /**
     * Used to get the instance of the Interval Question Controller.
     *
     * @return The Interval Question Controller.
     */
    public static SignatureQuestionController getInstance() {
        return signatureQuestionController;
    }

    /**
     * Initialise function for when this scene is loaded.
     */
    @SuppressWarnings("Unused")
    public void initialize() {
        answerInput.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                                Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue && KeyboardSceneController.getInstance() != null) {
                    KeyboardSceneController.getInstance().setLastFocusedField("SignatureInput");
                }
            }
        });
    }

    /**
     * Handles the check button being pressed.
     *
     * @param event The button event.
     */
    @FXML protected void checkButtonPressed(ActionEvent event) {
        String givenAnswer = answerInput.getText();

        if (givenAnswer.length() > 0) {
            Boolean answerCorrect = tutor.checkAnswer(givenAnswer);

            if (answerCorrect) {
                answerText.setText("Correct!");
                answerText.setFill(Color.GREEN);
            } else {
                answerText.setText("Incorrect");
                answerText.setFill(Color.RED);
                String actualAnswer = tutor.getCorrectAnswer();
                giveAnswerText.setText("Answer: " + actualAnswer);
            }
            checkAnswerButton.setDisable(true);
            nextButton.setDisable(false);
            answerInput.setDisable(true);
        }
    }

    /**
     * Used to set the question text.
     *
     * @param text The question text.
     */
    public void setQuestionText(String text) {
        questionText.setText(text);
    }

    /**
     * Used to set the answer input.
     *
     * @param input The answer input.
     */
    public void setInput(String input) {
        if (!answerInput.isDisabled()) {
            String givenText = answerInput.getText();
            if (givenText.length() > 0) {
                answerInput.setText(givenText + ", " + input);
            } else {
                answerInput.setText(input);
            }
        }
    }
}
