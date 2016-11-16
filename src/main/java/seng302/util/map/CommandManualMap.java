package seng302.util.map;

import seng302.command.app.GetCrotchet;
import seng302.command.app.GetTempo;
import seng302.command.app.Help;
import seng302.command.app.Keyboard;
import seng302.command.app.SetTempo;
import seng302.command.app.Version;
import seng302.command.musical.Chord;
import seng302.command.musical.ChordFunction;
import seng302.command.musical.Enharmonic;
import seng302.command.musical.EnharmonicInterval;
import seng302.command.musical.FindChord;
import seng302.command.musical.FindInterval;
import seng302.command.musical.FindScale;
import seng302.command.musical.FindScaleMidi;
import seng302.command.musical.Function;
import seng302.command.musical.HasEnharmonic;
import seng302.command.musical.HasEnharmonicInterval;
import seng302.command.musical.Interval;
import seng302.command.musical.Midi;
import seng302.command.musical.Mode;
import seng302.command.musical.Note;
import seng302.command.musical.Parent;
import seng302.command.musical.Quality;
import seng302.command.musical.ScaleSignature;
import seng302.command.musical.Semitone;
import seng302.command.musical.SemitonesInterval;
import seng302.command.musical.Signature;
import seng302.command.musical.SimpleEnharmonic;
import seng302.command.play.PlayChord;
import seng302.command.play.PlayInterval;
import seng302.command.play.PlayNote;
import seng302.command.play.PlayScale;
import seng302.command.term.TermCategory;
import seng302.command.term.TermDefine;
import seng302.command.term.TermLanguage;
import seng302.command.term.TermLookup;
import seng302.command.term.TermMeaning;
import seng302.command.term.TermRemove;
import seng302.command.tutor.ChordSpellingTutor;
import seng302.command.tutor.ChordTutor;
import seng302.command.tutor.IntervalTutor;
import seng302.command.tutor.PitchTutor;
import seng302.command.tutor.ScaleTutor;
import seng302.command.tutor.SignatureTutor;
import seng302.command.tutor.TermTutor;
import seng302.util.enumerator.CommandCategory;
import seng302.util.object.CommandManual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Mappings of command manuals that exist within the application's DSL. There are two maps,
 *     1) The command manuals sorted alphabetically.
 *     2) The command manuals sorted alphabetically within their command categories.
 *
 * @author adg62
 */
public final class CommandManualMap {

    private static List<CommandManual> manualsMapSortedAlphabetically;
    private static Map<CommandCategory, List<CommandManual>> categoryToManualsMap;

    /**
     * The raw manual map. All manuals go in here, they are unsorted and this map cannot and should
     * not be accessible by anything other than this class.
     * If a new command is created, it just needs to have it's manual object added into this raw map
     * and the other methods will include it in the sorting and such.
     *
     * @return A manual map of command name to it's corresponding manual object.
     */
    private static Map<String, CommandManual> createRawManualMap() {
        Map<String, CommandManual> rawManualMap = new HashMap<>();

        // Project/Application
        rawManualMap.put(
                GetCrotchet.getCommandManual().getName(),
                GetCrotchet.getCommandManual());
        rawManualMap.put(
                GetTempo.getCommandManual().getName(),
                GetTempo.getCommandManual());
        rawManualMap.put(
                SetTempo.getCommandManual().getName(),
                SetTempo.getCommandManual());
        rawManualMap.put(
                Version.getCommandManual().getName(),
                Version.getCommandManual());
        rawManualMap.put(
                Keyboard.getCommandManual().getName(),
                Keyboard.getCommandManual());
        rawManualMap.put(
                Help.getCommandManual().getName(),
                Help.getCommandManual());

        // Musical
        rawManualMap.put(
                Chord.getCommandManual().getName(),
                Chord.getCommandManual());
        rawManualMap.put(
                Enharmonic.getCommandManual().getName(),
                Enharmonic.getCommandManual());
        rawManualMap.put(
                FindInterval.getCommandManual().getName(),
                FindInterval.getCommandManual());
        rawManualMap.put(
                FindScale.getCommandManual().getName(),
                FindScale.getCommandManual());
        rawManualMap.put(
                FindScaleMidi.getCommandManual().getName(),
                FindScaleMidi.getCommandManual());
        rawManualMap.put(
                HasEnharmonic.getCommandManual().getName(),
                HasEnharmonic.getCommandManual());
        rawManualMap.put(
                Interval.getCommandManual().getName(),
                Interval.getCommandManual());
        rawManualMap.put(
                Midi.getCommandManual().getName(),
                Midi.getCommandManual());
        rawManualMap.put(
                Mode.getCommandManual().getName(),
                Mode.getCommandManual());
        rawManualMap.put(
                Note.getCommandManual().getName(),
                Note.getCommandManual());
        rawManualMap.put(
                Parent.getCommandManual().getName(),
                Parent.getCommandManual());
        rawManualMap.put(
                Semitone.getCommandManual().getName(),
                Semitone.getCommandManual());
        rawManualMap.put(
                SemitonesInterval.getCommandManual().getName(),
                SemitonesInterval.getCommandManual());
        rawManualMap.put(
                SimpleEnharmonic.getCommandManual().getName(),
                SimpleEnharmonic.getCommandManual());
        rawManualMap.put(
                EnharmonicInterval.getCommandManual().getName(),
                EnharmonicInterval.getCommandManual());
        rawManualMap.put(
                HasEnharmonicInterval.getCommandManual().getName(),
                HasEnharmonicInterval.getCommandManual());
        rawManualMap.put(
                Signature.getCommandManual().getName(),
                Signature.getCommandManual());
        rawManualMap.put(
                ScaleSignature.getCommandManual().getName(),
                ScaleSignature.getCommandManual());
        rawManualMap.put(
                FindChord.getCommandManual().getName(),
                FindChord.getCommandManual());
        rawManualMap.put(
                Quality.getCommandManual().getName(),
                Quality.getCommandManual());
        rawManualMap.put(
                ChordFunction.getCommandManual().getName(),
                ChordFunction.getCommandManual());
        rawManualMap.put(
                Function.getCommandManual().getName(),
                Function.getCommandManual());


        // Play
        rawManualMap.put(
                PlayChord.getCommandManual().getName(),
                PlayChord.getCommandManual());
        rawManualMap.put(
                PlayInterval.getCommandManual().getName(),
                PlayInterval.getCommandManual());
        rawManualMap.put(
                PlayNote.getCommandManual().getName(),
                PlayNote.getCommandManual());
        rawManualMap.put(
                PlayScale.getCommandManual().getName(),
                PlayScale.getCommandManual());

        // Term
        rawManualMap.put(
                TermCategory.getCommandManual().getName(),
                TermCategory.getCommandManual());
        rawManualMap.put(
                TermDefine.getCommandManual().getName(),
                TermDefine.getCommandManual());
        rawManualMap.put(
                TermLanguage.getCommandManual().getName(),
                TermLanguage.getCommandManual());
        rawManualMap.put(
                TermLookup.getCommandManual().getName(),
                TermLookup.getCommandManual());
        rawManualMap.put(
                TermMeaning.getCommandManual().getName(),
                TermMeaning.getCommandManual());
        rawManualMap.put(
                TermRemove.getCommandManual().getName(),
                TermRemove.getCommandManual());

        // Tutor
        rawManualMap.put(
                ChordTutor.getCommandManual().getName(),
                ChordTutor.getCommandManual());
        rawManualMap.put(
                IntervalTutor.getCommandManual().getName(),
                IntervalTutor.getCommandManual());
        rawManualMap.put(
                PitchTutor.getCommandManual().getName(),
                PitchTutor.getCommandManual());
        rawManualMap.put(
                ScaleTutor.getCommandManual().getName(),
                ScaleTutor.getCommandManual());
        rawManualMap.put(
                TermTutor.getCommandManual().getName(),
                TermTutor.getCommandManual());
        rawManualMap.put(
                SignatureTutor.getCommandManual().getName(),
                SignatureTutor.getCommandManual());
        rawManualMap.put(
                ChordSpellingTutor.getCommandManual().getName(),
                ChordSpellingTutor.getCommandManual());

        return Collections.unmodifiableMap(rawManualMap);
    }

    /**
     * Get a copy of the raw manual map.
     *
     * @return The raw manual map.
     */
    public static Map<String, CommandManual> getRawMap() {
        return createRawManualMap();
    }

    /**
     * Uses the raw command manual map, sorts it's values and then uses that sorted list to create
     * an alphabetically sorted list of command manuals.
     *
     * @return An unmodifiable map of command manuals, sorted alphabetically.
     */
    public static List<CommandManual> getManualsMapSortedAlphabetically() {
        if (manualsMapSortedAlphabetically == null) {
            // Get the raw manual map
            Map<String, CommandManual> rawManualMap = createRawManualMap();

            // Initialise the object we'll return
            manualsMapSortedAlphabetically = new LinkedList<>();

            // Create a list of the key strings (which are the names of the commands) and sort it.
            List<String> mapKeys = new ArrayList<>(rawManualMap.keySet());
            Collections.sort(mapKeys);

            // For each key in that sorted list
            for (String key : mapKeys) {
                // Add it's command manual item to the linked list.
                manualsMapSortedAlphabetically.add(rawManualMap.get(key));
            }
        }

        return Collections.unmodifiableList(manualsMapSortedAlphabetically);
    }

    /**
     * Get a map of the command manuals sorted alphabetically within their categories.
     * Categories are in the same order as they appear in the CommandCategory enum.
     *
     * @return Command categories mapping to a sorted list of command manuals.
     */
    public static Map<CommandCategory, List<CommandManual>> getCategoryToManualsMap() {
        if (categoryToManualsMap == null) {
            // Initialise the object
            categoryToManualsMap = new LinkedHashMap<>();

            // For each category in the enum, create a category mapped to an empty linked list.
            for (CommandCategory category : CommandCategory.values()) {
                categoryToManualsMap.put(category, new LinkedList<>());
            }

            // For each manual in the alphabetical manual map, put it into the correct category's
            // linked list.
            for (CommandManual manual : getManualsMapSortedAlphabetically()) {
                categoryToManualsMap.get(manual.getCategory()).add(manual);
            }
        }

        return categoryToManualsMap;
    }
}
