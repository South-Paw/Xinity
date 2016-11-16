# Xinity: User Manual
_Version 1.5.13_

# Contents

> * [1. Features](#1-features)
    * [1.1 Desktop Version Feature List](#11-desktop-version-feature-list)
    * [1.2 Web Version Feature List](#12-web-version-feature-list)
* [2. Getting Started (Desktop Version)](#2-getting-started-desktop-version)
    * [2.1 The Application](#21-the-application)
    * [2.2 Projects](#22-projects)
        * [2.2.1 Creating a New Project](#221-creating-a-new-project)
        * [2.2.2 Opening an Existing Project](#222-opening-an-existing-project)
        * [2.2.3 Saving an Existing Project](#223-saving-an-existing-project)
    * [2.3 Transcripts](#23-transcripts)
        * [2.3.1 Opening a Transcript](#231-opening-a-transcript)
        * [2.3.2 Saving a Transcript](#232-saving-a-transcript)
        * [2.3.3 Using the Transcript](#233-using-the-transcript)
        * [2.3.4 Running and Re-Running Commands](#234-running-and-re-running-commands)
        * [2.3.5 Writing your own Xinity script](#235-writing-your-own-xinity-script)
    * [2.4 Tutors](#24-tutors)
        * [2.4.1 Pitch Tutor](#241-pitch-tutor)
        * [2.4.2 Interval Tutor](#242-interval-tutor)
        * [2.4.3 Term Tutor](#243-term-tutor)
        * [2.4.4 Scale Tutor](#244-scale-tutor)
        * [2.4.5 Chord Tutor](#245-chord-tutor)
        * [2.4.6 Signature Tutor](#246-signature-tutor)
        * [2.4.7 Chord Spelling Tutor](#247-chord-spelling-tutor)
    * [2.5 Shortcut Keys](#25-shortcut-keys)
    * [2.6 Keyboard](#26-keyboard)
* [3. Getting Started (Web Version)](#3-getting-started-web-version)
    * [3.1 The Application](#31-the-application)
    * [3.2 Logging In](#32-the-application)
    * [3.3 Command Line](#33-command-line)
    * [3.4 Practice Schedules](#34-practice-schedules)
    * [3.5 Profile Settings](#35-profile-settings)
* [4. Transcript Commands](#4-transcript-commands)

# 1. Features

## 1.1 Desktop Version Feature List
* Stand alone application.
* Project Management (Saving & Loading).
* Transcript Management (Saving & Loading).
* Replayable Command list.
* DSL for commands and inputs & checking variables.
* Various commands for playing, calculating and experimenting with the musical
  universe (See the 'Transcript Commands' section).
* Ability to write your own programs and load them into the transcript for
  playback.
* Musical Term Dictionary, save terms, descriptions and languages.
* Pitch Tutor - test your skill of knowing different pitches of notes.
* Interval Tutor - test your knowledge of listening to different intervals and
  saying which was which.
* Term Tutor - test your knowledge of your defined musical terms.
* Scale Tutor - test your knowledge of scales by their notes.
* Chord Tutor - test your knowledge of chords by their notes.
* Signature Tutor - test your knowledge of key signatures
* Spelling Tutor - test your knowledge of chord names

## 1.2 Web Version Feature List
* All DSL commands from the desktop version (Excluding tutors and keyboard)
* Command history
* User profile management
* Profile customization
* Modern and easy to use UI
* Syntax highlighting
* Practice scheduling - Learn to play notes, intervals, chords and scales

# 2. Getting Started (Desktop Version)
## 2.1 The Application
To run the application, find the application jar file and double-click the file
to launch.

You can close the application at any time by selecting `File > Quit` from the
menu or clicking the `x` in the top right hand corner.

## 2.2 Projects
Xinity now manages each session with a Project. When you first start the
application, an empty project is created and any actions or changes made will
be stored in it.

### 2.2.1 Creating a New Project
To create a New Project, select `File > New Project` from the menu (or use the
hot key `Ctrl + N`). If the current project you are in has any changes (Which
will be indicated by a `*` at the end of the project name at the top of the
main window), Xinity will prompt you to either save those changes or discard
them.

### 2.2.2 Opening an Existing Project
To open an existing project, select `File > Open Project` from the menu. You
can also use the hot key combination `Ctrl + O`. Once the file project
selection dialogue is open, navigate to the project folder you wish to open,
click on it and then click `Open` in the bottom right. You can also open the
project folder and then click `Open` in the bottom right.

### 2.2.3 Saving an Existing Project
To save the currently opened project, select `File > Save Project` from the
menu. If this is the first time saving the project, Xinity will prompt you to
ask where you'd like to save this project to. After selecting a location, click
Save.

If you have already saved a project, selecting `File > Save Project` from the
menu again will save the current project to it's selected location. You can
also use the hot key combination `Ctrl + S` to save at any time.

Alternatively, if you wish to save a copy of the current project to a different
location, select `File > Save Project As...` from the menu and select a new
location for the project to be stored. You can also use the hot key combination
`Ctrl + Shift + S` to save as.

## 2.3 Transcripts
The transcript view is where commands can be entered to perform changes to the
programs settings, load new tutor sessions, play notes and more. For a full
list of valid commands please see the 'Transcript Commands' section of this
document.

### 2.3.1 Opening a Transcript
Transcripts are not related to a Project as one project can have multiple
transcripts or you might not even want to save transcripts with a project.

To open a transcript, select `File > Open Transcript...` from the menu and
navigate to the location of the transcript you wish to load. Once loaded, the
UI shall show how many Commands were loaded with the transcript beside the
`Run All` button or inform you if no commands were loaded.

Please see the 'Running and Re-Running Commands' section for more information
on replaying commands.

### 2.3.2 Saving a Transcript
After running some commands in the transcript area, you can save the contents
of the transcript and any commands that have been run by selecting
`File > Save Transcript`. This will display a drop out menu where you must pick
between `Full Transcript...` and `Commands Only...`

Selecting `Full Transcript...` will save the transcript, the command history
and all commands that have been run in this session to the transcript file.

Selecting `Commands Only...` will save a transcript with only the commands that
have been run this session.

When selecting either of these options, if any commands have been run that were
invalid commands, Xinity will prompt you whether you wish to Prune the invalid
commands from the saved transcript. If you select yes, any commands that were
invalid will be omitted from the save file, otherwise they will be included.

### 2.3.3 Using the Transcript
On first load of the application, the transcript will be blank and the command
input field will be empty. This field is used to input commands and have them
executed by the application. For example, try typing in `version()` and either
clicking the `Execute` button or pressing the `Enter` key with the field still
in focus.

The area above the transcript input field should now show the following;

```
> version()
Version: 1.3.17
```

Commands executed are shown with a preceding `>` character followed by the
command you entered. On the next line, the output of the command is shown or
any errors that were encountered.

To clear the text clutter in the Transcript area, you can click on the `Clear`
button in the top right which will remove all output from the transcript but
leave the command history and commands run intact.

### 2.3.4 Running and Re-Running Commands
As commands are executed, they are added onto a list of runnable commands that
can be played back at any time. Alternatively, when a transcript is loaded into
Xinity, the commands loaded with it can be played back immediately. This
functionality allows you to create runnable Xinity programs that can be
executed.

Once some commands have been entered into the program they are available for
re-running. To do so, click on the arrow beside the `Run All` button to change
the mode to either `Run One` or `Run All`

Both modes will begin at the first command in re-runnable commands list.
Running One will move forward one on each click. However if you move forward by
6 commands and then change to `Run All` mode, the remaining commands in the
list will all be executed.

At any time you can also click the `Restart` button which will move the next
command to be run back to the first command in the runnable list.

### 2.3.5 Writing your own Xinity script
You can write your own runnable Xinity script in your favourite text editor. To
do so, open your text editor.

The first line of the file needs to contain a line with `#%runnable%#` on it
so Xinity knows to read all items below this as commands. Beneath this heading,
you simply need to type out the commands you wish to run, each must be on their
own line.

An example runnable file;

```
#%runnable%#
setTempo(200)
play(60)
play(64)
play(56)
play(60)
```

You can also edit saved transcripts created in Xinity to add your own commands
or tweak existing ones.

## 2.4 Tutors
Tutors are a feature of our application that helps you to learn music and test
your skills.

### 2.4.1 Pitch Tutor
To load the Pitch Tutor, you can either select the tab `Pitch Tutor`, or enter
the `pitchTutor()` command into the transcript which will switch to the tab
with a set of questions loaded.

When the tab is in view, you can create a set of questions by selecting the
`Create Test` button and entering the number of note pairs (questions) to be
asked and the note range that the questions will be within. The note range can
be in either note names or midi numbers eg; `C4` and `60` are both valid.

Once the test has been started, the first question will be displayed in
the main pane. The play button will play two random notes. Test will require
you to decided whether the second note is higher, lower or the same. Once
decided, give the answer in the drop down box and press the check button to
check your answer. The tutor will tell you whether your answer was correct or
incorrect.

At the end of a question set, you can save or discard the results of the test
and you may also click `Retry Tests` to retry any tests that were failed in the
previous question set.

At any time you can click the `End Test` button to cancel all remaining tests
or click the `Reset` button to reset the whole tutor to a new state.

### 2.4.2 Interval Tutor
To load the Interval Tutor, you can either select the tab `Interval Tutor`, or
enter the `intervalTutor()` command into the transcript which will switch to the
tab with a set of questions loaded.

When the tab is in view, you can create a set of questions by selecting the
`Create Test` button and entering the number of intervals (questions) to be
asked.

Once the test has been started, the first question will be displayed in
the main pane. The play button will play the two notes of the random interval
starting on the octave of C4. The test will require you to decide what interval
is being played eg; `Perfect Unison`. Once decided, give the answer in the drop
down box and press the check button to check you answer. The tutor will tell you
whether your answer was correct or incorrect.

At any time you can click the `End Test` button to cancel all remaining tests
or click the `Reset` button to reset the whole tutor to a new state.

### 2.4.3 Term Tutor
To load the Term Tutor, make sure you have at least one term defined in the
dictionary. You can either select the tab `Term Tutor`, or enter the
`termTutor()` command into the transcript which will switch to the tab with a
set of questions loaded.

When the tab is in view, you can create a set of questions by selecting the
`Create Test` button and entering the number of terms (questions) to be asked.

Once the test has been started, the first question will be displayed in
the main pane. A question will be displayed asking to give an attribute of a
term. Once the question has been answered, give the answer in the text field and
press the check button to check you answer. The tutor will tell you whether
your answer was correct or incorrect.

At any time you can click the `End Test` button to cancel all remaining tests
or click the `Reset` button to reset the whole tutor to a new state.

### 2.4.4 Scale Tutor
To load the Scale Tutor, you can either select the tab `Scale Tutor`, or enter
the `scaleTutor()` command into the transcript which will switch to the tab
with a set of questions loaded.

When the tab is in view, you can create a set of questions by selecting the
`Create Test` button and entering the number of scales (questions) to be asked.
There is also the option to include scale modes.

Once the test has been started, the first question will be displayed in
the main pane. The play button will play the random scale starting on the octave
of C4. The test will require you to decide what scale type is being played
eg; `Major` or `Minor`. Once decided, give the answer in the drop down box and
press the check button to check your answer. The tutor will tell you whether
your answer was correct or incorrect.

At any time you can click the `End Test` button to cancel all remaining tests
or click the `Reset` button to reset the whole tutor to a new state.

### 2.4.5 Chord Tutor
To load the Chord Tutor, you can either select the tab `Chord Tutor`, or enter
the `chordTutor()` command into the transcript which will switch to the tab
with a set of questions loaded.

When the tab is in view, you can create a set a questions by selecting the
`Create Test` button and entering the number of chords (questions) to be asked.

Once the test has been started, the first question will be displayed in
the main pane. The play button will play the random chord starting on the octave
of C4. The test will require you to decide what chord type is being played
eg; `Major` or `Minor`. Once decided, give the answer in the drop down box and
press the check button to check your answer. The tutor will tell you whether
your answer was correct or incorrect.

At any time you can click the `End Test` button to cancel all remaining tests
or click the `Reset` button to reset the whole tutor to a new state.

### 2.4.6 Signature Tutor
To load the Signature Tutor, you can either select the tab `Signature Tutor`,
or enter the `signatureTutor()` command into the transcript which will switch
to the tab with a set of questions loaded.

When the tab is in view, you can create a set a questions by selecting the
`Create Test` button and entering the number of questions, the specifier type,
and the name type.

Once the test has been started, the first question will be displayed in
the main pane. There is an input field to enter your answer to the question.
When you press the check button,  the tutor will tell you whetheryour answer
was correct or incorrect.

At any time you can click the `End Test` button to cancel all remaining tests
or click the `Reset` button to reset the whole tutor to a new state.

### 2.4.7 Chord Spelling Tutor
To load the Chord Spelling Tutor, you can either select the tab
`Spelling Tutor`, or enter the `spellingTutor()` command into the transcript
which will switch to the tab with a set of questions loaded.

When the tab is in view, you can create a set a questions by selecting the
`Create Test` button and entering the number of chords (questions) to be asked.

Once the test has been started, the first question will be displayed in
the main pane. There are two types of test question. In the first, the system
gives the chord name and the user identifies the corresponding notes. In the
second, the system gives 3 or 4 notes and the user provides the chord name.
The tutor will tell you whether your answer put in the inbox was correct or
incorrect when check is clicked.

At any time you can click the `End Test` button to cancel all remaining tests
or click the `Reset` button to reset the whole tutor to a new state.

## 2.5 Shortcut Keys
* `Ctrl + N` Create a New Project
* `Ctrl + O` Open a Project
* `Ctrl + S` Save a Project
* `Ctrl + Shift + S` Save a Project As
* `Ctrl + K` Open keyboard

## 2.6 Keyboard
The keyboard can be opened in one of three ways. By clicking the menu item, by
running the keyboard command or by using the keyboard shortcut. The keyboard
contains keys ranging from C-1 to G9 (Midi numbers 0 - 127). There is a visual
representation of which octave you are in at the top of the keyboard. There are
two drop down menus at the bottom of the keyboard. The Enharmonics drop down
has the options to display higher or lower enharmonic equivalent note names on
the keys. The Note labels drop down has options for displaying the note names
on the keys.

# 3. Getting Started (Web Version)

## 3.1 The Application
There are two ways to use the web version of Xinity.

To use the web version locally, navigate to the folder the jar is stored,
open a command prompt and run the command `java -jar Xinity-X.X.X.jar -server`,
replacing the X's with the right version number. This will start the server.
Then open a web browser and go to `http://localhost:4567`.

To use the web version externally, go to http://xinity.ithub.net.nz:4567 on
your devices browser.

## 3.2 Logging in
When you go to http://xinity.ithub.net.nz:4567/#/login (or `localhost:4567` if
you are running the server locally) you will be presented with a login screen.
If you have an existing account, enter your username and login; otherwise
click '...or Create an Account'. Follow the steps shown to register, making
sure your password meets requirements and you will then be able to login. When
you have logged in successfully you will be presented with the dashboard of
the Xinity web app.

## 3.3 Command Line
When you have logged into the Xinity Web app there are a few tabs along the
side. One of these is the 'Command Line'. Clicking this tab will switch to the
command line where you can enter valid commands you will be familiar with from
the desktop application. For a list of valid commands you can consult the
valid commands within this documentation or enter the command 'help()' in the
Xinity Web Application.  The input for commands is found along the bottom of
your screen, when a command is typed it can be sent by either pressing enter,
or clicking the green play button.

Along the top of the Command Line tab you will also find buttons 'Clear Output'
to clear the transcript window of all entered commands and their results, along
with the 'Toggle Syntax' button for highlighting commands, arguments, and
strings, and other parts of the output.

## 3.4 Practice Schedules
In the Practice Schedules tab, you will begin with no available schedules.
A schedule is a collection of different tutor style tests that can be run.
To make your first schedule, click the '+ Create Schedule' button found in the
top left corner. When creating a schedule there are three primary parameters
to fill out: 'Name' the name of the schedule, 'Difficulty' the difficulty of
the tests to run - the option 'Hard' will include enharmonic equivalents along
with more difficult scale and chord types, and finally the 'Order' which can
be set to randomize the order in which the questions are presented to you.
With these set, the 'Add Question' button in the bottom left corner can be
used to add different question types to the schedule. The 4 question types
are Chord, Interval, Note, and Scale.

Chord Questions allow you to specify: The number of Questions you would like
to run, the Note Range to constrain the root note too, and an option to set
what sort of chord styles are valid - unison or arpeggio.

Interval Questions allow you to specify: The number of Questions you would
like to run, the Note Range to constrain the root note too.

Note Questions allow you to specify: The number of Questions you would like to
run, the Note Range to constrain the root note too.

Scale Questions allow you to specify: The number of Questions you would like to
run, the Note Range to constrain the root note too, the direction the scale
should be played, the number of octaves the scale should generate, and the play
style of the scale - straight or swing.

When questions have been added to your Schedule you can click 'Save' to save
the schedule so it is ready for use.

With the Schedule created you should now see it in your list of schedule(s).
The Schedule displays its name, the total number of questions in its schedule,
the number of times it has been run and three buttons. The first button will
allow you to delete the schedule, the second will allow you to edit it where
you can change any parameter of the schedule, and finally the 'Run' button
which starts running the given schedule.

When a schedule is running you will be given a different instruction of what
to do based on the question type. An example might be `Play the scale C4
Pentatonic Minor`. If you need a hint the button 'Show Hint' can be used near
the centre of the application which will give a different hint depending on the
question type. To check how you have done you can listen to the answer.
After listening to player or the tutor can rate how it went from 1 to 5 stars.
When all the tests have run you can then finish the schedule and are given the
option to save the results.

## 3.5 Profile Settings
To access the user settings, click on the small cogs at the bottom left of the
page. Here, you can edit your name, email, location, bio and profile picture.
On the left hand side, you can also switch to two other tabs. The theme tab
lets you change the theme of the application. The API key tab lets you reset
your API key if you think it has been compromised. You can log out of your
account any time with the log out button under the settings button.

# 4. Transcript Commands

### App
* `getCrotchet()` Returns the current Project's crochet.
* `getTempo()` Returns the current Project's tempo.
* `help([commandName])` Gives a list of commands or gives the specific command's manual page.
* `keyboard()` Launches the application's keyboard.
* `setTempo(tempo[, f])` Sets the current Project's tempo to the given speed if it's within the range of 20 to 400.
For values outside of this range an `f` argument can be specified to indicate an override of the default range.
* `version()` Returns the application version.

### Musical
* `chord(note, quality, inversion)` Returns a chord based on a given note and quality.
* `chordFunction(function, note)` Returns the diatonic chord based off the major scale of the given note.
* `enharmonic(direction, note)` Returns a note based on a direction and a given note.
* `enharmonicInterval(interval)` Displays an enharmonic equivalent for the given interval.
* `findChord(note1, note2, note3, [note4, ], [inversion, ], [noEnharmonic, ])` Determines if the given notes produce a valid chord and returns it.
* `findInterval(noteA, noteB)` Returns the interval between noteA and noteB, providing noteB is equal to or higher than noteA.
* `findScale(note, scale)` Returns a list of the notes in the given scale type, starting from the root note.
* `findScaleMidi(note, scale)` Returns a list of the midi notes in the given scale type, starting from the root note.
* `function(chordNote, quality, scaleNote)` Returns the function for the given chord and scale.
* `hasEnharmonic(note)` Returns whether a given note has a simple enharmonic.
* `hasEnharmonicInterval(interval)` Displays whether the given interval has a simple enharmonic equivalent.
* `interval(interval, note)` Given a note, the command will find the second note in the interval.
* `majorScale(note)` Returns the major scale of the given note in notes.
* `majorScaleMidi(note)` Returns the major scale of the given note in midi numbers.
* `midi(note)` Converts the given note into it's corresponding midi number.
* `mode(key, degree)` Returns the corresponding mode of the major scale.
* `note(midi)` Converts the given midi into it's corresponding note.
* `parent(note, mode)` Returns the corresponding parent scale of a mode.
* `quality(function)` Returns the chord quality relating to the given function
* `scaleSignature(signature)` Returns the scale name for the given key signature.
* `semitone(number, note)` Calculates the semitone for a given note, depending on the number value. Number value can be a positive or negative whole number and the command will produce a note that number of semitones above or below the given note.
* `semitonesInterval(interval)` Calculates the number of semitones within a given interval.
* `signature(note, scale, override)` Returns the key signature for the given scale, in a form determined by the override.
* `simpleEnharmonic(note)` Displays the simple enharmonic equivalent for a given note.

### Play
* `playChord(note, quality[, arpeggio|unison])` Plays a chord based on a given note and quality.
* `playInterval(interval, note)` Given an note and interval, the command will play the two notes of the interval.
* `play(number|note[, duration])` Given a midi number or a note, command will play the given or corresponding midi note.
* `playScale(note, scaleType[, scaleDirection][, octaves][, playStyle])` Plays a scale given a starting note and scale type.

### Tutor (Not available for Web)
* `spellingTutor([number][, constraints][, noEnharmonic][, randomNotes]) ` Run a Chord Spelling Test in the Spelling Tutor tab.
* `chordTutor([number][, chordStyle])` Run a Chord Recognition Test in the Chord Tutor tab.
* `intervalTutor([number])` Run a Interval Recognition Test in the Interval Tutor tab.
* `pitchTutor([number][, noteA, noteB])` Run a Pitch Recognition Test in the Pitch Tutor tab.
* `scaleTutor([number][, direction][, octaves][, playStyle][, modes])` Run a Scale Recognition Test in the Scale Tutor tab.
* `signatureTutor([number][, signatureSpecifier][, scaleType])` Run a Signature Recognition Test in the Signature Tutor tab.
* `termTutor([number])` Run a Term Recognition Test in the Term Tutor tab using terms defined in the users term dictionary.

### Term
* `termCategory(term)` Retrieves the category of the given term.
* `termDefine(term, language, meaning, category)` Defines a term given the meaning, language, and category and stores the term in the dictionary.
* `termLanguage(term)` Retrieves the language of the given term.
* `termLookup(term)` Retrieves the entire definition of the given term.
* `termMeaning(term)` Retrieves the meaning of the given term.
* `termRemove(term)` Removes the given term from the term dictionary.
