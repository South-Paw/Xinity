Feature: Find Scale Midi Cucumber

  Scenario: Find the midi notes of a natural minor scale beginning on middle C
    Given There is an environment
    When I use the command FindScaleMidi with parameters C, natural minor
    Then The output "60, 62, 63, 65, 67, 68, 70, 72" should be displayed

  Scenario: Get natural minor scale beginning on middle C, note entered lowercase
    Given There is an environment
    When I use the command FindScaleMidi with parameters c, natural minor
    Then The output "60, 62, 63, 65, 67, 68, 70, 72" should be displayed

  Scenario: Get natural minor scale beginning on middle C with octave specified
    Given There is an environment
    When I use the command FindScaleMidi with parameters c4, minor
    Then The output "60, 62, 63, 65, 67, 68, 70, 72" should be displayed

  Scenario: Get natural minor scale beginning on A#5
    Given There is an environment
    When I use the command FindScaleMidi with parameters A#5, Natural Minor
    Then The output "82, 84, 85, 87, 89, 90, 92, 94" should be displayed

  Scenario: Get natural minor scale beginning on Cb4
    Given There is an environment
    When I use the command FindScaleMidi with parameters Cb4, Natural Minor
    Then The output "59, 61, 62, 64, 66, 67, 69, 71" should be displayed

  Scenario: Get natural minor scale beginning on C-1
    Given There is an environment
    When I use the command FindScaleMidi with parameters C-1, Natural Minor
    Then The output "0, 2, 3, 5, 7, 8, 10, 12" should be displayed

  Scenario: Get natural minor scale beginning on C##4
    Given There is an environment
    When I use the command FindScaleMidi with parameters C##4, Natural Minor
    Then The output "62, 64, 65, 67, 69, 70, 72, 74" should be displayed

  Scenario: Get natural minor scale beginning on Cx4
    Given There is an environment
    When I use the command FindScaleMidi with parameters Cx4, Natural Minor
    Then The output "62, 64, 65, 67, 69, 70, 72, 74" should be displayed

  Scenario: Get natural minor scale beginning on Abb4
    Given There is an environment
    When I use the command FindScaleMidi with parameters Abb5, Natural Minor
    Then The output "79, 81, 82, 84, 86, 87, 89, 91" should be displayed

  Scenario: Get natural minor scale beginning on C-2
    Given There is an environment
    When I use the command FindScaleMidi with parameters C-2, Natural Minor
    Then The output "[ERROR] Invalid note. Not within the midi range (0 - 127)." should be displayed

  Scenario: Get natural minor scale beginning on G10
    Given There is an environment
    When I use the command FindScaleMidi with parameters G10, Natural Minor
    Then The output "[ERROR] Invalid note. Not within the midi range (0 - 127)." should be displayed

  Scenario: Getting a natural minor scale with invalid input: $
    Given There is an environment
    When I use the command FindScaleMidi with parameters $, Natural Minor
    Then The output "[ERROR] Invalid note. That note is invalid because it is not technically a note!" should be displayed

  Scenario: Getting a natural minor scale with invalid input: H
    Given There is an environment
    When I use the command FindScaleMidi with parameters H, Natural Minor
    Then The output "[ERROR] Invalid note. That note is invalid because it is not technically a note!" should be displayed

  Scenario: Getting a natural minor scale with invalid input: Space
    Given There is an environment
    When I use the command FindScaleMidi with parameters  , Natural Minor
    Then The output "[ERROR] Invalid note. That note is invalid because it is not technically a note!" should be displayed

  Scenario: Get harmonic minor scale beginning on C
    Given There is an environment
    When I use the command FindScaleMidi with parameters C, Harmonic Minor
    Then The output "60, 62, 63, 65, 67, 68, 71, 72" should be displayed

  Scenario: Get harmonic minor scale beginning on C9
    Given There is an environment
    When I use the command FindScaleMidi with parameters C9, Harmonic Minor
    Then The output "120, 122, 123, 125, 127 - The rest of the scale cannot be mapped to midi" should be displayed