#As a database controller, I want to limit the access by roles, so that I can enhance the security
#AC1 Sales Trade can view hedge trade subscription only
#AC2 IT Support can view hedge trade subscription only
#AC3 Middle Office can view/add/update/delete/copyFrom hedge trade subscription
#AC4 Onboarding can view/add/update/delete/copyFrom hedge trade subscription
#AC5 UIF Admin has not access to Hedge Trade feature
Feature: Release2.0 It Story 165694 Fund subscribes hedge trade service

  @ManualTest
  Scenario: AC1 Sales Trade can view hedge trade subscription only
    Given Login Reference Data Management as Sales Trade
    And User navigates to Client Profile Hedge Trade Subscription Page
    When Input valid FUND and INSTRUMENT_NAME
      | FUND | CUST_ID | INSTRUMENT_NAME         |
      | 52C8 | 9119539 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    Then System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Hedge Config is displayed

  @ManualTest
  Scenario: AC2 IT Support can view hedge trade subscription only
    Given Login Reference Data Management as IT Support
    And User navigates to Client Profile Hedge Trade Subscription Page
    When Input valid FUND and INSTRUMENT_NAME
      | FUND | CUST_ID | INSTRUMENT_NAME         |
      | 52C8 | 9119539 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    Then System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Hedge Config is displayed

  @ManualTest
  Scenario: AC3 Middle Office can view/add/update/delete/copyFrom hedge trade subscription
    Given Login Reference Data Management as Middle Office
    And User navigates to Client Profile Hedge Trade Subscription Page
    When Input valid FUND and INSTRUMENT_NAME
      | FUND | CUST_ID | INSTRUMENT_NAME         |
      | 52C8 | 9119539 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    Then System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Hedge Config is displayed
    When Input valid FUND and INSTRUMENT_NAME
      | FUND | CUST_ID | INSTRUMENT_NAME         |
      | 52C8 | 9119539 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    And User add/update/delete hedge trade subscription
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    Then Updated Hedge Config is displayed

  @ManualTest
  Scenario: AC3 Onboarding can view/add/update/delete/copyFrom hedge trade subscription
    Given Login Reference Data Management as Onboarding
    And User navigates to Client Profile Hedge Trade Subscription Page
    When Input valid FUND and INSTRUMENT_NAME
      | FUND | CUST_ID | INSTRUMENT_NAME         |
      | 52C8 | 9119539 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    Then System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Hedge Config is displayed
    When Input valid FUND and INSTRUMENT_NAME
      | FUND | CUST_ID | INSTRUMENT_NAME         |
      | 52C8 | 9119539 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    And User add/update/delete hedge trade subscription
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    Then Updated Hedge Config is displayed
