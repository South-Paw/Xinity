Feature: Function Cucumber

  Scenario: Passing the chord F Seventh and the note Bb into the function command
    Given I have the note F and the quality Seventh and the note Bb
    When I call the function command
    Then The correct function V is displayed

  Scenario: Passing the chord B Half Diminished and the note C into the function command
    Given I have the note B and the quality Half Diminished and the note C
    When I call the function command
    Then The correct function VII is displayed

  Scenario: Passing the chord F Major Seventh and the note Bb into the function command
    Given I have the note F and the quality Major Seventh and the note Bb
    When I call the function command
    Then The correct function Non Functional. is displayed
