#As an onboarding team, I want to be able to see a default page if fund hasn't subscribed hedge trade service under specific instrument, so that I could subscribe hedge trade service for that fund and instrument.
#AC1 See a default page if fund hasn't subscribed hedge trade
Feature: Release2.0 It Story 152469 The default page of hedge trade subscription

  Background: Login to Reference Data Management
    #Service available
    Given User login to Reference Data Management

  @Duplicate_With_Case_In_Story156542_Save_Hedge
  Scenario: AC1 See a default page if fund hasn't subscribed hedge trade
    Given User navigates to Client Profile Hedge Trade Subscription Page
    When Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1700_UI_TEST |
    And Click the Subscribe button
    Then System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Textbox of Fund Booking Code is displayed
    And Add button is displayed
    And No Hedge Trade Configuration is displayed
