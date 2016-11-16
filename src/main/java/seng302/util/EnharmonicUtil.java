package seng302.util;

import seng302.util.enumerator.NoteAccidental;
import seng302.util.object.XinityNote;

import java.util.Arrays;
import java.util.List;

/**
 * Enharmonic utility class.
 *
 * @author avh17, ljm163
 */
public final class EnharmonicUtil {

    //Lists ordered for Higher Enharmonics.
    private static List<String> higherLetters = Arrays.asList("B", "C", "D", "E", "F", "G", "A");
    private static List<String> higherAccidentalEven = Arrays.asList("bb", "none", "none", "b", "");
    private static List<String> higherAccidentalOdd = Arrays.asList("b", "bb", "none", "", "#");

    //Lists ordered for Lower Enharmonics.
    private static List<String> lowerLetters = Arrays.asList("G", "A", "B", "C", "D", "E", "F");
    private static List<String> lowerAccidentalEven = Arrays.asList("x", "#", "", "none", "none");
    private static List<String> lowerAccidentalOdd = Arrays.asList("#", "", "b", "x", "none");

    /** Private Constructor. */
    private EnharmonicUtil() {}

    /**
     * Calculates the enharmonic equivalent and returns it as a note object.
     * Returns null if there is no enharmonic for the given note.
     *
     * @param direction The higher or lower enharmonic.
     * @param note The note used for calculating.
     * @return The enharmonic note object or null.
     */
    public static XinityNote findEnharmonicEquivalent(String direction, XinityNote note) {
        Integer noteIndex = getLetterIndex(note.getLetter());
        Integer accidentalIndex = getAccidentalIndex(note);
        Integer octaveNumber = note.getOctave();
        String calculatedNote;
        XinityNote returnNote;

        //Calculates the note and octave
        if (direction.equals("+")) {
            if ((note.getLetter().equals("B")
                    && !note.getAccidental().equals(NoteAccidental.FLAT))) {
                octaveNumber++;
            }
            calculatedNote = handleHigherEnharmonic(noteIndex, accidentalIndex);
        } else {
            if (note.getLetter().equals("C")
                    && !note.getAccidental().equals(NoteAccidental.SHARP)) {
                octaveNumber--;
            }
            calculatedNote = handleLowerEnharmonic(noteIndex, accidentalIndex);
        }

        //Handles what to return
        if (calculatedNote == null) {
            return null;
        } else {
            try  {
                returnNote = new XinityNote(calculatedNote + octaveNumber);
            } catch (Exception ex) {
                return null;
            }
            return returnNote;
        }
    }

    /**
     * Finds the simple enharmonic of a given note, returns null if there isn't one.
     *
     * @param note The given note.
     * @return The enharmonic or null.
     */
    public static XinityNote findSimpleEnharmonicEquivalent(XinityNote note) {
        XinityNote higherEnharmonic = findEnharmonicEquivalent("+", note);
        XinityNote lowerEnharmonic = findEnharmonicEquivalent("-", note);

        if (higherEnharmonic == null) {
            if (lowerEnharmonic == null) {
                return null;
            } else if (lowerEnharmonic.getIsDouble()) {
                return null;
            } else {
                return lowerEnharmonic;
            }
        } else if (lowerEnharmonic == null) {
            if (higherEnharmonic.getIsDouble()) {
                return null;
            } else {
                return higherEnharmonic;
            }
        }

        if (higherEnharmonic.getIsDouble()) {
            if (lowerEnharmonic.getIsDouble()) {
                return null;
            } else {
                return lowerEnharmonic;
            }
        } else {
            return higherEnharmonic;
        }
    }

    /**
     * Calculates the higher enharmonic.
     *
     * @param noteIndex The index for getting the note letter.
     * @param accidentalIndex The index for getting the accidental.
     * @return The enharmonic as a string or null.
     */
    private static String handleHigherEnharmonic(Integer noteIndex, Integer accidentalIndex) {
        String noteLetter = higherLetters.get(noteIndex);
        String accidental;
        if (noteIndex == 1 || noteIndex == 4) {
            accidental = higherAccidentalOdd.get(accidentalIndex);
        } else {
            accidental = higherAccidentalEven.get(accidentalIndex);
        }
        if (accidental.equals("none")) {
            return null;
        }
        return noteLetter + accidental;
    }

    /**
     * Calculates the lower enharmonic.
     *
     * @param noteIndex The index for getting the note letter.
     * @param accidentalIndex The index for getting the accidental.
     * @return The enharmonic as a string or null.
     */
    private static String handleLowerEnharmonic(Integer noteIndex, Integer accidentalIndex) {
        String noteLetter = lowerLetters.get(noteIndex);
        String accidental;
        if (noteIndex == 2 || noteIndex == 5) {
            accidental = lowerAccidentalOdd.get(accidentalIndex);
        } else {
            accidental = lowerAccidentalEven.get(accidentalIndex);
        }
        if (accidental.equals("none")) {
            return null;
        } else {
            return noteLetter + accidental;
        }
    }

    /**
     * Used to find the number to use as the note letter index.
     *
     * @param note The note to use.
     * @return The index.
     */
    private static Integer getLetterIndex(String note) {
        Integer index = 0;
        List<String> noteLetters = Arrays.asList("A", "B", "C", "D", "E", "F", "G");
        for (int i = 0; i < noteLetters.size(); i++) {
            if (note.equals(noteLetters.get(i))) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Used to find the number to use as the accidental index.
     *
     * @param note The note to use.
     * @return The index.
     */
    private static Integer getAccidentalIndex(XinityNote note) {
        List<NoteAccidental> accidentals = Arrays.asList(
                NoteAccidental.NATURAL,
                NoteAccidental.FLAT,
                NoteAccidental.DOUBLEFLAT,
                NoteAccidental.SHARP,
                NoteAccidental.DOUBLESHARP);
        return accidentals.indexOf(note.getAccidental());
    }
}
