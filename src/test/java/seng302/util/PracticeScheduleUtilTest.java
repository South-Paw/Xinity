package seng302.util;

import org.junit.Before;
import org.junit.Test;
import seng302.util.enumerator.ChordQuality;
import seng302.util.enumerator.Interval;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.QuestionResponse;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for the practise schedule util.
 */
public class PracticeScheduleUtilTest {

    private XinityNote c4 = null;

    @Before
    public void setUp() throws Exception
    {
        c4 = new XinityNote("C4");
    }

    @Test
    public void playNoteQuestionText() throws Exception {
        QuestionResponse question = PracticeScheduleUtil.generatePlayNoteQuestion("normal", c4, c4);
        assertEquals("Play the note C4", question.getQuestion());
    }

    @Test
    public void playNoteNotesHint() throws Exception {
        QuestionResponse question = PracticeScheduleUtil.generatePlayNoteQuestion("normal", c4, c4);
        assertEquals(null, question.getHint());
    }

    @Test
    public void playIntervalQuestionText() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(Interval.PERFECTUNISON);
        QuestionResponse question = PracticeScheduleUtil.generatePlayIntervalQuestion("normal", c4, c4, intervals);
        assertEquals("Play the interval Perfect Unison starting on the note C4", question.getQuestion());
    }

    @Test
    public void playIntervalNotesHint() throws Exception {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(Interval.PERFECTUNISON);
        QuestionResponse question = PracticeScheduleUtil.generatePlayIntervalQuestion("normal", c4, c4, intervals);
        assertEquals("The notes in the interval are C4 and C4", question.getHint());
    }

    @Test
    public void playChordQuestionText() throws Exception {
        List<ChordQuality> chords = new ArrayList<>();
        chords.add(ChordQuality.MAJORTRIAD);
        QuestionResponse question = PracticeScheduleUtil.generatePlayChordQuestion("normal", c4, c4, chords, false, true);
        assertEquals("Play the chord C4 Major Unison", question.getQuestion());
    }

    @Test
    public void playChordNotesHint() throws Exception {
        List<ChordQuality> chords = new ArrayList<>();
        chords.add(ChordQuality.MAJORTRIAD);
        QuestionResponse question = PracticeScheduleUtil.generatePlayChordQuestion("normal", c4, c4, chords, false, true);
        assertEquals("The notes in the chord are C4, E4, G4", question.getHint());
    }

    @Test
    public void playScaleQuestionText() throws Exception {
        List<ScaleType> scales = new ArrayList<>();
        scales.add(ScaleType.MAJOR);
        QuestionResponse question = PracticeScheduleUtil.generatePlayScaleQuestion("normal", c4, c4, scales, 1, false, true, "straight");
        assertEquals("Play 1 octave of the scale C4 Major Ascending", question.getQuestion());
    }

    @Test
    public void playScaleNotesHint() throws Exception {
        List<ScaleType> scales = new ArrayList<>();
        scales.add(ScaleType.MAJOR);
        QuestionResponse question = PracticeScheduleUtil.generatePlayScaleQuestion("normal", c4, c4, scales, 1, false, true, "straight");
        assertEquals("The notes in the scale are C4, D4, E4, F4, G4, A4, B4, C5", question.getHint());
    }

}