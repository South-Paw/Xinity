Feature: Chord Function Cucumber

  Scenario: Passing the function I and the note C into the chord function command
    Given I have the function and note I and C
    When I call the chord function command
    Then The correct quality with chord C Major 7th is displayed

  Scenario: Passing the function IV and the note D into the chord function command
    Given I have the function and note IV and D
    When I call the chord function command
    Then The correct quality with chord G Major 7th is displayed

  Scenario: Passing the function VII and the note F into the chord function command
    Given I have the function and note VII and F
    When I call the chord function command
    Then The correct quality with chord E Half Diminished is displayed
