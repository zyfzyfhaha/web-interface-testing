#story 122470
#As a middle office, I want to delete the currency pair deadlines so that I can remove the deadlines not in use
#AC1> Given: middle office selects some currency pair
#When: user clicks delete button
#Then: an warning message is popped up for confirm
#AC2> Given: no currency pair is selected
#When: user clicks delete button
#Then: an warning message is popped up and ask user to select currency pairs.
#AC3> Given: middle office selects 1 currency pair in the currency pair list
#When: user clicks delete button and confirmed yes in the warning message.
#Then: the currency pair is deleted.
#AC4> Given: middle office selects multiple currency pair in the currency pair list
#When: user clicks delete button and confirmed yes in the warning message.
#Then: the currency pairs selected are deleted.
#AC5> Given: middle office selects multiple currency pairs in the currency pair list
#When: user clicks delete button and confirmed no in the warning message.
#Then: no currency pair is deleted.
#AC6> When IT support deletes the currency pairs, error message pops up indicating no access to delete.
@Regression
Feature: Story_122470 Delete currency pair deadline

  Background: Login to Deadline Page
    Given user login to Reference data config page
    And Navigate to Currency Pair Deadline Page

  Scenario Outline: AC1> selects one currency pair, pop up an warning message for confirm,choose cancel
    Given user add some currency pair
      | Pricing Service | CCY1 | CCY2 | Cutoff |
      | SFX             | AUD  | BHD  | 500    |
    And user prepare currency pair deadline query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    And User click search to query
    When user select currency pair to delete for below data
      | KEY | <KEY1> |
    Then pop up confirm message after click delete
      | DELETE | <DELETE> |
    And verify currency pair are not deleted
      | KEY | <KEY1> |

    @CI @DEV @QA @UAT
    Examples: 
      | Pricing_Service | CCY1  | CCY2  | KEY1        | DELETE |
      | SFX             | @Null | @Null | SFX_AUD_BHD | cancel |

  Scenario Outline: AC2> selects one currency pair, pop up an warning message for confirm, choose ok
    Given user add some currency pair
      | Pricing Service | CCY1 | CCY2 | Cutoff |
      | HPS             | AED  | CHF  | 100    |
    And user prepare currency pair deadline query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    And User click search to query
    When user select currency pair to delete for below data
      | KEY | <KEY> |
    Then pop up confirm message after click delete
      | DELETE | ok |
    And verify currency pair are deleted successfully
      | KEY | <KEY> |

    @CI @DEV @QA @UAT @quick
    Examples: 
      | Pricing_Service | CCY1  | CCY2  | KEY         |
      | HPS             | @Null | @Null | HPS_AED_CHF |

  Scenario Outline: AC3> selects multiple currency pair, pop up an warning message for confirm, choose ok
    Given user add some currency pair
      | Pricing Service | CCY1 | CCY2 | Cutoff |
      | HPS             | AED  | CHF  | 100    |
      | HPS             | AED  | BDT  | 200    |
    And user prepare currency pair deadline query data
      | Pricing Service | HPS   |
      | CCY1            | @Null |
      | CCY2            | @Null |
    And User click search to query
    When user select currency pair to delete for below data
      | KEY | <KEY1> |
    And user select currency pair to delete for below data
      | KEY | <KEY2> |
    Then pop up confirm message after click delete
      | DELETE | ok |
    And verify currency pair are deleted successfully
      | KEY1 | <KEY1> |
      | KEY2 | <KEY2> |
    #And user add the deleted currency pair
    
    @CI @DEV @QA @UAT
    Examples: 
      | KEY1        | KEY2        |
      | HPS_AED_CHF | HPS_AED_BDT |

  @CI @DEV @QA @UAT
  Scenario: AC4> selects multiple currency pair, pop up an warning message for confirm, choose ok
    Given user add some currency pair
      | Pricing Service | CCY1 | CCY2 | Cutoff |
      | AIR             | MXN  | CLP  | 100    |
      | AIR             | MXN  | BRL  | 200    |
    And user prepare currency pair deadline query data
      | Pricing Service | AIR   |
      | CCY1            | MXN   |
      | CCY2            | @Null |
    And User click search to query
    When user select all currency pair to delete
    Then pop up confirm message after click delete
      | DELETE | ok |
    And verify currency pair are deleted

  @CI @DEV @QA @UAT
  Scenario: AC5> no currency pair is selected
    Given user prepare currency pair deadline query data
      | Pricing Service | @Null |
      | CCY1            | @Null |
      | CCY2            | @Null |
    When User click search to query
    Then pop up error message after click delete
      | Result | Error!- Please select record(s). |
