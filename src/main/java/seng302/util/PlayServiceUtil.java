package seng302.util;

import seng302.App;
import seng302.util.enumerator.Interval;
import seng302.util.map.SongMap;
import seng302.util.object.XinityNote;
import seng302.util.runnable.PlayChordRunnable;
import seng302.util.runnable.PlayMidiRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

/**
 * Play Utility.
 *
 * @author adg62, ljm163
 */
public final class PlayServiceUtil {
    /** The play note executor. */
    private static ExecutorService executor = Executors.newFixedThreadPool(1);

    /** How 'hard' the key is pressed. */
    private static Integer noteVelocity = 10000;

    /** The Synthesizer object that is used to play. */
    private static Synthesizer synthesizer;

    /** Map of data to be sent to client for playing. */
    private static HashMap<Object, Object> playResponse;

    /** Private Constructor. */
    private PlayServiceUtil() {}

    /**
     * Get the note velocity.
     *
     * @return The current note velocity.
     */
    public static Integer getNoteVelocity() {
        return noteVelocity;
    }

    /**
     * Sets the note velocity.
     *
     * @param velocity The new note velocity.
     */
    public static void setNoteVelocity(Integer velocity) {
        noteVelocity = velocity;
    }

    /**
     * Returns the to be played string and clears what it was previously.
     *
     * @return A map of data containing play information for the client.
     */
    public static HashMap<Object, Object> getPlayResponse() {
        HashMap<Object, Object> thisResponse = playResponse;

        playResponse = null;

        return thisResponse;
    }

    /** Shuts down the executor. Used when the program closes. */
    public static void shutdownExecutor() {
        executor.shutdownNow();
    }

    /** Used to restart the executor if it has been shutdown in the current session. */
    public static void restartExecutor() {
        executor = Executors.newFixedThreadPool(1);
    }

    /** Creates the Synthesizer object. */
    private static void createSynthesizer() {
        if (synthesizer == null) {
            try {
                synthesizer = MidiSystem.getSynthesizer();

                synthesizer.open();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Play using a midi note.
     *
     * @param midi The midi number we want to play.
     * @param duration The duration to play for.
     */
    public static void playMidi(Integer midi, Integer duration) {
        // early return if midi outside of range
        if (!NoteUtil.isValidMidiRange(midi)) {
            return;
        }

        if (duration == -1) {
            duration = TempoUtil.getCrotchet();
        }

        if (!App.getInServerMode()) {
            createSynthesizer();

            executor.execute(new PlayMidiRunnable(synthesizer, midi, noteVelocity, duration));
        } else {
            ArrayList<Object> thisNote = new ArrayList<>();
            thisNote.add(midi);
            thisNote.add(duration);

            ArrayList<Object> thisPlay = new ArrayList<>();
            thisPlay.add(thisNote);

            playResponse = new HashMap<>();
            playResponse.put("note", thisPlay);
            playResponse.put("type", "single");
        }
    }

    /**
     * For lazy people - a function essentially the same as playMidi().
     * playNote() converts a given note to midi and then plays it.
     *
     * @param note The note we want to play.
     */
    private static void playNote(XinityNote note, Integer duration) {
        Integer midi = NoteUtil.convertToMidi(note);
        playMidi(midi, duration);
    }

    /**
     * Play a silent note. Used to create breaks such as when playing intervals.
     *
     * @param duration The duration to play for.
     */
    public static void playSilent(Integer duration) {
        if (duration == -1) {
            duration = TempoUtil.getCrotchet();
        }

        if (!App.getInServerMode()) {
            createSynthesizer();

            executor.execute(new PlayMidiRunnable(synthesizer, 0, 0, duration));
        } else {
            ArrayList<Object> thisNote = new ArrayList<>();
            thisNote.add(0);
            thisNote.add(duration);

            ArrayList<Object> thisPlay = new ArrayList<>();
            thisPlay.add(thisNote);

            playResponse = new HashMap<>();
            playResponse.put("silent", thisPlay);
            playResponse.put("type", "single");
        }
    }

    /**
     * Plays a scale in straight style given an array of notes.
     * Converts each note to an Xinity note and there calls playNote() to play.
     *
     * @param scaleArray a list a string notes.
     */
    public static void playStraightScale(List<XinityNote> scaleArray) {
        ArrayList<Object> thisPlay = new ArrayList<>();

        for (XinityNote note : scaleArray) {
            Integer duration = (TempoUtil.getCrotchet() / 2);


            if (!App.getInServerMode()) {
                playNote(note, duration);
            } else {
                ArrayList<Object> thisNote = new ArrayList<>();
                thisNote.add(NoteUtil.convertToMidi(note));
                thisNote.add(duration);

                thisPlay.add(thisNote);
            }

        }

        if (App.getInServerMode()) {
            playResponse = new HashMap<>();
            playResponse.put("scale", thisPlay);
            playResponse.put("type", "straight");
        }
    }

    /**
     * Plays a scale in swing style given an array of notes.
     * Converts each note to an Xinity note and there calls playNote() to play.
     *
     * @param scaleArray a list a string notes.
     */
    public static void playSwingScale(List<XinityNote> scaleArray) {
        Integer counter = 0;

        ArrayList<Object> thisPlay = new ArrayList<>();

        for (XinityNote note : scaleArray) {
            Integer duration;

            if (counter % 2 == 0) {
                // Play notes with the first crotchet split
                duration = Math.round(TempoUtil.getFirstSwingSplit());
            } else {
                // Play notes with the second crotchet split
                duration = Math.round(TempoUtil.getSecondSwingSplit());
            }


            ArrayList<Object> thisNote = new ArrayList<>();
            thisNote.add(NoteUtil.convertToMidi(note));
            thisNote.add(duration);

            thisPlay.add(thisNote);

            if (!App.getInServerMode()) {
                playNote(note, duration);
            }


            counter++;
        }

        if (App.getInServerMode()) {
            playResponse = new HashMap<>();
            playResponse.put("scale", thisPlay);
            playResponse.put("type", "swing");
        }
    }

    /**
     * Given two notes, play's them as an interval.
     *
     * @param startMidi The first note.
     * @param endMidi The second note.
     */
    public static void playInterval(Integer startMidi, Integer endMidi) {
        Integer silentNoteDuration = TempoUtil.getCrotchet() * 3;

        if (!App.getInServerMode()) {
            // Play first note
            playMidi(startMidi, -1);

            // Pause for three crotchets
            playSilent(silentNoteDuration);

            // Play second note
            playMidi(endMidi, -1);
        } else {
            Integer duration = TempoUtil.getCrotchet();

            ArrayList<Object> thisPlay = new ArrayList<>();

            ArrayList<Object> note1 = new ArrayList<>();
            note1.add(startMidi);
            note1.add(duration);

            thisPlay.add(note1);

            ArrayList<Object> silentNote = new ArrayList<>();
            silentNote.add(0);
            silentNote.add(silentNoteDuration);

            thisPlay.add(silentNote);

            ArrayList<Object> note2 = new ArrayList<>();
            note2.add(endMidi);
            note2.add(duration);

            thisPlay.add(note2);

            playResponse = new HashMap<>();
            playResponse.put("interval", thisPlay);
            playResponse.put("type", "interval");
        }
    }

    /**
     * Plays the Chord with all notes played at the same time.
     *
     * @param notes The notes in the chord we are playing.
     */
    private static void playChord(List<Integer> notes) {
        Integer duration = TempoUtil.getCrotchet();

        if (!App.getInServerMode()) {
            createSynthesizer();

            executor.execute(new PlayChordRunnable(synthesizer, notes, noteVelocity, duration));
        } else {
            ArrayList<Object> thisPlay = new ArrayList<>();

            for (Integer note : notes) {
                ArrayList<Object> thisNote = new ArrayList<>();
                thisNote.add(note.toString());
                thisNote.add(duration);

                thisPlay.add(thisNote);
            }

            playResponse = new HashMap<>();
            playResponse.put("chord", thisPlay);
            playResponse.put("type", "unison");
        }
    }

    /**
     * Plays the given notes through in unison.
     *
     * @param chordNotes Desired notes to be played in chord
     */
    public static void playUnisonChord(List<XinityNote> chordNotes)  {
        List<Integer> noteList = new ArrayList<>();

        for (XinityNote note : chordNotes) {
            noteList.add(NoteUtil.convertToMidi(note));
        }

        if (!App.getInServerMode()) {
            playChord(noteList);
        } else {
            Integer duration = TempoUtil.getCrotchet();

            ArrayList<Object> thisPlay = new ArrayList<>();

            for (Integer midi : noteList) {
                ArrayList<Object> thisNote = new ArrayList<>();
                thisNote.add(midi);
                thisNote.add(duration);

                thisPlay.add(thisNote);
            }

            playResponse = new HashMap<>();
            playResponse.put("chord", thisPlay);
            playResponse.put("type", "unison");
        }
    }

    /**
     *  Plays the given notes through in an arpeggio.
     *
     * @param chordNotes Desired notes to be played in chord
     */
    public static void playArpeggioChord(List<XinityNote> chordNotes) {
        ArrayList<Object> thisPlay = new ArrayList<>();

        for (XinityNote note : chordNotes) {
            // The duration is half a crotchet when played as arpeggio
            Integer duration = (Math.round((float) TempoUtil.getCrotchet() / (float) 2));

            if (!App.getInServerMode()) {
                PlayServiceUtil.playMidi(NoteUtil.convertToMidi(note), duration);
            } else {
                ArrayList<Object> thisNote = new ArrayList<>();
                thisNote.add(NoteUtil.convertToMidi(note));
                thisNote.add(duration);

                thisPlay.add(thisNote);
            }
        }

        playResponse = new HashMap<>();
        playResponse.put("chord", thisPlay);
        playResponse.put("type", "arpeggio");
    }

    /**
     * Temporary pentatonic/blues scale method.
     *
     * @param scaleNotes Pentatonic scales notes
     */
    public static void playScale(List<XinityNote> scaleNotes) {
        ArrayList<Object> thisPlay = new ArrayList<>();

        for (XinityNote note : scaleNotes) {
            Integer duration = (TempoUtil.getCrotchet() / 2);
            if (!note.isValidMidi()) {
                continue;
            }

            if (!App.getInServerMode()) {
                playNote(note, duration);
            } else {
                ArrayList<Object> thisNote = new ArrayList<>();
                thisNote.add(note);
                thisNote.add(duration);
                thisPlay.add(thisNote);
            }
        }

        if (App.getInServerMode()) {
            playResponse = new HashMap<>();
            playResponse.put("scale", thisPlay);
            playResponse.put("type", "straight");
        }
    }

    /**
     * Plays beethoven.
     */
    public static void playSong(List<List<Integer>> noteMap) {
        ArrayList<Object> thisPlay = new ArrayList<>();

        for (List<Integer> note : noteMap) {
            if (!App.getInServerMode()) {
                playMidi(note.get(0), note.get(1));
            } else {
                ArrayList<Object> thisNote = new ArrayList<>();
                thisNote.add(note.get(0));
                thisNote.add(note.get(1));

                thisPlay.add(thisNote);
            }
        }

        if (App.getInServerMode()) {
            playResponse = new HashMap<>();
            playResponse.put("scale", thisPlay);
            playResponse.put("type", "straight");
        }
    }
}
