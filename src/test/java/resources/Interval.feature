Feature: Interval Cucumber

  Scenario: Passing the note C4 and the interval Unison to the interval command
    Given I have note C4 and an interval Unison
    When I call the interval command
    Then The correct note of C4 is displayed

  Scenario: Passing the note A and the interval Major 3rd to the interval command
    Given I have note A and an interval Major 3rd
    When I call the interval command
    Then The correct note of C# is displayed

  Scenario: Passing the note G#5 and the interval Minor Seventh to the interval command
    Given I have note G#5 and an interval Minor Seventh
    When I call the interval command
    Then The correct note of F#6 is displayed

  Scenario: Passing the note Bb and the interval Octave to the interval command
    Given I have note Bb and an interval Octave
    When I call the interval command
    Then The correct note of Bb is displayed

  Scenario: Passing the note E1 and the interval Perfect Fifteenth to the interval command
    Given I have note E1 and an interval Perfect Fifteenth
    When I call the interval command
    Then The correct note of E3 is displayed

