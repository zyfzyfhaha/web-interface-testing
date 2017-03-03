#Story 120461 As a middle office, I want to auto search fund in the add normal size screen so that I don't need to type the whole fund name
#AC1> Given: middle office has navigated to "Add Normal Order Size" screen, When: he type 2 characters in fund field, Then: there lists out all the funds start with the input characters.
#AC2> Given: there lists out all the funds start with the input characters, When: he click one item in this list, Then: the selected fund is set into Fund field.
#Story 128020 As a middle office, I want the fund search result includes both short name and customer number when adding normal sizes
#AC1> Given type a fund into fund field, when click search button, a list of fund is searched out with both fund short name and customer number.
#AC2> Given a fund list, when select a fund, then one record is selected and only short name is displayed in fund field.
#AC3> Given a selected fund, when click save button, then normal size can be saved with customer number.
#AC4> Given a selected fund, when modify fund short name, then the save button is disabled.
#AC5> Given type a blank into fund field, when click search button, then no fund is search out.
@Regression
Feature: Story_128020 Normal Size Configuration: Display both fund name and customer number when search fund in add normal size screen

  @CI @DEV @QA @UAT @quick
  Scenario: 0. Login to Reference data configuration
    Given user login to Reference data config page
    When navigate to Order Normal Size Page
    Then user open Add panel

  Scenario Outline: 1. Search fund when input contail blank
    When User input below value in fund field
      | input | <input> |
    Then a list of search result will be listed
      | <Fund> | <CustID> |
    And click on the value in the list
      | Value | <Fund> |
    Then the fund field will be set as <Fund>

    @CI @DEV
    Examples: 
      | input  | Fund             | CustID  |
      | SHRT I | SHRT INC AGF TOR | 9136866 |

    @QA @UAT
    Examples: 
      | input    | Fund       | CustID  |
      | CHYAGF T | CHYAGF TOR | 9184276 |

  @CI @DEV
  Scenario: 2. Search fund when get muti result
    When User input below value in fund field
      | input | QSC1 |
    Then a list of search result will be listed
      | QSC10-TOR | 9100374 |
      | QSC11     | 9100376 |
      | QSC13     | 9100380 |
      | QSC14     | 9100381 |
      | QSC15     | 9100383 |
      | QSC19     | 9100389 |
    And click on the value in the list
      | Value | QSC14 |
    Then the fund field will be set as QSC14

  @QA @UAT
  Scenario: 2. Search fund when get muti result
    When User input below value in fund field
      | input | ZX0 |
    Then a list of search result will be listed
      | ZX02     | 9106138 |
      | ZX08-LON | 9133595 |
      | ZX09-LON | 9133596 |
    And click on the value in the list
      | Value | ZX08-LON |
    Then the fund field will be set as ZX08-LON

  @CI @DEV @QA @UAT @quick
  Scenario: 3. Search fund when input contail char and verify save button
    When User input below value in fund field
      | input | SSY9- |
    Then a list of search result will be listed
      | SSY9-LON | 9124846 |
      | SSY9-TOR | 9100717 |
    And click on the value in the list
      | Value | SSY9-TOR |
    Then the fund field will be set as SSY9-TOR
    And the save button is enabled
    Then User input below value in fund field
      | addition | ABC |
    And the save button is disabled

  @CI @DEV @QA @UAT
  Scenario: 4. Search fund when input is invalid, save failed
    When user input fund and normal_size for currency pair
      | Pricing service | SSH |
      | CCY1            | CNY |
      | CCY2            | CAD |
      | CCY1_NormalSize | 0   |
      | CCY2_NormalSize | 0   |
    And User input below value in fund field
      | input | SSY9-MMM |
    Then a list of search result will be listed
      | @Empty |
    And the save button is disabled
      
   @CI @DEV @QA @UAT
  Scenario: 5. Search fund when input is blank
    When User input below value in fund field
      | input | @Empty |
    Then a list of search result will be listed
      | @Empty |
