package seng302.util.enumerator;

/**
 * Enum containing all the error strings for each command.
 *
 * @author avh17
 */
@SuppressWarnings("SpellCheckingInspection")
public enum Error {
    // Used by NullCommand class
    NULLCOMMAND("Invalid command."),

    // General note errors
    INVALIDMIDINOTE("Invalid note. Not within the midi range (0 - 127)."),
    INVALIDNOTE("Invalid note. That note is invalid because it is not technically a note!"),

    // Used Midi class
    INVALIDMIDIARGUMENTS("Invalid arguments. Midi command expects a single note. Eg: midi(C4)."),

    // Used Note class
    INVALIDNOTEARGUMENTS(
            "Invalid arguments. Note command expects a single integer between 0 - 127."),

    // PlayInterval class
    INVALIDINTERVALARGUMENTS(
            "Invalid arguments. Interval command expects an interval and a single note."),
    INVALIDINTERVALNAME(
            "Invalid argument. Interval command expects an interval name. Eg: Major Third."),

    // Used by MajorScale class
    INVALIDMAJORSCALEARGUMENTS("Invalid arguments. "
            + "MajorScale command expects an a single note."),

    // Used by PentatonicScale class
    INVALIDPENTATONICNUMBEROFARGUMENTS("Invalid number of arguments. "
            + "PentatonicScale command expects a note and scale type."),
    INVALIDNOCORRESPONDINGPENTATONIC("No corresponding pentatonic scale."),

    // Used by the BluesScale class
    INVALIDBLUESSCALEARGUMENTS("Invalid arguments. "
            + "BluesScale command expects an a single note."),
    INVALIDNOCORRESPONDINGBLUES("Note has no corresponding blues scale."),

    // Used by FindScale class
    FINDSCALENOSCALE("Note does not have a corresponding scale."),
    INVALIDCOMMAND("Invalid command."),
    INVALIDSCALETYPE("Enter a valid scale type; e.g. 'major' or 'minor'."),
    INVALIDSCALEARGUMENTS("Invalid arguments. "
            + "FindScale command expects a single note and a scale type; e.g. 'major' or 'minor'."),

    NOMODE("No Corresponding Scale Mode"),

    // Used by Enharmonic class
    ENHARMONICINVALIDARGUMENTS("Arguments are invalid!\n"
            + "Must be in the form 'enharmonic(+ or -, note)'"),

    // Used by GetTempo class
    GETTEMPOFAILED("Failed to retrieve tempo."),

    // Used by GetCrotchet class
    GETCROTCHETFAILED("Failed to calculate crotchet"),

    // Used by HasEnharmonic class
    HASENHARMONICINVALIDARGUMENTS("No valid arguments given\n"
            + "Must be in the form 'hasEnharmonic(note)'"),

    // Used by Interval class
    INTERVALINVALIDOCTAVE("Note's octave must be within -1 and 9."),
    INTERVALDOESNOTEXIST("No note exists with that interval."),
    INTERVALINVALIDARGUMENTS("Incorrect number of arguments. Interval command expects a "
            + "valid interval and a note (eg \"interval(C, 'Major Third')\")."),

    // Used by HasEnharmonicInterval class
    HASEHARMONICINTERVALINTERVALDOESNOTEXIST("Invalid argument. hasEnharmonicInterval command"
            + " expects an interval name. Eg: Major Third."),
    HASENHARMONICINTERVALINVALIDNUMBEROFARGUMENTS("Invalid number of arguments! Expected 1,"
            + " (got %d)"),

    // Used by EnharmonicInterval
    ENHARMONICINTERVALINTERVALDOESNOTEXIST("Invalid argument. enharmonicInterval command expects"
            + " an interval name. Eg: Major Third."),
    ENHARMONICINTERVALINVALIDNUMBEROFARGUMENTS("Invalid number of arguments! Expected 1,"
            + " (got %d)"),

    // Used by IntervalTutor
    INTERVALTTUTORINVALIDARGUMENTS("Invalid argument!"),
    INTERVALTUTORINVALIDEXPLANATION("The Command only accepts arguments in the form 'x' "
            + "followed by a number"),

    // Used by PitchTutor class
    PITCHTUTORINVALIDNOTEPAIRS("The note pairs parameter needs to be in the form 'x' "
            + "followed by a number."),
    PITCHTUTORONENOTE("You need to give two notes in the parameters to create "
            + "a range."),
    PITCHTUTORNOTEPAIRSNOTNUMBER("The value given for the note pairs parameter was not "
            + "a number."),
    PITCHTUTORINVALIDNOTE("One or more of the given notes were not valid."),

    // Used by ChordTutor
    CHORDTUTORINVALIDARGUMENTS("Invalid arguments!"),
    CHORDTUTORINVALIDCHORDNUMBER("The Command only accepts arguments in the form 'x' "
            + "followed by a number"),
    CHORDTUTORINVALIDCHORDSTYLE("Invalid chord style. "
            + "Chord style must be 'arpeggio, 'unison' or 'both'."),
    CHORDTUTORCHORDNUMBERNOTNUMBER("The value given for the number of tests was not "
            + "a number."),

    // Used by PlayInterval class
    PLAYINTERVALZEROARGUMENTS("Expected two arguments, 0 given."),
    PLAYINTERVALEXPECTEDARGUMENTS("Expected two arguments, %d given."),
    PLAYINTERVALINVALIDNUMSEMITONES("Invalid number of semitones for interval. Number must lie "
            + "between 0 - 24."),

    // Used py PlayScale class
    PLAYSCALEDETERMINENOTE("Could not determine starting note."),
    PLAYSCALEDETERMINESCALE("Could not determine scale type."),
    PLAYSCALESETSCALE("Failed to set scale."),
    PLAYSCALEPLAYNOTE("Failed to play note."),
    PLAYSCALEARRAY("Cannot add note to scale array."),
    PLAYSCALEINVALIDNUMARGS("Invalid number of arguments! (got %d)"),
    PLAYSCALETHIRDARGONE("Third argument must be a direction or the number of octaves (got %s)."),
    PLAYSCALETHIRDARGTWO("Third argument must be a direction (got %s)."),
    PLAYSCALEFOURTHARG("Fourth argument must be the number of octaves (got %s)."),
    PLAYSCALEFIFTHARG("Fifth argument must be either 'swing' or 'straight' (got %s)."),

    // Used by Semitone class
    SEMITONENOARGS("No arguments given. Semitone expects input in the form semitone(1, C4)."),
    SEMITONEINVALIDARGS("Invalid arguments. Semitone expects input in the form semitone(1, C4)."),
    SEMITONESIMPLENOTE("Semitone command only excepts simple notes. Eg: C# or Db."),
    SEMITONEMIDIRANGE("Calculated note lies outside of the midi range"),

    // Used by SemitonesInterval class
    SEMITONESINTERVALNOARGS("Must pass one argument, the type of interval "
            + "desired to find the number of semitones for."),
    SEMITONESINTERVALUNKNOWN("Unknown Interval."),
    SEMITONESINTERVALINVALIDINTERVAL("Invalid Interval."),

    // Used by SetTempo
    SETTEMPOINVALIDARGS("Invalid arguments. Command expects a single positive integer."),
    SETTEMPOINVALIDTEMPO("Invalid tempo. Tempo was not changed."),
    SETTEMPOOUTSIDERANGE("Input lies outside of tempo range (%d - %d bpm). Tempo did not change."),

    // Used by SimpleEnharmonic
    SIMPLEENHARMOINICINVALIDARGS("No valid arguments given\n"
            +
            "Must be in the form 'enharmonicSimple(note)'"),

    // Used by PlayNote class
    PLAYNOTEINVALIDDURATION("Time duration must be between 0-10000 milliseconds"),
    PLAYNOTETOOMANYPARAMS("Incorrect parameters. Requires 1 or 2 parameters"),
    PLAYNOTENOPARAMS("No parameters given. Requires at least one Note value to play"),
    PLAYNOTEFAILED("Failed to play note!"),

    // Used by FindInterval class
    FINDINTERVALTOOFEWARGUMENTS("Too few arguments. Command expects two notes "
            + "(eg \"findInterval(D, C#)\")"),
    FINDINTERVALTOOMANYARGUMENTS("Too many arguments. Command expects two notes "
            + "(eg \"findInterval(D, C#)\")"),
    FINDINTERVALINVALIDARGUMENTS("Invalid arguments. Command expects two notes. "
            + "Eg: findInterval(C4, D4)."),
    FINDINTERVALNOTEHIGHER("Note 1 cannot be higher than Note 2."),
    FINDINTERVALMIDI("Failed to create midi's for notes."),
    FINDINTERVALINVALIDINTERVAL("The interval requested is out of the range of 0-24 semitones."),

    // Used by FindScale class
    FINDSCALEINVALIDARGUMENTS("Invalid arguments. FindScale command expects an scale type and a "
            + "single note."),

    // Used by Terms classes
    TERMLOOKUP("Command expected a single term to look up."),
    TERMREMOVE("Command expected a single term to remove."),
    TERMNOTDEFINED("\"%s\" is not defined."),
    TERMDEFINEINVALIDNUMARGS("Command expected 4 arguments. (Term, Language, Meaning, Category)."),
    TERMFAILEDTOADDTERM("Failed to add term."),
    TERMNOTDEFINEDNOTREMOVED("\"%s\" is not defined. Nothing removed."),

    //Used by the chord classes
    CHORDINVALIDNUMARGS("Incorrect number of arguments. Chord command expects at least 2."),
    CHORDINVALIDQUALITY("Invalid chord quality."),
    CHORDDOESNOTEXIST("That chord does not exist."),
    CHORDWRONGINVERSION("3rd argument must be of form '1st'."),
    CHORDWRONGINVERSIONTRIAD("Triads accept up to two inversions."),

    //Used by the PlayChord class
    PLAYCHORDINVALIDARP("Invalid chord arpeggio. playChord expects either "
            + "\"arpeggio\" or \"unison\"."),

    //Used by the XinityNote class
    XINITYNOTECANNOTCREATENOTE("Cannot create note from invalid string: "),


    // Used by TermTutor class
    TERMTUTORTOOMANYARGUMENTS("Too many arguments! The command will only accept 1."),
    TERMTUTORNOIDENTIFIER("The parameter needs to be in the form 'x' followed by a number."),
    TERMTUTORNOTANUMBER("The value given for parameter was not a number."),
    TERMTUTORINVALIDNUMBER("The number of tests to run must be greater than 10."),

    // Used by the Scale Tutor Class
    SCALETUTORINVALIDARGS("Tutor can only have parameters: number of scales, "
            + "direction, number of octaves, play style and a modes flag."),

    // Used by the Signature Class
    INVALIDSHOWSIGNATUREARGUMENTS("The command expects two arguments, eg. 'C, major'"),
    INVALIDSCALENOSIGNATURE("The given scale has no corresponding key signature."),

    // Used by the ScaleSignature Class
    SCALESIGNATUREWRONGARGUMENTS("Command accepts input in the form '2#' or key signature notes."),
    SCALESIGNATUREWRONGNUMBEROFNOTES("Command only excepts 1-7 key signature notes."),
    SCALESIGNATUREWRONGTYPE("If first argument is in the form '2#', second argument must be "
            + "'major', 'minor' or 'both'."),
    SCALESIGNATURENOSCALE("No corresponding scale."),
    SCALESIGNATUREWRONGFLATSHARPS("Number of flats/sharps must be between 0 and 7."),
    SCALESIGNATUREMIXEDFLATSHARPS("Mixed accidentals not allowed."),
    SCALESIGNATURENOTNOTES("Not key signature notes."),

    // Used by the Signature Tutor Class
    SIGNATURETUTORNOARGSGIVEN("Not enough arguments given! The command accepts 3 arguments."
            + "\nThe number of questions to ask, a key signature specification type "
            + "and a scale name argument (eg \"scaleTutor(x5, Notes, Major)\")."),
    SIGNATURETUTORTOOMANYARGSGIVEN("Too arguments given! The command accepts 3 arguments."
            + "\nThe number of questions to ask, a key signature specification type "
            + "and a scale name argument (eg \"scaleTutor(x5, Notes, Major)\")."),
    SIGNATURETUTORINVALIDARGUMENTS("One or more of the arguments are invalid! "
            + "The command accepts 3 arguments."
            + "\nThe number of questions to ask, a key signature specification type "
            + "and a scale name argument (eg \"scaleTutor(x5, Notes, Major)\")."),

    // Used by the spelling tutor
    SPELLINGTUTORINVALIDARGUMENTS("One or more of the arguments are invlaid."
            + "\nThe command accepts the number of questions to run, chord constraints, "
            + "the noEnharmonic flag and the randomNotes flag"
            + "and a scale name argument (eg \"scaleTutor(x5, Notes, Major)\")."),

    // Used by find chord class
    FINDCHORDNOOCTAVES("Command does not accept notes with octaves."),
    FINDCHORDNOVALIDCHORD("The given notes do not correspond to any known chord."),
    FINDCHORDINVALIDNOTE("Invalid note."),
    FINDCHORDOUTOFMIDIRANGE("Some notes go out of midi range 0-127, could not determine chord."),
    FINDCHORDINVALIDARGUMENTS("The given arguments are invalid. The command accepts chord notes "
            + "and the flag\'inversion\'"),

    // Used by Quality class
    QUALITYINVALIDARGS("Invalid Arguments. Command accepts a valid chord function."),

    // Used by the Chord Function class
    CHORDFUNCTIONINVALIDARGS("Invalid Arguments. Command accepts a "
            + "valid chord function and scale."),

    // Used by the Function class
    FUNCTIONINVALIDARGS("Invalid Arguments. Command accepts a valid chord and note."),

    FINDCHORDNOARGS("Invalid number of arguments."),

    // Used by the Mode Class
    MODEINVALIDARGS("Invalids number of arguments. Command accepts a valid note and degree"
            + " between 1 and 7."),
    MODEMIDIRANGE("Note not within the midi range (0 - 127). Command accepts a valid note and"
            + " degree between 1 and 7."),
    MODEINVALIDKEY("Key is invalid. Command accepts a valid note and degree between 1 and 7."),
    MODEINVALIDDEGREE("Degree is invalid. Command accepts a valid note and degree between 1"
            + " and 7."),
    MODEINVALIDMODETYPE("Mode specified is invalid. Try a valid mode such as \"major\""
            + " or \"melodic minor\""),

    // Used by the Parent Class
    PARENTINVALIDARGS("Invalids number of arguments. Command accepts a valid note "
            + "and scale mode"),
    PARENTMIDIRANGE("Note not within the midi range (0 - 127). Command accepts a valid note"
            + "and scale mode."),
    PARENTINVALIDNOTE("Note is invalid. Command accepts a valid note and scale mode"),
    PARENTINVALIDMODE("Scale mode is invalid. Command accepts a valid note and scale mode.");

    private String value;

    /**
     * Error enum Constructor.
     *
     * @param value The error message.
     */
    Error(String value) {
        this.value = value;
    }

    /**
     * Get the error message.
     *
     * @return The error string.
     */
    public String getError() {
        return value;
    }
}