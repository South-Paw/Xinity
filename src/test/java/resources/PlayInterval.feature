Feature: Play Interval Cucumber

  Scenario: Passing the note A4 and the interval Unison to the playInterval command
    Given I have to play a note A4 and an interval Unison
    When I call the playInterval command
    Then The correct notes of A4 and A4 are displayed

  Scenario: Passing the note B# and the interval Minor Third to the playInterval command
    Given I have to play a note C# and an interval Minor 3rd
    When I call the playInterval command
    Then The correct notes of C#4 and E4 are displayed

  Scenario: Passing the note D and the interval Augmented 5th to the playInterval command
    Given I have to play a note D and an interval Augmented 5th
    When I call the playInterval command
    Then The correct notes of D4 and A#4 are displayed

  Scenario: Passing the note E6 and the interval Diminished Ninth to the playInterval command
    Given I have to play a note E6 and an interval Diminished Ninth
    When I call the playInterval command
    Then The correct notes of E6 and Fb7 are displayed

  Scenario: Passing the note F# and the interval Perfect 11th to the playInterval command
    Given I have to play a note F# and an interval Perfect 11th
    When I call the playInterval command
    Then The correct notes of F#4 and B5 are displayed

  Scenario: Passing the note G1 and the interval Major Thirteenth to the playInterval command
    Given I have to play a note G1 and an interval Major Thirteenth
    When I call the playInterval command
    Then The correct notes of G1 and E3 are displayed

  Scenario: Passing the note G#6 and the interval Perfect 15th to the playInterval command
    Given I have to play a note G#6 and an interval Perfect 15th
    When I call the playInterval command
    Then The correct notes of G#6 and G#8 are displayed