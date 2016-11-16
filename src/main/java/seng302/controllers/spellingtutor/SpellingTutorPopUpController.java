package seng302.controllers.spellingtutor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;
import seng302.command.Command;
import seng302.command.tutor.ChordSpellingTutor;
import seng302.controllers.commontutor.TutorPopUpController;
import seng302.util.enumerator.ChordQuality;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the spelling tutor pop up.
 */
public class SpellingTutorPopUpController extends TutorPopUpController {

    private static SpellingTutorPopUpController spellingTutorPopUpController;

    @FXML private TextField numberOfQuestionsField;
    @FXML private CheckComboBox chordConstraintDropDown;
    @FXML private CheckBox noEnharmonicsCheckBox;
    @FXML private CheckBox randomNotesCheckBox;

    /**
     * Constructor for class.
     */
    public SpellingTutorPopUpController() {
        spellingTutorPopUpController = this;
    }

    /**
     * Used to get the instance of the Tutor Pop Up Controller.
     *
     * @return The Tutor Pop Up Controller.
     */
    public static SpellingTutorPopUpController getInstance() {
        return spellingTutorPopUpController;
    }

    /**
     * Initialise function for when this scene is loaded.
     */
    @SuppressWarnings("Unused")
    public void initialize() {
        // Adds all the chord types to the drop down and checks them all
        ObservableList<String> constraints = FXCollections.observableArrayList();
        for (ChordQuality quality: ChordQuality.values()) {
            String qualityString = quality.getChordQualities().get(0);
            if (!qualityString.equalsIgnoreCase("unknown")) {
                constraints.addAll(qualityString);
            }
        }
        chordConstraintDropDown.getItems().addAll(constraints);
        for (Integer index = 0; index < constraints.size(); index++) {
            chordConstraintDropDown.getCheckModel().checkIndices(index);
        }
    }

    /**
     * Handles the start test button being pressed.
     */
    protected void startTestButtonHandler() {
        List<String> arguments = new ArrayList<>();
        List<String> constraintList = chordConstraintDropDown.getCheckModel().getCheckedItems();

        arguments.add("x" + numberOfQuestionsField.getText());
        for (String constraint: constraintList) {
            arguments.add(constraint);
        }

        if (noEnharmonicsCheckBox.isSelected()) {
            arguments.add("noEnharmonic");
        }

        if (randomNotesCheckBox.isSelected()) {
            arguments.add("randomNotes");
        }

        Command spellingTutor = new ChordSpellingTutor(arguments);
        Boolean validTestValue = isNumberOfTestsValid(numberOfQuestionsField);
        startTestThroughDsl(spellingTutor, validTestValue);
    }
}
