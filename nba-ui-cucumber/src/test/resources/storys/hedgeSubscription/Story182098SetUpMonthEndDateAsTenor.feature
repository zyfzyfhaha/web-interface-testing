#As a onboarding team, I want to see a month end date option when I subscribe hedge trade, so that I can select the option
Feature: Release2.0 It13 Story 182098 Save hedge trade subscription

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
  Scenario: AC1 Month End Dates are displayed and checked by default
    When User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      |      |      |            | Month End Date       |        |
    Then Month End Dates are displayed and checked by default
      | TENORS                                                                                          |
      | JAN END,FEB END,MAR END,APR END,MAY END,JUN END,JUL END,AUG END,SEP END,OCT END,NOV END,DEC END |
    And No tenor warning message below is dispalyed under Tenor

  @CI @DEV @QA
  Scenario: AC2 Errpr message for invalid value
    When User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | AUD  | PLN  | 50         | Month End Date       | NONE   |
    And User click Save button
    Then Error message is pop up
      | ERROR                         |
      | Error!- Invalid Hedge Config |
    And No tenor warning message below is dispalyed under Tenor
