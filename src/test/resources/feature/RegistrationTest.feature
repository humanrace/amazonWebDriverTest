#Registration and authorization scenarios
@critical
Feature: Registration

  Scenario Outline: Registration error responses: <scenarioStep>

    When send registration request
      | first_name | last_name | email   | password   |
      | <userName> | Fox       | <email> | <password> |
    Then check response 200
    """
      {
        "isSuccessful":false,
        "message":<message>,
        "result":null,
        "code":<code>,
        "codeList":[<codeList>]
      }
    """
    Examples:
      | scenarioStep                 | email                 | password  | userName | message                                   | code | codeList |
      | Already registered email     | pavel.fokin@amaiz.com | 1qa2ws#ED | Pavel    | ["You have already registered"]           | 4    | 4        |
      | Password less than 6 symbols | humanrace2@mail.ru    | 12345     | Pavel    | {"password":["Invalid password length"]}  | 29   | 29       |
      | First name is blank          | humanrace3@mail.ru    | 12345     |          | {"first_name":["First name is required"]} | 12   | 12       |
################################################################################################################################################

  Scenario Outline: Success authorization: <scenarioStep>

    When send auth request
    """
      {
        "email":"<email>",
        "password":"<password>"
      }
    """
    Then check response 200
    """
      {"data":
         {
           "ssid":"REGEX:AnySsid"
         }
      }
    """
    Examples:
      | scenarioStep | email             | password  |
      | Valid login  | humanrace@mail.ru | 1qa2ws#ED |
################################################################################################

  Scenario Outline: Negative authorization: <scenarioStep>

    When send auth request
    """
      {
        "email":"<email>",
        "password":"<password>"
      }
    """
    Then check response <statusCode>
    """
      {
         "errors":[
            {
               "code":<code>,
               "title":"<title>"
            }
         ]
      }
    """
    Examples:
      | scenarioStep                  | email                   | password   | statusCode | title               | code |
      | Not valid login               | humanrace               | 1qa2ws#ED  | 400        | Invalid email       | 1    |
      | Password, less than 6 symbols | humanrace@mail.ru       | 1qa2w      | 403        | Invalid credentials | 202  |
      | Not valid password            | humanrace@mail.ru       | 1qa2ws#EDs | 403        | Invalid credentials | 202  |
      | Not registered email          | human01race0001@mail.ru | 1qa2w      | 403        | Invalid credentials | 202  |
      | Not valid login and password  | humanrace               | 1qa2ws#E   | 400        | Invalid email       | 1    |
################################################################################################

