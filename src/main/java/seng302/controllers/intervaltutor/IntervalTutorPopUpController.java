package seng302.controllers.intervaltutor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller for the Interval Tutor Create Test Pop Up.
 *
 * @author avh17
 */
public class IntervalTutorPopUpController {

    private static IntervalTutorPopUpController intervalTutorPopUpController;

    @FXML private Button startTestButton;
    @FXML private TextField intervalTestNumberTextField;
    @FXML private Text invalidValueText;

    private Stage stage;
    private Integer numberOfIntervalTests = 0;
    private Integer validIntervalTestNumberLowerLimit = 0;
    private String invalidValueString = "Invalid Value";

    /**
     * The constructor for Interval Tutor Pop Up Controller.
     */
    public IntervalTutorPopUpController() {
        intervalTutorPopUpController = this;
    }

    /**
     * Used to get the instance of the Interval Tutor Pop Up Controller.
     *
     * @return The Interval Tutor Pop Up Controller.
     */
    public static IntervalTutorPopUpController getInstance() {
        return intervalTutorPopUpController;
    }

    /**
     * Handles a button being pressed on the popup.
     *
     * @param event The button event.
     * @throws Exception unable to open fxml file.
     */
    @FXML
    @SuppressWarnings("unused")
    private void buttonPressed(ActionEvent event) throws Exception {
        if (event.getSource().equals(startTestButton)) {
            startTestButtonHandler();
        } else {
            stage = (Stage) startTestButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Handles what happens when the start test button is pressed.
     */
    private void startTestButtonHandler() {
        boolean validIntervalTestNumber;

        //Assigns the interval tests number
        try {
            this.numberOfIntervalTests = Integer.parseInt(intervalTestNumberTextField.getText());
            if (numberOfIntervalTests > validIntervalTestNumberLowerLimit) {
                validIntervalTestNumber = true;
            } else {
                validIntervalTestNumber = false;
            }
        } catch (Exception ex) {
            validIntervalTestNumber = false;
        }

        if (validIntervalTestNumber) {
            IntervalTutorSceneController.getInstance().startTest();
            stage = (Stage) startTestButton.getScene().getWindow();
            stage.close();
        } else {
            invalidValueText.setText(invalidValueString);
        }
    }

    /**
     * Used to get the number of interval tests.
     *
     * @return The number of interval tests.
     */
    public Integer getNumberOfIntervalTests() {
        return numberOfIntervalTests;
    }
}
