#As an onboarding team, I want an interface where I can input OMS customer ID and instrument, so that I can search the currently hedge setting
#AC1 Search the hedge trade setting with valid cust_id and instrument
Feature: Release2.0 It Story 152466 & 177397 Fund subscribes hedge trade service

  Background: Login to Reference Data Management
    #Service available
    Given User login to Reference Data Management

  @CI @DEV @QA
  Scenario: AC1 Input valid cust_id and instrument
    Given User navigates to Client Profile Hedge Trade Subscription Page
    When Input valid FUND and INSTRUMENT_NAME
      | FUND | CUST_ID | INSTRUMENT_NAME         |
      | 52C8 | 9119539 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    Then System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Fund is FUND input input earlier
      | FUND |
      | 52C8 |
    And Customer ID is CUST_ID input earlier
      | CUST_ID |
      | 9119539 |
    And Instrument Name is INSTRUMENT_NAME input earlier
      | INSTRUMENT_NAME         |
      | SFX_WM_BOS_1500_UI_TEST |
