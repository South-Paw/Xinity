Feature: Signature Cucumber

  Scenario: Passing the chord D major to the Signature command
    Given I have a chord D, major
    When I call the show signature command
    Then The correct key signature 2# is displayed

  Scenario: Passing the chord E major to the Signature command
    Given I have a chord Eb, major
    When I call the show signature command
    Then The correct key signature 3b is displayed

  Scenario: Passing the chord E major to the Signature command
    Given I have a chord E, major
    When I call the show signature command
    Then The correct key signature 4# is displayed

  Scenario: Passing the chord F# major to the Signature command
    Given I have a chord F#, major
    When I call the show signature command
    Then The correct key signature 6# is displayed

  Scenario: Passing the chord G major to the Signature command
    Given I have a chord G, major
    When I call the show signature command
    Then The correct key signature 1# is displayed

  Scenario: Passing the chord Ab major to the Signature command
    Given I have a chord Ab, major
    When I call the show signature command
    Then The correct key signature 4b is displayed

  Scenario: Passing the chord A major to the Signature command
    Given I have a chord A, major
    When I call the show signature command
    Then The correct key signature 3# is displayed

  Scenario: Passing the chord Bb major to the Signature command
    Given I have a chord Bb, major
    When I call the show signature command
    Then The correct key signature 2b is displayed

  Scenario: Passing the chord B major to the Signature command
    Given I have a chord B, major
    When I call the show signature command
    Then The correct key signature 5# is displayed

  Scenario: Passing the chord D major with flag to the Signature command
    Given I have flag notes and a chord D, major
    When I call the show signature command with flag
    Then The correct key signature F#, C# is displayed

  Scenario: Passing the chord Eb major with flag to the Signature command
    Given I have flag notes and a chord Eb, major
    When I call the show signature command with flag
    Then The correct key signature Bb, Eb, Ab is displayed

  Scenario: Passing the chord E major to the Signature command
    Given I have flag notes and a chord E, major
    When I call the show signature command with flag
    Then The correct key signature F#, C#, G#, D# is displayed

  Scenario: Passing the chord G major to the Signature command
    Given I have flag notes and a chord G, major
    When I call the show signature command with flag
    Then The correct key signature F# is displayed

  Scenario: Passing the chord Ab major to the Signature command
    Given I have flag notes and a chord Ab, major
    When I call the show signature command with flag
    Then The correct key signature Bb, Eb, Ab, Db is displayed

  Scenario: Passing the chord A major to the Signature command
    Given I have flag notes and a chord A, major
    When I call the show signature command with flag
    Then The correct key signature F#, C#, G# is displayed

  Scenario: Passing the chord Bb major to the Signature command
    Given I have flag notes and a chord Bb, major
    When I call the show signature command with flag
    Then The correct key signature Bb, Eb is displayed

  Scenario: Passing the chord B major to the Signature command
    Given I have flag notes and a chord B, major
    When I call the show signature command with flag
    Then The correct key signature F#, C#, G#, D#, A# is displayed


