Feature: Natural Minor Scales Cucumber

  Scenario: Get natural minor scale beginning on middle C
    Given There is an environment
    When I use FindScale with C4, Natural Minor as the parameter
    Then The output "C4, D4, Eb4, F4, G4, Ab4, Bb4, C5" should be displayed

  Scenario: Get natural minor scale beginning on middle C, note entered lowercase
    Given There is an environment
    When I use FindScale with c4, Natural Minor as the parameter
    Then The output "C4, D4, Eb4, F4, G4, Ab4, Bb4, C5" should be displayed

  Scenario: Get natural minor scale beginning on middle C with octave specified
    Given There is an environment
    When I use FindScale with C4, natural Minor as the parameter
    Then The output "C4, D4, Eb4, F4, G4, Ab4, Bb4, C5" should be displayed

  Scenario: Get natural minor scale beginning on A#5
    Given There is an environment
    When I use FindScale with A#5, Natural Minor as the parameter
    Then The output "A#5, B#5, C#6, D#6, E#6, F#6, G#6, A#6" should be displayed

  Scenario: Get natural minor scale beginning on G#5
    Given There is an environment
    When I use FindScale with G#5, Natural Minor as the parameter
    Then The output "G#5, A#5, B5, C#6, D#6, E6, F#6, G#6" should be displayed

  Scenario: Get natural minor scale beginning on Cb4
    Given There is an environment
    When I use FindScale with Cb4, Natural Minor as the parameter
    Then The output "Cb4, Db4, Ebb4, Fb4, Gb4, Abb4, Bbb4, Cb5" should be displayed

  Scenario: Get natural minor scale beginning on C-1
    Given There is an environment
    When I use FindScale with C-1, Natural Minor as the parameter
    Then The output "C-1, D-1, Eb-1, F-1, G-1, Ab-1, Bb-1, C0" should be displayed

  Scenario: Get natural minor scale beginning on C##4
    Given There is an environment
    When I use FindScale with C##4, Natural Minor as the parameter
    Then The output "Cx4, Dx4, E#4, Fx4, Gx4, A#4, B#4, Cx5" should be displayed

  Scenario: Get natural minor scale beginning on Cx4
    Given There is an environment
    When I use FindScale with Cx4, Natural Minor as the parameter
    Then The output "Cx4, Dx4, E#4, Fx4, Gx4, A#4, B#4, Cx5" should be displayed

  Scenario: Get natural minor scale beginning on Abb4
    Given There is an environment
    When I use FindScale with Abb4, Natural Minor as the parameter
    Then The output "Abb4, Bbb4, Cbb5, Dbb5, Ebb5, Fbb5, Gbb5, Abb5" should be displayed

  Scenario: Get natural minor scale beginning on C-2
    Given There is an environment
    When I use FindScale with C-2, Natural Minor as the parameter
    Then The output "[ERROR] Invalid note. Not within the midi range (0 - 127)." should be displayed

  Scenario: Get natural minor scale beginning on G10
    Given There is an environment
    When I use FindScale with G10, Natural Minor as the parameter
    Then The output "[ERROR] Invalid note. Not within the midi range (0 - 127)." should be displayed

  Scenario: Getting a natural minor scale with invalid input: $
    Given There is an environment
    When I use FindScale with $, Natural Minor as the parameter
    Then The output "[ERROR] Invalid note. That note is invalid because it is not technically a note!" should be displayed

  Scenario: Getting a natural minor scale with invalid input: H
    Given There is an environment
    When I use FindScale with H, Natural Minor as the parameter
    Then The output "[ERROR] Invalid note. That note is invalid because it is not technically a note!" should be displayed

  Scenario: Getting a natural minor scale with invalid input: Space
    Given There is an environment
    When I use FindScale with  , Natural Minor as the parameter
    Then The output "[ERROR] Invalid note. That note is invalid because it is not technically a note!" should be displayed

  Scenario: Getting a natural minor scale with invalid octave: #
    Given There is an environment
    When I use FindScale with C$, Natural Minor as the parameter
    Then The output "[ERROR] Invalid note. That note is invalid because it is not technically a note!" should be displayed

  Scenario: Find the midi notes of a natural minor scale beginning on middle C
    Given There is an environment
    When I use the command FindScaleMidi with parameters C, natural minor
    Then The output "60, 62, 63, 65, 67, 68, 70, 72" should be displayed

  Scenario: Find the midi notes of a natural minor scale beginning on middle C with octave specified
    Given There is an environment
    When I use the command FindScaleMidi with parameters C4, Natural minor
    Then The output "60, 62, 63, 65, 67, 68, 70, 72" should be displayed

  Scenario: Play natural minor scale beginning on middle C
    Given There is an environment
    When I use the command PlayScale with parameters C4, Minor
    Then The output "C4, D4, Eb4, F4, G4, Ab4, Bb4, C5" should be displayed

  Scenario: Play natural minor scale beginning on middle C with octave specified
    Given There is an environment
    When I use the command PlayScale with parameters C4, Minor
    Then The output "C4, D4, Eb4, F4, G4, Ab4, Bb4, C5" should be displayed