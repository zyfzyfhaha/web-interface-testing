#Story 116536: As a middle office, I want to add minimum sizes for currencies so that I can apply the minimum size in the validation
#AC1> Given: middle office navigate to "Create Order Size Tolerance" screen
#When: he inputs minimum size for ccy1 and ccy2 and other mandatory fields and clicks save
#Then: the minimum sizes for ccy1 and ccy2 are saved.
#AC2> Given: middle office navigate to "Create Order Size Tolerance" screen
#When: he inputs an invalid minimum size (<0) for ccy1 and ccy2 and other mandatory fields and clicks save
#Then: there is error message indicating the minimum sizes are invalid.
@Regression
Feature: Story_116536 Minimum Size Configuration - Add minimum size for a currency pair

  Background: Login to Reference data configuration
    Given user login to Reference data config page
    Then Navigate to Order Size Tolerance Page

  @CI @DEV @QA @quick
  Scenario Outline: Add Minimum Size should be successful
    When user open Add panel
    And user select and input Pricing Service and currency
      | Pricing service | SSH Security Settlements |
      | CCY1            | NZD                      |
      | CCY2            | CAD                      |
      | CCY1 Minimum    | <CCY1 Minimum>           |
      | CCY2 Minimum    | <CCY2 Minimum>           |
    And tier is setup for CCY1
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | Max       | 1.2    |
    And tier is setup for CCY2
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | Max       | 1.1    |
    And there is no same record in database
    Then user can get Add result after click Save
      | Result | <Result> |
    And search for Added tolerance, Minimun size is correct
      | Expected CCY1 Minimum | <CCY1 Minimum> |
      | Expected CCY2 Minimum | <CCY2 Minimum> |
    And the Minimun size in detail panel is also correct
      | Expected CCY1 Minimum | <CCY1 Minimum> |
      | Expected CCY2 Minimum | <CCY2 Minimum> |

    Examples: 
      | CCY1 Minimum | CCY2 Minimum | Result                                        |
      | 1,000,000.86 | 2,000,000    | Order size tolerance is created successfully. |

  @CI @DEV
  Scenario Outline: Add minimum size
    When user open Add panel
    And user select and input Pricing Service and currency
      | Pricing service | SSH Security Settlements |
      | CCY1            | NZD                      |
      | CCY2            | RUB                      |
      | CCY1 Minimum    | <CCY1 Minimum>           |
      | CCY2 Minimum    | <CCY2 Minimum>           |
    And tier is setup for CCY1
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | Max       | 1.2    |
    And tier is setup for CCY2
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | Max       | 1.1    |
    Then user can get Add result after click Save
      | Result | <Result> |

    Examples: 
      | CCY1 Minimum | CCY2 Minimum | Result                         |
      | -1000000     | 2000000      | Error!- Form field is invalid. |
      | 1000000      | -2000000     | Error!- Form field is invalid. |
