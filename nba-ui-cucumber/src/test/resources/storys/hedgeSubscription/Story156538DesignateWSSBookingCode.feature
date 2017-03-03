#As an onboarding team, I want to be able to input WSS Booking Code for hedge trade so that the hedge trade will be booked to it on WSS.
#AC1 Input WSS Booking Code for hedge trade
Feature: Release2.0 It Story 156538 Designate WSS Booking Code for hedge trade

  Background: Login to Reference Data Management and navigate to HEDGE TRADE SUBSCRIPTION page
    #Service available
    Given User login to Reference Data Management
    And User navigates to Client Profile Hedge Trade Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1700_UI_TEST |
    And Click the Subscribe button
    And System is navigated to HEDGE TRADE SUBSCRIPTION page

  @Duplicate_With_Case_In_Story156542_Save_Hedge
  Scenario: AC1 Designate WSS Booking Code for hedge trade
    Given User click Add button for Hedge Config
    When Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 9T19              |
    Then Fund Booking Code below is displayed
      | FUND_BOOKING_CODE |
      | 9T19              |
