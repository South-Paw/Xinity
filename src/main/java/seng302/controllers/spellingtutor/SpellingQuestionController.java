package seng302.controllers.spellingtutor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seng302.command.tutor.ChordSpellingTutor;
import seng302.controllers.commontutor.TutorQuestionController;
import seng302.controllers.keyboard.KeyboardSceneController;

/**
 * Controller class for the spelling tutor question.
 *
 * @author avh17.
 */
public class SpellingQuestionController extends TutorQuestionController {

    private static SpellingQuestionController spellingQuestionController;

    @FXML private TextField answerInput;
    @FXML private Text questionText;

    /**
     * Constructor for class.
     */
    public SpellingQuestionController() {
        spellingQuestionController = this;
        tutor = ChordSpellingTutor.getInstance().getTutor();
        sceneController = SpellingTutorSceneController.getInstance();
    }

    /**
     * Used to get the instance of the Question Controller.
     *
     * @return The Question Controller.
     */
    public static SpellingQuestionController getInstance() {
        return spellingQuestionController;
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
                    KeyboardSceneController.getInstance().setLastFocusedField("SpellingInput");
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
