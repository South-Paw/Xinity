package seng302.controllers.signaturetutor;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng302.Environment;
import seng302.command.Command;
import seng302.command.tutor.SignatureTutor;
import seng302.controllers.commontutor.TutorPopUpController;

import java.util.Arrays;
import java.util.List;

/**
 * Controller class for the signature tutor pop up.
 *
 * @author avh17
 */
public class SignatureTutorPopUpController extends TutorPopUpController {

    private static SignatureTutorPopUpController signatureTutorPopUpController;

    @FXML private TextField numberOfQuestionsField;
    @FXML private ChoiceBox signatureSpecifierDropDown;
    @FXML private ChoiceBox scaleNameTypeDropDown;

    /**
     * Constructor for class.
     */
    public SignatureTutorPopUpController() {
        signatureTutorPopUpController = this;
    }

    /**
     * Used to get the instance of the Tutor Pop Up Controller.
     *
     * @return The Tutor Pop Up Controller.
     */
    public static SignatureTutorPopUpController getInstance() {
        return signatureTutorPopUpController;
    }

    /**
     * Handles the start test button being pressed.
     */
    protected void startTestButtonHandler() {
        Boolean validTestValue = isNumberOfTestsValid(numberOfQuestionsField);

        String signatureSpecifier = signatureSpecifierDropDown.getSelectionModel()
                .getSelectedItem().toString();
        String scaleNameType = scaleNameTypeDropDown.getSelectionModel()
                .getSelectedItem().toString();
        List<String> arguments = Arrays.asList("x" + numberOfTests.toString(),
                signatureSpecifier, scaleNameType);

        Command signatureTutor = new SignatureTutor(arguments);

        startTestThroughDsl(signatureTutor, validTestValue);
    }
}
