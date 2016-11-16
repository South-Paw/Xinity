package seng302.controllers.commontutor;

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
import seng302.tutor.Tutor;

/**
 * Common controller for Tutor Scene Controllers.
 *
 * @author avh17, wrs35
 */
public abstract class TutorSceneController {

    protected static TutorSceneController tutorSceneController;

    @FXML private Button createTestButton;
    @FXML private Button endTestButton;
    @FXML private Button retryTestsButton;
    @FXML private Button resetButton;
    @FXML private Text testStatusTextField;
    @FXML private Text testResultsTextField;
    @FXML private VBox contentBox = new VBox();
    @FXML private ScrollPane testScrollPane;

    public String tutorQuestionFxmlFile = "";
    public String tutorPopUpFxmlFile = "";
    public String testButtonLabel = "Test Button";

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
            root = FXMLLoader.load(getClass().getResource(tutorPopUpFxmlFile));
            stage.setScene(new Scene(root));
            stage.setTitle(testButtonLabel);
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
        getQuestion().disableQuestion();
        getTutorTest().handleEndOfTest();
    }

    /**
     * Handles the retry tests button being pressed.
     *
     * @param event The button event.
     */
    @FXML
    protected void retryTestsButtonPressed(ActionEvent event) {
        resetToInitialState();
        getTutorTest().handleRetryTests();
    }

    /**
     * Handles the reset button being pressed.
     *
     * @param event The button event.
     */
    @FXML
    protected void resetButtonPressed(ActionEvent event) {
        resetToInitialState();
    }

    /**
     * This function creates a test object and adds it to the scroll pane.
     */
    public void addQuestionToScene() {
        Pane newPane = new Pane();
        //Loads the test fxml
        try {
            newPane = FXMLLoader.load(getClass().getResource(tutorQuestionFxmlFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Adds the test to the scroll pane
        contentBox.getChildren().addAll(newPane);
        newPane.prefWidthProperty().bind(testScrollPane.widthProperty());
        testScrollPane.setContent(contentBox);
        testScrollPane.setVvalue(1.0);
    }

    /**
     * Used to reset all values to default.
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
     * Used to get the status field object.
     *
     * @return The status field object.
     */
    public Text getStatusTextField() {
        return testStatusTextField;
    }

    /**
     * Get the test results text field.
     * @return the test results text field.
     */
    public Text getTestResultsTextField() {
        return testResultsTextField;
    }

    /**
     * Used to toggle the scene buttons at the end of a test.
     *
     * @param retryTests Indicates if the retry tests button should be on.
     */
    public void endTestButtonToggle(Boolean retryTests) {
        createTestButton.setDisable(false);
        endTestButton.setDisable(true);
        resetButton.setDisable(false);
        if (retryTests) {
            retryTestsButton.setDisable(false);
        }
    }

    /**
     * Used to toggle the scene buttons at the start of a test.
     */
    public void startTestButtonToggle() {
        createTestButton.setDisable(true);
        endTestButton.setDisable(false);
        resetButton.setDisable(true);
    }

    /**
     * Used to get the tutor object.
     *
     * @return The tutor object.
     */
    protected abstract Tutor getTutorTest();

    /**
     * Used to get the instance of the question controller.
     *
     * @return The question controller instance.
     */
    public abstract TutorQuestionController getQuestion();
}
