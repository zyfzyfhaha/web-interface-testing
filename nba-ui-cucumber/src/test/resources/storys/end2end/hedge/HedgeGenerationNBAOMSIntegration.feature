#AC1: Add hedge trade subscription for OMS AC1&AC4
#AC2: Update hedge trade subscription for OMS AC2
#AC3: Delete hedge trade subscription for OMS AC5
#AC4-1: Save multiple hedge trade subscription for OMS AC7
#AC4-2: Save hedge trade subscription for OMS AC7
#AC5: Save hedge trade subscription for OMS AC3&AC6
@NBA-OMS-INTEGRATION
Feature: Release2.0 NBA OMS Integration Testing

  Background: Login to Reference Data Management and navigate to HEDGE TRADE SUBSCRIPTION page
    #Service available
    Given User login to Reference Data Management
    And User navigates to Client Profile Hedge Trade Subscription Page

  Scenario: AC1 Add hedge trade subscription for OMS AC1&AC4
    Given Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME |
      | F43K | SFX WM HON 1600 |
    And Click the Subscribe button
    #And System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | F43K              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                  |
      | EUR  | USD  | 50         | Specific Date        | 03-30,06-30,10-30,12-30 |
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME |
      | F43K | SFX WM HON 1600 |
    And Click the Subscribe button
    And Delete all hedge subscription
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME |
      | F43K | SFX WM HON 1600 |
    And Click the Subscribe button
    And No hedge trade subscription is displayed
    When Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | F43K              |
    And User add hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                  |
      | EUR  | USD  | 50         | Specific Date        | 03-30,06-30,10-30,12-30 |
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME |
      | F43K | SFX WM HON 1600 |
    And Click the Subscribe button
    Then Hedge Config below is displayed
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                  |
      | EUR  | USD  | 50         | Specific Date        | 03-30,06-30,10-30,12-30 |

  Scenario: AC2 Update hedge trade subscription for OMS AC2
    Given Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME |
      | 00JV | SFX WM HON 1600 |
    And Click the Subscribe button
    #And System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 00JV              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | MYR  | GBP  | 85         | Interval Days        | 10     |
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME |
      | 00JV | SFX WM HON 1600 |
    And Click the Subscribe button
    And Hedge Config below is displayed
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | GBP  | MYR  | 85         | Interval Days        | 10     |
    When User update hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | GBP  | MYR  | 100        | Interval Days        | 30     |
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME |
      | 00JV | SFX WM HON 1600 |
    And Click the Subscribe button
    Then Hedge Config below is displayed
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | GBP  | MYR  | 100        | Interval Days        | 30     |

  Scenario: AC3 Delete hedge trade subscription for OMS AC5
    Given Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME |
      | F43K | SFX WM BOS 1700 |
    And Click the Subscribe button
    #And System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | F43K              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | EUR  | CAD  | 85         | Interval Days        | 10     |
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME |
      | F43K | SFX WM BOS 1700 |
    And Click the Subscribe button
    And Hedge Config below is displayed
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | EUR  | CAD  | 85         | Interval Days        | 10     |
    When Delete all hedge subscription
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME |
      | F43K | SFX WM BOS 1700 |
    And Click the Subscribe button
    Then No hedge trade subscription is displayed

  Scenario: AC4-1 Save multiple hedge trade subscription for OMS AC7
    Given Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME |
      | 1360 | SFX WM TWN 1500 |
    And Click the Subscribe button
    #And System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 360               |
    When User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                          |
      | TWD  | USD  | 70         | IMM Date             | IMM MAR,IMM JUN,IMM SEP,IMM DEC |
      | CAD  | TWD  | 100        | IMM Date             | IMM MAR,IMM JUN,IMM DEC         |
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME |
      | 1360 | SFX WM TWN 1500 |
    And Click the Subscribe button
    Then Hedge Config below is displayed
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                          |
      | TWD  | USD  | 70         | IMM Date             | IMM MAR,IMM JUN,IMM SEP,IMM DEC |
      | CAD  | TWD  | 100        | IMM Date             | IMM MAR,IMM JUN,IMM DEC         |

  Scenario Outline: AC4-2 Save hedge trade subscription for OMS AC7
    Given Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME |
      | 1360 | SFX WM SEO 1700 |
    And Click the Subscribe button
    #And System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 360               |
    When User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                          |
      | CAD  | KRW  | 70         | IMM Date             | IMM MAR,IMM JUN,IMM SEP,IMM DEC |
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME |
      | 1360 | SFX WM SEO 1700 |
    And Click the Subscribe button
    Then Hedge Config below is displayed
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                          |
      | CAD  | KRW  | 70         | IMM Date             | IMM MAR,IMM JUN,IMM SEP,IMM DEC |

  Scenario Outline: AC5 Save hedge trade subscription for OMS AC3&AC6
    Given Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    #And System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    When User input hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    Then Hedge Config below is displayed
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |

    Examples: 
      | OMS CASE | FUND | INSTRUMENT_NAME | FUND_BOOKING_CODE | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                  |
      | OMS AC3  | F43K | SFX WM BOS 1600 | F43K              | USD  | CHF  | 100        | Interval Days        | 30                      |
      | OMS AC6  | F43K | SFX WM LON 0600 | F43K              | USD  | HKD  | 70         | Specific Date        | 03-30,06-30,10-30,12-30 |
      | OMS AC6  | F43K | SFX WM BOS 0500 | F43K              | USD  | HKD  | 70         | Specific Date        | 03-30,06-30,10-30,12-30 |
      | OMS AC6  | F43K | SFX WM BOS 1700 | F43K              | USD  | HKD  | 70         | Specific Date        | 03-30,06-30,10-30,12-30 |
