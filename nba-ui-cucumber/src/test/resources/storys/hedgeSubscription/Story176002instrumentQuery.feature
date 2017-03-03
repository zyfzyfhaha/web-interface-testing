#As a onboarding team, I want to be able to search fund , so that the relevant fund names will be represented in a drop down list
#AC1 Instrument whose name start with input characters are searched out
#AC2 Error message for search fund
Feature: Release2.0 It Story 176002 Drop down list for INSTRUMENT

  Background: Login to Reference Data Management and navigate to HEDGE TRADE SUBSCRIPTION page
    #Log into UIF Hedge Subscription page
    Given User login to Reference Data Management
    And User navigates to Client Profile Hedge Trade Subscription Page

  @CI @DEV @QA
  Scenario Outline: AC1 Instrument whose name start with input characters are searched out
    When Input INSTRUMENT value and click search icon
      | INSTRUMENT   |
      | <INSTRUMENT> |
    Then All Instruments start with INSTRUMENT input are displayed in list
      | INSTRUMENT   |
      | <INSTRUMENT> |

    Examples: 
      | INSTRUMENT              | COMMENT                        |
      | SFX_WM_LON_1            | Search out list of instruments |
      | SFX_WM_BOS_1500_UI_TEST | Search out one instrument      |
      | SFX_TEST_NO_VALUE       | Search out no instrument       |

  @ManualTest
  Scenario: AC2 Error message for search fund
    Given No character input in INSTRUMENT text box
    When Click on the ! sign in the INSTRUMENT text box
    Then Error message below is displayed
      | ERROR              |
      | Field is required. |
