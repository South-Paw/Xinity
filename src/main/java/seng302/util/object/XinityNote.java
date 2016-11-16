package seng302.util.object;

import seng302.util.NoteUtil;
import seng302.util.enumerator.NoteAccidental;
import seng302.util.map.XinityNoteMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common note class.
 * Creates and stores notes and allows us to get information about about said note.
 *
 * @author adg62, ljm163, plr37
 */
public class XinityNote implements Comparable<XinityNote> {
    private String note;
    private String letter;
    private Integer octave;
    private Boolean isDouble = false;
    private Boolean hasOctave = false;
    private NoteAccidental accidental = NoteAccidental.NATURAL;

    /**
     * Constructor for a note.
     *
     * @param note A given string that may or may not be a note.
     * @throws Exception when the note is unsuccessfully created.
     */
    public XinityNote(String note) throws Exception {
        createNote(note);
    }

    /**
     * Creates a valid note object given a string.
     *
     * @param note A given string that may or may not be a note.
     * @throws Exception when the note is invalid
     */
    private void createNote(String note) throws Exception {
        String xinityFullNote = "([a-gA-G]){1}(x|b{1,2}|#{1,2})?(-?[0-9]+)?";
        Pattern fullNote = Pattern.compile(xinityFullNote);

        if (note.matches(xinityFullNote)) {
            Matcher fullMatchedNote = fullNote.matcher(note);
            while (fullMatchedNote.find()) {
                this.letter = fullMatchedNote.group(1).toUpperCase();
                if (fullMatchedNote.group(2) != null) {
                    if (fullMatchedNote.group(2).equals("x")
                            || (fullMatchedNote.group(2).equals("##"))) {
                        this.accidental = NoteAccidental.DOUBLESHARP;
                        this.isDouble = true;
                    } else if (fullMatchedNote.group(2).equals("bb")) {
                        this.accidental = NoteAccidental.DOUBLEFLAT;
                        this.isDouble = true;
                    } else if (fullMatchedNote.group(2).equals("#")) {
                        this.accidental = NoteAccidental.SHARP;
                    }  else if (fullMatchedNote.group(2).equals("b")) {
                        this.accidental = NoteAccidental.FLAT;
                    } else {
                        // This is currently not reached, but if moved out include this!
                        this.accidental = NoteAccidental.NATURAL;
                    }
                }

                if (fullMatchedNote.group(3) == null) {
                    this.octave = 4;
                    this.hasOctave = false;
                } else {
                    this.octave = Integer.valueOf(fullMatchedNote.group(3));
                    this.hasOctave = true;
                }
            }

            // Build up a proper note from all the pieces collected.
            this.note = this.letter;
            this.note += this.accidental.toMusicalRepresentation();
            this.note += this.octave.toString();
        } else {
            throw new Exception("Cannot create note from invalid string: \"" + note + "\".");
        }
    }

    /**
     * Get the full note.
     *
     * @return The full note as a string.
     */
    public String getNote() {
        return this.note;
    }

    public String getLetterAndAccidental() {
        return this.letter + this.accidental.toMusicalRepresentation();
    }

    /**
     * Get this notes letter
     *
     * @return This notes letter.
     */
    public String getLetter() {
        return this.letter;
    }

    /**
     * Get this notes octave.
     *
     * @return This notes octave.
     */
    public Integer getOctave() {
        return this.octave;
    }

    /**
     * Get if this note is a double.
     *
     * @return True if it is, or false if not.
     */
    public Boolean getIsDouble() {
        return this.isDouble;
    }

    /**
     * Get the accidental of the given note.
     *
     * @return The accidental.
     */
    public NoteAccidental getAccidental() {
        return this.accidental;
    }


    /**
     * True if the note has a non-default octave.
     *
     * @return True if the note has a non-default octave
     */
    public Boolean hasOctave() {
        return this.hasOctave;
    }

    /**
     * Return the midinote.
     *
     * @return Integer
     */
    public Integer getMidi() {
        return NoteUtil.convertToMidi(this);
    }

    /**
     * Return true if corresponding midi note is between 0 and 127.
     *
     * @return True or False
     */
    public boolean isValidMidi() {
        return NoteUtil.isValidMidiRange(this.getMidi());
    }

    /**
     * Overrides the equals method.
     * Compares the note strings to
     * check for equality.
     *
     * @param obj Object
     * @return Boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        XinityNote note = (XinityNote)obj;
        return this.getNote().equals(note.getNote());
    }

    /**
     * Overriding the hashCode method.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return this.getNote().hashCode();
    }

    /**
     * Overriding the compareTo method.
     * Compares this XinityNote with the specified XinityNote for order.
     * Returns a negative integer, zero, or a positive integer
     * as this XinityNote is less than, equal to, or greater than
     * the specified XinityNote.
     *
     * @param other XinityNote
     * @return int
     */
    @Override
    public int compareTo(XinityNote other) {
        if (getMidi().equals(other.getMidi())) {
            if (this.equals(other)) {
                return 0;
            } else if (XinityNoteMap.getNoteMap().get(this.getMidi() - 60)
                    .indexOf(this.getLetterAndAccidental()) < XinityNoteMap.getNoteMap()
                    .get(other.getMidi() - 60).indexOf(other.getLetterAndAccidental())) {
                return -1;
            } else {
                return 1;
            }
        } else if (this.getMidi() > other.getMidi()) {
            return 1;
        } else {
            return -1;
        }
    }
}
