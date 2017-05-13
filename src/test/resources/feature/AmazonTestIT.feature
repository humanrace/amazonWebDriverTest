Feature: Simple Amazon page test

  Background:

    Given Im opening 'https://www.amazon.co.uk'

  Scenario: Simple Amazon page test

    And Search for 'Harry Potter' and the 'Cursed Child' in section books

    And  Verify that the first items has the title: 'Harry Potter and The Cursed Child - Parts One and Two'

    #And  It has a badge “Best Seller”

    #And  Selected type is Hardcover

    #And the price is £9.00