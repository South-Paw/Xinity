package seng302.controllers.scaletutor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seng302.command.tutor.ScaleTutor;
import seng302.controllers.commontutor.TutorQuestionController;
import seng302.util.PlayServiceUtil;
import seng302.util.ScalesUtil;
import seng302.util.enumerator.ScaleDirection;
import seng302.util.enumerator.ScaleGroup;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for an individual scale test.
 *
 * @author ljm163
 */
public class ScaleQuestionController extends TutorQuestionController {

    private static ScaleQuestionController scaleQuestionController;

    @FXML private ChoiceBox scaleChoiceDropDown;
    @FXML private Text questionText;
    @FXML private TextField answerInput;
    @FXML private Button playTestButton;

    /**
     * Private constructor for the class.
     */
    public ScaleQuestionController() {
        scaleQuestionController = this;
        tutor = ScaleTutor.getInstance().getTutor();
        sceneController = ScaleTutorSceneController.getInstance();
    }

    /**
     * Initialise function for when this scene is loaded.
     */
    public void initialize() {
        if (tutor.getCurrentQuestion().get(2).equalsIgnoreCase("play")) {
            setScaleChoiceDropDown(
                    ScaleTutor.getInstance().includeModes(),
                    ScaleTutor.getInstance().includeHarmonic()
            );
        }
    }

    /**
     * Static instance method used to retrieve class instance.
     *
     * @return Class instance.
     */
    public static ScaleQuestionController getInstance() {
        return scaleQuestionController;
    }

    /**
     * Used to disable the last test.
     */
    @Override
    public void disableQuestion() {
        super.disableQuestion();
        if (tutor.getCurrentQuestion().get(2).equalsIgnoreCase("play")) {
            playTestButton.setDisable(true);
            scaleChoiceDropDown.setDisable(true);
        } else {
            answerInput.setDisable(true);
        }
    }

    /**
     * Handles the play button being pressed.
     *
     * @param event The button event.
     */
    @FXML
    private void playButtonPressed(ActionEvent event) {
        checkAnswerButton.setDisable(false);
        scaleChoiceDropDown.setDisable(false);
        try {
            playTestScale();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the check button being pressed.
     *
     * @param event The button event.
     */
    @FXML protected void checkButtonPressed(ActionEvent event) {
        if (tutor.getCurrentQuestion().get(2).equalsIgnoreCase("play")) {
            if (scaleChoiceDropDown.getSelectionModel().getSelectedItem() != null) {
                String givenAnswer = scaleChoiceDropDown.getSelectionModel()
                        .getSelectedItem().toString();

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
                    scaleChoiceDropDown.setDisable(true);
                    playTestButton.setDisable(true);
                }
            }
        } else {
            if (answerInput.getText().length() > 0) {
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
        }
    }

    /**
     * This function plays the scale of the individual test.
     *
     * @throws Exception Can't play scale.
     */
    private void playTestScale() throws Exception {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    String playStyle = ScaleTutor.getInstance().getPlayStyle();

                    //start note
                    String note = tutor.getCurrentQuestion().get(1);
                    XinityNote startNote = new XinityNote(note);

                    //scale type
                    String type = tutor.getCorrectAnswer();
                    ScaleType scaleType = ScaleType.fromString(type);

                    //number of octaves
                    Integer noOfOctaves = ScaleTutor.getInstance().getNumberOfOctaves();

                    //scale direction
                    String direction = ScaleTutor.getInstance().getDirection();
                    ScaleDirection scaleDirection =
                            ScaleDirection.fromString(direction);

                    //build scale array
                    List<XinityNote> scaleArray = ScalesUtil.buildPlayingScale(
                            startNote, scaleType, scaleDirection, noOfOctaves);

                    //play scale
                    if (playStyle.equalsIgnoreCase("straight")) {
                        PlayServiceUtil.playStraightScale(scaleArray);
                    } else {
                        PlayServiceUtil.playSwingScale(scaleArray);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    /**
     * This sets the test number on the individual test.
     *
     * @param number The number to set.
     */
    public void setTestNumber(Double number) {
        Integer testNumber = number.intValue();
        testNumberText.setText("Test #" + testNumber);
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
     * This adds the options to the drop down menu in an individual test.
     */
    public void setScaleChoiceDropDown(Boolean includeModes, Boolean includeHarmonic) {
        List<String> scales = ScaleType.getScaleNames();
        List<String> acceptableScales = new ArrayList<>();

        for (ScaleType scaleType : ScaleGroup.NATURAL.getGroup()) {
            acceptableScales.add(scaleType.toString());
        }

        for (ScaleType scaleType : ScaleGroup.MELODIC.getGroup()) {
            acceptableScales.add(scaleType.toString());
        }

        for (ScaleType scaleType : ScaleGroup.PENTATONIC.getGroup()) {
            acceptableScales.add(scaleType.toString());
        }

        for (ScaleType scaleType : ScaleGroup.JAZZ.getGroup()) {
            acceptableScales.add(scaleType.toString());
        }

        // Check Harmonic Flag
        if (ScaleTutor.getInstance().includeHarmonic()) {
            for (ScaleType scaleType : ScaleGroup.HARMONIC.getGroup()) {
                acceptableScales.add(scaleType.toString());
            }

            for (ScaleType scaleType : ScaleGroup.HARMONICMINORMODES.getGroup()) {
                acceptableScales.add(scaleType.toString());
            }
        }

        // Check for Modes
        if (ScaleTutor.getInstance().includeModes()) {

            for (ScaleType scaleType : ScaleGroup.MAJORMODES.getGroup()) {
                acceptableScales.add(scaleType.toString());
            }

            for (ScaleType scaleType : ScaleGroup.MELODICMINORMODES.getGroup()) {
                acceptableScales.add(scaleType.toString());
            }

            acceptableScales.remove("Minormajor");
            acceptableScales.remove("Ionian");
        }

        ObservableList<String> dropDownList = FXCollections.observableArrayList(acceptableScales);
        scaleChoiceDropDown.setItems(dropDownList);
    }

    /**
     * Used to disable the last test.
     */
    public void disablePreviousTest() {
        nextButton.setDisable(true);
        scaleChoiceDropDown.setDisable(true);
        checkAnswerButton.setDisable(true);
        playTestButton.setDisable(true);
    }
}
