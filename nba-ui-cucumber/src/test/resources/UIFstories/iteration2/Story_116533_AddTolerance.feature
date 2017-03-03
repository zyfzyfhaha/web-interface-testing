#Story 116533: As a middle office, I want to add the currency pair tiers for different pricing service and currency pairs so that I can have the correct tier for validation
#AC1>Given: middle office has navigated to "Create Order Size Tolerance" screen
#When: he selects pricing service, enter currency pair, range and factor and click save.
#Then: the order size tolerance for the input currency pair has been created successfully.
#AC2>Given: middle office has navigated to "Order Size Tolerance" screen
#When: he clicks Add button and opens "Create Order Size Tolerance" screen
#Then: the first tier ranges of currency 1 and currency 2 are to be defaulted as: start value is 0, end value is 0, factor is 0. First tier range can not be updated or deleted.
#AC3>Given: middle office has navigated to "Order Size Tolerance" screen
#When: he clicks Add button and opens "Create Order Size Tolerance" screen
#Then: the end value of last tier range for each currency is "MAX", and it can't be updated or deleted.
#AC4>Given: middle office has navigated to "Create Order Size Tolerance" screen
#When: he clicks the "Add tier range" button
#Then: a new tier range row is displayed, and start value is the end value of previous tier, factor is default to 0
#AC5>Given: middle office has navigated to "Create Order Size Tolerance" screen
#When: he clicks the "Delete tier range" button
#Then: the tier range is removed from UI.
#AC6>Given: middle office has navigated to "Create Order Size Tolerance" screen
#When: he input a pricing service and currency pair, which has already exists
#Then: there is error message indicates the record already exists.
#AC7> Given: middle office has navigated to "Create Order Size Tolerance" screen
#When: he input an invalid start value or invalid end value or invalid factor (<0)
#Then: there is error message indicating the input is invalid.
#AC8> Given: middle office has navigated to "Create Order Size Tolerance" screen
#When: he selects a pricing service, input correct values, but some tier range is missing and not fully covered the whole range.
#Then: there is error message indicating the tier range is missing.
@Regression
Feature: Story_116533 Order Size Tolerance Configuration- Add tolerance

  Background: Login to Reference data configuration
    Given user login to Reference data config page
    Then Navigate to Order Size Tolerance Page

  @CI @DEV 
  Scenario: Check add page
    When user open Add panel
    Then the Add panel displayed
    And the value of pricing service list should be
      | SFX Street FX            |
      | AIR Automated Income     |
      | HPS Hourly Pricing       |
      | SSH Security Settlements |
    And the default value of first tier and factor should be 0

  @CI @DEV @QA @quick @testNow123
  Scenario: Add new tier should be successful
    When user open Add panel
    And user select and input Pricing Service and currency
      | Pricing service | SSH Security Settlements |
      | CCY1            | MXN                      |
      | CCY2            | RUB                      |
    And tier is setup for CCY1
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | 1000      | 1      |
      | 1000        | 2000      | 2      |
      | 2000        | Max       | 1.2    |
    And tier is setup for CCY2
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | Max       | 1.1    |
    And there is no same record in database
    Then user can get Add result after click Save
      | Result | Order size tolerance is created successfully. |

  Scenario Outline: Add new tier for duplicate
    When user open Add panel
    And user select and input Pricing Service and currency
      | Pricing service | <Pricing service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    And tier is setup for CCY1
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | Max       | 1.2    |
    And tier is setup for CCY2
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | Max       | 1.1    |
    Then user can get Add result after click Save
      | Result | Error!- The record already exists. |

    Examples: 
      | CCY1 | CCY2 | Pricing service |
      | CNY  | USD  | SFX Street FX   |
      | USD  | CNY  | SFX Street FX   |

    @CI @DEV @QA @quick
    Examples: 
      | CCY1 | CCY2 | Pricing service          |
      | MXN  | RUB  | SSH Security Settlements |
      | RUB  | MXN  | SSH Security Settlements |

  Scenario Outline: Add new tier negative Pricing Service and currency
    When user open Add panel
    And user select and input Pricing Service and currency
      | Pricing service | <Pricing service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
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

    @CI @DEV
    Examples: 
      | Pricing service              | CCY1 | CCY2 | Result                               |
      | - Select a Pricing Service - | CNY  | USD  | Error!- Pricing service is required. |
      | SFX Street FX                |      | USD  | Error!- Currency pair is required.   |

    @Mock
    Examples: Mock test for service internal error
      | Pricing service    | CCY1 | CCY2 | Result                                                       |
      | HPS Hourly Pricing | XYZ  | USD  | Error!- Internal server error! Please contact administrator. |

  Scenario Outline: Add new tier negative tier
    When user open Add panel
    And user select and input Pricing Service and currency
      | Pricing service | AIR Automated Income |
      | CCY1            | CNY                  |
      | CCY2            | USD                  |
    And tier is setup for CCY1
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | Max       | 1.1    |
    And tier is setup for CCY2
      | Range Start      | Range End      | Factor       |
      | 0                | 0              | 0            |
      | 0                | <Lel2RangeEnd> | <Lel2Factor> |
      | <Lel3RangeStart> | Max            | 1.2          |
    Then user can get Add result after click Save
      | Result | <Result> |

    @CI
    Examples: range end not equal next range start
      | Lel2RangeEnd | Lel3RangeStart | Lel2Factor | Result                         |
      | 100          | 300            | 1.5        | Error!- Form field is invalid. |

	@CI @DEV
    Examples: range start or range end or factor <0
      | Lel2RangeEnd | Lel3RangeStart | Lel2Factor | Result                         |
      | -100         | 300            | 1.5        | Error!- Form field is invalid. |
      | 100          | -100           | 1.2        | Error!- Form field is invalid. |
      | 100          | 200            | -1.2       | Error!- Form field is invalid. |
