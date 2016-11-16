package seng302.util;

import seng302.util.enumerator.ChordFunctionEnum;
import seng302.util.enumerator.ChordQuality;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.Interval;
import seng302.util.enumerator.Inversion;
import seng302.util.enumerator.ScaleDirection;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Chord Utility class.
 *
 * @author mvj14, ljm163, plr37
 */
public final class ChordUtil {
    /**
     * Returns a chord given a chord quality and a starting note.
     *
     * @param chordQuality The the scale type being passed in.
     * @param root         The starting note
     * @return Array of notes in the desired chord
     */
    public static List<XinityNote> chord(XinityNote root, ChordQuality chordQuality) {
        List<Interval> intervals = chordQuality.getChordIntervals();
        List<XinityNote> notes = new ArrayList<>();
        for (Interval interval : intervals) {
            XinityNote note = IntervalUtil.interval(root, interval);
            if (note == null) {
                return null;
            }
            notes.add(note);
        }
        return notes;
    }

    /**
     * Inverts a chord given as a list of notes, also given an inversion.
     *
     * @param chord     a list of chord notes
     * @param inversion 'root', '1st', '2nd' or '3rd' inversion
     */
    public static void invertChord(List<XinityNote> chord, Inversion inversion) {
        for (Integer i = 0; i < inversion.getInteger(); i++) {
            // note is removed from front and added to end incremented octave
            try {
                chord.add(NoteUtil.incrementedOctaveNote(chord.remove(0), 1));
            } catch (Exception e) {
                return;
            }
        }
    }

    /**
     * Finds the name of the chord given a list of Xinity notes. Since the findChord command does
     * not accept octaves the method will assume that the notes entered are put in ascending order.
     *
     * @param notes         The given notes of a possible chord.
     * @param showInversion flag that indicates if the output should display what inversion the
     *                      chord is.
     * @return the chord name textual output to be passed to the findChord command.
     */
    public static Set<String> findChord(List<String> notes, Boolean showInversion) {
        // creates an original copy of chord notes so they can be retrieved after conversion
        List<String> inputNotes = new ArrayList<>(notes);
        List<String> chords = new ArrayList<>(); // list of found chord + enharmonic(s)
        String root = ""; // re-used variable, used by each found chord
        Inversion inversionNumber; // re-used variable, used by each found chord
        ChordQuality chordQuality = null; // this needs to be tracked during each stage

        // gets list of valid chord Xinity notes
        List<XinityNote> chordNotes = getChordNotes(notes);
        if (chordNotes == null) {
            return null;
        }

        // intervals between the root and each other chord note, re-used by each found chord
        List<Interval> chordIntervals = getIntervals(chordNotes);

        // stage 1 - find chord from given notes and order
        if (ChordQuality.getQuality(getIntervals(chordNotes)) != null) {
            root = chordNotes.get(0).getLetterAndAccidental();
            chordQuality = ChordQuality.getQuality(chordIntervals);
            inversionNumber = getInversionNumber(inputNotes.get(0), chordNotes);
            if (showInversion) {
                chords.add(root + " " + chordQuality + " " + inversionNumber);
            } else {
                chords.add(root + " " + chordQuality);
            }
        }

        // stage 2 - order notes and find chord from sorted notes
        List<XinityNote> orderedNotes = getOrderedNotes(inputNotes);
        if (orderedNotes == null) {
            return null;
        }


        // stage 3 - get quality of ordered notes and then keep inverting chord
        for (Integer i = 0; i < orderedNotes.size(); i++) {
            // get quality of ordered notes
            if (ChordQuality.getQuality(getIntervals(orderedNotes)) != null) {
                root = orderedNotes.get(0).getLetterAndAccidental();
                chordQuality = ChordQuality.getQuality(getIntervals(orderedNotes));
                inversionNumber = getInversionNumber(inputNotes.get(0), orderedNotes);
                if (showInversion) {
                    chords.add(root + " " + chordQuality + " " + inversionNumber);
                } else {
                    chords.add(root + " " + chordQuality);
                }
            }

            // get chord quality of inverted chord
            invertChord(orderedNotes, Inversion.FIRST);
        }

        // no chord was found from 3 stages, return null
        if (chordQuality == null) {
            return null;
        }

        switch (chordQuality) {
            case DIMINISHEDSEVENTH:
            case AUGMENTEDTRIAD:
                List<XinityNote> chord;
                try { // get true order of chord for displaying enharmonics
                    chord = chord(new XinityNote(root), chordQuality);
                    if (chord == null) {
                        return null;
                    }
                } catch (Exception e) {
                    return null;
                }

                Inversion inversion;
                for (XinityNote note : chord) {
                    for (Integer i = 0; i < inputNotes.size(); i++) {
                        if (inputNotes.get(i).equalsIgnoreCase(note.getLetterAndAccidental())) {
                            if (i == 0) { // first note is root position
                                inversion = Inversion.ROOT;
                            } else {
                                // inversion is note's distance from last index
                                Integer integer = inputNotes.size() - i;
                                inversion = Inversion.fromString(integer.toString());
                            }
                            if (showInversion) {
                                chords.add(note.getLetterAndAccidental() + " "
                                        + chordQuality + " " + inversion);
                            } else {
                                chords.add(note.getLetterAndAccidental() + " "
                                        + chordQuality);
                            }
                        }
                    }
                }

                break;
            default:
                break;
        }

        return new LinkedHashSet<>(chords); // list of unique chords (order retained)
    }


    /**
     * Given a list of non-octave notes, the method converts them to octave notes and returns a
     * list of Xinity chord octave notes.
     * Octaves are assigned to make the chord a valid ascending chord.
     *
     * @param notes A list of input notes of findChord command
     * @return A list of Xinity chord notes
     */
    private static List<XinityNote> getChordNotes(List<String> notes) {
        List<XinityNote> chordNotes = new ArrayList<>();
        try {
            for (int i = 0; i < notes.size(); i++) {
                notes.set(i, new XinityNote(notes.get(i)).getNote());
                if (i < notes.size() - 1) {
                    XinityNote note1 = new XinityNote(notes.get(i));
                    XinityNote note2 = new XinityNote(notes.get(i + 1));
                    while (NoteUtil.convertToMidi(note1) >= NoteUtil.convertToMidi(note2)) {
                        notes.set(i + 1, note2.getLetterAndAccidental() + (note2.getOctave() + 1));
                        note1 = new XinityNote(notes.get(i));
                        note2 = new XinityNote(notes.get(i + 1));
                    }
                }
            }
            for (String note : notes) {
                chordNotes.add(new XinityNote(note));
            }
            return chordNotes;
        } catch (Exception e) {
            return null; // this will never be reached as notes are always valid Xinity notes
        }
    }

    /**
     * Get the intervals making up the intervals of chord notes.
     *
     * @param notes the notes of the chord.
     * @return return a list of valid intervals for the chord.
     */
    private static List<Interval> getIntervals(List<XinityNote> notes) {
        Interval interval;
        List<Interval> intervals = new ArrayList<>();
        for (XinityNote note : notes) {
            interval = IntervalUtil.findInterval(notes.get(0), note);
            if (interval == null) {
                return null;
            }
            intervals.add(interval);
        }
        return intervals;
    }

    /**
     * Given a list of input notes (notes without octaves), the method will process each note into
     * an XinityNote. Since all notes are given without octaves, they lie on the same octave (4).
     * Each notes is there converted into midi and added to a list, sorted by ascending order.
     * From the sorted midi list, a list of sorted/ordered Xinity notes is returned.
     *
     * @param inputNotes the notes given by the findChord command
     * @return the ordered notes
     */
    private static List<XinityNote> getOrderedNotes(List<String> inputNotes) {
        List<XinityNote> orderedNotes = new ArrayList<>();
        try {
            List<Integer> midiNotes = new ArrayList<>();
            for (String note : inputNotes) {
                midiNotes.add(NoteUtil.convertToMidi(new XinityNote(note)));
            }
            Collections.sort(midiNotes);
            for (Integer midi : midiNotes) {
                for (String note : inputNotes) {
                    if (NoteUtil.convertToMidi(new XinityNote(note)).equals(midi)) {
                        orderedNotes.add(new XinityNote(note));
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return orderedNotes;
    }

    /**
     * Given a root note of the findChord command and a list of ordered Xinity notes,
     * the method determines the inversion number. The inversion number is the index of the given
     * root note in the root position of the chord.
     * e.g. E G C is 1st inversion as 'E' is index 1 of C E G.
     *
     * @param root         the first note given by the findChord command
     * @param orderedNotes the ordered Xinity notes of the input notes
     * @return the Inversion
     */
    private static Inversion getInversionNumber(String root, List<XinityNote> orderedNotes) {
        // create a final list of non-octave notes from the ordered notes
        List<String> nonOctaveNotes = new ArrayList<>();
        for (XinityNote note : orderedNotes) {
            nonOctaveNotes.add(note.getLetterAndAccidental());
        }
        try {
            XinityNote bass = new XinityNote(root); // the first note given by command
            Integer inversionNumber = nonOctaveNotes.indexOf(bass.getLetterAndAccidental());
            return Inversion.fromString(inversionNumber.toString());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the diatonic chord of a major scale relating to a function.
     *
     * @param function The chord function.
     * @param scaleNote The scale root note.
     * @return The diatonic chord.
     */
    public static String getDiatonicChord(ChordFunctionEnum function, XinityNote scaleNote) {
        // Get the scale for the given note
        List<XinityNote> scale = ScalesUtil.buildPlayingScale(scaleNote, ScaleType.MAJOR,
                ScaleDirection.UP, 1);

        // Get the note of the given index
        XinityNote note = scale.get(Integer.parseInt(function.getChordFunction().get(1)) - 1);

        return note.getLetterAndAccidental() + " " + function.getQuality().toString();
    }

    /**
     * Finds the position of a given chord within a major scale.
     *
     * @param scaleNote A scale root note
     * @param chordNote A chord root note
     * @param chordQuality The quality of the chord found from the chord root note
     * @return The position of the chord within the given scale
     */
    public static Integer functionNumberFinder(XinityNote scaleNote, XinityNote chordNote,
                                               ChordQuality chordQuality) {
        List<XinityNote> scale = ScalesUtil.buildPlayingScale(scaleNote, ScaleType.MAJOR,
                ScaleDirection.UP, 1);

        List<XinityNote> chord = ChordUtil.chord(chordNote, chordQuality);

        // Get the position of the chord root note in the scale
        Integer rootNotePosition = -1;
        Integer index = 0;
        for (XinityNote note : scale) {
            if (note.getLetterAndAccidental().equals(chordNote.getLetterAndAccidental())) {
                rootNotePosition = index;
                break;
            }
            index++;
        }

        if (rootNotePosition == -1) {
            return -1;
        }

        if (!checkChordMatchesScale(chord, scale, rootNotePosition)) {
            // This is hacky but used to tell apart errors in Function.java
            return -2;
        }

        // Prints the function
        return rootNotePosition + 1;
    }

    /**
     * Checks if every note in a chord, matches to every second note in a scale.
     *
     * @param chord The chord.
     * @param scale The scale
     * @param rootPosition the root position.
     * @return A boolean value.
     */
    private static Boolean checkChordMatchesScale(List<XinityNote> chord,
                                           List<XinityNote> scale, Integer rootPosition) {
        Boolean chordMatches = true;
        List<Integer> chordPositions = new ArrayList<>();
        Integer index = rootPosition;

        // Creates four indexes that ere two number apart, but in the range of a scale size
        for (Integer i = 0; i < 4; i++) {
            chordPositions.add(index);
            index += 2;
            if (index == 7) {
                index = 0;
            }
            if (index == 8) {
                index = 1;
            }
        }

        // Checks that every second note of the scale, matches every note in the chord
        for (Integer i = 0; i < 4; i++) {
            String noteOne = chord.get(i).getLetterAndAccidental();
            String noteTwo = scale.get(chordPositions.get(i)).getLetterAndAccidental();
            if (!noteOne.equals(noteTwo)) {
                chordMatches = false;
            }
        }

        return chordMatches;
    }

    /**
     * Given a list of chord notes, returns a formatted string of the chord.
     *
     * @param chordArray the given chord notes
     * @param hasOctave Boolean indicating if the root was given with an octave
     * @return the formatted chord array
     */
    public static String getChordString(List<XinityNote> chordArray, Boolean hasOctave) {
        String chord = "";
        Boolean outOfRange = false;
        for (XinityNote note : chordArray) {
            if (note.isValidMidi()) {
                if (hasOctave) {
                    chord += note.getNote() + ", ";
                } else {
                    chord += note.getLetterAndAccidental() + ", ";
                }
            } else {
                outOfRange = true;
            }
        }
        if (chord.equals("")) {
            chord += "No notes could be mapped to midi";
        } else {
            chord = chord.substring(0, chord.length() - 2);
            if (outOfRange) {
                chord += " - The rest of the chord cannot be mapped to midi";
            }
        }
        return chord;
    }
}
