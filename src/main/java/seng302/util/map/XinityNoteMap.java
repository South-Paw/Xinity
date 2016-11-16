package seng302.util.map;

import seng302.util.enumerator.NoteAccidental;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Common note mappings.
 *
 * @author adg62, plr37, ljm163
 */
public final class XinityNoteMap {
    private static Map<Integer, String> midiToNoteMap;
    private static Map<String, Integer> pianoToMidiMap;
    private static Map<Integer, List<String>> noteMap;
    private static Map<Integer, List<String>> keyBoardNoteMap;
    private static Map<Integer, NoteAccidental> integerToAccidentalMap;


    /**
     * Gets the classical note map of midi notes to their classical note. This table does not
     * include flats because they are equivalents.
     *
     * @return The midi to note map.
     */
    public static Map<Integer, String> getMidiToNoteMap() {
        if (midiToNoteMap == null) {
            Map<Integer, String> map = new HashMap<>();

            map.put(0, "C");
            map.put(1, "C#");
            map.put(2, "D");
            map.put(3, "D#");
            map.put(4, "E");
            map.put(5, "F");
            map.put(6, "F#");
            map.put(7, "G");
            map.put(8, "G#");
            map.put(9, "A");
            map.put(10, "A#");
            map.put(11, "B");

            midiToNoteMap = map;
        }

        return Collections.unmodifiableMap(midiToNoteMap);
    }

    /**
     * Gets the Piano Note map of classical notes to their midi note.
     *
     * @return The note to midi map.
     */
    public static Map<String, Integer> getPianoToMidiMap() {
        if (pianoToMidiMap == null) {
            Map<String, Integer> map = new HashMap<>();

            map.put("Cbb", 2);
            map.put("Cb", -1);
            map.put("C", 0);
            map.put("Dbb", 0);
            map.put("C#", 1);
            map.put("Db", 1);
            map.put("Cx", 2);
            map.put("D", 2);
            map.put("Ebb", 2);
            map.put("D#", 3);
            map.put("Eb", 3);
            map.put("Dx", 3);
            map.put("E", 4);
            map.put("Fb", 4);
            map.put("Fbb", 4);
            map.put("E#", 5);
            map.put("F", 5);
            map.put("Gbb", 5);
            map.put("Ex", 6);
            map.put("F#", 6);
            map.put("Gb", 6);
            map.put("Fx", 6);
            map.put("G", 7);
            map.put("Abb", 7);
            map.put("G#", 8);
            map.put("Ab", 8);
            map.put("Gx", 9);
            map.put("A", 9);
            map.put("Bbb", 9);
            map.put("A#", 10);
            map.put("Bb", 10);
            map.put("Ax", 11);
            map.put("B", 11);
            map.put("B#", 12);
            map.put("Bx", 13);

            pianoToMidiMap = map;
        }

        return Collections.unmodifiableMap(pianoToMidiMap);
    }

    /**
     * Gets the Note Map.
     * Notes are kept in lexigraphical ordering.
     *
     * @return midi to a map of all notes, including enharmonic equivalents
     */
    public static Map<Integer, List<String>> getNoteMap() {
        if (noteMap == null) {
            Map<Integer, List<String>> map = new HashMap<>();

            map.put(0, Arrays.asList("B#", "C", "Dbb"));
            map.put(1, Arrays.asList("Bx", "C#", "Db"));
            map.put(2, Arrays.asList("Cx", "D", "Ebb"));
            map.put(3, Arrays.asList("D#", "Eb", "Fbb"));
            map.put(4, Arrays.asList("Dx", "E", "Fb"));
            map.put(5, Arrays.asList("E#", "F", "Gbb"));
            map.put(6, Arrays.asList("Ex", "F#", "Gb"));
            map.put(7, Arrays.asList("Fx", "G", "Abb"));
            map.put(8, Arrays.asList("G#", "Ab"));
            map.put(9, Arrays.asList("Gx", "A", "Bbb"));
            map.put(10, Arrays.asList("A#", "Bb", "Cbb"));
            map.put(11, Arrays.asList("Ax", "B", "Cb"));

            noteMap = map;
        }

        return Collections.unmodifiableMap(noteMap);
    }

    /**
     * Gets the Keyboard Note Map (used for the keyboard).
     *
     * @return midi to a map of all notes, including enharmonic equivalents
     */
    public static Map<Integer, List<String>> getKeyboardNoteMap() {
        if (keyBoardNoteMap == null) {
            Map<Integer, List<String>> map = new HashMap<>();

            map.put(0, Arrays.asList("C", "B#", "Dbb"));
            map.put(1, Arrays.asList("C#", "Bx", "Db"));
            map.put(2, Arrays.asList("D", "Cx", "Ebb"));
            map.put(3, Arrays.asList("D#", "", "Eb"));
            map.put(4, Arrays.asList("E", "Dx", "Fb"));
            map.put(5, Arrays.asList("F", "E#", "Gbb"));
            map.put(6, Arrays.asList("F#", "Ex", "Gb"));
            map.put(7, Arrays.asList("G", "Fx", "Abb"));
            map.put(8, Arrays.asList("G#", "", "Ab"));
            map.put(9, Arrays.asList("A", "Gx", "Bbb"));
            map.put(10, Arrays.asList("A#", "", "Bb"));
            map.put(11, Arrays.asList("B", "Ax", "Cb"));

            keyBoardNoteMap = map;
        }

        return Collections.unmodifiableMap(keyBoardNoteMap);
    }

    /**
     * Map of integer to accidental.
     * @return the integerToAccidentalMap
     */
    public static Map<Integer, NoteAccidental> getIntegerToAccidentalMap() {
        if (integerToAccidentalMap == null) {
            Map<Integer, NoteAccidental> map = new HashMap<>();
            map.put(-2, NoteAccidental.DOUBLEFLAT);
            map.put(-1, NoteAccidental.FLAT);
            map.put(0, NoteAccidental.NATURAL);
            map.put(1, NoteAccidental.SHARP);
            map.put(2, NoteAccidental.DOUBLESHARP);
            integerToAccidentalMap = map;
        }
        return Collections.unmodifiableMap(integerToAccidentalMap);
    }
}
