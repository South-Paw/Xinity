Feature: Find Scale Cucumber

  Scenario: Passing the note C and the scaleType pentatonic major
    Given I have a noteString C#4 and a scaleType pentatonic major
    When I call the findScale command
    Then The output C#4, D#4, F4, G#4, A#4, C#5 is displayed

  Scenario: Passing the note D and the scaleType pentatonic major
    Given I have a noteString D4 and a scaleType pentatonic major
    When I call the findScale command
    Then The output D4, E4, F#4, A4, B4, D5 is displayed

  Scenario: Passing the note E6 and the scaleType pentatonic major
    Given I have a noteString E6 and a scaleType pentatonic major
    When I call the findScale command
    Then The output E6, F#6, G#6, B6, C#7, E7 is displayed

  Scenario: Passing the note B# and the scaleType pentatonic major
    Given I have a noteString B# and a scaleType pentatonic major
    When I call the findScale command
    Then The output [ERROR] Note does not have a corresponding scale. is displayed

  Scenario: Passing the note F4 and the scaleType pentatonic major
    Given I have a noteString F4 and a scaleType pentatonic major
    When I call the findScale command
    Then The output F4, G4, A4, C5, D5, F5 is displayed

  Scenario: Passing the note Eb and the scaleType pentatonic major
    Given I have a noteString Eb4 and a scaleType penta maj
    When I call the findScale command
    Then The output Eb4, F4, G4, Bb4, C5, Eb5 is displayed

  Scenario: Passing the note A# and the scaleType pentatonic major
    Given I have a noteString A#4 and a scaleType pentatonic minor
    When I call the findScale command
    Then The output A#4, C#5, D#5, F5, G#5, A#5 is displayed

  Scenario: Passing the note Gb-1 and the scaleType pentatonic minor
    Given I have a noteString Gb-1 and a scaleType pentatonic min
    When I call the findScale command
    Then The output Gb-1, A-1, B-1, Db0, E0, Gb0 is displayed

  Scenario: Passing the note Gb-1 and the scaleType major
    Given I have a noteString Gb-1 and a scaleType major
    When I call the findScale command
    Then The output Gb-1, Ab-1, Bb-1, Cb0, Db0, Eb0, F0, Gb0 is displayed

  Scenario: Passing the note Gb4 and the scaleType pentatonic minor
    Given I have a noteString Gb4 and a scaleType pentatonic min
    When I call the findScale command
    Then The output Gb4, A4, B4, Db5, E5, Gb5 is displayed

  Scenario: Passing the note C and the scaleType harmonic minor
    Given I have a noteString C and a scaleType harmonic minor
    When I call the findScale command
    Then The output C, D, Eb, F, G, Ab, B, C is displayed

  Scenario: Passing the note C#4 and the scaleType harmonic minor
    Given I have a noteString C#4 and a scaleType harmonic minor
    When I call the findScale command
    Then The output C#4, D#4, E4, F#4, G#4, A4, B#4, C#5 is displayed

  Scenario: Passing the note Db and the scaleType harmonic minor
    Given I have a noteString Db and a scaleType harmonic minor
    When I call the findScale command
    Then The output Db, Eb, Fb, Gb, Ab, Bbb, C, Db is displayed

  Scenario: Passing the note D and the scaleType harmonic minor
    Given I have a noteString D and a scaleType harmonic minor
    When I call the findScale command
    Then The output D, E, F, G, A, Bb, C#, D is displayed

  Scenario: Passing the note D# and the scaleType harmonic minor
    Given I have a noteString D# and a scaleType harmonic minor
    When I call the findScale command
    Then The output D#, E#, F#, G#, A#, B, Cx, D# is displayed

  Scenario: Passing the note D# and the scaleType harmonic minor
    Given I have a noteString D# and a scaleType harmonic minor
    When I call the findScale command
    Then The output D#, E#, F#, G#, A#, B, Cx, D# is displayed

  Scenario: Passing the note C9 and the scaleType harmonic minor
    Given I have a noteString C9 and a scaleType harmonic minor
    When I call the findScale command
    Then The output C9, D9, Eb9, F9, G9 - The rest of the scale cannot be mapped to midi is displayed