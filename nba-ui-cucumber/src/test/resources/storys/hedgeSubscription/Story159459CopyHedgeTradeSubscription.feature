#As an onboarding team, I want to be able to save the change, so that next time I can edit it
#AC1 Subscribe hedge by copying
#AC1-1 Subscribe one ccy hedge by copying (tenor days;dates;IMM;Month End)
#AC1-2 Subscribe multiple ccy hedge by copying
#AC2 Update hedge by copying
#AC2-1 Update one ccy hedge subscription by copying (tenor days;dates;IMM)
#AC2-2 Add/Update/Delete multiple ccy hedge subscription by copying
#AC2-3 Update hedge by copying its own hedge
#AC3 Subscribe hedge by copying when no existing hedge
#AC4 No copy button if no exsiting instrument
Feature: Hedge Trade Subscription Release2.1 It16 Story 159459 & 178290 & 182095 & 182098 Copy from existing Hedge trade subscription

  Background: Login to Reference Data Management and navigate to HEDGE TRADE SUBSCRIPTION page
    #Log into UIF Hedge Subscription page
    Given User login to Reference Data Management
    And User navigates to Client Profile Hedge Trade Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button

  @CI @DEV @QA
  Scenario Outline: AC1-1 Subscribe one ccy hedge by copying
    #Copy hedge from SFX_WM_BOS_1600_UI_TEST to SFX_WM_BOS_1500_UI_TEST
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 52C8              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | AUD  | PLN  | 90         | Interval Days        | 30     |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    And Delete all hedge subscription
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User input hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    When Click copy icon
    And Select the instrument to be copied from and click Apply button
      | INSTRUMENT_NAME   |
      | <INSTRUMENT_NAME> |
    And User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    And System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Hedge Config below is displayed
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                                  |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 90.02      | Interval Days        | 10                                      |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 100        | Specific Date        | 03-30,06-30,09-30                       |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 100        | IMM Date             | IMM MAR,IMM SEP,IMM DEC                 |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 9          | Month End Date       | JAN END,MAR END,JUN END,SEP END,DEC END |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 9          | 3 Months End Date    |                                         |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 9          | 6 Months End Date    |                                         |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 9          | 9 Months End Date    |                                         |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 9          | 1 Year Date          |                                         |

  @CI @DEV @QA
  Scenario Outline: AC1-2 Subscribe multiple ccy hedge by copying
    #Copy hedge from SFX_WM_BOS_1600_UI_TEST to SFX_WM_BOS_1500_UI_TEST
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 52C8              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | AUD  | PLN  | 90         | Interval Days        | 30     |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    And Delete all hedge subscription
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                                  |
      | EUR  | RON  | 9          | Month End Date       | JAN END,MAR END,JUN END,SEP END,DEC END |
      | CAD  | PLN  | 50         | IMM Date             | IMM MAR,IMM JUN,IMM SEP,IMM DEC         |
      | EUR  | PLN  | 30         | Specific Date        | 03-30,06-30,09-30                       |
      | AUD  | PLN  | 100        | Interval Days        | 30                                      |
      | AUD  | ZAR  | 9          | 3 Months End Date    |                                         |
      | CAD  | ZAR  | 9          | 6 Months End Date    |                                         |
      | SGD  | PLN  | 9          | 9 Months End Date    |                                         |
      | GBP  | ZAR  | 9          | 1 Year Date          |                                         |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    When Click copy icon
    And Select the instrument to be copied from and click Apply button
      | INSTRUMENT_NAME   |
      | <INSTRUMENT_NAME> |
    And User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    And System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Hedge Config below is displayed
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                                  |
      | EUR  | RON  | 9          | Month End Date       | JAN END,MAR END,JUN END,SEP END,DEC END |
      | CAD  | PLN  | 50         | IMM Date             | IMM MAR,IMM JUN,IMM SEP,IMM DEC         |
      | EUR  | PLN  | 30         | Specific Date        | 03-30,06-30,09-30                       |
      | AUD  | PLN  | 100        | Interval Days        | 30                                      |
      | AUD  | ZAR  | 9          | 3 Months End Date    |                                         |
      | CAD  | ZAR  | 9          | 6 Months End Date    |                                         |
      | SGD  | PLN  | 9          | 9 Months End Date    |                                         |
      | GBP  | ZAR  | 9          | 1 Year Date          |                                         |

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         |

  @CI @DEV @QA
  Scenario Outline: AC2-1 Update one ccy hedge subscription by copying
    #Copy hedge from SFX_WM_BOS_1600_UI_TEST to SFX_WM_BOS_1500_UI_TEST
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 52C8              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | AUD  | PLN  | 90         | Interval Days        | 30     |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User input hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    When Click copy icon
    And Select the instrument to be copied from and click Apply button
      | INSTRUMENT_NAME   |
      | <INSTRUMENT_NAME> |
    And User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    And System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Hedge Config below is displayed
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                                  |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 90.02      | Interval Days        | 10                                      |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 100        | Specific Date        | 03-30,06-30,09-30                       |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 100        | IMM Date             | IMM MAR,IMM SEP,IMM DEC                 |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 9          | Month End Date       | JAN END,MAR END,JUN END,SEP END,DEC END |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 9          | 3 Months End Date    |                                         |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 9          | 6 Months End Date    |                                         |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 9          | 9 Months End Date    |                                         |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         | AUD  | PLN  | 9          | 1 Year Date          |                                         |

  @CI @DEV @QA 
  Scenario Outline: AC2-2 Add/Update/Delete multiple ccy hedge subscription by copying
    #Copy hedge from SFX_WM_BOS_1600_UI_TEST to SFX_WM_BOS_1500_UI_TEST
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 52C8              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS          |
      | AUD  | PLN  | 90         | Interval Days        | 30              |
      | EUR  | PLN  | 90         | IMM Date             | IMM MAR,IMM JUN |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                          |
      | AUD  | PLN  | 50         | Month End Date       | JUN END                         |
      | CAD  | PLN  | 50         | IMM Date             | IMM MAR,IMM JUN,IMM SEP,IMM DEC |
      | EUR  | PLN  | 30         | Specific Date        | 03-30,06-30,09-30               |
      | AUD  | ZAR  | 9          | 3 Months End Date    |                                 |
      | CAD  | ZAR  | 9          | 6 Months End Date    |                                 |
      | SGD  | PLN  | 9          | 9 Months End Date    |                                 |
      | GBP  | ZAR  | 9          | 1 Year Date          |                                 |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    When Click copy icon
    And Select the instrument to be copied from and click Apply button
      | INSTRUMENT_NAME   |
      | <INSTRUMENT_NAME> |
    And User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    And System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Hedge Config below is displayed
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                          |
      | AUD  | PLN  | 50         | Month End Date       | JUN END                         |
      | CAD  | PLN  | 50         | IMM Date             | IMM MAR,IMM JUN,IMM SEP,IMM DEC |
      | EUR  | PLN  | 30         | Specific Date        | 03-30,06-30,09-30               |
      | AUD  | ZAR  | 9          | 3 Months End Date    |                                 |
      | CAD  | ZAR  | 9          | 6 Months End Date    |                                 |
      | SGD  | PLN  | 9          | 9 Months End Date    |                                 |
      | GBP  | ZAR  | 9          | 1 Year Date          |                                 |

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST | 52C8 CODE         |

  @CI @DEV @QA
  Scenario: AC2-3 Update hedge by copying its own hedge
    #Copy hedge from SFX_WM_BOS_1500_UI_TEST to SFX_WM_BOS_1500_UI_TEST
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 52C8              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS            |
      | AUD  | PLN  | 90         | Interval Days        | 30                |
      | EUR  | PLN  | 90         | IMM Date             | IMM MAR,IMM JUN   |
      | CAD  | PLN  | 50         | Specific Date        | 03-30,06-30,09-30 |
      | AUD  | ZAR  | 9          | 3 Months End Date    |                   |
      | CAD  | ZAR  | 9          | 6 Months End Date    |                   |
      | SGD  | PLN  | 9          | 9 Months End Date    |                   |
      | GBP  | ZAR  | 9          | 1 Year Date          |                   |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    When Click copy icon
    And Select the instrument to be copied from and click Apply button
      | INSTRUMENT_NAME         |
      | SFX_WM_BOS_1500_UI_TEST |
    And User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    And System is navigated to HEDGE TRADE SUBSCRIPTION page
    And Hedge Config below is displayed
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS            |
      | AUD  | PLN  | 90         | Interval Days        | 30                |
      | EUR  | PLN  | 90         | IMM Date             | IMM MAR,IMM JUN   |
      | CAD  | PLN  | 50         | Specific Date        | 03-30,06-30,09-30 |
      | AUD  | ZAR  | 9          | 3 Months End Date    |                   |
      | CAD  | ZAR  | 9          | 6 Months End Date    |                   |
      | SGD  | PLN  | 9          | 9 Months End Date    |                   |
      | GBP  | ZAR  | 9          | 1 Year Date          |                   |

  @ManualTest
  Scenario: AC3 Subscribe hedge by copying when no existing hedge
    #Copy hedge from SFX_WM_BOS_1600_UI_TEST to SFX_WM_BOS_1500_UI_TEST
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 52C8              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS          |
      | AUD  | PLN  | 90         | Interval Days        | 30              |
      | EUR  | PLN  | 90         | IMM Date             | IMM MAR,IMM JUN |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 52C8              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                          |
      | CAD  | PLN  | 50         | IMM Date             | IMM MAR,IMM JUN,IMM SEP,IMM DEC |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1600_UI_TEST |
    And Delete all hedge subscription
    When User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    And Click copy icon
    And Instrument is not in the dropdown list
      | INSTRUMENT_NAME         |
      | SFX_WM_BOS_1600_UI_TEST |

  @ManualTest
  Scenario: AC4 No copy button if no exsiting instrument
    Given Fund subscribe no instrument
    When Fund subscribe hedge trade
    Then No copy icon displayed in subscription page
