#This feature is for Subscription page. Will be impletement once free from other business stories.
Feature: Hedge Trade Susbcription Page Verification

  #Release2.0 It9 Story 152466 Fund subscribes hedge trade service. As an onboarding team, I want to be able to subscribe hedge trade service for specific fund and instrument so that I could manage hedge trade subscription per fund and instrument.
  #Release2.0 It9 Story 152469 The default page of hedge trade subscription. As an onboarding team, I want to be able to see a default page if fund hasn't subscribed hedge trade service under specific instrument, so that I could subscribe hedge trade service for that fund and instrument.
  #Release2.0 It9 Story 152468 The existing information of hedge trade subscription. As an onboarding team, I want to be able to see the existing information of hedge trade subscription if a fund has subscribed it under specific instrument.
  #Release2.0 It9 Story 156538 Designate WSS Booking Code for hedge trade. As an onboarding team, I want to be able to input WSS Booking Code for hedge trade so that the hedge trade will be booked to it on WSS.
  #Release2.0 It11 Story 156539 Add currency pair and amount percentage for hedge trade. As an onboarding team, I want to be able to add currency pair and related amount percentage to specific fund and instrument so that ccy pair will be eligible for hedge trade.
  #Release2.0 It9 Story 156540 Set up specific dates as tenors. As an onboarding team, I want to be able to input specific VDs, so the tenor will pick up the next available VD
  #Release2.0 It11 Story 156541 Set up # of days forward as tenors. As an onboarding team, I want to be able to input the number of calendar days forward, so the tenor will roll over per the number I input
  #Release2.0 It10 Story 156547 Remove ccy pair from hedge trade. As an onboarding team, I want to be able to remove CCY pair. So that  ccy pair will be removed from fund profile
  #Release2.0 It12 Story 157081 Warning message when tenors are updated. As an onboarding team, I want to see a warning message “Note, the tenor change will take effective immediately”, so that I know the risk.
  #Release2.0 It11 Story 159493 UI: Drop down list for "fund". As a onboarding team, I want to be able to search fund , so that the relavent fund names will be represented in a drop down list
  #Release2.0 It11 Story 176002 UI: Drop down list for 'instrument'. As a onboarding team, I want to be able to search instrument, so that the relavent instrument names will be represented in a drop down list
  #Release2.0 It12 Story 177397 UI: Add fund ID on subscription page. As a onboarding team, I want to be able to see the fund I am subscription on subscription page
  #Release2.0 It12 Story 182095 UI: IMM date. As a onboarding team, I want to be able to setup IMM date as value date, so that I will have a option to select it when subscribing hedge trade.
  #Release2.1 It13 Story 182098 UI: Month-end date. As a onboarding team, I want to see a month end date option when I subscribe hedge trade, so that I can select the option
  Background: Login to Reference Data Management and navigate to HEDGE TRADE SUBSCRIPTION page
    #Service available
    Given User login to Reference Data Management
    And User navigates to Client Profile Hedge Trade Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1700_UI_TEST |
    And Click the Subscribe button
    And System is navigated to HEDGE TRADE SUBSCRIPTION page

  @TBD
  Scenario: AC1 Month End Dates are displayed and checked by default
    When User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      |      |      |            | Month End Date       |        |
    Then Month End Dates are displayed and checked by default
      | TENORS                                                                                          |
      | JAN END,FEB END,MAR END,APR END,MAY END,JUN END,JUL END,AUG END,SEP END,OCT END,NOV END,DEC END |
    And No tenor warning message below is dispalyed under Tenor

  @TBD
  Scenario: AC2 Errpr message for invalid value
    When User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | AUD  | PLN  | 50         | Month End Date       | NONE   |
    And User click Save button
    Then Error message is pop up
      | ERROR                        |
      | Error!- Invalid Hedge Config |
    And No tenor warning message below is dispalyed under Tenor
