#As an onboarding team, I want to be able to input the number of calendar days forward, so the tenor will roll over per the number I input
#AC1 Input the number of calendar days forward
#AC2 Errpr message for invalid value
Feature: Release2.0 It Story 156541 &157081 Set up # of days forward as tenors

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
  Scenario: AC1 Input the number of calendar days forward
    Given User click Add button for Hedge Config
    And User select Interval Days from Next Available Tenors dropdown list
    When Input Tenor below
      | TENOR |
      | 30    |
    Then Tenor is entered successfully
      | TENOR |
      | 30    |

  @ManualTest
  Scenario Outline: AC2 Errpr message for invalid value
    Given User click Add button for Hedge Config
    And User select Interval Days from Next Available Tenors dropdown list
    When Input Tenor below
      | TENOR   |
      | <TENOR> |
    Then Error message is displayed
      | ERROR   |
      | <ERROR> |
    And No tenor warning message below is dispalyed under Tenor

    Examples: 
      | TENOR | ERROR              |
      |       | Field is required. |
      | -1    | invalid tenor days |
      | 0     | invalid tenor days |
