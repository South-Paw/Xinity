Feature: Semitones Interval Cucumber
  
  Scenario: Passing the interval Unison to the semitonesInterval command
    Given I have a given interval Unison
    When I call the semitonesInterval command
    Then The correct response of Unison contains 0 semitones. is displayed

  Scenario: Passing the interval Major Third to the semitonesInterval command
    Given I have a given interval Major third
    When I call the semitonesInterval command
    Then The correct response of Major third contains 4 semitones. is displayed

  Scenario: Passing the interval Diminished 6th to the semitonesInterval command
    Given I have a given interval Diminished 6th
    When I call the semitonesInterval command
    Then The correct response of Diminished 6th contains 7 semitones. is displayed

  Scenario: Passing the interval Octave to the semitonesInterval command
    Given I have a given interval Octave
    When I call the semitonesInterval command
    Then The correct response of Octave contains 12 semitones. is displayed

  Scenario: Passing the interval Augmented 9th to the semitonesInterval command
    Given I have a given interval Augmented 9th
    When I call the semitonesInterval command
    Then The correct response of Augmented 9th contains 15 semitones. is displayed

  Scenario: Passing the interval Perfect twelfth to the semitonesInterval command
    Given I have a given interval Perfect twelfth
    When I call the semitonesInterval command
    Then The correct response of Perfect twelfth contains 19 semitones. is displayed

  Scenario: Passing the interval Minor 14th to the semitonesInterval command
    Given I have a given interval Minor 14th
    When I call the semitonesInterval command
    Then The correct response of Minor 14th contains 22 semitones. is displayed

  Scenario: Passing the interval Perfect fifteenth to the semitonesInterval command
    Given I have a given interval Perfect fifteenth
    When I call the semitonesInterval command
    Then The correct response of Perfect fifteenth contains 24 semitones. is displayed