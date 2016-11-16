package seng302.controllers.chordtutor;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import seng302.command.Command;
import seng302.command.tutor.ChordTutor;
import seng302.controllers.commontutor.TutorPopUpController;

import java.util.Arrays;
import java.util.List;

/**
 * Controller class for the chord tutor pop up.
 *
 * @author avh17, wrs35
 */
public class ChordTutorPopUpController extends TutorPopUpController {

    private static ChordTutorPopUpController chordTutorPopUpController;

    @FXML private ChoiceBox chordStyleDropDown;
    @FXML private TextField numberOfQuestionsField;

    /**
     * Constructor for class.
     */
    public ChordTutorPopUpController() {
        chordTutorPopUpController = this;
    }

    /**
     * Used to get the instance of the Tutor Pop Up Controller.
     *
     * @return The Tutor Pop Up Controller.
     */
    public static ChordTutorPopUpController getInstance() {
        return chordTutorPopUpController;
    }

    /**
     * Handles the start test button being pressed.
     */
    protected void startTestButtonHandler() {
        boolean validTestValue = isNumberOfTestsValid(numberOfQuestionsField);

        String chordStyle = chordStyleDropDown.getSelectionModel().getSelectedItem().toString();
        List<String> arguments = Arrays.asList("x" + numberOfTests.toString(), chordStyle);

        Command chordTutorCommand = new ChordTutor(arguments);

        startTestThroughDsl(chordTutorCommand, validTestValue);
    }


}
