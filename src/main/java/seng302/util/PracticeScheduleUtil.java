package seng302.util;

import seng302.util.enumerator.ChordQuality;
import seng302.util.enumerator.Interval;
import seng302.util.enumerator.ScaleDirection;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.QuestionResponse;
import seng302.util.object.XinityNote;

import java.util.List;
import java.util.Random;

/**
 * The utility for the practice schedule.
 *
 * @author avh17.
 */
public final class PracticeScheduleUtil {

    private static Random random = new Random();

    /**
     * Generates a play note question for the practice schedule.
     *
     * @param rangeOne The lower note range.
     * @param rangeTwo The higher note range.
     * @return A schedule question object.
     */
    public static QuestionResponse generatePlayNoteQuestion(
        String difficulty, XinityNote rangeOne, XinityNote rangeTwo) {
        QuestionResponse question = new QuestionResponse();
        Integer intRangeOne = rangeOne.getMidi();
        Integer intRangeTwo = rangeTwo.getMidi();
        String hint = null;
        XinityNote randomNote = NoteUtil.randomNote(intRangeOne, intRangeTwo);

        // Handle the difficulty being hard (use an enharmonic equivalent)
        if (difficulty.equalsIgnoreCase("hard")) {
            hint = "The equivalent note is " + randomNote.getNote();
            randomNote = convertToEnharmonicNote(randomNote);
        }

        // Set the question details
        if (randomNote != null) {
            // Set the question text
            String questionText = "Play the note " + randomNote.getNote();
            question.setQuestion(questionText);

            // Set the play response
            PlayServiceUtil.playMidi(NoteUtil.convertToMidi(randomNote), -1);
            question.setPlay(PlayServiceUtil.getPlayResponse());

            // Set the hint
            question.setHint(hint);
        }

        return question;
    }

    /**
     * Generates a play interval question for the practice schedule.
     *
     * @param rangeOne The lower note range.
     * @param rangeTwo The higher note range.
     * @return A schedule question object.
     */
    public static QuestionResponse generatePlayIntervalQuestion(
        String difficulty, XinityNote rangeOne, XinityNote rangeTwo, List<Interval> intervals) {
        QuestionResponse question = new QuestionResponse();
        Integer intRangeOne = rangeOne.getMidi();
        Integer intRangeTwo = rangeTwo.getMidi();
        String hint = null;
        XinityNote randomNote = NoteUtil.randomNote(intRangeOne, intRangeTwo);

        // Handle the difficulty being hard (use an enharmonic equivalent)
        if (difficulty.equalsIgnoreCase("hard")) {
            randomNote = convertToEnharmonicNote(randomNote);
        }

        if (randomNote != null) {
            // Get a random interval
            Integer randomNumber = random.nextInt(intervals.size());
            Interval randomInterval = intervals.get(randomNumber);

            // Get the second note for the interval
            XinityNote secondIntervalNote = IntervalUtil.interval(randomNote, randomInterval);
            while (secondIntervalNote == null || randomNote == null) {
                randomNote = NoteUtil.randomNote(intRangeOne, intRangeTwo);
                secondIntervalNote = IntervalUtil.interval(randomNote, randomInterval);
            }
            // Set the question text
            String questionText = "Play the interval " + Interval.toString(randomInterval)
                    + " starting on the note " + randomNote.getNote();
            question.setQuestion(questionText);

            // Set the play response
            PlayServiceUtil.playInterval(randomNote.getMidi(), secondIntervalNote.getMidi());
            question.setPlay(PlayServiceUtil.getPlayResponse());

            // Set the hint
            hint = "The notes in the interval are " + randomNote.getNote()
                    + " and " + secondIntervalNote.getNote();
            question.setHint(hint);
        }
        return question;
    }

    /**
     * Generates a play chord question for the practice schedule.
     *
     * @param rangeOne The lower note range.
     * @param rangeTwo The higher note range.
     * @param qualities The chord qualities to include.
     * @return A schedule question object.
     */
    public static QuestionResponse generatePlayChordQuestion(
        String difficulty, XinityNote rangeOne, XinityNote rangeTwo, List<ChordQuality> qualities,
        Boolean arpeggio, Boolean unison) {
        Integer intRangeOne = rangeOne.getMidi();
        Integer intRangeTwo = rangeTwo.getMidi();
        String hint;
        ChordQuality quality = null;

        // Generate a valid chord
        XinityNote randomNote = null;
        List<XinityNote> chord = null;
        while (chord == null) {
            randomNote = NoteUtil.randomNote(intRangeOne, intRangeTwo);
            if (difficulty.equalsIgnoreCase("hard")) {
                randomNote = convertToEnharmonicNote(randomNote);
            }
            quality = qualities.get(random.nextInt(qualities.size()));
            chord = ChordUtil.chord(randomNote, quality);
        }

        // Set the question text
        String questionText = String.format("Play the chord %s %s",
            randomNote.getNote(), quality.toString());

        // Set the play response
        if (arpeggio && !unison) {
            PlayServiceUtil.playArpeggioChord(chord);
            questionText += " Arpeggio";
        } else if (unison && !arpeggio) {
            PlayServiceUtil.playUnisonChord(chord);
            questionText += " Unison";
        } else { // Both
            if (random.nextInt(2) == 0)  {
                PlayServiceUtil.playArpeggioChord(chord);
                questionText += " Arpeggio";
            } else {
                PlayServiceUtil.playUnisonChord(chord);
                questionText += " Unison";
            }
        }

        // Set the hint
        hint = "The notes in the chord are ";
        for (XinityNote note : chord) {
            if (hint.equalsIgnoreCase("The notes in the chord are ")) {
                hint += note.getNote();
            } else {
                hint += ", " + note.getNote();
            }
        }

        QuestionResponse question = new QuestionResponse();
        question.setQuestion(questionText);
        question.setPlay(PlayServiceUtil.getPlayResponse());
        question.setHint(hint);

        return question;
    }

    /**
     * Generates a play scale question for the practice schedule.
     *
     * @param rangeOne The lower note range.
     * @param rangeTwo The higher note range.
     * @param scales The scale types to include.
     * @return A schedule question object.
     */
    public static QuestionResponse generatePlayScaleQuestion(
        String difficulty, XinityNote rangeOne, XinityNote rangeTwo, List<ScaleType> scales,
        Integer octaves, Boolean down, Boolean up, String style) {
        Integer intRangeOne = rangeOne.getMidi();
        Integer intRangeTwo = rangeTwo.getMidi();
        XinityNote randomNote = null;
        ScaleType scaleType = null;

        // Set Scale Direction
        ScaleDirection direction;
        if (up && !down) {
            direction = ScaleDirection.UP;
        } else if (down && !up) {
            direction = ScaleDirection.DOWN;
        } else {
            direction = ScaleDirection.BOTH;
        }

        List<XinityNote> scale = null;
        while (scale == null) {
            randomNote = NoteUtil.randomNote(intRangeOne, intRangeTwo);
            scaleType = scales.get(random.nextInt(scales.size()));
            // Handle the difficulty being hard (use an enharmonic equivalent)
            if (difficulty.equalsIgnoreCase("hard")) {
                randomNote = convertToEnharmonicNote(randomNote);
            }
            scale = ScalesUtil.buildPlayingScale(randomNote,
                scaleType, direction, octaves);
        }

        // Set the question text
        String multipleOctaves = "";
        if (octaves > 1) {
            multipleOctaves = "s";
        }

        String questionText = String.format("Play %s octave%s of the scale %s %s %s", octaves,
            multipleOctaves, randomNote.getNote(), scaleType.toString(),
            direction.getProperName());

        if (style.equalsIgnoreCase("swing")) {
            questionText += " Swing";
        }

        // Set the play response
        if (style.equalsIgnoreCase("straight")) {
            PlayServiceUtil.playStraightScale(scale);
        } else { // Swing
            PlayServiceUtil.playSwingScale(scale);
        }

        // Set the hint
        String hint = "The notes in the scale are ";
        for (XinityNote note : scale) {
            if (hint.equalsIgnoreCase("The notes in the scale are ")) {
                hint += note.getNote();
            } else {
                hint += ", " + note.getNote();
            }
        }

        QuestionResponse question = new QuestionResponse();
        question.setQuestion(questionText);
        question.setPlay(PlayServiceUtil.getPlayResponse());
        question.setHint(hint);

        return question;
    }

    /**
     * Converts the given note to a higher or lower enharmonic equivalent.
     *
     * @param note The note.
     * @return The enharmonic equivalent.
     */
    private static XinityNote convertToEnharmonicNote(XinityNote note) {
        XinityNote enharmonic = null;
        while (enharmonic == null) {
            Integer randomNumber = random.nextInt(2);
            String direction = "";
            if (randomNumber == 0) {
                direction = "+";
            } else {
                direction = "-";
            }
            enharmonic = EnharmonicUtil.findEnharmonicEquivalent(direction, note);
        }
        return enharmonic;
    }
}

