#AC1 Save hedge trade subscription
#AC1-0 Delete all hedge
#AC1-1 Add hedge trade subscription for one currency pair with tenor value
#AC1-2 Add hedge trade subscription for one currency pair with tenor dates
#AC1-3 Update hedge trade subscription for one currency pair with tenor dates
#AC1-4 Update hedge trade subscription for one currency pair with tenor value
#AC1-5 Add or Update hedge trade subscription for multiple currency pairs
#AC1-6 Add hedge trade subscription for one currency pair with IMM Dates
#AC1-7 Update hedge trade subscription for one currency pair with IMM Dates
#AC1-8 Add hedge trade subscription for one currency pair with Month End Dates
#AC1-9 Update hedge trade subscription for one currency pair with Month End Dates
#AC1-10 Add hedge trade subscription for one currency pair with 3M6M9M1Y_MonthEnd
#AC1-11 Update hedge trade subscription for one currency pair with 3M6M9M1Y_MonthEnd
#AC2 Error message for save error
Feature: Release2.1 It16 Story 156542 & 182095 & 182098 &201513 Save hedge trade subscription

  Background: Login to Reference Data Management and navigate to HEDGE TRADE SUBSCRIPTION page
    #Log into UIF Hedge Subscription page
    Given User login to Reference Data Management
    And User navigates to Client Profile Hedge Trade Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    And System is navigated to HEDGE TRADE SUBSCRIPTION page

  @CI @DEV @QA
  Scenario Outline: AC1-0 Delete all hedge trade subscription
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 52C8              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | AUD  | PLN  | 90         | Interval Days        | 30     |
      | CAD  | PLN  | 100        | Interval Days        | 30     |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    When Delete all hedge subscription
    And User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And System is navigated to HEDGE TRADE SUBSCRIPTION page
    And No hedge trade subscription is displayed

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              |

  @CI @DEV @QA
  Scenario Outline: AC1-1 Add hedge trade subscription for one currency pair with tenor value
    Given No hedge trade subscription is displayed
    When Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User add hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Hedge Config below is displayed
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And Tenor warning message below is dispalyed under Tenor
      | WARNING                                                 |
      | Note: the tenor change will take effective immediately. |

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8 CODE         | AUD  | PLN  | 90.02      | Interval Days        | 30     |

  @CI @DEV @QA
  Scenario Outline: AC1-2 Add hedge trade subscription for one currency pair with tenor dates
    Given Delete all hedge subscription
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And System is navigated to HEDGE TRADE SUBSCRIPTION page
    And No hedge trade subscription is displayed
    When Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User add hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Hedge Config below is displayed
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And Tenor warning message below is dispalyed under Tenor
      | WARNING                                                 |
      | Note: the tenor change will take effective immediately. |

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS      |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              | AUD  | PLN  | 90         | Specific Date        | 03-31,06-30 |

  @CI @DEV @QA
  Scenario Outline: AC1-3 Update hedge trade subscription for one currency pair with tenor dates
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS      |
      | AUD  | PLN  | 90         | Specific Date        | 03-31,06-30 |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User update hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    When User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    Then Hedge Config below is displayed
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And Tenor warning message below is dispalyed under Tenor
      | WARNING                                                 |
      | Note: the tenor change will take effective immediately. |

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                                  |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8 CODE         | AUD  | PLN  | 85         | Interval Days        | 10                                      |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8 CODE         | AUD  | PLN  | 85         | IMM Date             | IMM JUN,IMM SEP                         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              | AUD  | PLN  | 90.09      | Month End Date       | JAN END,MAR END,JUN END,SEP END,DEC END |

  @CI @DEV @QA
  Scenario Outline: AC1-4 Update hedge trade subscription for one currency pair with tenor value
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | AUD  | PLN  | 85         | Interval Days        | 10     |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User update hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    When User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    Then Hedge Config below is displayed
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And Tenor warning message below is dispalyed under Tenor
      | WARNING                                                 |
      | Note: the tenor change will take effective immediately. |

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                                  |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              | AUD  | PLN  | 100        | Specific Date        | 03-31,06-30,09-30,12-31                 |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              | AUD  | PLN  | 100        | IMM Date             | IMM JUN,IMM SEP,IMM DEC                 |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              | AUD  | PLN  | 90.09      | Month End Date       | JAN END,MAR END,JUN END,SEP END,DEC END |

  @CI @DEV @QA
  Scenario Outline: AC1-5 Add or Update hedge trade subscription for multiple currency pairs
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS         |
      | PLN  | AUD  | 85         | Interval Days        | 10             |
      | EUR  | RON  | 100        | Interval Days        | 20             |
      | CAD  | PLN  | 100        | Specific Date        | 3-31,6-30,9-30 |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Hedge Config below is displayed
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS            |
      | AUD  | PLN  | 85         | Interval Days        | 10                |
      | EUR  | RON  | 100        | Interval Days        | 20                |
      | CAD  | PLN  | 100        | Specific Date        | 03-31,06-30,09-30 |
    When User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                                  |
      | EUR  | PLN  | 90         | Month End Date       | JAN END,MAR END,JUN END,SEP END,DEC END |
      | AUD  | PLN  | 90         | IMM Date             | IMM MAR                                 |
      | RON  | EUR  | 100        | Interval Days        | 20                                      |
      | CAD  | ZAR  | 90         | Specific Date        | 03-31,06-30,09-30                       |
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    Then Hedge Config below is displayed
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                                  |
      | EUR  | PLN  | 90         | Month End Date       | JAN END,MAR END,JUN END,SEP END,DEC END |
      | AUD  | PLN  | 90         | IMM Date             | IMM MAR                                 |
      | EUR  | RON  | 100        | Interval Days        | 20                                      |
      | CAD  | ZAR  | 90         | Specific Date        | 03-31,06-30,09-30                       |
    And Tenor warning message below is dispalyed under Tenor
      | WARNING                                                 |
      | Note: the tenor change will take effective immediately. |

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              |

  @CI @DEV @QA
  Scenario Outline: AC1-6 Add hedge trade subscription for one currency pair with IMM Dates
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 52C8              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS  |
      | AUD  | PLN  | 90         | IMM Date             | IMM MAR |
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And Delete all hedge subscription
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User add hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Hedge Config below is displayed
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And Tenor warning message below is dispalyed under Tenor
      | WARNING                                                 |
      | Note: the tenor change will take effective immediately. |

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                          |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              | AUD  | PLN  | 90.02      | IMM Date             | IMM MAR,IMM DEC                 |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              | AUD  | PLN  | 90.02      | IMM Date             | IMM MAR,IMM JUN,IMM SEP,IMM DEC |

  @CI @DEV @QA
  Scenario Outline: AC1-7 Update hedge trade subscription for one currency pair with IMM Dates
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                          |
      | AUD  | PLN  | 85         | IMM Date             | IMM JUN,IMM SEP,IMM DEC,IMM MAR |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User update hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    When User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    Then Hedge Config below is displayed
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And Tenor warning message below is dispalyed under Tenor
      | WARNING                                                 |
      | Note: the tenor change will take effective immediately. |

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                  |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              | AUD  | PLN  | 100        | Specific Date        | 03-31,06-30,09-30,12-31 |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8 CODE         | AUD  | PLN  | 30         | Interval Days        | 30                      |

  @CI @DEV @QA
  Scenario Outline: AC1-8 Add hedge trade subscription for one currency pair with Month End Date
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 52C8              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS  |
      | AUD  | PLN  | 90         | Month End Date       | JAN END |
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And Delete all hedge subscription
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User add hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    When User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Hedge Config below is displayed
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And Tenor warning message below is dispalyed under Tenor
      | WARNING                                                 |
      | Note: the tenor change will take effective immediately. |

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                                                                                          |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8 CODE         | AUD  | PLN  | 90.02      | Month End Date       | JAN END,SEP END                                                                                 |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              | AUD  | PLN  | 100        | Month End Date       | JAN END,FEB END,MAR END,APR END,MAY END,JUN END,JUL END,AUG END,SEP END,OCT END,NOV END,DEC END |

  @CI @DEV @QA
  Scenario Outline: AC1-9 Update hedge trade subscription for one currency pair with Month End Dates
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | AUD  | PLN  | 85         | Month End Date       |        |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User update hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    When User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    Then Hedge Config below is displayed
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And Tenor warning message below is dispalyed under Tenor
      | WARNING                                                 |
      | Note: the tenor change will take effective immediately. |

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                          |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              | AUD  | PLN  | 100        | Specific Date        | 03-31,06-30,09-30,12-31         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8 CODE         | AUD  | PLN  | 30         | Interval Days        | 30                              |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8 CODE         | AUD  | PLN  | 30         | IMM Date             | IMM MAR,IMM JUN,IMM SEP,IMM DEC |

  @CI @DEV @QA
  Scenario Outline: AC1-10 Add hedge trade subscription for one currency pair with 3M6M9M1Y_MonthEnd
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 52C8              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS  |
      | AUD  | PLN  | 90         | Month End Date       | JAN END |
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And Delete all hedge subscription
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User add hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    When User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Hedge Config below is displayed
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And Tenor warning message below is dispalyed under Tenor
      | WARNING                                                 |
      | Note: the tenor change will take effective immediately. |

    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8 CODE         | AUD  | PLN  | 90.02      | 3 Months End Date    |        |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              | AUD  | PLN  | 100        | 6 Months End Date    |        |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              | AUD  | PLN  | 100        | 9 Months End Date    |        |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              | AUD  | PLN  | 100        | 1 Year Date          |        |

  @CI @DEV @QA @test
  Scenario Outline: AC1-11 Update hedge trade subscription for one currency pair with 3M6M9M1Y_MonthEnd
    #Given Prepare Hedge_Property Data
    # | CUST_ID | XREF_FUND_INSTRUMENT_DETAIL_ID |
    # | 9119539 | 9                              |
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | AUD  | PLN  | 85         | 1 Year Date          |        |
    And User click Save button
    And System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    And Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    And User update hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    When User click Save button
    Then System is navigated to Client Profile Subscription Page
    And Input valid FUND and INSTRUMENT_NAME
      | FUND   | INSTRUMENT_NAME   |
      | <FUND> | <INSTRUMENT_NAME> |
    And Click the Subscribe button
    Then Hedge Config below is displayed
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And Tenor warning message below is dispalyed under Tenor
      | WARNING                                                 |
      | Note: the tenor change will take effective immediately. |

    # And The hedge property table should be updated, record 9 should be clean as null
    #  | XREF_FUND_INSTRUMENT_DETAIL_ID | NEXT_AVAILABLE_TENOR   |
    #  | 9                              | <NEXT_AVAILABLE_TENOR> |
    # And One record 9 found in adt hedge property table
    #  | XREF_FUND_INSTRUMENT_DETAIL_ID | NEXT_AVAILABLE_TENOR   |
    #  | 9                              | <NEXT_AVAILABLE_TENOR> |
    Examples: 
      | FUND | INSTRUMENT_NAME         | FUND_BOOKING_CODE | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS                                                                                  |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8              | AUD  | PLN  | 100        | Specific Date        | 03-31,06-30,09-30,12-31                                                                 |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8 CODE         | AUD  | PLN  | 30         | Interval Days        | 30                                                                                      |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8 CODE         | AUD  | PLN  | 30         | IMM Date             | IMM MAR,IMM JUN,IMM SEP,IMM DEC                                                         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8 CODE         | AUD  | PLN  | 30         | Month End Date       | FEB END,MAR END,APR END,MAY END,JUN END,JUL END,AUG END,SEP END,OCT END,NOV END,DEC END |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8 CODE         | AUD  | PLN  | 30         | 3 Months End Date    |                                                                                         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8 CODE         | AUD  | PLN  | 30         | 6 Months End Date    |                                                                                         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST | 52C8 CODE         | AUD  | PLN  | 30         | 9 Months End Date    |                                                                                         |

  @CI @DEV @QA
  Scenario Outline: AC2-1 Error message for empty fields
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE   |
      | <FUND_BOOKING_CODE> |
    When User add hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    And User click Save button
    Then Error message is pop up
      | ERROR                        |
      | Error!- Invalid Hedge Config |

    Examples: 
      | FUND_BOOKING_CODE | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | 9T19              |      | PLN  | 50         | Interval Days        | 30     |
      | 9T19              | AUD  |      | 50         | Interval Days        | 30     |
      | 9T19              | AUD  | PLN  |            | Interval Days        | 30     |
      | 9T19              | AUD  | PLN  | 50         | Interval Days        |        |
      | 9T19              | AUD  | PLN  | 50         | Specific Date        | 0      |
      |                   | AUD  | PLN  | 50         | Interval Days        | 30     |

  @CI @DEV @QA
  Scenario Outline: AC2-1 Error message for duplicate ccy
    Given Input Fund Booking Code for hedge trade
      | FUND_BOOKING_CODE |
      | 9T19              |
    And User input hedge trade subscription below
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | AUD  | PLN  | 85         | Interval Days        | 10     |
    And User click Save button
    And Input valid FUND and INSTRUMENT_NAME
      | FUND | INSTRUMENT_NAME         |
      | 52C8 | SFX_WM_BOS_1500_UI_TEST |
    And Click the Subscribe button
    And User add hedge trade subscription below
      | CCY1   | CCY2   | PERCENTAGE   | NEXT_AVAILABLE_TENOR   | TENORS   |
      | <CCY1> | <CCY2> | <PERCENTAGE> | <NEXT_AVAILABLE_TENOR> | <TENORS> |
    When User click Save button
    Then Error message is pop up
      | ERROR                                                 |
      | Error!- Duplicate currency pair found in the request. |

    Examples: 
      | CCY1 | CCY2 | PERCENTAGE | NEXT_AVAILABLE_TENOR | TENORS |
      | AUD  | PLN  | 85         | Interval Days        | 30     |
      | PLN  | AUD  | 85         | Interval Days        | 30     |

  @ManualTest
  Scenario: AC2 Error for duplicate dates
    Given User click Add button for Hedge Config
    And User select Specific Date from Next Available Tenors dropdown list
    When Input Tenor below
      | TENOR       |
      | 03-31,03-31 |
    And User click Save button
    Then Error message is pop up
      | ERROR                        |
      | Error!- Invalid Hedge Config |
    And Error message is displayed in text box of tenor date
      | ERROR                |
      | duplicate tenor date |
