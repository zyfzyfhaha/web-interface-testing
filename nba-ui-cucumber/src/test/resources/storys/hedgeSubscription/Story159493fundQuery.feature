#As a onboarding team, I want to be able to search fund , so that the relevent fund names will be represented in a drop down list
#AC1 Fund whose short name start with input characters are searched out
#AC2 Error message for search fund
Feature: Release2.0 It11 Story 15949 Drop down list for FUND

  Background: Login to Reference Data Management and navigate to HEDGE TRADE SUBSCRIPTION page
    #Log into UIF Hedge Subscription page
    Given User login to Reference Data Management
    And User navigates to Client Profile Hedge Trade Subscription Page

  @CI @DEV @QA
  Scenario Outline: AC1 Fund whose short name start with input characters are searched out
    When Input FUND value and click search icon
      | FUND   |
      | <FUND> |
    Then All active Funds start with FUND input are displayed in list
      | FUND   |
      | <FUND> |

    Examples: 
      | FUND       | COMMENT                  |
      | 52C        | Search out list of funds |
      | 52C8       | Search out one fund      |
      | 52C8XXTEST | Search out no fund       |

  @ManualTest
  Scenario: AC2 Error message for search fund
    Given No character input in FUND text box
    When Click on the ! sign in the FUND text box
    Then Error message below is displayed
      | ERROR              |
      | Field is required. |
