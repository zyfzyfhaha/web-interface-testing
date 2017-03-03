#story 119387
#As a middle office, I want to edit normal size on normal size screen so that I can correct the normal sizes.
#AC1> Given a user who has Middle Office or Risk Manage Role modifies normal size
#when click save button
#then all of new normal sizes are saved successfully.
#AC2> Given middle office enter invalid new normal size (<0)
#when click save button
#then error message is popped up.
#AC3> Given middle office modifies the normal size and clicks save button
#when there is back end error
#then the error message is popped up and the query screen is auto refreshed.
#AC4> Given a user who not has Middle Office or Risk Manage Role modifies normal size
#when click save button
#then error message popped up indicating no access to add.
@Regression
Feature: Story_119387 Edit_Normal_Size

  Background: 
    Given user login to Reference data config page
    And navigate to Order Normal Size Page

  @quick
  Scenario Outline: AC1> Edit normal size, save successfully
    Given user add normal order size data
      | Pricing Service | CCY1 | CCY2 | CustID  |
      | SSH             | CHF  | AUD  | 9122020 |
      | SFX             | USD  | CHF  | 9140162 |
    And user prepare normal size query data
      | Pricing Service | <Pricing_Service> |
      | IM              | <IM>              |
      | FUND            | <FUND>            |
    And User click search to query
    When user modify normal size for below data
      | KEY              | <KEY>            |
      | CCY1_NORMAL_SIZE | <ccy1NormalSize> |
      | CCY2_NORMAL_SIZE | <ccy2NormalSize> |
    And verify new normal sizes are saved successfully after click save

    @CI
    Examples: 
      | Pricing_Service | IM        | FUND  | KEY         | ccy1NormalSize | ccy2NormalSize |
      | SSH             | B T AND T | @Null | SSH_CHF_AUD | 15000          | 65000000       |
      | SSH             | @Null     | ZN71  | SSH_CHF_AUD | 0              | 1.5            |
      | SSH             | @Null     | ZN71  | SSH_CHF_AUD | 0              | 0              |

    @DEV @QA @UAT
    Examples: 
      | Pricing_Service | IM | FUND  | KEY         | ccy1NormalSize | ccy2NormalSize |
      | SFX             | M  | @Null | SFX_USD_CHF | 90500          | 6550           |

  Scenario Outline: AC2> Edit normal size more than one record, save successfully
    Given user add normal order size data
      | Pricing Service | CCY1 | CCY2 | CustID  |
      | SSH             | CHF  | BDT  | 9122020 |
      | SSH             | BRL  | CHF  | 9122020 |
    Given user prepare normal size query data
      | Pricing Service | <Pricing_Service> |
      | IM              | <IM>              |
      | FUND            | <FUND>            |
    And User click search to query
    When user modify normal size for below data
      | KEY              | <KEY1>            |
      | CCY1_NORMAL_SIZE | <ccy1NormalSize1> |
      | CCY2_NORMAL_SIZE | <ccy2NormalSize1> |
    And user modify normal size for below data
      | KEY              | <KEY2>            |
      | CCY1_NORMAL_SIZE | <ccy1NormalSize2> |
      | CCY2_NORMAL_SIZE | <ccy2NormalSize2> |
    And verify new normal sizes are saved successfully after click save

    @CI
    Examples: 
      | Pricing_Service | IM        | FUND | KEY1        | ccy1NormalSize1 | ccy2NormalSize1 | KEY2        | ccy1NormalSize2 | ccy2NormalSize2 |
      | SSH             | B T AND T | ZN71 | SSH_CHF_BDT | 0               | 0               | SSH_BRL_CHF | 500             | 1500001         |

    @DEV @QA @UAT
    Examples: 
      | Pricing_Service | IM        | FUND  | KEY1        | ccy1NormalSize1 | ccy2NormalSize1 | KEY2        | ccy1NormalSize2 | ccy2NormalSize2 |
      | SSH             | B T AND T | @Null | SSH_CHF_BDT | 30000           | 60000           | SSH_BRL_CHF | 2000.6          | 5000            |

  Scenario Outline: AC3> Edit normal size, do not save and click search, data will be roll back
    Given user add normal order size data
      | Pricing Service | CCY1 | CCY2 | CustID  |
      | SSH             | CHF  | AUD  | 9122020 |
      | SFX             | USD  | CHF  | 9140162 |
    And user prepare normal size query data
      | Pricing Service | <Pricing_Service> |
      | IM              | <IM>              |
      | FUND            | <FUND>            |
    And User click search to query
    When user modify normal size for below data
      | KEY              | <KEY>            |
      | CCY1_NORMAL_SIZE | <ccy1NormalSize> |
      | CCY2_NORMAL_SIZE | <ccy2NormalSize> |
    And User click search to query
    And verify normal size values are not changed

    @CI
    Examples: 
      | Pricing_Service | IM        | FUND  | KEY         | ccy1NormalSize | ccy2NormalSize |
      | SSH             | B T AND T | @Null | SSH_CHF_AUD | 15000          | 65000000       |
      | SSH             | @Null     | ZN71  | SSH_CHF_AUD | 0              | 1.5            |

    @DEV @QA @UAT
    Examples: 
      | Pricing_Service | IM | FUND  | KEY         | ccy1NormalSize | ccy2NormalSize |
      | SFX             | M  | @Null | SFX_USD_CHF | 20000          | 5500           |

  @Invalid
  Scenario Outline: AC3> Enter invalid new normal size(less than 0 or not digital)
    Given user prepare normal size query data
      | Pricing Service | <Pricing_Service> |
      | IM              | <IM>              |
      | FUND            | <FUND>            |
    And User click search to query
    When user modify normal size for below data
      | KEY              | <KEY>            |
      | CCY1_NORMAL_SIZE | <ccy1NormalSize> |
      | CCY2_NORMAL_SIZE | <ccy2NormalSize> |
    Then user can get normal size result after click Save
      | Result | <Result> |
    And verify normal size values are not changed

    Examples: 
      | Pricing_Service | IM    | FUND | KEY         | ccy1NormalSize | ccy2NormalSize | Result                                      |
      | SSH             | @Null | ZN71 | SSH_CHF_AUD | fsfsd          | fsfsd          | Error!- Form field is invalid.              |
      | SSH             | @Null | ZN71 | SSH_CHF_AUD | -1000          | -3000          | Error!- Invalid value. Please verify again. |

    Examples: 
      | Pricing_Service | IM | FUND  | KEY         | ccy1NormalSize | ccy2NormalSize | Result                                      |
      | SFX             | MI | @Null | SFX_USD_CAD | fsfsd          | 500.6          | Error!- Form field is invalid.              |
      | SFX             | MI | @Null | SFX_USD_CAD | -1000          | -3000          | Error!- Invalid value. Please verify again. |
