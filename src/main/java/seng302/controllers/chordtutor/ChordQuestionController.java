package seng302.controllers.chordtutor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seng302.command.tutor.ChordTutor;
import seng302.controllers.commontutor.TutorQuestionController;
import seng302.util.ChordUtil;
import seng302.util.PlayServiceUtil;
import seng302.util.TempoUtil;
import seng302.util.enumerator.ChordQuality;
import seng302.util.enumerator.ChordStyle;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * Controller class for a chord tutor question.
 *
 * @author avh17, ljm163, wrs35.
 */
public class ChordQuestionController extends TutorQuestionController {

    private static ChordQuestionController chordQuestionController;

    @FXML private Button playTestButton;
    @FXML private Text questionText;
    @FXML private TextField answerInput;
    @FXML protected ChoiceBox choiceDropDown;

    /**
     * Constructor for class.
     */
    public ChordQuestionController() {
        chordQuestionController = this;
        tutor = ChordTutor.getInstance().getTutor();
        sceneController = ChordTutorSceneController.getInstance();
    }

    /**
     * Used to get the instance of the Interval Question Controller.
     *
     * @return The Interval Question Controller.
     */
    public static ChordQuestionController getInstance() {
        return chordQuestionController;
    }

    /**
     * Used to disable the previous question.
     */
    public void disableQuestion() {
        super.disableQuestion();
        // 2 is the question type
        if (tutor.getCurrentQuestion().get(2).equalsIgnoreCase("play")) {
            choiceDropDown.setDisable(true);
            playTestButton.setDisable(true);
        } else {
            answerInput.setDisable(true);
        }
    }

    /**
     * Used to set the text for the question.
     *
     * @param text The question text.
     */
    public void setQuestionText(String text) {
        questionText.setText(text);
    }

    /**
     * Plays the chord for the given question.
     *
     * @param event The button event.
     */
    @FXML
    private void playButtonPressed(ActionEvent event) {
        //Create a list of notes in the chord
        List<XinityNote> chordNotes;

        //Get current question containing quality and root
        List<String> currentQuestion =
                ChordTutor.getInstance().getTutor().getCurrentQuestion();

        //Extract quality from question
        ChordQuality quality = ChordQuality.fromString(currentQuestion.get(0));

        try {
            //Extract root from question
            XinityNote root = new XinityNote(currentQuestion.get(1));
            chordNotes = ChordUtil.chord(root, quality);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //Get chord style eg. Arpeggio, Unison or Both.
        ChordStyle chordStyle = ChordTutor.getInstance().getChordStyle();

        //Play the notes depending on the style
        if (chordStyle == ChordStyle.ARPEGGIO) {
            PlayServiceUtil.playArpeggioChord(chordNotes);
        } else if (chordStyle == ChordStyle.BOTH) {
            try {
                PlayServiceUtil.playArpeggioChord(chordNotes);
                PlayServiceUtil.playSilent(TempoUtil.getCrotchet() * 2);
                PlayServiceUtil.playUnisonChord(chordNotes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else { //Unison by default
            PlayServiceUtil.playUnisonChord(chordNotes);
        }
        choiceDropDown.setDisable(false);
        checkAnswerButton.setDisable(false);
    }

    /**
     * Handles the check button being pressed.
     *
     * @param event The button event.
     */
    @SuppressWarnings("Duplicates")
    @FXML protected void checkButtonPressed(ActionEvent event) {
        if (tutor.getCurrentQuestion().get(2).equalsIgnoreCase("play")) {
            if (choiceDropDown.getSelectionModel().getSelectedItem() != null) {
                String answer = choiceDropDown.getSelectionModel().getSelectedItem().toString();
                Boolean answerCorrect = tutor.checkAnswer(answer);

                if (answerCorrect) {
                    answerText.setText("Correct!");
                    answerText.setFill(Color.GREEN);
                } else {
                    answerText.setText("Incorrect");
                    answerText.setFill(Color.RED);
                    String actualAnswer = tutor.getCorrectAnswer();
                    giveAnswerText.setText("Answer: " + actualAnswer);
                }
                choiceDropDown.setDisable(true);
                checkAnswerButton.setDisable(true);
                nextButton.setDisable(false);
            }

            if (choiceDropDown.getSelectionModel().getSelectedItem() != null) {
                playTestButton.setDisable(true);
            }
        } else if (tutor.getCurrentQuestion().get(2).equalsIgnoreCase("diatonic")) {
            if (answerInput.getText().length() > 0) {
                String answer = answerInput.getText();
                Boolean answerCorrect = tutor.checkAnswer(answer);

                if (answerCorrect) {
                    answerText.setText("Correct!");
                    answerText.setFill(Color.GREEN);
                } else {
                    answerText.setText("Incorrect");
                    answerText.setFill(Color.RED);
                    String actualAnswer = tutor.getCorrectAnswer();
                    giveAnswerText.setText("Answer: " + actualAnswer);
                }
                answerInput.setDisable(true);
                checkAnswerButton.setDisable(true);
                nextButton.setDisable(false);
            }
        }
    }
}
