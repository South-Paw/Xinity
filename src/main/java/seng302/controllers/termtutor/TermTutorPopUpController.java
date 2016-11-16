package seng302.controllers.termtutor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng302.Environment;
import seng302.command.tutor.TermTutor;

import java.util.Arrays;

/**
 * The controller class for the term tutor pop up.
 *
 * @author avh17
 * @author plr37
 */
@SuppressWarnings("unused")
public class TermTutorPopUpController {

    @FXML private Button startTestButton;
    @FXML private TextField termsTestNumberTextField;
    @FXML private Text invalidValueText;

    private Stage stage;

    /**
     * Handles a button being pressed.
     *
     * @param event The button event.
     * @throws Exception Unable to open fxml file.
     */
    public void buttonPressed(ActionEvent event) throws Exception {

        if (event.getSource().equals(startTestButton)) {
            startTestButtonHandler();
        } else {
            stage = (Stage) startTestButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Handles the start test button being pressed.
     */
    private void startTestButtonHandler() {
        Boolean validValue = true;
        Integer numberOfTests = 0;
        try {
            numberOfTests = Integer.parseInt(termsTestNumberTextField.getText());
            if (numberOfTests <= 0) {
                validValue = false;
            }
        } catch (Exception ex) {
            validValue = false;
        }

        if (validValue) {
            TermTutor termTutor = new TermTutor(Arrays.asList("x" + numberOfTests.toString()));
            termTutor.execute(new Environment());

            stage = (Stage) startTestButton.getScene().getWindow();
            stage.close();
        } else {
            invalidValueText.setText("Invalid Value!");
        }
    }
}
