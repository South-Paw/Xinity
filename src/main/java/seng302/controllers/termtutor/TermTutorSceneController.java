package seng302.controllers.termtutor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng302.command.tutor.TermTutor;

/**
 * Controller for the Interval Tutor Scene Controller.
 *
 * @author avh17.
 * @author plr37.
 */
@SuppressWarnings({"unchecked", "unused"})
public class TermTutorSceneController {

    private static TermTutorSceneController termTutorSceneController;

    @FXML private Button createTestButton;
    @FXML private Button endTestButton;
    @FXML private Button retryTestsButton;
    @FXML private Button resetButton;
    @FXML private Text testStatusTextField;
    @FXML private Text testResultsTextField;
    @FXML private VBox contentBox = new VBox();
    @FXML private ScrollPane testScrollPane;

    /**
     * Constructor for the Interval tutor scene controller.
     */
    public TermTutorSceneController() {
        termTutorSceneController = this;
    }

    /**
     * Used to get the instance of the term tutor scene controller.
     *
     * @return The term tutor scene controller.
     */
    public static TermTutorSceneController getInstance() {
        return termTutorSceneController;
    }

    /**
     * Deals with the create test button being pressed.
     *
     * @param event The button event.
     * @throws Exception Unable to open fxml file.
     */
    @FXML
    private void createTestButtonPressed(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        Parent root;

        if (event.getSource() == createTestButton) {
            //Loads the popup
            root = FXMLLoader.load(getClass().getResource("/scenes/termtutor/TermTutorPopUp.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("New Term Test");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(createTestButton.getScene().getWindow());
            stage.showAndWait();
        } else {
            stage = (Stage) createTestButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * This handles the end button being pressed.
     *
     * @param event The button event.
     */
    @FXML
    private void endTestButtonPressed(ActionEvent event) {
        TermTutor.getTermTutorInstance().handleEndOfTest();
    }

    /**
     * Handles the retry tests button being pressed.
     *
     * @param event The button event.
     */
    @FXML
    private void retryTestsButtonPressed(ActionEvent event) {
        TermTutor.getTermTutorInstance().handleRetryTests();
    }

    /**
     * Handles the reset button being pressed.
     *
     * @param event The button event.
     */
    @FXML
    private void resetButtonPressed(ActionEvent event) {
        TermTutor.getTermTutorInstance().resetToDefaults();
    }

    /**
     * Used to reset the GUI to its initial state.
     */
    public void resetToInitialState() {
        createTestButton.setDisable(false);
        endTestButton.setDisable(true);
        retryTestsButton.setDisable(true);
        resetButton.setDisable(true);

        testStatusTextField.setText("");
        testResultsTextField.setText("");

        contentBox = new VBox();
        testScrollPane.setContent(contentBox);
    }

    /**
     * Builds a question and adds it to the scene.
     */
    public void addCurrentQuestionToScene() {
        //Loads the fxml into a pane
        Pane newPane = new Pane();
        try {
            newPane = FXMLLoader.load(getClass().getResource(
                    "/scenes/termtutor/TermTutorQuestion.fxml"));
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        //Adds the pane to the scroll pane
        contentBox.getChildren().addAll(newPane);
        newPane.prefWidthProperty().bind(testScrollPane.widthProperty());
        testScrollPane.setContent(contentBox);
        testScrollPane.setVvalue(100.0);
    }

    /**
     * Getter fo the status field.
     *
     * @return The status field.
     */
    public Text getStatusTextField() {
        return testStatusTextField;
    }

    /**
     * Getter for the results field.
     *
     * @return The results field.
     */
    public Text getTestResultsTextField() {
        return testResultsTextField;
    }

    /**
     * Getter for the create test button.
     *
     * @return The create test button.
     */
    public Button getCreateButton() {
        return createTestButton;
    }

    /**
     * Getter for the end test button.
     *
     * @return The end test button.
     */
    public Button getEndTestButton() {
        return endTestButton;
    }

    /**
     * Getter for the retry button.
     *
     * @return The retry button.
     */
    public Button getRetryTestsButton() {
        return retryTestsButton;
    }

    /**
     * Getter for the reset button.
     *
     * @return the reset button.
     */
    public Button getResetButton() {
        return resetButton;
    }

}

