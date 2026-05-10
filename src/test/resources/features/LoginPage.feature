Feature: Login Page

  @regression @dataFile:env/{env}/data/userData.json
  Scenario: Login with Json data
    Given I am on the login page
    When the user logs in with username "${username}" and password "${password}"
    Then user should see the dashboard header text "Products"
    Then user print address information from "${address}" section of the data file
