#Story 122463 As a middle office, I want to have all the currencies listed in a dropdown so that I don't need to type in a currency when query and add normal sizes
#AC1>Given:A middle office  in normal size screen or add normal size screen When: He clicks the currency1 or currency2 list button Then:He can see the currency1 or currency2 value list
#AC2> Given:A middle office clicks the currency1 or currency2 list button When :He clicks a currency1 or currency2 value Then:The value is selected
#Story 122465 As a middle office, I want to have all the currencies listed in a dropdown so that I don't need to type in a currency when query and add  Order Size Tolerance
#AC1>Given:A middle office  in Order Size Tolerance  screen or add Order Size Tolerance  screen When: He clicks the currency1 or currency2 list button Then:He can see the currency1 or currency2 value list
#AC2> Given:A middle office clicks the currency1 or currency2 list button When :He clicks a currency1 or currency2 value Then:The value is selected
#Story 125373 As a middle office, I want to have all the currencies listed in a dropdown so that I don't need to type in a currency when query and add  Currency Pair Deadline
#AC1>Given:A middle office  in Currency Pair Deadline screen or add Currency Pair Deadline screen When: He clicks the currency1 or currency2 list button Then:He can see the currency1 or currency2 value list
#AC2> Given:A middle office clicks the currency1 or currency2 list button When :He clicks a currency1 or currency2 value Then:The value is selected
@Regression
Feature: Stroy_122463 Story_122465 Story_125373 Use dropdown control for currencies

  Background: 
    Given user logins to Reference data config page

  @CI @DEV @QA @UAT
  Scenario Outline: AC1 user logins into Order Normal Size,Order Size Tolerance,Currency Pair Deadline page ,clicks CCY1 and CCY2 drop down list ,clicks currency pair value
    Given user navigates to <Page> Page
    When user clicks CCY1 drop down list of <Page>
    Then user sees the size of list > 2
    And the value of currency list are same as service result 
    And user clicks a CCY1 value of <Page>
      | value | <CCY1> |
    Then the CCY1 value of <Page> is selected
      | value | <CCY1> |
    When user clicks CCY2 drop down list of <Page>
    Then user sees the size of list > 2 
    And the value of currency list are same as service result 
    And user clicks a CCY2 value of <Page>
      | value | <CCY2> |
    Then the CCY2 value of <Page> is selected
      | value | <CCY2> |

    Examples: 
      | Page                   | CCY1 | CCY2 |
      #Check order normal size page currency
      | Order Normal Size      | AED  | ARS  |
      #Check order size tolerance page currency
      | Order Size Tolerance   | AED  | ARS  |
      #Check currency pair deadline page currency
      | Currency Pair Deadline | AED  | ARS  |

  @CI @DEV @QA @UAT
  Scenario Outline: Ac2 user guides to Order Normal Size,Order Size Tolerance,Currency Pair Deadline add page ,clicks CCY1 and CCY2 drop down list ,clicks currency pair value
    Given user navigates to <Page> Page
    Then user open Add panel
    When user clicks CCY1 drop down list of <Page> Add Panel
    Then user sees the size of list > 2 
    And the value of currency list are same as service result 
    And user clicks a CCY1 value of <Page> Add Panel
      | value | <CCY1> |
    Then the CCY1 value of <Page> Add Panel is selected
      | value | <CCY1> |
    When user clicks CCY2 drop down list of <Page> Add Panel
    Then user sees the size of list > 2 
    And the value of currency list are same as service result 
    And user clicks a CCY2 value of <Page> Add Panel
      | value | <CCY2> |
    Then the CCY2 value of <Page> Add Panel is selected
      | value | <CCY2> |

    Examples: 
      | Page                   | CCY1 | CCY2 |
      #Check order normal size page currency
      | Order Normal Size      | AED  | ARS  |
      #Check order size tolerance page currency
      | Order Size Tolerance   | AED  | ARS  |
      #Check currency pair deadline page currency
      | Currency Pair Deadline | AED  | ARS  |
