Feature: Simple Amazon page test

  Background:

    Given Im opening 'https://www.amazon.co.uk'

  Scenario Outline: Simple Amazon page test

    And Search for 'Harry Potter and the Cursed Child' in section books

    And  Verify that the first items has the title: '<title>'

    And  It has a badge '<badge>'

    And  Selected type is '<type>'

    And the price is '<price>'

    Then Navigate to the book details and verify the '<title>', the '<badge>' the '<price>' and the type '<type>'

    And Add the book to the basket

    And Verify that the notification is shown with the title 'Added to Basket'

    And There is '1 item' in the basket

    Then Click on edit the basket

    And Verify that the book is shown on the list

    And The title is '<title>', type of print is '<type>', price is '<price>', quantity is '1' and total price is '<price>'

    Examples:

      | title                                                 | badge       | price | type      |
      | Harry Potter and The Cursed Child - Parts One and Two | Best Seller | £9.00 | Hardcover |