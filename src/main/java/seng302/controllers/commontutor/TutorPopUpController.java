package seng302.controllers.commontutor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng302.Environment;
import seng302.command.Command;

/**
 * Common controller for Tutor Create Test Pop Ups.
 *
 * @author wrs35, avh17
 */
public abstract class TutorPopUpController {

    @FXML protected Button startTestButton;
    @FXML protected Text invalidValueText;

    protected Stage stage;
    protected Integer numberOfTests = 0;

    /**
     * Handles a button being pressed on the popup.
     *
     * @param event The button event.
     * @throws Exception unable to open fxml file.
     */
    @FXML
    protected void buttonPressed(ActionEvent event) throws Exception {
        if (event.getSource().equals(startTestButton)) {
            startTestButtonHandler();
        } else {
            stage = (Stage) startTestButton.getScene().getWindow();
            stage.close();
        }
    }


    /**
     * Checks if the number of tests input is valid
     *
     * @param numberOfQuestionsField The text field with the users input for
     *                               desired number of questions.
     * @return True if the input is valid.
     */
    @SuppressWarnings("Duplicates")
    protected boolean isNumberOfTestsValid(TextField numberOfQuestionsField) {
        Boolean validValue = true;
        try {
            this.numberOfTests = Integer.parseInt(numberOfQuestionsField.getText());
            if (this.numberOfTests <= 0) {
                validValue = false;
            }
        } catch (Exception ex) {
            validValue = false;
        }

        return validValue;
    }


    @SuppressWarnings("Duplicates")
    protected void startTestThroughDsl(Command tutor, Boolean validTestValue) {
        if (validTestValue) {
            Environment hiddenEnv = new Environment();
            tutor.execute(hiddenEnv);

            stage = (Stage) startTestButton.getScene().getWindow();
            stage.close();
        } else {
            invalidValueText.setText("Invalid Value!");
        }
    }

    /**
     * Handles what happens when the start test button is pressed.
     */
    protected abstract void startTestButtonHandler();

    /**
     * Used to get the number of tests.
     *
     * @return The number of tests.
     */
    public Integer getNumberOfTests() {
        return numberOfTests;
    }

}
