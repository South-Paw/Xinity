Feature: Mode Cucumber

  Scenario: Finding the melodic minor mode of C 2
    Given I want to find the melodic minor mode from C and a degree of 2
    When I call the mode command
    Then The corresponding scale of D Dorian b2 is displayed

  Scenario: Finding the melodic minor mode of C4 2
    Given I want to find the melodic minor mode from C4 and a degree of 2
    When I call the mode command
    Then The corresponding scale of D4 Dorian b2 is displayed

  Scenario: Finding the melodic minor mode of D 5
    Given I want to find the melodic minor mode from D and a degree of 5
    When I call the mode command
    Then The corresponding scale of A Mixolydian b6 is displayed