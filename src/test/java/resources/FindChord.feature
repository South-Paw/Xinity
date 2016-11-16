Feature: Find Chord Cucumber

  Scenario: Passing the notes relating to C Major
    Given I have the three notes C, E, G
    When I call the findChord command with three notes and without the inversion flag
    Then The correct name for the chord C Major is displayed

  Scenario: Passing the notes relating to C Major with the inversion flag
    Given I have the three notes C, E, G
    When I call the findChord command with three notes and the inversion flag
    Then The correct name for the chord C Major Root Position is displayed

  Scenario: Passing the inverted notes relating to C Major
    Given I have the three notes E, G, C
    When I call the findChord command with three notes and without the inversion flag
    Then The correct name for the chord C Major is displayed

  Scenario: Passing the inverted notes relating to C Major with the inversion flag
    Given I have the three notes E, G, C
    When I call the findChord command with three notes and the inversion flag
    Then The correct name for the chord C Major 1st Inversion is displayed

  Scenario: Passing the inverted notes relating to C Minor
    Given I have the three notes G, C, Eb
    When I call the findChord command with three notes and without the inversion flag
    Then The correct name for the chord C Minor is displayed

  Scenario: Passing the inverted notes relating to C Minor with the inversion flag
    Given I have the three notes G, C, Eb
    When I call the findChord command with three notes and the inversion flag
    Then The correct name for the chord C Minor 2nd Inversion is displayed

  Scenario: Passing the inverted notes relating to D# Major
    Given I have the three notes A#, D#, Fx
    When I call the findChord command with three notes and without the inversion flag
    Then The correct name for the chord D# Major is displayed

  Scenario: Passing the inverted notes relating to D# Major with the inversion flag
    Given I have the three notes A#, D#, Fx
    When I call the findChord command with three notes and the inversion flag
    Then The correct name for the chord D# Major 2nd Inversion is displayed

  Scenario: Passing the inverted notes relating to Fb Minor
    Given I have the three notes Abb, Cb, Fb
    When I call the findChord command with three notes and without the inversion flag
    Then The correct name for the chord Fb Minor is displayed

  Scenario: Passing the inverted notes relating to Fb Minor with the inversion flag
    Given I have the three notes Abb, Cb, Fb
    When I call the findChord command with three notes and the inversion flag
    Then The correct name for the chord Fb Minor 1st Inversion is displayed

  Scenario: Passing the notes relating to E Minor 6th and  C# Half Diminished without the inversion flag
    Given I have the four notes E, G, B, C#
    When I call the findChord command with four notes and without the inversion flag
    Then The correct name for the chord E Minor 6th, C# Half Diminished is displayed

  Scenario: Passing the inverted notes relating to C# Half Diminished, E Minor 6th without the inversion flag
    Given I have the four notes G, B, C#, E
    When I call the findChord command with four notes and without the inversion flag
    Then The correct name for the chord C# Half Diminished, E Minor 6th is displayed

  Scenario: Passing the notes relating to C Major 6th and A Minor 7th without the inversion flag
    Given I have the four notes C, E, G, A
    When I call the findChord command with four notes and without the inversion flag
    Then The correct name for the chord C Major 6th, A Minor 7th is displayed

  Scenario: Passing the notes relating to C Major 6th and A Minor 7th with the noEnharmonic flag
    Given I have the four notes C, E, G, A
    When I call the findChord command with four notes and with the noEnharmonic flag
    Then The correct name for the chord C Major 6th is displayed

  Scenario: Passing the notes relating to C Half Diminished and Eb Minor 6th without the inversion flag
    Given I have the four notes C, Eb, Gb, Bb
    When I call the findChord command with four notes and without the inversion flag
    Then The correct name for the chord C Half Diminished, Eb Minor 6th is displayed

  Scenario: Passing the notes relating to C Augmented Triad, E Augmented Triad, G# Augmented Triad without the inversion flag
    Given I have the three notes C, E, G#
    When I call the findChord command with three notes and without the inversion flag
    Then The correct name for the chord C Augmented Triad, E Augmented Triad, G# Augmented Triad is displayed

  Scenario: Passing the notes relating to C Augmented Triad without the inversion flag and with noEnharmonic flag
    Given I have the three notes C, E, G#
    When I call the findChord command with three notes and without the inversion flag and with the noEnharmonic flag
    Then The correct name for the chord C Augmented Triad is displayed

  Scenario: Passing the inverted notes relating to C Augmented Triad with the inversion flag and with noEnharmonic flag
    Given I have the three notes E, G#, C
    When I call the findChord command with three notes and with the inversion flag and with the noEnharmonic flag
    Then The correct name for the chord C Augmented Triad 1st Inversion is displayed