package seng302.controllers.keyboard;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import seng302.controllers.TranscriptSceneController;
import seng302.controllers.signaturetutor.SignatureQuestionController;
import seng302.controllers.spellingtutor.SpellingQuestionController;
import seng302.util.PlayServiceUtil;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.List;

/**
 * Keyboard scene controller.
 *
 * @author adg62, avh17
 */
public class KeyboardSceneController {

    private static KeyboardSceneController keyboardSceneController;
    private List<OneOctaveKeysController> controllerList = new ArrayList<>();
    private List<String> multiSelectNotes = new ArrayList<>();
    private Boolean canShowNoteLabel = false;
    private HBox contentBox;
    private Integer enharmonic = 0;
    private Boolean canInput = true;
    private Boolean muted = false;
    private Boolean multiSelect = false;

    private String lastFocusedField = "Transcript";

    @FXML private ScrollPane keyboardScrollPane;
    @FXML private ComboBox noteLabelsDropDown;
    @FXML private ComboBox enharmonicDropDown;
    @FXML private Rectangle viewRectangle;
    @FXML private Button enterSelectedButton;
    @FXML private Button clearSelectedButton;
    @FXML private CheckBox inputCheckBox;
    @FXML private CheckBox muteCheckBox;

    /**
     * Constructor for the class.
     */
    public KeyboardSceneController() {
        keyboardSceneController = this;
    }

    /**
     * Static method to get the class instance.
     *
     * @return The class instance.
     */
    public static KeyboardSceneController getInstance() {
        return keyboardSceneController;
    }

    /**
     * Handles a selection in the note names drop down.
     *
     * @param event The action event.
     */
    @FXML
    public void noteLabelsDropDownSelection(ActionEvent event) {
        clearAllNoteNames();
        String selection = noteLabelsDropDown.getSelectionModel().getSelectedItem().toString();
        switch (selection) {
            case "On Click":
                canShowNoteLabel = true;
                break;
            case "Always":
                switch (enharmonic) {
                    case 0:
                        showAllNoteNames();
                        break;
                    case 1:
                        showLowerNoteNames();
                        break;
                    case 2:
                        showHigherNoteNames();
                        break;
                    default:
                        showAllNoteNames();
                        break;
                }
                canShowNoteLabel = false;
                break;
            default:
                clearAllNoteNames();
                canShowNoteLabel = false;
                break;
        }
    }

    /**
     * Handles a selection in the enharmonic drop down.
     *
     * @param event The action event.
     */
    @FXML
    public void enharmonicDropDownSelection(ActionEvent event) {
        clearAllNoteNames();
        String selection = enharmonicDropDown.getSelectionModel().getSelectedItem().toString();
        String labelSelection = noteLabelsDropDown.getSelectionModel().getSelectedItem().toString();
        switch (selection) {
            case "Default":
                enharmonic = 0;
                if (labelSelection.equals("Always")) {
                    showAllNoteNames();
                }
                break;
            case "Lower":
                enharmonic = 1;
                if (labelSelection.equals("Always")) {
                    showLowerNoteNames();
                }
                break;
            case "Higher":
                enharmonic = 2;
                if (labelSelection.equals("Always")) {
                    showHigherNoteNames();
                }
                break;
            default:
                enharmonic = 0;
                if (labelSelection.equals("Always")) {
                    showAllNoteNames();
                }
                break;
        }
    }

    /**
     * Handles any button being pressed on the keyboard scene.
     *
     * @param event The action event.
     */
    public void buttonClicked(ActionEvent event) throws Exception {
        if (event.getSource() == enterSelectedButton) {
            // Handles what happens when the enter selected button is pressed
            if (canInput) {
                String allNotes = "";
                String nonOctaveNotes = "";
                for (Integer i = 0; i < multiSelectNotes.size(); i++) {
                    String note = multiSelectNotes.get(i);
                    allNotes += note;
                    nonOctaveNotes += note.substring(0, note.length() - 1);
                    if (i < (multiSelectNotes.size() - 1)) {
                        allNotes += ", ";
                        nonOctaveNotes += ", ";
                    }
                }
                if (lastFocusedField.equals("Transcript")) {
                    TranscriptSceneController.getInstance().addTextToEntryField(allNotes);
                } else if (lastFocusedField.equals("SignatureInput")) {
                    SignatureQuestionController.getInstance().setInput(nonOctaveNotes);
                } else if (lastFocusedField.equals("SpellingInput")) {
                    System.out.println(nonOctaveNotes);
                    SpellingQuestionController.getInstance().setInput(nonOctaveNotes);
                }
            }

            // Plays all the selected notes in unison
            if (!muted) {
                List<XinityNote> noteList = new ArrayList<>();

                for (String note : multiSelectNotes) {
                    noteList.add(new XinityNote(note));
                }

                PlayServiceUtil.playUnisonChord(noteList);
            }
            resetKeyFills();
            multiSelectNotes = new ArrayList<>();
        } else if (event.getSource() == clearSelectedButton) {
            // Handles what happens when the clear selected button is pressed
            resetKeyFills();
            multiSelectNotes = new ArrayList<>();
        }
    }

    /**
     * Handles Key inputs. Set multi select to true if key was SHIFT.
     *
     * @param keyEvent key event
     */
    @FXML
    public void handleKeyPress(KeyEvent keyEvent) {
        // Enables multi selecting
        if (keyEvent.getCode().equals(KeyCode.SHIFT)) {
            multiSelect = true;
        }

        // Bind for enharmonic lower
        if (keyEvent.getCode().equals(KeyCode.O)) {
            enharmonicDropDown.getSelectionModel().select(1);
        }

        // Bind for enharmonic higher
        if (keyEvent.getCode().equals(KeyCode.P)) {
            enharmonicDropDown.getSelectionModel().select(2);
        }
    }

    /**
     * Handles Key release. Sets multi select to false if key was SHIFT.
     *
     * @param keyEvent key event
     */
    @FXML
    public void handleKeyRelease(KeyEvent keyEvent) {
        // Disables multi selecting
        if (keyEvent.getCode().equals(KeyCode.SHIFT)) {
            multiSelect = false;
            if (canShowNoteLabel) {
                clearAllNoteNames();
            }
        }

        // Bind for enharmonic lower
        if (keyEvent.getCode().equals(KeyCode.O)) {
            enharmonicDropDown.getSelectionModel().select(0);
        }

        // Bind for enharmonic higher
        if (keyEvent.getCode().equals(KeyCode.P)) {
            enharmonicDropDown.getSelectionModel().select(0);
        }
    }

    /**
     * Handles any of the checkboxes being selected.
     *
     * @param event The action event.
     */
    @FXML
    public void handleCheckButtons(ActionEvent event) {
        if (event.getSource() == inputCheckBox) {
            canInput = inputCheckBox.isSelected();
        } else if (event.getSource() == muteCheckBox) {
            muted = muteCheckBox.isSelected();
        }
    }

    /**
     * Used to load the keyboard elements into the scene.
     */
    public void loadKeyboard(Double keyboardSceneWidth) {
        contentBox = new HBox();
        Integer numberOfFullOctaveKeys = 10;

        // Add 8 one octave key sets to the HBox
        for (Integer index = 0; index < numberOfFullOctaveKeys; index++) {
            Pane pane = new Pane();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                pane = fxmlLoader.load(getClass().getResource(
                        "/scenes/keyboard/OneOctaveKeys.fxml").openStream());
                controllerList.add(fxmlLoader.getController());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Sets the userdata to the octave number
            pane.setUserData(index - 1);
            contentBox.getChildren().addAll(pane);
        }

        // Add the last octave key set to the HBox
        Pane pane = new Pane();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            pane = fxmlLoader.load(getClass().getResource(
                    "/scenes/keyboard/FinalOctaveKeys.fxml").openStream());
            controllerList.add(fxmlLoader.getController());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Sets the userdata to the octave number
        pane.setUserData(9);
        contentBox.getChildren().addAll(pane);

        // Add the HBox with the keyboard elements to the scroll pane
        keyboardScrollPane.setContent(contentBox);

        // Translates the rectangle based on the scroll value
        keyboardScrollPane.hvalueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number oldValue, Number newValue) {
                Double keyboardLength = 5625.0;
                Double lengthPercentage = 1 - (keyboardSceneWidth / keyboardLength);
                viewRectangle.setTranslateX((lengthPercentage * keyboardSceneWidth)
                        * keyboardScrollPane.getHvalue());
            }
        });

        // Sets the octave slider to the middle c octave
        Double horizontalScrollValue = 0.572;
        keyboardScrollPane.setHvalue(horizontalScrollValue);
    }

    /**
     * Calls the controllers for the keys and sets the note names.
     */
    private void showAllNoteNames() {
        for (Integer index = 0; index < 11; index++) {
            OneOctaveKeysController controller = controllerList.get(index);
            controller.addNoteNamesToKeys(index - 1);
        }
    }

    /**
     * Calls the controllers for the keys and sets the note names.
     */
    private void showLowerNoteNames() {
        for (Integer index = 0; index < 11; index++) {
            OneOctaveKeysController controller = controllerList.get(index);
            controller.addLowerNoteNamesToKeys(index - 1);
        }
    }

    /**
     * Calls the controllers for the keys and sets the note names.
     */
    private void showHigherNoteNames() {
        for (Integer index = 0; index < 11; index++) {
            OneOctaveKeysController controller = controllerList.get(index);
            controller.addHigherNoteNamesToKeys(index - 1);
        }
    }

    /**
     * Calls the controllers for the keys and sets the note names.
     */
    private void clearAllNoteNames() {
        for (Integer index = 0; index < 11; index++) {
            OneOctaveKeysController controller = controllerList.get(index);
            controller.clearNoteNameKeys(index - 1);
        }
    }

    /**
     * Resets all the key fills for the whole keyboard.
     */
    public void resetKeyFills() {
        for (Integer index = 0; index < 11; index++) {
            OneOctaveKeysController controller = controllerList.get(index);
            controller.resetKeyFills(index - 1);
        }
    }

    /**
     * Used to add a multi selected note to an array if it is not already in there.
     *
     * @param note The multi selected note.
     */
    public void addMultiSelectedNote(String note) {
        if (!multiSelectNotes.contains(note)) {
            multiSelectNotes.add(note);
        }
    }

    /**
     * Used to check if a note is already in the multi selected notes array.
     *
     * @param note The note.
     * @return A boolean.
     */
    public Boolean containsMultiSelectedNote(String note) {
        return multiSelectNotes.contains(note);
    }

    /**
     * Used to delete a note from the multi selected notes array.
     *
     * @param note The note.
     */
    public void deleteMultiSelectedNote(String note) {
        multiSelectNotes.remove(note);
    }

    /**
     * Used to get the integer related to a higher or lower enharmonic.
     *
     * @return The enharmonic integer.
     */
    public Integer getEnharmonic() {
        return enharmonic;
    }

    /**
     * Returns a boolean indicating whether to show the note label or not.
     *
     * @return The boolean.
     */
    public Boolean canShowNoteLabel() {
        return canShowNoteLabel;
    }

    /**
     * Returns a boolean indicating whether to the keyboard is in multi select mode.
     *
     * @return The boolean.
     */
    public Boolean isMultiSelect() {
        return multiSelect;
    }

    /**
     * Returns true if the keyboard can enter input.
     *
     * @return The boolean value.
     */
    public Boolean getCanInput() {
        return canInput;
    }

    /**
     * Returns true if the notes should be played.
     *
     * @return The boolean.
     */
    public Boolean muteEnabled() {
        return muted;
    }

    /**
     * Used to get the last focused field.
     *
     * @return The last focused field.
     */
    public String getLastFocusedField() {
        return lastFocusedField;
    }

    /**
     * Used to set the last focused field.
     *
     * @param lastFocusedField The last focused field.
     */
    public void setLastFocusedField(String lastFocusedField) {
        this.lastFocusedField = lastFocusedField;
    }
}
