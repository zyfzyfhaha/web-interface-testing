@Role
Feature: Role authorization access control on UI framework service

  #------------------------Order Size Tolerance-----------------------------#
  #Query
  Scenario Outline: AC1> Middle office or Risk manager role query order size tolerance successful
    Given user login to Reference data page
      | RoleType | <RoleType> |
    When Navigate to Order Size Tolerance Page
    And user prepare order tolerance query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    And User click search to query
    Then Verify IDF Service and UI results are equal

    @CI @DEV
    Examples: 
      | RoleType      | CCY1  | CCY2 | Pricing_Service |
      | Middle_office | @Null | AUD  | @Null           |
      | Risk_manager  | @Null | AUD  | @Null           |
      | IT_support    | @Null | AUD  | @Null           |
      | Sales_trader  | @Null | AUD  | @Null           |
      | Admin         | @Null | AUD  | @Null           |

  #Add Order Size Tolerance Successful
  Scenario Outline: AC2> Middle office or Risk manager role add order size tolerance successful
    Given user login to Reference data page
      | RoleType | <RoleType> |
    When Navigate to Order Size Tolerance Page
    When user open Add panel
    And user select and input Pricing Service and currency
      | Pricing service | SSH Security Settlements |
      | CCY1            | <CCY1>                   |
      | CCY2            | <CCY2>                   |
    And tier is setup for CCY1
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | 2000      | 1      |
      | 2000        | 3000      | 1.5    |
      | 3000        | Max       | 1.2    |
    And tier is setup for CCY2
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | Max       | 1.3    |
    And there is no same record in database
    Then user can get Add result after click Save
      | Result | Order size tolerance is created successfully. |

    @CI @DEV
    Examples: 
      | RoleType      | CCY1 | CCY2 |
      | Middle_office | AUD  | CAD  |
      | Risk_manager  | AUD  | USD  |

  #Add Order Size Tolerance Failed
  Scenario Outline: AC4> IT support role or Sales trader role or Admin role add order size tolerance failed
    Given user login to Reference data page
      | RoleType | <RoleType> |
    When Navigate to Order Size Tolerance Page
    And user open Add panel
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
    Then popup warning message and save failed after click Save
      | Result | Error!- Your ID is not privileged to do this operation. |

    @CI @DEV @UAT
    Examples: 
      | RoleType     |
      | IT_support   |
      | Sales_trader |
      | Admin        |

  #Edit Order Size Tolerence successful
  Scenario Outline: AC2 Update minimum size and tiers should be successful
    Given user login to Reference data page
      | RoleType | <RoleType> |
    And Navigate to Order Size Tolerance Page
    And user prepare order tolerance query data
      | Pricing Service | HPS |
      | CCY1            | CHF |
      | CCY2            | AUD |
    And User click search to query
    And User click view detail for first record
    And click Edit button for detail panel
    When user update minimum size value
      | CCY1 Minimum size | 0 |
      | CCY2 Minimum size | 0 |
    And user update tiers for CCY1
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | MAX       | 1.1    |
    And user update tiers for CCY2
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | 500       | 1.5    |
      | 500         | MAX       | 1.3    |
    Then save the tolerance should be ok
    And User click view detail for first record
    And the value refreshed should reflect the update

    @CI @DEV @UAT
    Examples: 
      | RoleType      |
      | Middle_office |
      | Risk_manager  |

  #Edit Order Size Tolerence Failed
  Scenario Outline: AC2 Update minimum size and tiers should be failed
    Given user login to Reference data page
      | RoleType | <RoleType> |
    And Navigate to Order Size Tolerance Page
    And user prepare order tolerance query data
      | Pricing Service | HPS |
      | CCY1            | CHF |
      | CCY2            | AUD |
    And User click search to query
    And User click view detail for first record
    And click Edit button for detail panel
    When user update minimum size value
      | CCY1 Minimum size | 0 |
      | CCY2 Minimum size | 0 |
    And user update tiers for CCY1
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | MAX       | 1.1    |
    And user update tiers for CCY2
      | Range Start | Range End | Factor |
      | 0           | 0         | 0      |
      | 0           | 500       | 1.5    |
      | 500         | MAX       | 1.3    |
    Then save the tolerance should get error
      | result | Error!- Your ID is not privileged to do this operation. |

    @CI @DEV @UAT
    Examples: 
      | RoleType     |
      | IT_support   |
      | Sales_trader |
      | Admin        |

  #-----------------------------Normal Order Size -----------------------------#
  #Query And Edit
  @New
  Scenario Outline: AC7> Query and Edit normal size
    Given user login to Reference data page
      | RoleType | <RoleType> |
    And navigate to Order Normal Size Page
    When user prepare normal size query data
      | IM   | <IM>   |
      | FUND | <FUND> |
    And User click search to query
    And user modify normal size for below data
      | KEY              | <KEY> |
      | CCY1_NORMAL_SIZE | 0     |
      | CCY2_NORMAL_SIZE | 0     |
    Then user can get normal size result after click Save
      | Result | <Result> |

    @CI
    Examples: 
      | RoleType      | IM        | FUND | KEY         | Result                                                  |
      | Middle_office | B T AND T | ZN71 | SSH_CHF_BDT | Normal order size is updated successfully.              |
      | Risk_manager  | B T AND T | ZN71 | SSH_CHF_BDT | Normal order size is updated successfully.              |
      | IT_support    | B T AND T | ZN71 | SSH_CHF_BDT | Error!- Your ID is not privileged to do this operation. |
      | Sales_trader  | B T AND T | ZN71 | SSH_CHF_BDT | Error!- Your ID is not privileged to do this operation. |
      | Admin         | B T AND T | ZN71 | SSH_CHF_BDT | Error!- Your ID is not privileged to do this operation. |

    @DEV
    Examples: 
      | RoleType      | IM | FUND  | KEY         | Result                                                  |
      | Middle_office | AI | @Null | SFX_USD_CAD | Normal order size is updated successfully.              |
      | Risk_manager  | AI | @Null | SFX_USD_CAD | Normal order size is updated successfully.              |
      | IT_support    | AI | @Null | SFX_USD_CAD | Error!- Your ID is not privileged to do this operation. |
      | Sales_trader  | AI | @Null | SFX_USD_CAD | Error!- Your ID is not privileged to do this operation. |
      | Admin         | AI | @Null | SFX_USD_CAD | Error!- Your ID is not privileged to do this operation. |

    @QA @UAT
    Examples: 
      | RoleType      | IM  | FUND  | KEY         | Result                                                  |
      | Middle_office | MIT | @Null | SFX_USD_CAD | Normal order size is updated successfully.              |
      | Risk_manager  | MIT | @Null | SFX_USD_CAD | Normal order size is updated successfully.              |
      | IT_support    | MIT | @Null | SFX_USD_CAD | Error!- Your ID is not privileged to do this operation. |
      | Sales_trader  | MIT | @Null | SFX_USD_CAD | Error!- Your ID is not privileged to do this operation. |
      | Admin         | MIT | @Null | SFX_USD_CAD | Error!- Your ID is not privileged to do this operation. |

  #Add Normal Size Successful
  Scenario Outline: AC3> Middle office or Risk manager role add new normal order size successful
    Given user login to Reference data page
      | RoleType | <RoleType> |
    When navigate to Order Normal Size Page
    And user open Add panel
    And user input fund and normal_size for currency pair
      | Pricing service | <pricing_service> |
      | FUND            | <fund>            |
      | CCY1            | <ccy1>            |
      | CCY2            | <ccy2>            |
      | CCY1_NormalSize | <normal_size1>    |
      | CCY2_NormalSize | <normal_size2>    |
    Then user can get result after click Save
      | Result | Normal order size is created successfully. |

    @CI @DEV
    Examples: 
      | RoleType      | fund | pricing_service | ccy1 | ccy2 | normal_size1 | normal_size2 |
      | Middle_office | Q5UA | SSH             | JPY  | MXN  | 100          | 200          |
      | Risk_manager  | Q5UA | AIR             | JPY  | AUD  | 10           | 20           |

  #Add Normal Size Failed
  Scenario Outline: AC5> IT support or Sales trader role or Admin role add normal order size failed
    Given user login to Reference data page
      | RoleType | <RoleType> |
    When navigate to Order Normal Size Page
    And user open Add panel
    And user input fund and normal_size for currency pair
      | Pricing service | <pricing_service> |
      | FUND            | <fund>            |
      | CCY1            | <ccy1>            |
      | CCY2            | <ccy2>            |
      | CCY1_NormalSize | <normal_size1>    |
      | CCY2_NormalSize | <normal_size2>    |
    Then popup warning message and save failed after click Save
      | Result | Error!- Your ID is not privileged to do this operation. |

    @CI @DEV @UAT
    Examples: 
      | RoleType     | fund | pricing_service | ccy1 | ccy2 | normal_size1 | normal_size2 |
      | IT_support   | Q5UA | SFX             | JPY  | CAD  | 10           | 20           |
      | Sales_trader | Q5UA | AIR             | JPY  | AUD  | 10           | 20           |
      | Admin        | Q5UA | AIR             | CAD  | AUD  | 10           | 20           |

  #-----------------------------CCY Pair Deadline -----------------------------#
  #Query Currency Pair Deadline
  Scenario Outline: Query currency pair deadline
    Given user login to Reference data page
      | RoleType | <RoleType> |
    And Navigate to Currency Pair Deadline Page
    And user prepare currency pair deadline query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    And User click search to query
    Then Verify Currency Pair Deadline Query IDF Service and UI results are equal

    @CI @DEV @QA @UAT
    Examples: 
      | RoleType      | Pricing_Service | CCY1  | CCY2  |
      | Middle_office | SFX             | @Null | @Null |
      | IT_support    | SFX             | @Null | @Null |
      | Sales_trader  | SFX             | @Null | @Null |
      | Admin         | SFX             | @Null | @Null |

  #Add Currency Pair Deadline
  @New
  Scenario Outline: AC8 Add currency pair deadline
    Given user login to Reference data page
      | RoleType | <RoleType> |
    And Navigate to Currency Pair Deadline Page
    And user open Add panel
    When User Prepare Add Currency Pair Cutoff data
      | Pricing Service | SSH |
      | CCY1            | MXN |
      | CCY2            | RUB |
      | Cutoff          | 101 |
    Then user can get Add result after click Save
      | Result | <Result> |

    @CI @DEV @QA @UAT
    Examples: 
      | RoleType      | Result                                                  |
      | Middle_office | Error!- The record already exists.                      |
      | IT_support    | Error!- Your ID is not privileged to do this operation. |
      | Sales_trader  | Error!- Your ID is not privileged to do this operation. |
      | Admin         | Error!- Your ID is not privileged to do this operation. |

  #Delete Currency Pair Deadline
  @New
  Scenario Outline: AC9> Delete Currency Pair Deadline
    Given user login to Reference data page
      | RoleType | <RoleType> |
    And Navigate to Currency Pair Deadline Page
    And user add some currency pair
      | Pricing Service | CCY1 | CCY2 | Cutoff |
      | HPS             | AED  | CHF  | 1000   |
    When user prepare currency pair deadline query data
      | Pricing Service | HPS   |
      | CCY1            | AED   |
      | CCY2            | @Null |
    And User click search to query
    Then user select currency pair to delete for below data
      | KEY | <KEY> |
    And pop up confirm message after click delete
      | DELETE | ok |
    And pop up error message after confirm ok
      | Result | <Result> |

    @CI @DEV @QA @UAT
    Examples: 
      | RoleType     | KEY         | Result                                                  |
      | IT_support   | HPS_AED_CHF | Error!- Your ID is not privileged to do this operation. |
      | Sales_trader | HPS_AED_CHF | Error!- Your ID is not privileged to do this operation. |
      | Admin        | HPS_AED_CHF | Error!- Your ID is not privileged to do this operation. |

  @New
  Scenario Outline: AC10> selects one currency pair, pop up an warning message for confirm, choose ok
    Given user login to Reference data page
      | RoleType | <RoleType> |
    And Navigate to Currency Pair Deadline Page
    Given user add some currency pair
      | Pricing Service | CCY1 | CCY2 | Cutoff |
      | HPS             | AED  | CHF  | 1000   |
    And user prepare currency pair deadline query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    And User click search to query
    When user select currency pair to delete for below data
      | KEY | <KEY> |
    Then pop up confirm message after click delete
      | DELETE | ok |
    And verify currency pair are deleted successfully
      | KEY | <KEY> |

    @CI @DEV @QA @UAT
    Examples: 
      | RoleType      | Pricing_Service | CCY1  | CCY2  | KEY         |
      | Middle_office | HPS             | @Null | @Null | HPS_AED_CHF |

  #Edit Currency Pair Deadline
  Scenario Outline: AC11> Middle office or Risk Manager edit one currency pair deadline, save successfully
    Given user login to Reference data page
      | RoleType | <RoleType> |
    And Navigate to Currency Pair Deadline Page
    Given user add some currency pair
      | Pricing Service | CCY1 | CCY2 | Cutoff |
      | HPS             | ARS  | BDT  | 5000   |
    And user prepare currency pair deadline query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    And User click search to query
    When user modify deadline for below data
      | KEY      | <KEY>      |
      | DEADLINE | <DEADLINE> |
    And verify new deadline are saved successfully after click save

    @CI @DEV @QA
    Examples: 
      | RoleType      | Pricing_Service | CCY1 | CCY2  | KEY         | DEADLINE |
      | Middle_office | HPS             | ARS  | @Null | HPS_ARS_BDT | 98710    |

  Scenario Outline: AC12> IT_support or Sales_trader or Admin
    Given user login to Reference data page
      | RoleType | <RoleType> |
    And Navigate to Currency Pair Deadline Page
    Given user prepare currency pair deadline query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    And User click search to query
    When user modify deadline for below data
      | KEY      | <KEY>      |
      | DEADLINE | <DEADLINE> |
    Then user get error message after click save
      | Result | <Result> |

    @CI @DEV @QA
    Examples: 
      | RoleType     | Pricing_Service | CCY1 | CCY2 | KEY         | DEADLINE | Result                                                  |
      | IT_support   | HPS             | ARS  | BDT  | HPS_ARS_BDT | 1000123  | Error!- Your ID is not privileged to do this operation. |
      | Sales_trader | HPS             | ARS  | BDT  | HPS_ARS_BDT | 2000123  | Error!- Your ID is not privileged to do this operation. |
      | Admin        | HPS             | ARS  | BDT  | HPS_ARS_BDT | 3000123  | Error!- Your ID is not privileged to do this operation. |

  #-----------------------------Trader Mapping -----------------------------#
  #Add trader mapping
  Scenario Outline: AC6> input data GMBH_REUTERS_ID and SSBT_REUTERS_ID pair are exist, add trader mapping failed for duplication
    Given user login to Reference data page
      | RoleType | <RoleType> |
    When Navigate to Trader Mapping Page
    And user open Add panel
    When User Prepare Add Trader Id mapping data
      | GMBH_REUTERS_ID | MT1 |
      | SSBT_REUTERS_ID | LT1 |
      | GMBH_WSS_ID     | BMI |
      | SSBT_WSS_ID     | LMR |
      | CITY            | LON |
    Then user can get Add result after click Save
      | Result | <Result> |

    @CI @DEV @UAT @QA
    Examples: 
      | RoleType      | Result                                                   |
      | Middle_office | Error!- Internal Service Error: Duplicated record exists |
      | IT_support    | Error!- Your ID is not privileged to do this operation.  |
      | Admin         | Error!- Your ID is not privileged to do this operation.  |
