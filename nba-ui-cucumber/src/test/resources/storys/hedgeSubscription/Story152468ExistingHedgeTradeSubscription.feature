#As an onboarding team, I want to be able to see the existing information of hedge trade subscription if a fund has subscribed it under specific instrument.
#AC1 Search the hedge trade setting with valid cust_id and instrument
Feature: Release2.0 It Story 152468 & 157081 The existing information of hedge trade subscription.

  Background: Login to Reference Data Management
    #Service available
    Given User login to Reference Data Management

  @Duplicate_With_Case_In_Story156542_Save_Hedge
  Scenario: AC1 Search the hedge trade setting with valid cust_id and instrument
    Given User navigates to Client Profile Hedge Trade Subscription Page
    And Hedge Trade Subscirption below are existed in DB
      | CUST_ID | INSTRUMENT_NAME         | CCY1 | CCY2 | Percentage | Next Available Tenors   |
      | 9119539 | SFX_WM_BOS_0900_UI_TEST | AUD  | PLN  | 60         | 30                      |
      | 9119539 | SFX_WM_BOS_0900_UI_TEST | CAD  | PLN  | 30         | 03-31,06-30,09-30,12-31 |
    When Input valid FUND and INSTRUMENT_NAME
      | FUND | CUST_ID | INSTRUMENT_NAME         |
      | 52C8 | 9119539 | SFX_WM_BOS_0900_UI_TEST |
    And Click the Subscribe button
    Then System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Fund Booking Code below is displayed
      | FUND_BOOKING_CODE |
      | 52C8              |
    And Hedge Config below is displayed
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                  |
      | AUD  | PLN  | 60         | Interval Days        | 30                      |
      | CAD  | PLN  | 30         | Specific Date        | 03-31,06-30,09-30,12-31 |
    And Tenor warning message below is dispalyed under Tenor
      | WARNING                                                 |
      | Note: the tenor change will take effective immediately. |
