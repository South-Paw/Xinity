package seng302.util;

import seng302.util.enumerator.NoteAccidental;
import seng302.util.map.XinityNoteMap;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Note utility.
 *
 * @author ljm163, plr37
 */
public final class NoteUtil {

    private static Map<String, Integer> midiTable = XinityNoteMap.getPianoToMidiMap();
    private static Map<Integer, String> classicalTable = XinityNoteMap.getMidiToNoteMap();

    /**
     * Private Constructor.
     */
    private NoteUtil() {
    }

    /**
     * Converts the given inputNote to a midi plus octave representation.
     *
     * @param inputNote The note given by the user.
     * @return The midi note number for a given note plus octave representation.
     */
    public static Integer convertToMidi(XinityNote inputNote) {
        String note;
        Integer octave;
        Integer midiValue;

        note = inputNote.getLetter();
        octave = inputNote.getOctave() + 1;

        if (inputNote.getAccidental().equals(NoteAccidental.DOUBLESHARP)) {
            midiValue = 12 * octave + midiTable.get(note) + 2;
        } else if (inputNote.getAccidental().equals(NoteAccidental.DOUBLEFLAT)) {
            midiValue = 12 * octave + midiTable.get(note) - 2;
        } else if (inputNote.getAccidental().equals(NoteAccidental.SHARP)) {
            midiValue = 12 * octave + midiTable.get(note) + 1;
        } else if (inputNote.getAccidental().equals(NoteAccidental.FLAT)) {
            midiValue = 12 * octave + midiTable.get(note) - 1;
        } else {
            midiValue = 12 * octave + midiTable.get(note);
        }
        return midiValue;
    }

    /**
     * Find the note for the given midi number.
     *
     * @param inputMidi A midi number.
     * @return The note and octave.
     */
    public static String convertToNote(Integer inputMidi) {
        Integer numericNote = inputMidi % 12;
        String letter = classicalTable.get(numericNote % 12);
        return letter + Integer.toString((inputMidi - numericNote) / 12 - 1);
    }

    /**
     * Converts an Array of String notes into an Array of XinityNotes.
     *
     * @param noteStringArray List
     * @return List
     */
    public static List<XinityNote> arrayStringAsNoteArray(List<String> noteStringArray) {
        List<XinityNote> noteArray = new ArrayList<>();
        try {
            for (String string : noteStringArray) {
                noteArray.add(new XinityNote(string));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return noteArray;
    }

    /**
     * Determine if the midiNote is within the valid midi range of the project.
     *
     * @return True if the note can be mapped to midi between 0 - 127
     */
    public static Boolean isValidMidiRange(Integer midiNote) {
        return (midiNote >= 0 && midiNote <= 127);
    }

    /**
     * Sorts a list of notes from least to greatest
     * while maintaining the correct enharmonics.
     *
     * @param notes List of Xinity Notes.
     */
    public static void sort(List<XinityNote> notes) {
        Collections.sort(notes);
    }


    /**
     * Function that increments the octave number.
     * Used for Chord inversions.
     */
    public static XinityNote incrementedOctaveNote(XinityNote note, Integer increment) {
        try {
            return new XinityNote(
                    note.getLetter() + note.getAccidental().toMusicalRepresentation()
                            + (note.getOctave() + increment));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Generates a random note between the given ranges.
     *
     * @param rangeOne The lower range.
     * @param rangeTwo The higher range.
     * @return The random note.
     */
    public static XinityNote randomNote(Integer rangeOne, Integer rangeTwo) {
        Random random = new Random();
        XinityNote note;

        // Make sure range one is lower than range two
        if (rangeOne > rangeTwo) {
            Integer tempRange = rangeOne;
            rangeOne = rangeTwo;
            rangeTwo = tempRange;
        }

        // Generates a random midi number between the given ranges (inclusive)
        Integer randomMidi = random.nextInt((rangeTwo - rangeOne) + 1) + rangeOne;

        try {
            note = new XinityNote(NoteUtil.convertToNote(randomMidi));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return note;
    }
}
