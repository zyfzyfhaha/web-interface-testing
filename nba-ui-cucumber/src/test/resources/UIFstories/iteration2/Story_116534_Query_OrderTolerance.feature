#story 116534
#As a middle office, I want to query the currency pair tolerance setup so that I can review the tolerance setups
#AC1> Given: middle office has navigated to "Order Size Tolerance" screen
#When: he doesn't select a pricing service, no currency pair is entered, and clicks search button
#Then: all the currency pairs which has tolerance setup are listed.
#AC2> Given: middle office has navigated to "Order Size Tolerance" screen
#When: he selects a pricing service, but no currency pair is entered, and clicks search button
#Then: all the currency pairs which is under the pricing service and having tolerance setup are listed.
#AC3> Given: middle office has navigated to "Order Size Tolerance" screen
#When: he doesn't select a pricing service, but has inputted a currency 1 or currency 2 or currency pair
#Then: all the currency pairs has the inputted currency under all pricing services are listed
#AC4> Given: middle office has navigated to "Order Size Tolerance" screen
#When: he selects a pricing service, and inputs currency pair, and clicks search button
#Then: the currency pairs which matches the inputted one and is under the pricing service and having tolerance setup are listed.
#AC5> Given: a list of currency pairs is searched out
#When: it displays on the UI
#Then: there is columns for ccy1 minimum size and ccy2 minimum size.
#AC6> Given: a list of currency pairs is searched out
#When: it displays on the UI
#Then: there is a column for tolerance details column with a button of "Details".
@Regression
Feature: Story_116534 Query Order Tolerance

  Background: 
    Given user login to Reference data config page
    Then Navigate to Order Size Tolerance Page

  @CI 
  Scenario Outline: AC1> Query all, list pricing service and currency pair infomation
    Given user prepare order tolerance query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    When User click search to query
    And verify the numbers of IDF service and UI results is equal

    Examples: 
      | Pricing_Service | CCY1  | CCY2  |
      | @Null           | @Null | @Null |

  Scenario Outline: AC2> Query by only pricing service, then list pricing service and currency pair infomation
    Given user add order tolerance tiers
      | Pricing Service | CCY1 | CCY2 |
      | SSH             | LTL  | BGN  |
    And user prepare order tolerance query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    Then User click search to query
    And verify the numbers of IDF service and UI results is equal

    @CI @DEV @QA @UAT
    Examples: 
      | Pricing_Service | CCY1  | CCY2  |
      | SSH             | @Null | @Null |

  Scenario Outline: AC3> Query by currency pair, then list pricing service and currency pair infomation;
    Given user add order tolerance tiers
      | Pricing Service | CCY1 | CCY2 |
      | HPS             | MXN  | CNH  |
      | AIR             | AED  | IDR  |
      | SFX             | INR  | SGD  |
    And user prepare order tolerance query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    Then User click search to query
    And verify the numbers of IDF service and UI results is equal

    @CI @DEV @QA @UAT
    Examples: Query by currency pair
      | Pricing_Service | CCY1  | CCY2  |
      | @Null           | @Null | CNH   |
      | @Null           | IDR   | @Null |
      | @Null           | INR   | SGD   |

  Scenario Outline: AC4> Query by pricing service and currency pair
    Given user add order tolerance tiers
      | Pricing Service | CCY1 | CCY2 |
      | SSH             | BHD  | RON  |
      | SFX             | EUR  | JPY  |
    Given user prepare order tolerance query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    Then User click search to query
    And Verify IDF Service and UI results are equal

    @CI @DEV @QA @UAT
    Examples: positive
      | Pricing_Service | CCY1  | CCY2 |
      | SSH             | @Null | RON  |

    @CI @DEV @quick 
    Examples: 
      | Pricing_Service | CCY1 | CCY2 |
      | SFX             | EUR  | JPY  |

  Scenario Outline: AC5> Query Negative, No record is found
    Given user prepare order tolerance query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    Then User click search to query
    And Verify IDF Service return error message <errMsg>

    @CI @DEV @QA @UAT
    Examples: no data found
      | Pricing_Service | CCY1 | CCY2 | errMsg              |
      | HPS             | CYP  | FIM  | No record is found. |

  Scenario Outline: AC6> Choose same currency1 and currency2, then generate error message indicate the currency is duplicated
    Given user prepare order tolerance query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    When User click search to query
    Then user can get result after click search
      | Result | <Result> |

    @CI @DEV @QA @UAT
    Examples: 
      | Pricing_Service | CCY1 | CCY2 | Result                                     |
      | @Null           | AED  | AED  | Error!- Currency should not be duplicated. |
