#Story 120461 As a middle office, I want to auto search fund in the add normal size screen so that I don't need to type the whole fund name
#AC1> Given: middle office has navigated to "Add Normal Order Size" screen, When: he type 2 characters in fund field, Then: there lists out all the funds start with the input characters.
#AC2> Given: there lists out all the funds start with the input characters, When: he click one item in this list, Then: the selected fund is set into Fund field.
@Invalid
Feature: Story_120461 Normal Size Configuration - Auto search fund when add normal size

  @CI @DEV @QA @UAT
  Scenario: 0. Login to Reference data configuration
    Given user login to Reference data config page
    And navigate to Order Normal Size Page
    And user open Add panel

  @CI @DEV
  Scenario Outline: 1. Search fund when input contail blank
    When User input below value in fund field
      | input | <input> |
    Then a list of search result will be listed
      | <Fund> |
    And click on the value in the list
      | Value | <Fund> |
    Then the fund field will be set as <Fund>

    @CI @DEV
    Examples: 
      | input  | Fund             |
      | SHRT I | SHRT INC AGF TOR |

    @QA @UAT
    Examples: 
      | input    | Fund       |
      | CHYAGF T | CHYAGF TOR |

  @CI @DEV
  Scenario: 2. Search fund when get muti result
    When User input below value in fund field
      | input | QSC1 |
    Then a list of search result will be listed
      | QSC10-TOR |
      | QSC11     |
      | QSC13     |
      | QSC14     |
      | QSC15     |
      | QSC19     |
    And click on the value in the list
      | Value | QSC14 |
    Then the fund field will be set as QSC14

  @QA @UAT
  Scenario: 2. Search fund when get muti result
    When User input below value in fund field
      | input | ZX0 |
    Then a list of search result will be listed
      | ZX02     |
      | ZX08-LON |
      | ZX09-LON |
    And click on the value in the list
      | Value | ZX08-LON |
    Then the fund field will be set as ZX08-LON

  @CI @DEV @QA @UAT
  Scenario: 3. Search fund when input contail -
    When User input below value in fund field
      | input | SSY9- |
    Then a list of search result will be listed
      | SSY9-LON |
      | SSY9-TOR |
    And click on the value in the list
      | Value | SSY9-TOR |
    Then the fund field will be set as SSY9-TOR

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
    And user can get Add result after click Save
      | Result | Error!- The fund is not found. |
