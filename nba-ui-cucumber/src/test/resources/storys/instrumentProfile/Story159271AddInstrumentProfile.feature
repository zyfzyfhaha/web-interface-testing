Feature: Instrument Profile Release 2.1 It16 Story 159271 Query Instrument Profile

  Background: Login to Reference Data Management and navigate to INSTRUMENT page
    #Service available
    Given User login to Reference Data Management
    And User navigates to INSTRUMENT Page

  @CI @DEV @QA
  Scenario: AC1 Check page of Add Instrument Profile
    When User click add instrument profile button and navigate to add instrument panel
    Then The value of pricing service list should be the combination of pricing Service id and full name

  @CI @DEV @QA
  Scenario Outline: AC2 Add Instrument Profile
    Given User click add instrument profile button and navigate to add instrument panel
    And Input instrument profile details below
      | INSTRUMENT_NAME   | PRICING_SERVICE_ID   | PHY_LOC_ID   | RATE_SOURCE_ID   | LOCATION_TIME(HHMM)   | CCY1_CCY2   |
      | <INSTRUMENT_NAME> | <PRICING_SERVICE_ID> | <PHY_LOC_ID> | <RATE_SOURCE_ID> | <LOCATION_TIME(HHMM)> | <CCY1_CCY2> |
    When User click add instrument profile save button
    Then Message box with Info Instrument is created successfully pops up
      | Result | Instrument is created successfully. |
    And The instrument is saved successfully and can be viewed in UI
      | INSTRUMENT_NAME   | PRICING_SERVICE_ID   | PHY_LOC_ID   | RATE_SOURCE_ID   | LOCATION_TIME(HHMM)   | CCY1_CCY2   |
      | <INSTRUMENT_NAME> | <PRICING_SERVICE_ID> | <PHY_LOC_ID> | <RATE_SOURCE_ID> | <LOCATION_TIME(HHMM)> | <CCY1_CCY2> |

    Examples: 
      | INSTRUMENT_NAME              | PRICING_SERVICE_ID | PHY_LOC_ID | RATE_SOURCE_ID | LOCATION_TIME(HHMM) | CCY1_CCY2              |
      | SFX WM BOS 0000 UI ADD TEST  | SFX                | BOS        | WM             | 0000                | AUDUSD                 |
      | INSTRUMENT UI ADD            | SFX                | LON        | MRK            | 1200                | AUDUSD, AUDPHP, EURPLN |
      | SFX MRK LON 1200 UI ADD TEST | SFX                | LON        | MRK            | 1200                | AUDUSD                 |

  @CI @DEV @TESTLOCAL
  Scenario Outline: AC3 Exception when add Instrument Profile
    Given User click add instrument profile button and navigate to add instrument panel
    And Input instrument profile details below
      | INSTRUMENT_NAME   | PRICING_SERVICE_ID   | PHY_LOC_ID   | RATE_SOURCE_ID   | LOCATION_TIME(HHMM)   | CCY1_CCY2   |
      | <INSTRUMENT_NAME> | <PRICING_SERVICE_ID> | <PHY_LOC_ID> | <RATE_SOURCE_ID> | <LOCATION_TIME(HHMM)> | <CCY1_CCY2> |
    When User click add instrument profile save button
    Then Message box with error message pops up
      | Result | <ERROR> |

    Examples: 
      | ERROR                                                        | INSTRUMENT_NAME                                                                                       | PRICING_SERVICE_ID | PHY_LOC_ID | RATE_SOURCE_ID | LOCATION_TIME(HHMM) | CCY1_CCY2              |
      | Error!- Instrument Name is required.                         |                                                                                                       | SFX                | BOS        | WM             | 0000                | AUDUSD                 |
      | Error!- Internal server error! Please contact administrator. | TEST_INSTRUMENT_NAM1TEST_INSTRUMENT_NAM2TEST_INSTRUMENT_NAM3TEST_INSTRUMENT_NAM4TEST_INSTRUMENT_NAM56 | SFX                | LON        | MRK            | 1200                | AUDUSD, AUDPHP, EURPLN |
      | Error!- Pricing service is required.                         | SFX MRK LON 1200 UI ADD TEST                                                                          |                    | LON        | MRK            | 1200                | AUDUSD                 |
