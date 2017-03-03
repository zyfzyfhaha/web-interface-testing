#Story 120461 As a risk manager, when I check multiple order normal size records and click Fill Down button, So that I can bulk update order normal size to same size
#AC1>
#Given 5 Order Normal Size records are checked
#And  ccy1 size and ccy2 size of first record are 0
#When click Fill Down button and Save button
#Then ccy1 size and ccy2 size of all left 4 records below the first record are updated to 0
#And the Auto_calculate of 4 records are N
#AC2>
#Given 5 Order Normal Size records are checked
#And  ccy1 size and ccy2 size of first record are 1
#When click Fill Down button and Save button
#Then ccy1 size and ccy2 size of all left 4 records below the first record are updated to 1
#And the Auto_calculate of 4 records are Y
#AC3>
#Given 5 Order Normal Size records are checked
#When User uncheck the first record
#And Click Fill Down button and Save Button
#Then ccy1 size and ccy2 size of all 3 records below the first checked record are updated
Feature: Story_222345 Order Normal Size Fill Down

  Background: 
    Given user login to Reference data config page
    Given navigate to Order Normal Size Page

  @CI @DEV @QA @UAT
  Scenario Outline: AC1 Normal Size fill down with 0 (the first line order normal size is 0 & 0)
    #Given user add normal order size data
     # | Pricing Service | CCY1 | CCY2 | CustID  |
     # | SFX             | AUD  | CAD  | 9143265 |
     # | SFX             | PLN  | EUR  | 9143265 |
     # | SFX             | USD  | BRL  | 9143265 |
    Given user prepare normal size query data
      | Pricing Service | <Pricing Service> |
      | FUND            | <FUND>            |
    When user click search to query
    And click the all-check check box
    Then user modify normal size for below data
      | KEY              | <KEY>            |
      | CCY1_NORMAL_SIZE | <ccy1NormalSize> |
      | CCY2_NORMAL_SIZE | <ccy2NormalSize> |
    Then click filldown button and save button
    And verify new normal sizes are saved successfully after click save

    # And the Auto_calculate of 5 records are N
    @zyf
    Examples: 
      | Pricing Service | FUND | KEY         | ccy1NormalSize | ccy2NormalSize |
      | SFX             | F43K | SFX_AUD_CAD | 0              | 0              |
