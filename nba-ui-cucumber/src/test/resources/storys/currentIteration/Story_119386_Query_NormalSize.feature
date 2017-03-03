#story 119386
#As a middle office, I want to query normal sizes on normal size screen so that I can update the correct normal sizes.
#AC1> Given: middle office has navigated to "Normal Order Size" screen
#When: he inputs at least IM or fund, but no pricing service, currency pair and clicks search button
#Then: all the setup normal sizes which under input IM/fund are listed.
#AC2> Given: middle office has navigated to "Normal Order Size" screen
#When: he inputs pricing service, IM/fund, but no currency pair are entered, and clicks search button
#Then: all the setup normal size which under input pricing service are listed.
#AC3> Given: middle office has navigated to "Normal Order Size" screen
#When: he inputs IM but no fund and clicks search button
#Then: all the setup normal size which are under input IM are listed.
#AC4> Given: middle office has navigated to "Normal Order Size" screen
#When: he inputs fund but no IM and clicks search button
#Then: all the setup normal size which are under input fund are listed.
#AC4> Given: middle office has navigated to "Normal Order Size" screen
#When: he input the combine parameters with pricing service, IM, fund, currency pair and clicks search button
#Then: all the setup normal size which are under input parameters are listed.
#AC5> Given: a list of normal order size is searched out
#When: it displays on the UI
#Then: there are the columns pricing service, ccy1, ccy2, ccy1 normal size and ccy2 normal size, last modify by, last modify time.@Regression
@Regression
Feature: Story_119386 Normal_Size_Query

  Background: 
    Given user login to Reference data config page
    And navigate to Order Normal Size Page

  Scenario Outline: AC1> Query by no pricing service or no Fund and IM and Currency Pairs, then pop up errorMsg
    Given user prepare normal size query data
      | Pricing Service | <Pricing_Service> |
      | IM              | <IM>              |
      | FUND            | <FUND>            |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    When User click search to query
    And user can get result after click search
      | Result | <Result> |
    And UI columns include below fields
      | PRICING SERVICE  |
      | IM               |
      | FUND             |
      | CCY1             |
      | CCY2             |
      | CCY1 NORMAL SIZE |
      | CCY2 NORMAL SIZE |
      | AUTO CALCULATE   |
      | COMMENT          |
      | LAST MODIFY BY   |
      | LAST MODIFY TIME |

    @CI @DEV @QA @UAT
    Examples: 
      | Pricing_Service | IM    | FUND  | CCY1  | CCY2  | Result                                               |
      | @Null           | MI    | Q5    | AUD   | CAD   | Error!- Pricing Service cannot be empty              |
      | SSH             | @Null | @Null | @Null | @Null | Error!- IM, Fund, CCY1, CCY2 cannot not be all empty |

  Scenario Outline: AC2> Query by IM no Fund, then list all infomation
    Given user add normal order size data
      | Pricing Service | CCY1 | CCY2 | CustID  |
      | SFX             | GRC  | CNH  | 9143265 |
      | SFX             | CLP  | CNH  | 9140162 |
    And user prepare normal size query data
      | Pricing Service | <Pricing_Service> |
      | IM              | <IM>              |
      | FUND            | <FUND>            |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    When User click search to query
    Then Verify Normal Size Query IDF Service and UI results are equal

    @QA @UAT
    Examples: 
      | Pricing_Service | IM | FUND  | CCY1  | CCY2  |
      | SFX             | MI | @Null | @Null | @Null |

    @CI @DEV @quick
    Examples: 
      | Pricing_Service | IM | FUND  | CCY1  | CCY2  |
      | SFX             | AI | @Null | @Null | @Null |

  Scenario Outline: AC3> Query by Pricing Service and IM or Fund or Curency Pair, then list all infomation
    Given user add normal order size data
      | Pricing Service | CCY1 | CCY2 | CustID  |
      | AIR             | BHD  | AED  | 9143265 |
      | AIR             | CHF  | AED  | 9140162 |
    And user prepare normal size query data
      | Pricing Service | <Pricing_Service> |
      | IM              | <IM>              |
      | FUND            | <FUND>            |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    When User click search to query
    Then Verify Normal Size Query IDF Service and UI results are equal

    @QA @CI @DEV
    Examples: By pricing service and IM
      | Pricing_Service | IM | FUND  | CCY1  | CCY2  |
      | AIR             | A  | @Null | @Null | @Null |

    @QA @UAT
    Examples: By pricing service and Fund
      | Pricing_Service | IM    | FUND | CCY1  | CCY2  |
      | AIR             | @Null | Q5   | @Null | @Null |

    @CI @DEV
    Examples: By pricing service and Fund
      | Pricing_Service | IM    | FUND | CCY1  | CCY2  |
      | AIR             | @Null | F4   | @Null | @Null |

    @QA @CI @DEV
    Examples: By pricing service and Currency Pair
      | Pricing_Service | IM    | FUND  | CCY1 | CCY2 |
      | AIR             | @Null | @Null | BHD  | AED  |

    @QA @CI @DEV
    Examples: By pricing service and Currency
      | Pricing_Service | IM    | FUND  | CCY1 | CCY2  |
      | AIR             | @Null | @Null | AED  | @Null |

  Scenario Outline: AC5> Query Negative "No Data Found"
    Given user prepare normal size query data
      | Pricing Service | <Pricing_Service> |
      | IM              | <IM>              |
      | FUND            | <FUND>            |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    When User click search to query
    Then verify Query return error message
      | Result | <Result> |

    @QA @CI @DEV
    Examples: 
      | Pricing_Service | IM  | FUND  | CCY1  | CCY2  | Result              |
      | AIR             | abc | @Null | @Null | @Null | No record is found. |
