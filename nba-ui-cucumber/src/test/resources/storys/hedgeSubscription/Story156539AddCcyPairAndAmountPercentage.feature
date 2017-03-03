#As an onboarding team, I want to be able to add currency pair and related amount percentage to specific fund and instrument so that ccy pair will be eligible for hedge trade.
#AC1 Add currency pair and amount percentage for hedge trade
#AC2 Error message for invalid percentage
Feature: Release2.0 It Story 156539 Add currency pair and amount percentage for hedge trade

  Background: Login to Reference Data Management and navigate to HEDGE TRADE SUBSCRIPTION page
    #Service available
    Given User login to Reference Data Management
    And User navigates to Client Profile Hedge Trade Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1700_UI_TEST |
    And Click the Subscribe button
    And System is navigated to HEDGE TRADE SUBSCRIPTION page

  @CI @DEV @QA
  Scenario Outline: AC1 Select currency pair and input valid percentage
    Given User click Add button for Hedge Config
    When Select CCY1 and CCY2
      | CCY1 | CCY2 |
      | AUD  | PLN  |
    Then CCY1 and CCY2 is selected successfully
      | CCY1 | CCY2 |
      | AUD  | PLN  |
    When Input Percentage
      | PERCENTAGE   |
      | <PERCENTAGE> |
    Then Percentage is enetered successfully
      | PERCENTAGE   |
      | <PERCENTAGE> |
    And Percentage sign is displayed in UI

    Examples: 
      | PERCENTAGE |
      | 1          |
      | 100        |
      | 67.89      |

  @ManualTest
  Scenario Outline: AC2 Error message for invalid percentage
    Given User click Add button for Hedge Config
    When Input Percentage
      | PERCENTAGE   |
      | <PERCENTAGE> |
    Then Error message is displayed
      | ERROR   |
      | <ERROR> |

    Examples: 
      | PERCENTAGE | ERROR                              |
      |            | Field is required.                 |
      | 100.01     | The range of percentage is (0,100] |
      | 101        | The range of percentage is (0,100] |
      | -1         | The range of percentage is (0,100] |
