#Story 129746: Allow 1m/1b input in order tolerance and minimum setup
#AC1> Given user in add order tolerance screen, When user input a valid number end with m or b in range start/end, minimum box, Then the number will be automatically multiply by 1,000,000 or 1,000,000,000
#AC2> Given user in add order tolerance screen, When user input an invalid input end with m or b in range start/end, minimum box, Then the user will get error message when save add result
#AC3> Given user in edit order tolerance screen, When user input a valid number end with m or b in range start/end, minimum box, Then the number will be automatically multiply by 1,000,000 or 1,000,000,000
#AC4> Given user in edit order tolerance screen, When user input an invalid input end with m or b in range start/end, minimum box, Then the user will get error message when save edit result
#@Regression
Feature: Story_116533 Order Size Tolerance Configuration- Add tolerance

  Background: Login to Reference data configuration
    Given user login to Reference data config page
    Then Navigate to Order Size Tolerance Page

  @CI @DEV @QA @quick
  Scenario Outline: AC1 check add panel 1m1b positive
    When user open Add panel
    And user select and input Pricing Service and currency
      | Pricing service | SSH Security Settlements |
      | CCY1            | MXN                      |
      | CCY2            | RUB                      |
      | CCY1 Minimum    | <CCY1 Minimum>           |
      | CCY2 Minimum    | <CCY2 Minimum>           |
    And tier is setup for CCY1
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | <Range>   | 1      |
      | <Range>     | Max       | 1.2    |
    And tier is setup for CCY2
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | Max       | 1.1    |
    And there is no same record in database
    Then user can get Add result after click Save
      | Result | Order size tolerance is created successfully. |
    And search for Added tolerance, Minimun size is correct
      | Expected CCY1 Minimum | <ExpectMin1> |
      | Expected CCY2 Minimum | <ExpectMin2> |
    And the Minimun size in detail panel is also correct
      | Expected CCY1 Minimum | <ExpectMin1> |
      | Expected CCY2 Minimum | <ExpectMin2> |
    And the tire for CCY1 in detail panel is correct
      | Range Start   | Range End     | Factor |
      | 0             | 0             | 0      |
      | 0             | <ExpectRange> | 1      |
      | <ExpectRange> | MAX           | 1.2    |
    And the tire for CCY2 in detail panel is correct
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | MAX       | 1.1    |

    Examples: 
      | CCY1 Minimum | CCY2 Minimum | Range | ExpectMin1 | ExpectMin2 | ExpectRange    |
      | 1M           | 19.5M        | 25B   | 1,000,000  | 19,500,000 | 25,000,000,000 |

  Scenario Outline: AC2 check add panel 1m1b negative
    When user open Add panel
    And user select and input Pricing Service and currency
      | Pricing service | SSH Security Settlements |
      | CCY1            | MXN                      |
      | CCY2            | RUB                      |
      | CCY1 Minimum    | <CCY1 Minimum>           |
      | CCY2 Minimum    | <CCY2 Minimum>           |
    And tier is setup for CCY1
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | <Range>   | 1      |
      | <Range>     | Max       | 1.2    |
    And tier is setup for CCY2
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | Max       | 1.1    |
    And there is no same record in database
    Then user can get Add result after click Save
      | Result | Error!- Form field is invalid. |

    Examples: 
      | CCY1 Minimum | CCY2 Minimum | Range |
      | 1MB          | 19FW.5M      | -25B  |
