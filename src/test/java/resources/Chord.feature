Feature: Chord Cucumber

  Scenario: Passing the quality Major Triad and the note A to the Chord command
    Given I have a quality Major Triad and a note A
    When I call the chord command
    Then The correct chord A, C#, E is displayed

  Scenario: Passing the quality Minor Triad and the note C# to the Chord command
    Given I have a quality Minor Triad and a note C#
    When I call the chord command
    Then The correct chord C#, E, G# is displayed

  Scenario: Passing the quality Major Triad and the note D to the Chord command
    Given I have a quality Major Triad and a note D
    When I call the chord command
    Then The correct chord D, F#, A is displayed

  Scenario: Passing the quality Minor Triad and the note G#2 to the Chord command
    Given I have a quality Minor Triad and a note G#2
    When I call the chord command
    Then The correct chord G#2, B2, D#3 is displayed

  Scenario: Passing the quality minor triad and the note e7 to the Chord command
    Given I have a quality minor triad and a note e7
    When I call the chord command
    Then The correct chord E7, G7, B7 is displayed

  Scenario: Passing the quality minor triad and the note Dx to the Chord command
    Given I have a quality minor triad and a note Dx
    When I call the chord command
    Then The correct chord Dx, Fx, Ax is displayed

  Scenario: Passing the quality major 7th and the note C to the Chord command
    Given I have a quality major 7th and a note C
    When I call the chord command
    Then The correct chord C, E, G, B is displayed

  Scenario: Passing the quality minor seventh and the note D6 to the Chord command
    Given I have a quality minor seventh and a note D6
    When I call the chord command
    Then The correct chord D6, F6, A6, C7 is displayed

  Scenario: Passing the quality Seventh and the note G# to the Chord command
    Given I have a quality Seventh and a note G#
    When I call the chord command
    Then The correct chord G#, B#, D#, F# is displayed

  Scenario: Passing the quality Half Diminished and the note Bb to the Chord command
    Given I have a quality Half Diminished and a note Bb
    When I call the chord command
    Then The correct chord Bb, Db, Fb, Ab is displayed

  Scenario: Passing the quality major sixth and the note C#4 to the Chord command
    Given I have a quality major sixth and a note C#4
    When I call the chord command
    Then The correct chord C#4, E#4, G#4, A#4 is displayed

  Scenario: Passing the quality Diminished 7th and the note C#7 to the Chord command
    Given I have a quality Diminished 7th and a note C#7
    When I call the chord command
    Then The correct chord C#7, E7, G7, Bb7 is displayed