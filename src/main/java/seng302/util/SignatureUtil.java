package seng302.util;

import seng302.util.enumerator.NoteAccidental;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Signature utility.
 * Currently the utility is used by ScaleSignature and showSignature commands.
 *
 * @author ljm163, plr37
 */
public final class SignatureUtil {

    private static Map<String, String> majorScaleToSignatureMap;
    private static Map<String, String> minorScaleToSignatureMap;
    private static Map<String, String> signatureToMajorScaleMap;
    private static Map<String, String> signatureToMinorScaleMap;

    /**
     * Returns a map mapping a Major Scale to the number of
     * flats or sharps in the key signature.
     *
     * @return major scale circleOfFifthsMap
     */
    public static Map<String, String> getMajorScaleToKeySignatureMap() {
        if (majorScaleToSignatureMap == null) {
            Map<String, String> map = new HashMap<>();

            map.put("C", "0#");
            map.put("G", "1#");
            map.put("D", "2#");
            map.put("A", "3#");
            map.put("E", "4#");
            map.put("B", "5#");
            map.put("F#", "6#");
            map.put("C#", "7#");

            map.put("F", "1b");
            map.put("Bb", "2b");
            map.put("Eb", "3b");
            map.put("Ab", "4b");
            map.put("Db", "5b");
            map.put("Gb", "6b");
            map.put("Cb", "7b");

            majorScaleToSignatureMap = map;
        }

        return Collections.unmodifiableMap(majorScaleToSignatureMap);
    }

    /**
     * Returns mapping of key signatures to major scales.
     *
     * @return key signature to major scale map.
     */
    private static Map<String, String> getKeySignatureToMajorScaleMap() {
        if (signatureToMajorScaleMap == null) {
            Map<String, String> map = new HashMap<>();

            map.put("0", "C");
            map.put("0#", "C");
            map.put("0b", "C");

            map.put("1#", "G");
            map.put("2#", "D");
            map.put("3#", "A");
            map.put("4#", "E");
            map.put("5#", "B");
            map.put("6#", "F#");
            map.put("7#", "C#");

            map.put("1b", "F");
            map.put("2b", "Bb");
            map.put("3b", "Eb");
            map.put("4b", "Ab");
            map.put("5b", "Db");
            map.put("6b", "Gb");
            map.put("7b", "Cb");

            signatureToMajorScaleMap = map;
        }

        return Collections.unmodifiableMap(signatureToMajorScaleMap);
    }

    /**
     * Returns a map mapping a minor scale to the number of
     * flats or sharps.
     *
     * @return minorKeySignatureMap
     */
    public static Map<String, String> getMinorScaleToKeySignatureMap() {

        if (minorScaleToSignatureMap == null) {
            Map<String, String> map = new HashMap<>();

            map.put("A", "0#");
            map.put("E", "1#");
            map.put("B", "2#");
            map.put("F#", "3#");
            map.put("C#", "4#");
            map.put("G#", "5#");
            map.put("D#", "6#");
            map.put("A#", "7#");

            map.put("D", "1b");
            map.put("G", "2b");
            map.put("C", "3b");
            map.put("F", "4b");
            map.put("Bb", "5b");
            map.put("Eb", "6b");
            map.put("Ab", "7b");

            minorScaleToSignatureMap = map;
        }

        return Collections.unmodifiableMap(minorScaleToSignatureMap);
    }

    /**
     * Returns mapping of key signatures to minor scales.
     *
     * @return key signature to minor scale map.
     */
    private static Map<String, String> getKeySignatureToMinorScaleMap() {
        if (signatureToMinorScaleMap == null) {
            Map<String, String> map = new HashMap<>();

            map.put("0", "A");
            map.put("0#", "A");
            map.put("0b", "A");

            map.put("1#", "E");
            map.put("2#", "B");
            map.put("3#", "F#");
            map.put("4#", "C#");
            map.put("5#", "G#");
            map.put("6#", "D#");
            map.put("7#", "A#");

            map.put("1b", "D");
            map.put("2b", "G");
            map.put("3b", "C");
            map.put("4b", "F");
            map.put("5b", "Bb");
            map.put("6b", "Eb");
            map.put("7b", "Ab");

            signatureToMinorScaleMap = map;
        }

        return Collections.unmodifiableMap(signatureToMinorScaleMap);
    }

    /**
     * Returns the Key Signature Map corresponding to the given scale.
     *
     * @param scale String
     * @return key signature map
     */
    private static Map<String, String> getKeySignatureMap(ScaleType scale) {
        if (scale == ScaleType.MAJOR) {
            return getMajorScaleToKeySignatureMap();
        } else if (scale == ScaleType.MINOR) {
            return getMinorScaleToKeySignatureMap();
        } else {
            return getMajorScaleToKeySignatureMap();
        }
    }

    /**
     * Produces the key signature notes from a given scale.
     * @param note the scale starting note.
     * @param type the scale type.
     * @return the key signature notes.
     */
    public static Set<XinityNote> getSignatureNotes(String note, String type) {
        try {
            XinityNote startNote = new XinityNote(note);
            ScaleType scaleType = ScaleType.fromString(type);
            String accidental; //the accidental displayed in the key signature eg. '#'
            String numberOfAccidentals;

            Map<String, String> map = getKeySignatureMap(scaleType);

            // check if given note is in the circle of fifths map
            if (map != null && map.containsKey(startNote.getLetter()
                    + startNote.getAccidental().toMusicalRepresentation())) {
                //determine if key signature will contain sharps or flats
                numberOfAccidentals = map.get(startNote.getLetter()
                        + startNote.getAccidental().toMusicalRepresentation());
                if (!numberOfAccidentals.equals("0")) { // currently only or C major
                    accidental = numberOfAccidentals.substring(1, 2); //the '#' part of '3#'
                } else {
                    return null;
                }
                List<XinityNote> accidentalScale = produceAccidentalScale(startNote, scaleType);
                return produceKeySignatureNotes(accidental, accidentalScale);
            }
            // note has not been found in circle of fifths map e.g. 'G#'
            return null;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get the corresponding key signature from map given a scale.
     * @param note the given scale start note
     * @param type the given scale type
     * @return key signature from the map
     */
    public static String getKeySignatureFromMap(String note, String type) {
        try {
            XinityNote startNote;
            startNote = new XinityNote(note);
            ScaleType scaleType = ScaleType.fromString(type);

            Map<String, String> map = getKeySignatureMap(scaleType);

            if (map != null) {
                if (map.containsKey(startNote.getLetter()
                        + startNote.getAccidental().toMusicalRepresentation())) {
                    return map.get(startNote.getLetter()
                            + startNote.getAccidental().toMusicalRepresentation());
                }
            }
            return null;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Produce an array of only the accidental notes of a given scale.
     * @param startNote the start note of the given scale.
     * @param scaleType the scale type of the given scale. eg. 'Major'
     * @return the array of accidental notes of the given scale.
     */
    private static List<XinityNote> produceAccidentalScale(
            XinityNote startNote, ScaleType scaleType) {

        List<XinityNote> accidentalScale = new ArrayList<>();

        //find scale without octave on notes
        List<XinityNote> notes = ScalesUtil.findScale(startNote, scaleType);
        if (notes != null) {
            for (XinityNote note : notes) {
                if (NoteAccidental.isSimpleAccidental(note.getAccidental())) {
                    accidentalScale.add(note);
                }
            }
        }
        return accidentalScale;
    }

    /**
     * Produce an array of notes of the key signature given an accidental and the scale with only
     * the accidental notes.
     * @param accidental the given flat/sharp
     * @param accidentalScale the given scale with only the flat/sharp notes
     * @return the key signature notes for the showSignature commmand when the 'note' flag is given.
     */
    private static Set<XinityNote> produceKeySignatureNotes(
            String accidental, List<XinityNote> accidentalScale) {

        List<XinityNote> keySignature = new ArrayList<>();
        List<String> keySignatureOrder = getKeySignatureOrder(accidental);

        if (keySignatureOrder != null) {
            for (String letter : keySignatureOrder) {
                for (XinityNote note : accidentalScale) {
                    if (note.getLetter().equals(letter)) {
                        try {
                            keySignature.add(new XinityNote(letter + accidental));
                        } catch (Exception e) {
                            return null;
                        }
                    }
                }
            }
            // remove duplicate notes (ignoring octave)
            return new LinkedHashSet<>(keySignature);
        }

        return null;
    }

    /**
     * Determines what order the key signature is displayed given the accidental.
     * @param accidental given flat/sharp
     * @return an array of note ordering for displaying the key signature
     */
    public static List<String> getKeySignatureOrder(String accidental) {
        switch (accidental) {
            case "#":
                return Arrays.asList("F", "C", "G", "D", "A", "E", "B");
            case "b":
                return Arrays.asList("B", "E", "A", "D", "G", "C", "F");
            default:
                return null;
        }
    }

    /**
     * Returns the Key Signature Map corresponding to the given scale.
     *
     * @param scaleType ScaleType
     * @return key signature map
     */
    private static Map<String, String> getKeySignatureToScaleMap(ScaleType scaleType) {
        if (scaleType == ScaleType.MAJOR) {
            return getKeySignatureToMajorScaleMap();
        } else if (scaleType == ScaleType.MINOR) {
            return getKeySignatureToMinorScaleMap();
        } else {
            return null;
        }
    }

    /**
     * Determine scale from key signature and scale type.
     * @param keySignature the given keysignature
     * @param scaleType the given scaletype
     * @return the scale from key signature map
     */
    public static String determineScaleFromKeySignature(String keySignature,
                                                       ScaleType scaleType) {
        String scale = null;
        Map<String, String> keySignatureMap = getKeySignatureToScaleMap(scaleType);
        if (keySignatureMap != null) {
            scale = keySignatureMap.get(keySignature);
        }
        return scale;
    }
}
