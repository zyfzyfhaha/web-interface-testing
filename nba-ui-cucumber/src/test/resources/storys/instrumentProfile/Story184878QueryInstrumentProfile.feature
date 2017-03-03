Feature: Instrument Profile Release 2.1 It16 Story 184878 Query Instrument Profile

  Background: Login to Reference Data Management and navigate to INSTRUMENT page
    #Service available
    Given User login to Reference Data Management
    And User navigates to INSTRUMENT Page

  Scenario Outline: AC1 Query Instrument Profile
    Given User enter Pricing Service, Rate Source, Location and Instrument Name
      | INSTRUMENT_NAME   | PRICING_SERVICE_ID   | RATE_SOURCE_ID   | PHY_LOC_ID   |
      | <INSTRUMENT_NAME> | <PRICING_SERVICE_ID> | <RATE_SOURCE_ID> | <PHY_LOC_ID> |
    When User click Instrument Search button
    Then All matched Instrument Profile order by Pricing Service and Instrument Name are displayed in pages with 25 records at most per page
      | INSTRUMENT_NAME   | PRICING_SERVICE_ID   | RATE_SOURCE_ID   | PHY_LOC_ID   | LOCATION_TIME(HHMM)   | CCY_PAIR   | LAST_MODIFY_BY   | LAST_MODIFY_TIME   |
      | <INSTRUMENT_NAME> | <PRICING_SERVICE_ID> | <RATE_SOURCE_ID> | <PHY_LOC_ID> | <LOCATION_TIME(HHMM)> | <CCY_PAIR> | <LAST_MODIFY_BY> | <LAST_MODIFY_TIME> |

    @CI @DEV @QA
    Examples: 
      | INSTRUMENT_NAME | PRICING_SERVICE_ID | RATE_SOURCE_ID | PHY_LOC_ID |
      | SFX WM BOS 0500 | SFX                | WM             | BOS        |
      | WM              | SFX                | WM             | BOS        |
      | 00              |                    | MRK            | LON        |
      | SFX             |                    |                | BRA        |
      | WM MUN          |                    |                |            |
      |                 |                    |                |            |

  @CI @DEV @QA @TEST
  Scenario Outline: AC2 Nagivate to specific page
    Given User enter Pricing Service, Rate Source, Location and Instrument Name
      | PRICING_SERVICE_ID | INSTRUMENT_NAME | RATE_SOURCE_ID | PHY_LOC_ID |
      |                    |                 |                |            |
    And User click Instrument Search button
    When User enter specific page number
      | PAGE   |
      | <PAGE> |
    Then All matched Instrument Profile order by Pricing Service and Instrument Name are displayed in the sepcific page
      | INSTRUMENT_NAME | PRICING_SERVICE_ID | RATE_SOURCE_ID | PHY_LOC_ID | LOCATION_TIME(HHMM)   | CCY_PAIR   | LAST_MODIFY_BY   | LAST_MODIFY_TIME   |
      |                 |                    |                |            | <LOCATION_TIME(HHMM)> | <CCY_PAIR> | <LAST_MODIFY_BY> | <LAST_MODIFY_TIME> |

    @CI @DEV @QA
    Examples: 
      | PAGE        |
      | 1           |
      | MIDDLE      |
      | LAST SECOND |
      | LAST        |
      | 0           |

  @CI @DEV @QA @TEST
  Scenario Outline: AC3 Sort Instrument Profile
    Given User enter Pricing Service, Rate Source, Location and Instrument Name
      | PRICING_SERVICE_ID | INSTRUMENT_NAME | RATE_SOURCE_ID | PHY_LOC_ID |
      |                    |                 |                |            |
    And User click Instrument Search button
    When User click column header to sort Instrument Profile
      | SORT_BY   | ORDER   |
      | <SORT_BY> | <ORDER> |
    Then All matched Instrument Profile are sorted and displayed in pages with 25 records at most per page
      | INSTRUMENT_NAME | PRICING_SERVICE_ID | RATE_SOURCE_ID | PHY_LOC_ID | LOCATION_TIME(HHMM)   | CCY_PAIR   | LAST_MODIFY_BY   | LAST_MODIFY_TIME   | SORT_BY   | ORDER   |
      |                 |                    |                |            | <LOCATION_TIME(HHMM)> | <CCY_PAIR> | <LAST_MODIFY_BY> | <LAST_MODIFY_TIME> | <SORT_BY> | <ORDER> |

    @CI @DEV @QA
    Examples: 
      | SORT_BY         | ORDER |
      | PRICING SERVICE | ASC   |
      | RATE SOURCE     | DESC  |
      | LOCATION        | ASC   |
      | INSTRUMENT NAME | DESC  |

  Scenario Outline: AC4 Query Negative "No Data Found"
    Given User enter Pricing Service, Rate Source, Location and Instrument Name
      | INSTRUMENT_NAME   | PRICING_SERVICE_ID   | RATE_SOURCE_ID   | PHY_LOC_ID   |
      | <INSTRUMENT_NAME> | <PRICING_SERVICE_ID> | <RATE_SOURCE_ID> | <PHY_LOC_ID> |
    When User click Instrument Search button
    Then verify Query return error message
      | Result | No record is found. |

    @CI @DEV @QA
    Examples: 
      | INSTRUMENT_NAME      | PRICING_SERVICE_ID | RATE_SOURCE_ID | PHY_LOC_ID |
      | SFX WM BOS 0500 XXXX | SFX                | WM             | BOS        |

  Scenario Outline: AC6 Query Instrument Profile Details
    Given User enter Pricing Service, Rate Source, Location and Instrument Name
      | INSTRUMENT_NAME   | PRICING_SERVICE_ID   | RATE_SOURCE_ID   | PHY_LOC_ID   |
      | <INSTRUMENT_NAME> | <PRICING_SERVICE_ID> | <RATE_SOURCE_ID> | <PHY_LOC_ID> |
    When User click Instrument Search button
    And User Click Detail button
    Then The instrument profile details are displayed
      | INSTRUMENT_NAME   |
      | <INSTRUMENT_NAME> |

    @CI @DEV @QA
    Examples: 
      | INSTRUMENT_NAME  | PRICING_SERVICE_ID | RATE_SOURCE_ID | PHY_LOC_ID |
      | SFX MRK BOS 0000 |                    |                |            |
      | SFX MRK BOS 0100 |                    |                |            |
