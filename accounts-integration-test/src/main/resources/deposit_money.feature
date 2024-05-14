Feature: Deposit Money into Account

  Scenario: Successful deposit of money into account
    Given an account with id "123e4567-e89b-12d3-a456-556642440000"
    And a deposit request with amount "100"
    When the deposit endpoint is called
    Then the deposit is successful
    And the balance of the account is "100"
