#Negative registration scenarios for
Feature: RegistrationNegative

  Scenario Outline: Registration error responses: <scenarioStep>

    And send registration request
    """
      {
        "email": "<email>",
        "password": "<password>",
        "first_name": "<userName>",
        "last_name": "Fox",
        "tz": "Europe/Moscow"
      }
    """
    And check response 400
    """
      {
        "isSuccessful":false,
        "message":["Вы уже зарегистрированы"],
        "result":null,
        "code":4,
        "codeList":[4]
      }
    """

    Examples:

      | scenarioStep                 | email              | password  | userName |
      | already registered email     | humanrace@mail.ru  | 1qa2ws#ED | Pavel    |
      | password less than 6 symbols | humanrace2@mail.ru | 12345     | Pavel    |
      | not valid email              | humanrace3@mail.ru | 12345     | Pavel    |
      | userName is blank            | humanrace3@mail.ru | 12345     |          |