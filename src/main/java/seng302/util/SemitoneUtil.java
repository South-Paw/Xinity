package seng302.util;

import seng302.util.enumerator.NoteAccidental;
import seng302.util.map.XinityNoteMap;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Semitone utility.
 * Note: There is no 'range' on the SemitoneUtil. ie; you can give it 1, A9 and it'll return A#9.
 * The reason for this is that while this util will give what may be considered an 'invalid note',
 * other utils should create issues/errors when the note is out of range. SemitoneUtil is just like
 * a math library. It does a job - not the error checking.
 * Please also see the comment on the neighbouringNote() method about note types.
 *
 * @author ljm163, adg62
 */
public final class SemitoneUtil {
    /** Map containing Xinity notes. */
    private static Map<String, Integer> noteTable =  XinityNoteMap.getPianoToMidiMap();

    /** Private Constructor. */
    private SemitoneUtil() {}

    /**
     * Locates the note given the value in the hash map.
     * Key is different based on the ascending/descending.
     *
     * @param increment The given increment.
     * @param index The index of the note.
     * @return the result note.
     */
    private static XinityNote getNoteByIndex(Integer increment, Integer index) {
        List<NoteAccidental> noteAccidentals = new ArrayList<>();

        if (increment > 0) { // If ascending
            noteAccidentals = Arrays.asList(NoteAccidental.SHARP, NoteAccidental.NATURAL);
        } else if (increment < 0) { // If descending
            noteAccidentals = Arrays.asList(NoteAccidental.FLAT, NoteAccidental.NATURAL);
        }

        for (String string : noteTable.keySet()) {
            try {
                XinityNote note = new XinityNote(string);
                NoteAccidental accidental = note.getAccidental();
                if (noteTable.get(string).equals(index) && noteAccidentals.contains(accidental)) {
                    return note;
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Find the next note in the musical sequence (semitone higher).
     * Note: Will accept double sharps/flats but will still treat it as a simple note.
     *
     * @param increment The given increment.
     * @param note The given note.
     * @return The next note in the sequence by the given increment.
     */
    public static XinityNote neighbouringNote(Integer increment, XinityNote note) {
        // note will note change
        if (increment == 0) {
            return note;
        }

        String simpleNote = note.getLetterAndAccidental();
        Integer octave = note.getOctave() + (increment / 12);

        // Find index of neighboring note in the note array.
        Integer index = noteTable.get(simpleNote) + (increment % 12);
        if (index >= 12) { // Check if reached end of array and start at beginning.
            index = index - 12;
            octave++;
        } else if (index < 0) { // Check if reached end of array and got to end.
            index = 12 + index;
            octave--;
        }

        // neighbouring note without octave
        XinityNote neighbouringNote = getNoteByIndex(increment, index);
        try {
            if (neighbouringNote != null) {
                // return neighbouring note with new octave
                return new XinityNote(neighbouringNote.getLetterAndAccidental() + octave);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Find the number of semitones between the notes. Method can assume that the second note is
     * always larger than the first, therefore semitones will always be positive.
     *
     * @param note1 The first note.
     * @param note2 The second note.
     * @return The number of semitones between the two notes.
     */
    public static Integer findSemitones(XinityNote note1, XinityNote note2) {
        Integer note1AsMidi = NoteUtil.convertToMidi(note1);
        Integer note2AsMidi = NoteUtil.convertToMidi(note2);
        return note2AsMidi - note1AsMidi;
    }

    /**
     * Flatten notes of a major scale to get pentatonic notes.
     * It passes test but does it work for all possible notes of a major scale?
     *
     * @param note the note to be flattened
     * @return flattened pentatonic note
     */
    public static XinityNote flattenNote(XinityNote note) {
        try {
            if (note.getAccidental().equals(NoteAccidental.SHARP)) {
                return new XinityNote(note.getLetter() + note.getOctave());
            } else if (note.getAccidental().equals(NoteAccidental.DOUBLESHARP)) {
                return new XinityNote(note.getLetter()
                        + NoteAccidental.SHARP.toMusicalRepresentation() + note.getOctave());
            }
            return neighbouringNote(-1, note);
        } catch (Exception e) {
            return null;
        }
    }
}
