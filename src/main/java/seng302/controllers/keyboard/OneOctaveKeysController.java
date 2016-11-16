package seng302.controllers.keyboard;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import seng302.controllers.TranscriptSceneController;
import seng302.controllers.pitchtutor.PitchTutorPopUpController;
import seng302.controllers.signaturetutor.SignatureQuestionController;
import seng302.controllers.spellingtutor.SpellingQuestionController;
import seng302.util.EnharmonicUtil;
import seng302.util.NoteUtil;
import seng302.util.PlayServiceUtil;
import seng302.util.map.XinityNoteMap;
import seng302.util.object.XinityNote;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Controller for one octave of keyboard keys.
 */
public class OneOctaveKeysController {

    @FXML private Rectangle ckey;
    @FXML private Rectangle dkey;
    @FXML private Rectangle ekey;
    @FXML private Rectangle fkey;
    @FXML private Rectangle gkey;
    @FXML private Rectangle akey;
    @FXML private Rectangle bkey;
    @FXML private Rectangle csharpKey;
    @FXML private Rectangle dsharpKey;
    @FXML private Rectangle fsharpKey;
    @FXML private Rectangle gsharpKey;
    @FXML private Rectangle asharpKey;

    @FXML private Text ckeyText;
    @FXML private Text dkeyText;
    @FXML private Text ekeyText;
    @FXML private Text fkeyText;
    @FXML private Text gkeyText;
    @FXML private Text akeyText;
    @FXML private Text bkeyText;
    @FXML private Text csharpKeyText;
    @FXML private Text dsharpKeyText;
    @FXML private Text fsharpKeyText;
    @FXML private Text gsharpKeyText;
    @FXML private Text asharpKeyText;

    private Map<Integer, List<String>> noteMap = XinityNoteMap.getKeyboardNoteMap();
    private KeyboardSceneController keyboard = KeyboardSceneController.getInstance();
    private List<String> keyStrings;
    private List<Text> keyTexts;
    private List<Rectangle> keyElements;

    /**
     * The initialize function which is called after the class is constructed.
     */
    @FXML
    public void initialize() {
        // Populate the keyStrings, keyTexts and keyElements lists
        keyStrings = Arrays.asList(
                "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B");

        keyTexts = Arrays.asList(ckeyText, csharpKeyText, dkeyText, dsharpKeyText,
                ekeyText, fkeyText, fsharpKeyText, gkeyText, gsharpKeyText,
                akeyText, asharpKeyText, bkeyText);

        keyElements = Arrays.asList(ckey, csharpKey, dkey,
                dsharpKey, ekey, fkey, fsharpKey, gkey, gsharpKey, akey, asharpKey, bkey);
    }

    /**
     * Function that handles the keys being pressed.
     *
     * @param event The mouse click event.
     */
    @FXML
    public void pianoKeyPressed(MouseEvent event) throws Exception {
        Paint noteFill = Paint.valueOf("#c6afaf");
        Paint sharpNoteFill = Paint.valueOf("#5b5151");

        // displays the note name for the given key
        Node key = (Node) event.getSource();
        Node octave = ((Node) event.getSource()).getParent();
        String keyName = key.getUserData().toString();
        String octaveNumber = octave.getUserData().toString();
        String note = keyName + octaveNumber;
        String enharmonicNote = note;
        Integer enharmonic = keyboard.getEnharmonic();

        // Convert note to enharmonic equivalent if needed
        if (enharmonic == 1 || enharmonic == 2) { // Must be higher or lower
            XinityNote xinityNote;
            XinityNote enharmonicEquivalent;
            try {
                xinityNote = new XinityNote(note);
            } catch (Exception ex) {
                xinityNote = null;
            }
            if (xinityNote != null) {
                String direction = "+";
                if (enharmonic == 1) {
                    direction = "-";
                }
                enharmonicEquivalent = EnharmonicUtil.findEnharmonicEquivalent(direction,
                        xinityNote);
                if (enharmonicEquivalent != null) {
                    enharmonicNote = enharmonicEquivalent.getNote();
                }
            }
        }

        Integer noteIndex = keyStrings.indexOf(keyName);

        if (keyboard.canShowNoteLabel()) {
            if ((noteMap.get(noteIndex)).get(enharmonic).equals("")) {
                octaveNumber = "";
            }
            String keyText = (noteMap.get(noteIndex)).get(enharmonic) + octaveNumber;
            keyTexts.get(noteIndex).setText(keyText);
        }

        if (keyStrings.get(noteIndex).length() > 1) {
            keyElements.get(noteIndex).setFill(sharpNoteFill);
        } else {
            keyElements.get(noteIndex).setFill(noteFill);
        }

        // Handles multi selection and keyboard input and mute actions
        if (keyboard.isMultiSelect()) {
            if (keyboard.containsMultiSelectedNote(enharmonicNote)) {
                keyboard.deleteMultiSelectedNote(enharmonicNote);
                if (keyStrings.get(noteIndex).length() > 1) {
                    keyElements.get(noteIndex).setFill(Paint.valueOf("BLACK"));
                } else {
                    keyElements.get(noteIndex).setFill(Paint.valueOf("WHITE"));
                }
            } else {
                keyboard.addMultiSelectedNote(enharmonicNote);
            }
        } else {
            if (!keyboard.muteEnabled()) {
                PlayServiceUtil.playMidi(NoteUtil.convertToMidi(new XinityNote(note)), 200);
            }

            if (keyboard.getCanInput()) {
                if (keyboard.getLastFocusedField().equals("Transcript")) {
                    TranscriptSceneController.getInstance().addTextToEntryField(enharmonicNote);
                } else if (keyboard.getLastFocusedField().equals("PitchRangeOne")) {
                    try {
                        PitchTutorPopUpController.getInstance().setNoteRangeOne(enharmonicNote);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (keyboard.getLastFocusedField().equals("PitchRangeTwo")) {
                    try {
                        PitchTutorPopUpController.getInstance().setNoteRangeTwo(enharmonicNote);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (keyboard.getLastFocusedField().equals("SignatureInput")) {
                    try {
                        SignatureQuestionController.getInstance().setInput(enharmonicNote
                                .substring(0, enharmonicNote.length() - 1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (keyboard.getLastFocusedField().equals("SpellingInput")) {
                    try {
                        SpellingQuestionController.getInstance().setInput(enharmonicNote
                                .substring(0, enharmonicNote.length() - 1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Function that handles the keys being released.
     *
     * @param event The mouse click event.
     */
    @FXML
    public void pianoKeyReleased(MouseEvent event) {
        Node octave = ((Node) event.getSource()).getParent();

        // Checks if the user is multi selecting
        if (!keyboard.isMultiSelect()) {
            // Resets the key fills for the whole keyboard
            keyboard.resetKeyFills();

            // Clears the key names of the current octave
            if (keyboard.canShowNoteLabel()) {
                clearNoteNameKeys(Integer.valueOf(octave.getUserData().toString()));
            }
        }
    }

    /**
     * Used to reset the colour values of the keys in the octave.
     *
     * @param octaveNumber The octave number.
     */
    public void resetKeyFills(Integer octaveNumber) {
        ckey.setFill(Paint.valueOf("WHITE"));
        dkey.setFill(Paint.valueOf("WHITE"));
        ekey.setFill(Paint.valueOf("WHITE"));
        fkey.setFill(Paint.valueOf("WHITE"));
        gkey.setFill(Paint.valueOf("WHITE"));
        csharpKey.setFill(Paint.valueOf("BLACK"));
        dsharpKey.setFill(Paint.valueOf("BLACK"));
        fsharpKey.setFill(Paint.valueOf("BLACK"));

        if (octaveNumber != 9) {
            akey.setFill(Paint.valueOf("WHITE"));
            bkey.setFill(Paint.valueOf("WHITE"));
            gsharpKey.setFill(Paint.valueOf("BLACK"));
            asharpKey.setFill(Paint.valueOf("BLACK"));
        }
    }

    /**
     * Adds the note names to all the keys.
     *
     * @param octaveNumber The octaveNumber.
     */
    public void addNoteNamesToKeys(Integer octaveNumber) {
        ckeyText.setText((noteMap.get(0)).get(0) + octaveNumber);
        csharpKeyText.setText((noteMap.get(1)).get(0) + octaveNumber);
        dkeyText.setText((noteMap.get(2)).get(0) + octaveNumber);
        dsharpKeyText.setText((noteMap.get(3)).get(0) + octaveNumber);
        ekeyText.setText((noteMap.get(4)).get(0) + octaveNumber);
        fkeyText.setText((noteMap.get(5)).get(0) + octaveNumber);
        fsharpKeyText.setText((noteMap.get(6)).get(0) + octaveNumber);
        gkeyText.setText((noteMap.get(7)).get(0) + octaveNumber);

        // Handle the keys that do not exist in the final octave
        if (octaveNumber != 9) {
            gsharpKeyText.setText((noteMap.get(8)).get(0) + octaveNumber);
            akeyText.setText((noteMap.get(9)).get(0) + octaveNumber);
            asharpKeyText.setText((noteMap.get(10)).get(0) + octaveNumber);
            bkeyText.setText((noteMap.get(11)).get(0) + octaveNumber);
        }
    }

    /**
     * Adds the lower enharmonic note names to all the keys.
     *
     * @param octaveNumber The octaveNumber.
     */
    public void addLowerNoteNamesToKeys(Integer octaveNumber) {
        ckeyText.setText((noteMap.get(0)).get(1) + octaveNumber);
        csharpKeyText.setText((noteMap.get(1)).get(1) + octaveNumber);
        dkeyText.setText((noteMap.get(2)).get(1) + octaveNumber);
        dsharpKeyText.setText("");
        ekeyText.setText((noteMap.get(4)).get(1) + octaveNumber);
        fkeyText.setText((noteMap.get(5)).get(1) + octaveNumber);
        fsharpKeyText.setText((noteMap.get(6)).get(1) + octaveNumber);
        gkeyText.setText((noteMap.get(7)).get(1) + octaveNumber);

        // Handle the keys that do not exist in the final octave
        if (octaveNumber != 9) {
            gsharpKeyText.setText("");
            akeyText.setText((noteMap.get(9)).get(1) + octaveNumber);
            asharpKeyText.setText("");
            bkeyText.setText((noteMap.get(11)).get(1) + octaveNumber);
        }
    }

    /**
     * Adds the higher enharmonic note names to all the keys.
     *
     * @param octaveNumber The octaveNumber.
     */
    public void addHigherNoteNamesToKeys(Integer octaveNumber) {
        ckeyText.setText((noteMap.get(0)).get(2) + octaveNumber);
        csharpKeyText.setText((noteMap.get(1)).get(2) + octaveNumber);
        dkeyText.setText((noteMap.get(2)).get(2) + octaveNumber);
        dsharpKeyText.setText((noteMap.get(3)).get(2) + octaveNumber);
        ekeyText.setText((noteMap.get(4)).get(2) + octaveNumber);
        fkeyText.setText((noteMap.get(5)).get(2) + octaveNumber);
        fsharpKeyText.setText((noteMap.get(6)).get(2) + octaveNumber);
        gkeyText.setText((noteMap.get(7)).get(2) + octaveNumber);

        // Handle the keys that do not exist in the final octave
        if (octaveNumber != 9) {
            gsharpKeyText.setText((noteMap.get(8)).get(2) + octaveNumber);
            akeyText.setText((noteMap.get(9)).get(2) + octaveNumber);
            asharpKeyText.setText((noteMap.get(10)).get(2) + octaveNumber);
            bkeyText.setText((noteMap.get(11)).get(2) + octaveNumber);
        }
    }

    /**
     * Clears the note names on the keys.
     *
     * @param octaveNumber The octaveNumber.
     */
    public void clearNoteNameKeys(Integer octaveNumber) {
        ckeyText.setText("");
        dkeyText.setText("");
        ekeyText.setText("");
        fkeyText.setText("");
        gkeyText.setText("");
        csharpKeyText.setText("");
        dsharpKeyText.setText("");
        fsharpKeyText.setText("");

        // Handle the keys that do not exist in the final octave
        if (octaveNumber != 9) {
            akeyText.setText("");
            bkeyText.setText("");
            gsharpKeyText.setText("");
            asharpKeyText.setText("");
        }
    }
}
