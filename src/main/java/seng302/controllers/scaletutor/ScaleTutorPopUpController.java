package seng302.controllers.scaletutor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import seng302.command.tutor.ScaleTutor;
import seng302.controllers.commontutor.TutorPopUpController;

import java.util.Arrays;

/**
 * The Controller class for the scale tutor popup.
 *
 * @author ljm163.
 */
public class ScaleTutorPopUpController extends TutorPopUpController {

    private static ScaleTutorPopUpController scaleTutorPopUpController;

    @FXML private TextField noOfScalesTextField;
    @FXML private ComboBox scaleDirectionComboBox;
    @FXML private ComboBox playStyleDropDown;
    @FXML private TextField noOfOctavesTextField;
    @FXML private CheckBox modeCheckBox;
    @FXML private CheckBox harmonicCheckBox;

    /**
     * The constructor for Scale Tutor Pop Up Controller.
     */
    public ScaleTutorPopUpController() {
        scaleTutorPopUpController = this;
    }

    /**
     * Static method used for retrieving class instance.
     *
     * @return The class.
     */
    public static ScaleTutorPopUpController getInstance() {
        return scaleTutorPopUpController;
    }

    /**
     * Handles what happens when the start test button is pressed.
     */
    @Override
    protected void startTestButtonHandler() {
        Boolean validTestValue = true;

        if (noOfScalesTextField.getText().length() <= 0) {
            validTestValue = false;
        }

        if (noOfOctavesTextField.getText().length() <= 0) {
            validTestValue = false;
        }

        String numberOfQuestions = noOfScalesTextField.getText();
        String direction = scaleDirectionComboBox.getSelectionModel().getSelectedItem().toString();
        String noOfOctaves = noOfOctavesTextField.getText();
        String playStyle = playStyleDropDown.getSelectionModel().getSelectedItem().toString();

        if (!numberIsValid(noOfOctaves) || !numberIsValid(numberOfQuestions)) {
            validTestValue = false;
        }

        if (harmonicCheckBox.isSelected()) {
            if (modeCheckBox.isSelected()) {
                startTestThroughDsl(new ScaleTutor(Arrays.asList("x" + numberOfQuestions,
                        direction, noOfOctaves, playStyle, "modes", "harmonic")), validTestValue);
            } else {
                startTestThroughDsl(new ScaleTutor(Arrays.asList("x" + numberOfQuestions,
                        direction, noOfOctaves, playStyle, "harmonic")), validTestValue);
            }
        } else {
            if (modeCheckBox.isSelected()) {
                startTestThroughDsl(new ScaleTutor(Arrays.asList("x" + numberOfQuestions,
                        direction, noOfOctaves, playStyle, "modes")), validTestValue);
            } else {
                startTestThroughDsl(new ScaleTutor(Arrays.asList("x" + numberOfQuestions,
                        direction, noOfOctaves, playStyle)), validTestValue);
            }

        }
    }

    /**
     * Used to check if a given number is valid.
     *
     * @param number the number.
     * @return A boolean value.
     */
    private boolean numberIsValid(String number) {
        Integer value;
        //Assigns the midi note range numbers
        try {
            value = Integer.parseInt(number);

            //Check the number of octaves is greater than the lower limit
            return (value > 0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
