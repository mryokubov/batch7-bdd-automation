Feature: Test Login functionality

  Background: Login functionality
    Given user launches the "chrome" browser
    When user is on the login page

  Scenario: Positive scenario for login functionality
    When user enters "Tester" and "test" credentials
    And user clicks on login button
    Then user should be navigated to the dashboard

  Scenario: Negative scenario for login functionality
    When user enters "Tester" and "test123" credentials
    And user clicks on login button
    Then user should not be able to log in







