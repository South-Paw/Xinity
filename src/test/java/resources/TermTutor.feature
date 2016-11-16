Feature: Term Tutor Cucumber

  Scenario: Running a term tutor test with the parameters not containing an identifier
    Given I want to run a term tutor test
    When The command is executed with the invalid parameters 10
    Then A sensible error should be displayed

  Scenario: Running a term tutor test with the parameters not containing an identifier or number
    Given I want to run a term tutor test
    When The command is executed with the invalid parameters test
    Then A sensible error should be displayed

  Scenario: Running a term tutor test with the parameters not containing a number
    Given I want to run a term tutor test
    When The command is executed with the invalid parameters xtest
    Then A sensible error should be displayed

  Scenario: Running a term tutor test with the parameters containing an invalid number
    Given I want to run a term tutor test
    When The command is executed with the invalid parameters x0
    Then A sensible error should be displayed