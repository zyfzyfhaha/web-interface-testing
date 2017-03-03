#As an onboarding team, I want to be able to input specific VDs, so the tenor will pick up the next available VD
#AC1 Input sepcific tenor dates
Feature: Release2.0 It Story 156540 & 157081 Set up specific dates as tenors

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
  Scenario: AC1 Input sepcific tenor dates
    Given User click Add button for Hedge Config
    And User select Specific Date from Next Available Tenors dropdown list
    When Input Tenor below
      | TENOR             |
      | 03-31,06-30,12-31 |
    Then Tenor is entered successfully
      | TENOR             |
      | 03-31,06-30,12-31 |

  @ManualTest
  Scenario: AC1 Input sepcific tenor dates
    Given User click Add button for Hedge Config
    When User select Specific Date from Next Available Tenors dropdown list
    Then MM-DD is displayed in the text box of tenor dates
    And No tenor warning message below is dispalyed under Tenor
