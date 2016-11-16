Feature: Quality Cucumber

  Scenario: Passing the function I into the quality command
    Given I have only a function I
    When I call the quality command
    Then The correct chordQuality Major 7th is displayed

  Scenario: Passing the function II into the quality command
    Given I have only a function II
    When I call the quality command
    Then The correct chordQuality Minor 7th is displayed

  Scenario: Passing the function V into the quality command
    Given I have only a function V
    When I call the quality command
    Then The correct chordQuality 7th is displayed

  Scenario: Passing the function VII into the quality command
    Given I have only a function VII
    When I call the quality command
    Then The correct chordQuality Half Diminished is displayed

