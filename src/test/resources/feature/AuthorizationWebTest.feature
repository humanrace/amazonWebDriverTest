#Web authorization scenarios for https://eu.iqoption.com/en
@web @critical
Feature: Web authorization smoke test

  Scenario Outline: Web Successful login: <scenarioStep>
    Then enter login '<email>' and password '<password>'
    And click login and check profile '<profileName>' opened

    Examples:
      | scenarioStep     | email             | password  | profileName |
      | Successful login | humanrace@mail.ru | 1qa2ws#ED | Павел Фокин |
################################################################################################

  Scenario Outline: Invalid email: <scenarioStep>
    Then enter login '<email>' and password '<password>'
    And click login
    And check email error message '<errorMessage>'

    Examples:
      | scenarioStep    | email     | password  | errorMessage   |
      | Not valid login | humanrace | 1qa2ws#ED | Invalid e-mail |
################################################################################################

  Scenario Outline: Invalid login: <scenarioStep>
    Then enter login '<email>' and password '<password>'
    And click login
    And check login error message '<errorMessage>'

    Examples:
      | scenarioStep                  | email                            | password   | errorMessage              |
      | Password, less than 6 symbols | humanrace@mail.ru                | 1qa2ws     | Invalid login or password |
      | Not valid password            | humanrace@mail.ru                | 1qa2ws#EDD | Invalid login or password |
      | Not registered email          | notExistedEmailReally001@mail.ru | 1qa2ws#ED  | Invalid login or password |
################################################################################################