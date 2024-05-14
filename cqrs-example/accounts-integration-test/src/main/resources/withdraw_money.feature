Feature: Withdraw Money from Account

  Scenario: Successful withdrawal of money from account
    Given an account with id "123e4567-e89b-12d3-a456-556642440001"
    And a deposit request with amount "200"
    When the deposit endpoint is called
    Then the deposit is successful
    And a withdrawal request with amount "100"
    When the withdrawal endpoint is called
    Then the withdrawal is successful
    And the balance of the account is "100"
