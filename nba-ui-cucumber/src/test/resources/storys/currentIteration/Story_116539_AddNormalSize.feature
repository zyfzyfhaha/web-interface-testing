#Story 116537
#As a middle office, I want to navigate to normal size configuration screen when click the menu so that I can update the normal size
#AC1> Given: middle office login Reference Data UI
#When: he clicks the menu of "Normal Order Size"
#Then: he is navigated to "Normal Order Size" screen.
#Story 116539
#As a middle office, I want to edit normal sizes so that I can add new normal sizes in validation
#AC1> Given: middle office has navigated to "Normal Order Size" screen
#When: he clicks add button
#Then: it opens a "add normal size" screen for adding normal order size.
#AC2> Given: middle office has navigated to "Add Normal Size" screen
#When: he selects a pricing service, entered currency pair, a fund short name and normal size for each currency and clicks save
#Then: the normal order size for the pricing service/fund/currency pair is saved.
#AC3> Given: middle office has navigated to "Add Normal Size" screen
#When: he selects a pricing service, entered currency pair, a fund short name but inputs an invalid normal size (<0) and clicks save
#Then: there is error message indicates the normal size is incorrect.
#AC4>Given: middle office has navigated to "Add Normal Size" screen
#When: he selects a pricing service, entered currency pair, a fund short name which already exists and clicks save
#Then: there is error message indicates the currency pair for the fund under the pricing service already exists.
@Regression
Feature: Story_116539 Navigate to normal size and add normal size

  Background: 
    Given user login to Reference data config page
    And navigate to Order Normal Size Page
    And user open Add panel

  @QA @CI @DEV
  Scenario: AC2> Check add Normal Size Page
    When the Add Normal Size panel displayed
    And the value of pricing service list in normal size should be
      | SFX Street FX            |
      | AIR Automated Income     |
      | HPS Hourly Pricing       |
      | SSH Security Settlements |
    And the page include fund and currency pair table
    And user close the add page

  Scenario Outline: AC3> Add new normal order size successful
    When user input fund and normal_size for currency pair
      | Pricing service | <pricing_service> |
      | FUND            | <fund>            |
      | CCY1            | <ccy1>            |
      | CCY2            | <ccy2>            |
      | CCY1_NormalSize | <normal_size1>    |
      | CCY2_NormalSize | <normal_size2>    |
    And there is no same record in database for normal size
    Then user can get Add result after click Save
      | Result | Normal order size is created successfully. |

    @QA @CI @DEV
    Examples: 
      | fund | pricing_service | ccy1 | ccy2 | normal_size1 | normal_size2 |
      | Q5UA | SFX             | USD  | CAD  | 10           | 20           |

    @mock
    Examples: 
      | fund | pricing_service | ccy1 | ccy2 | normal_size1 | normal_size2 |
      | abcd | SFX             | CCY1 | CCY2 | 1            | 2            |

  Scenario Outline: AC4> Add new normal Size for duplicate
    When select Pricing Serivice <pricing_service>
    And user input fund and normal_size for currency pair
      | Pricing service | <pricing_service> |
      | FUND            | <fund>            |
      | CCY1            | <ccy1>            |
      | CCY2            | <ccy2>            |
      | CCY1_NormalSize | <normal_size1>    |
      | CCY2_NormalSize | <normal_size2>    |
    Then user can get Add result after click Save
      | Result | Error!- The record already exists. |
    And user close the add page

    @QA @CI @DEV
    Examples: 
      | fund | pricing_service | ccy1 | ccy2 | normal_size1 | normal_size2 |
      | Q5UA | SFX             | EUR  | JPY  | 1            | 2            |

    @mock
    Examples: 
      | fund | pricing_service | ccy1 | ccy2 | normal_size1 | normal_size2 |
      | YY   | SSH             | AUD  | USD  | 10000        | 20000        |

  Scenario Outline: AC5> Add negative normal Size
    When select Pricing Serivice <pricing_service>
    And user input fund and normal_size for currency pair
      | Pricing service | <pricing_service> |
      | FUND            | <fund>            |
      | CCY1            | <ccy1>            |
      | CCY2            | <ccy2>            |
      | CCY1_NormalSize | <normal_size1>    |
      | CCY2_NormalSize | <normal_size2>    |
    Then user can get Add result after click Save
      | Result | <Result> |
    And user close the add page

    @QA @CI @DEV
    Examples: not input all fields(fund,ccy1,ccy2,ccy1_normalSize,ccy2_normalSize)
      | fund | pricing_service | ccy1 | ccy2 | normal_size1 | normal_size2 | Result                                      |
      |      | SSH             | CAD  | AUD  | 1            | 2            | Save button is disabled                     |
      | Q5UA | Select          | USD  | AUD  | 100          | 200          | - Pricing service is required.              |
      | Q5UA | HPS             |      | CNY  | 1000         | 2000         | Error!- Currency pair is required.          |
      | Q5UA | AIR             | CAD  | CNY  | -1           | 2000         | Error!- Invalid value. Please verify again. |

    @mock
    Examples: pricing_service not select
      | fund | pricing_service | ccy1 | ccy2 | normal_size1 | normal_size2 | Result                         |
      | 1234 | HPS             | USD  | AUD  | @Null        | 3000         | Error!- Form field is invalid. |
