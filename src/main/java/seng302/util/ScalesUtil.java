package seng302.util;

import seng302.util.enumerator.ScaleDirection;
import seng302.util.enumerator.ScaleGroup;
import seng302.util.enumerator.ScaleMode;
import seng302.util.enumerator.ScaleType;
import seng302.util.map.XinityNoteMap;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Scales utility.
 * The different FindScale and FindScaleMidi functions are applied
 * to ScaleGroups from the ScaleGroup Enum.
 *
 * @author wrs35, ljm163, plr37
 */
public final class ScalesUtil {
    private static final Map<Integer, List<String>> noteMap = XinityNoteMap.getNoteMap();

    /** Private Constructor. */
    private ScalesUtil() {}
    /**
     * Generic find scale method. Finds and returns a scale given a tonic (starting note)
     * and a scale type.
     *
     * @param tonic - The starting note
     * @param scaleType - The type of Scale
     * @return The array list of XinityNotes representing the scale.
     */
    public static List<XinityNote> findScale(XinityNote tonic, ScaleType scaleType) {
        ScaleGroup scaleGroup = ScaleGroup.fromScaleType(scaleType);
        switch (scaleGroup) {
            case NATURAL:
            case MELODIC:
            case HARMONIC:
            case HARMONICMINORMODES:
            case MELODICMINORMODES:
            case MAJORMODES:
                return findNaturalScale(tonic, scaleType);
            case PENTATONIC:
                return findPentatonicScale(tonic, scaleType);
            case JAZZ:
                return findBluesScale(tonic, ScaleType.BLUES);
            default:
                return null;
        }
    }

    /**
     * Finds and returns a Natural scale given a starting note and a scale type.
     * When command is being run, the constructor initially checks if the note "isDouble".
     *
     * @param tonic Starting note of the scale.
     * @param scaleType The type of the scale, eg. Major, Minor.
     * @return The array of major scale notes in a string format.
     */
    private static List<XinityNote> findNaturalScale(XinityNote tonic, ScaleType scaleType) {
        List<XinityNote> scaleNotes = new ArrayList<>();
        scaleNotes.add(tonic);

        String expectedNote = tonic.getLetter() + tonic.getAccidental().toMusicalRepresentation();
        Integer octave = tonic.getOctave();

        Integer numChromaticNotes = 12;
        Integer numberOfNotesInAScale = scaleType.getScalePattern().size();
        Integer position = determineStartingPosition(expectedNote);

        for (Integer i = 0; i < numberOfNotesInAScale - 1; i++) {
            position += scaleType.getScalePattern().get(i);
            if (position >= numChromaticNotes) {
                position %= numChromaticNotes;
            }
            // Must come after position has been updated
            expectedNote = determineNextNote(position, expectedNote);
            if (expectedNote == null) {
                return null;
            }
            //update of octave
            if (expectedNote.contains("C")) {
                octave++;
            }
            try {
                scaleNotes.add(new XinityNote(expectedNote + octave));
            } catch (Exception e) {
                return null;
            }
        }
        return scaleNotes;
    }

    /**
     * Given a startNote and scaleType a major/minor scale is computed. A pentatonic scale is
     * extracted from this computed scale.
     *
     * @param tonic the scale notes
     * @param scaleType the scale type pentatonic major/minor
     * @return the pentatonic scale
     */
    private static List<XinityNote> findPentatonicScale(XinityNote tonic, ScaleType scaleType) {
        List<XinityNote> scaleNotes;
        switch (scaleType) {
            case PENTATONICMAJOR:
                scaleNotes = findNaturalScale(tonic, ScaleType.MAJOR);
                break;
            case PENTATONICMINOR:
                scaleNotes = findNaturalScale(tonic, ScaleType.MINOR);
                break;
            default:
                return null;
        }
        if (scaleNotes == null) {
            return null;
        }

        // if scale tonic is not found in circle of fifths, scale is invalid
        if (SignatureUtil.getMajorScaleToKeySignatureMap().get(
                tonic.getLetterAndAccidental()) == null
                && SignatureUtil.getMinorScaleToKeySignatureMap().get(
                        tonic.getLetterAndAccidental()) == null) {

            return null;
        }

        return getPartialScale(scaleNotes, scaleType);
    }

    /**
     * Return a list of notes corresponding to the blues scales given a note.
     * @param tonic the start note of the blues scale
     * @return the blues scale
     */
    private static List<XinityNote> findBluesScale(XinityNote tonic, ScaleType scaleType) {
        // construct blues scale from major scale
        List<XinityNote> scaleNotes = findNaturalScale(tonic, ScaleType.MAJOR);
        if (scaleNotes == null) {
            return null;
        }

        // if scale tonic is not found in circle of fifths, scale is invalid
        if (SignatureUtil.getMajorScaleToKeySignatureMap().get(
                tonic.getLetterAndAccidental()) == null
                && SignatureUtil.getMinorScaleToKeySignatureMap().get(
                tonic.getLetterAndAccidental()) == null) {

            return null;
        }

        List<XinityNote> bluesScale = getPartialScale(scaleNotes, scaleType);

        // flatten 3rd, 5th and 7th note of major scale
        if (bluesScale.size() > 1) {
            bluesScale.set(1, SemitoneUtil.flattenNote(scaleNotes.get(2)));
        }
        if (bluesScale.size() > 3) {
            bluesScale.set(3, SemitoneUtil.flattenNote(scaleNotes.get(4)));
        }
        if (bluesScale.size() > 5) {
            bluesScale.set(5, SemitoneUtil.flattenNote(scaleNotes.get(6)));
        }
        return bluesScale;
    }

    /**
     * Given the full set of notes for a scale, the method returns the partial scale corresponding
     * to the notes of a pentatonic/blues scale.
     *
     * @param scaleNotes the full scale notes
     * @param scaleType the scale type
     * @return the corresponding pentatonic or blues notes of the full scale
     */
    private static List<XinityNote> getPartialScale(
            List<XinityNote> scaleNotes, ScaleType scaleType) {

        List<XinityNote> partialScale = new ArrayList<>();
        for (Integer i = 0; i < scaleNotes.size(); i++) {
            for (Integer j = 0; j < scaleType.getScalePattern().size(); j++) {
                if (i.equals(j) && j < scaleNotes.size()) {
                    if (scaleType.getScalePattern().get(i) < scaleNotes.size()) {
                        XinityNote nextNote = scaleNotes.get(scaleType.getScalePattern().get(i));

                        // if first or last of scale do not alter note
                        if (i == 0 || i == scaleType.getScalePattern().size() - 1) {
                            partialScale.add(nextNote);
                        } else {
                            if (getLetterNoteEquivalent(nextNote) != null) {
                                partialScale.add(getLetterNoteEquivalent(nextNote));
                            } else {
                                partialScale.add(nextNote);
                            }
                        }
                    }
                }
            }
        }
        return partialScale;
    }

    /**
     * Builds an array of notes for the given a start note, a scale type,
     * a direction and the number of octaves so that it can be played via PlayScale.
     * Notes out of range are not rejected.
     *
     * @param tonic The starting note of the scale.
     * @param scaleType The type of the scale, eg. Major or Minor.
     * @param scaleDirection The direction the scale is being played, eg. up or down.
     * @param numberOfOctaves The number of octaves the scale is being played
     * @return The scale array that PlayScale will play.
     */
    public static List<XinityNote> buildPlayingScale(
            XinityNote tonic, ScaleType scaleType,
            ScaleDirection scaleDirection, Integer numberOfOctaves) {

        // create a list of one octave of the scale
        List<XinityNote> oneOctaveScale = findScale(tonic, scaleType);
        if (oneOctaveScale == null) {
            return null;
        }

        // minor and major scales will repeat note when played in 'both' directions
        Boolean repeatNote = false;
        if (ScaleGroup.fromScaleType(scaleType) == ScaleGroup.NATURAL
            && scaleDirection == ScaleDirection.BOTH) {
            repeatNote = true;
        }

        // simply reverse the notes played
        if (scaleDirection.equals(ScaleDirection.DOWN)) {
            Collections.reverse(oneOctaveScale);
        }

        List<XinityNote> scaleArray = new ArrayList<>();
        scaleArray.addAll(oneOctaveScale); // play one octave by default

        // handle multiple octaves specified
        for (Integer i = 1; i < numberOfOctaves; i++) {
            for (XinityNote scaleNote : oneOctaveScale.subList(1, oneOctaveScale.size())) {
                if (scaleDirection == ScaleDirection.DOWN) {
                    scaleArray.add(NoteUtil.incrementedOctaveNote(scaleNote, -i));
                } else {
                    scaleArray.add(NoteUtil.incrementedOctaveNote(scaleNote, i));
                }
            }
        }

        // play the scale up then down
        if (scaleDirection.equals(ScaleDirection.BOTH)) {
            List<XinityNote> descendingPart = new ArrayList<>(scaleArray);
            Collections.reverse(descendingPart);
            if (repeatNote) {
                scaleArray.addAll(descendingPart);
            } else { // add a copy of the descending list without the first note
                scaleArray.addAll(descendingPart.subList(1, descendingPart.size()));
            }
        }
        return scaleArray;
    }

    /**
     * Finds the bucket within the note map to start in.
     * @param startNote The starting note of the scale.
     * @return The startNote's position in the noteMap.
     */
    private static Integer determineStartingPosition(String startNote) {
        Integer startingPosition = -1;
        for (Integer i = 0; i < noteMap.size(); i++) {
            for (String note : noteMap.get(i)) {
                if (note.toLowerCase().equals(startNote.toLowerCase())) {
                    startingPosition = i;
                }
            }
        }
        return startingPosition;
    }

    /**
     * Determine's the letter value of the next note for use of searching within the expected bucket
     * in the note map.
     * @param position The position of the next note of the scale in the noteMap.
     * @param expectedNote The expected note or "next" note of the scale.
     * @return The "real" note that is being added to the scale.
     */
    private static String determineNextNote(Integer position, String expectedNote) {
        String nextNote = expectedNote.substring(0, 1).toUpperCase();
        int nextNoteAscii = (int) nextNote.charAt(0) + 1;
        nextNote = Character.toString((char) nextNoteAscii);
        if (nextNote.equals("H")) {
            nextNote = "A";
        }
        for (String note : noteMap.get(position)) {
            if (note.substring(0, 1).equals(nextNote)) {
                return note;
            }
        }
        return null;
    }

    /**
     * Gets upper and lower enharmonic of note and determines if it gives a letter note.
     * Used by the findPentatonicScale method.
     *
     * @param note the pentatonic scale note
     * @return the letter equivalent or null
     */
    private static XinityNote getLetterNoteEquivalent(XinityNote note) {
        // find the upper enharmonic equivalent
        XinityNote enharmonic = EnharmonicUtil.findEnharmonicEquivalent("+", note);
        if (enharmonic == null) { // if null find the lower enharmonic equivalent
            enharmonic = EnharmonicUtil.findEnharmonicEquivalent("-", note);
        }

        // if no enharmonic is found
        if (enharmonic == null) {
            return null;
        }

        // if enharmonic is a letter note, return it
        if (enharmonic.getLetter().equals(enharmonic.getLetterAndAccidental())) {
            return enharmonic;
        }
        return null;
    }

    /**
     * Gets the mode of a major scale given the key and the degree.
     *
     * @param key String - a Valid Note
     * @param degree - Integer - a degree between 1 and 7
     * @return String - The corresponding scale
     */
    public static String getMajorMode(XinityNote key, Integer degree) {
        String tonic;

        XinityNote note = getModeTonic(key, degree);
        if (note == null) {
            return null;
        }

        String mode = ScaleMode.fromDegreeMajor(degree).toString();
        if (key.hasOctave()) {
            tonic = note.getNote();
        } else {
            tonic = note.getLetterAndAccidental();
        }
        if (note.isValidMidi()) {
            return tonic + " " + mode;
        } else {
            return null;
        }
    }

    /**
     * Gets the mode given the key, degree, and mode type.
     *
     * @param key The root note of the scale
     * @param degree Degree of the mode must be between 1 and 7
     * @param scaleGroup The type of scale to use e.g. Major, Melodic Minor
     * @return The resulting mode
     */
    public static String getMode(XinityNote key, Integer degree, ScaleGroup scaleGroup) {
        if (scaleGroup == ScaleGroup.MAJORMODES) {
            return getMajorMode(key, degree);
        } else if (scaleGroup == ScaleGroup.MELODICMINORMODES) {
            return getMelodicMinorMode(key, degree);
        } else {
            return null;
        }
    }

    /**
     * Gets the mode of a major scale given the key and the degree.
     *
     * @param key String - a Valid Note
     * @param degree - Integer - a degree between 1 and 7
     * @return String - The corresponding scale
     */
    public static String getMelodicMinorMode(XinityNote key, Integer degree) {
        List<XinityNote> melodicMinorScale = findNaturalScale(key, ScaleType.MELODICMINOR);

        if (melodicMinorScale == null) {
            return null;
        }

        XinityNote note = melodicMinorScale.get(degree - 1);
        String mode = ScaleGroup.MELODICMINORMODES.getGroup().toArray()[degree - 1].toString();

        String noteString;
        if (key.hasOctave()) {
            noteString = note.getNote();
        } else {
            noteString = note.getLetterAndAccidental();
        }
        if (NoteUtil.isValidMidiRange(note.getMidi())) {
            return noteString + " " + mode;
        } else {
            return null;
        }
    }

    /**
     * Gets the tonic for a mode given a note and degree.
     *
     * @param key XinityNote
     * @param degree Integer
     * @return XinityNote
     */
    private static XinityNote getModeTonic(XinityNote key, Integer degree) {
        List<XinityNote> majorScale = findNaturalScale(key, ScaleType.MAJOR);
        if (majorScale == null) {
            return null;
        }
        return majorScale.get(degree - 1);
    }

    /**
     * Gets the parent scale tonic of a note and mode.
     *
     * @param note XinityNote - the note
     * @param mode ScaleMode - the mode
     * @return The parent scale tonic note
     */
    public static XinityNote getParentNote(XinityNote note, ScaleMode mode) {
        ScaleType scaleType = ScaleType.fromString(mode.toString());
        List<XinityNote> scale = findScale(note, scaleType);
        if (scale == null) {
            return null;
        }
        Integer index = mode.getDegree() - 1;
        Integer noteIndex = scaleType.getScalePattern().size() - index;
        return NoteUtil.incrementedOctaveNote(scale.get(noteIndex - 1), -1);
    }

    /**
     * Given a list of Xinity notes of a scale, the method returns the list as string format.
     *
     * @param scaleArray the Xinity scale notes
     * @param hasOctave Boolean indicating if the root was given with an octave
     * @return the formatted scale string
     */
    public static String getScaleString(List<XinityNote> scaleArray, Boolean hasOctave) {
        String scale = "";
        Boolean outOfRange = false;
        for (XinityNote note : scaleArray) {
            if (note.isValidMidi()) {
                if (hasOctave) {
                    scale += note.getNote() + ", ";
                } else {
                    scale += note.getLetterAndAccidental() + ", ";
                }
            } else {
                outOfRange = true;
            }
        }
        scale = scale.substring(0, scale.length() - 2);
        if (outOfRange) {
            scale += " - The rest of the scale cannot be mapped to midi";
        }
        return scale;
    }


    /**
     * Given a list of midi numbers, the method returns the list as string format.
     *
     * @param scaleArray the scale midi numbers
     * @return the formatted scale string
     */
    public static String getScaleString(List<Integer> scaleArray) {
        String scale = "";
        Boolean outOfRange = false;
        for (Integer note : scaleArray) {
            if (NoteUtil.isValidMidiRange(note)) {
                scale += note + ", ";
            } else {
                outOfRange = true;
            }
        }
        scale = scale.substring(0, scale.length() - 2);
        if (outOfRange) {
            scale += " - The rest of the scale cannot be mapped to midi";
        }
        return scale;
    }

    /**
     * Given a list of Xinity notes, converts into midi.
     *
     * @param notes a list of Xinity notes representing a scale
     * @return a list of midi notes
     */
    public static List<Integer> convertToMidiScale(List<XinityNote> notes) {
        List<Integer> midiScale = new ArrayList<>();
        for (XinityNote note : notes) {
            midiScale.add(NoteUtil.convertToMidi(note));
        }
        return midiScale;
    }
}
