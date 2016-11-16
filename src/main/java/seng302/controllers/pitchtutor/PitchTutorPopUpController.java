package seng302.controllers.pitchtutor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import seng302.controllers.keyboard.KeyboardSceneController;
import seng302.util.NoteUtil;
import seng302.util.object.XinityNote;

/**
 * The Controller class for the pitch tutor popup.
 *
 * @author avh17
 */
public class PitchTutorPopUpController {

    private static PitchTutorPopUpController pitchTutorPopUpController;

    //GUI Elements
    @FXML private Button startTestButton;
    @FXML private TextField notePairsTextField = new TextField();
    @FXML private TextField noteRangeTextFieldOne = new TextField();
    @FXML private TextField noteRangeTextFieldTwo  = new TextField();
    @FXML private Text invalidValueText;

    private Stage stage;

    //Input data to set from pop up
    private Integer notePairsNumber = -1;
    private Integer midiNoteRangeOne = -1;
    private Integer midiNoteRangeTwo = -1;
    private final Integer upperMidiLimit = 127;
    private final Integer lowerMidiLimit = 0;
    private Integer lowerNotePairLimit = 0;
    private String invalidNotePairs = "Invalid Note Pairs Value";
    private String invalidNoteRange = "Invalid Note Range Values";

    /**
     * The constructor for the pitch tutor popup controller.
     */
    public PitchTutorPopUpController() {
        pitchTutorPopUpController = this;
    }

    /**
     * Used to get the instance of the Pitch Tutor Pop UP Controller.
     *
     * @return The Pitch Tutor Pop UP Controller.
     */
    public static PitchTutorPopUpController getInstance() {
        return pitchTutorPopUpController;
    }

    /**
     * Initialise function for when this scene is loaded.
     */
    @SuppressWarnings("Unused")
    public void initialize() {
        noteRangeTextFieldOne.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                                Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue && KeyboardSceneController.getInstance() != null) {
                    KeyboardSceneController.getInstance().setLastFocusedField("PitchRangeOne");
                }
            }
        });

        noteRangeTextFieldTwo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                                Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue && KeyboardSceneController.getInstance() != null) {
                    KeyboardSceneController.getInstance().setLastFocusedField("PitchRangeTwo");
                }
            }
        });
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
     * Used to set the default values in the text fields.
     */
    public void setDefaults() {
        notePairsTextField.appendText("1");
        noteRangeTextFieldOne.appendText("C4");
        noteRangeTextFieldTwo.appendText("C5");
    }

    /**
     * Handles the logic for the start test button.
     */
    private void startTestButtonHandler() {
        Boolean validNotePairNumber;
        Boolean validNoteValues;

        //Assigns the note pairs number
        try {
            this.notePairsNumber = Integer.parseInt(notePairsTextField.getText());
            validNotePairNumber = notePairsNumber > lowerNotePairLimit;
        } catch (Exception ex) {
            validNotePairNumber = false;
        }

        //Assigns the note range values and checks if they are valid
        validNoteValues = assignMidiNoteRange();

        //Checks to see if it is okay to close the pop up
        if (validNoteValues && validNotePairNumber) {
            PitchTutorSceneController.getInstance().startTest();
            stage = (Stage) startTestButton.getScene().getWindow();
            stage.close();
        } else {
            //Prints the error to the pop up GUI
            if (!validNotePairNumber) {
                invalidValueText.setText(invalidNotePairs);
            } else {
                invalidValueText.setText(invalidNoteRange);
            }
        }
    }

    /**
     * Used to set the midi note range.
     *
     * @return A boolean value to indicate whether the range is valid
     */
    private boolean assignMidiNoteRange() {
        //Assigns the midi note range numbers
        this.midiNoteRangeOne = setNoteNumber(noteRangeTextFieldOne.getText());
        this.midiNoteRangeTwo = setNoteNumber(noteRangeTextFieldTwo.getText());

        //Check the Midi number are in the correct range
        return !((midiNoteRangeOne < lowerMidiLimit) || (midiNoteRangeTwo < lowerMidiLimit)
                || (midiNoteRangeOne > upperMidiLimit) || (midiNoteRangeTwo > upperMidiLimit));
    }

    /**
     * Used to set the midi note number from a string.
     *
     * @param note The note string.
     * @return A midi note number.
     */
    private Integer setNoteNumber(String note) {
        Integer returnNote;
        try {
            //Try to pass the note value as a midi number
            returnNote = Integer.parseInt(note);
        } catch (Exception notInteger) {
            try {
                //Try to pass the note value as a note name string
                returnNote = NoteUtil.convertToMidi(new XinityNote(note));
            } catch (Exception notNote) {
                returnNote = -1;
            }
        }
        return returnNote;
    }

    /**
     * Getter for the note pairs number.
     *
     * @return The note pairs number.
     */
    public Integer getNotePairsNumber() {
        return notePairsNumber;
    }

    /**
     * Getter for the first midi note range value.
     *
     * @return The first midi note range value.
     */
    public Integer getMidiNoteRangeOne() {
        return midiNoteRangeOne;
    }

    /**
     * Getter for the second midi not range value.
     *
     * @return The second midi note range value.
     */
    public Integer getMidiNoteRangeTwo() {
        return midiNoteRangeTwo;
    }

    /**
     * Used to set the note on the range one field.
     *
     * @param note The note.
     */
    public void setNoteRangeOne(String note) {
        noteRangeTextFieldOne.setText(note);
    }

    /**
     * Used to set the note on the range two field.
     *
     * @param note The note.
     */
    public void setNoteRangeTwo(String note) {
        noteRangeTextFieldTwo.setText(note);
    }
}
