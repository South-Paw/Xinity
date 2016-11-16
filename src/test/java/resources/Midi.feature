Feature: Midi Cucumber

  Scenario: Passing the note C4 to the midi command
    Given I have a note C4
    When I call the midi command
    Then The correct midi number of 60 is displayed