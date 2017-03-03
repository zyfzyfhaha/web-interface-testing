#Story 122467 As a middle office, I want to add a currency pair deadline so that I can setup the deadlines for validation
#AC1> Given middle office selects pricing service, entered currency pair,  deadline, When clicks save button, Then  the new currency pair deadline is saved successfully.
#AC2> Given middle office add an existing currency pair deadline, When clicks save button, Then error message is popped up.
#AC3> Given middle office entered an invalid deadline or missing required fields (eg. deadline < 0), When clicks save button, Then error message is popped up.
#AC4> Given not has middle office selects pricing service , entered currency pair,  deadline, When clicks save button, Then error message popped up indicating no access to add.
@Regression
Feature: Story_122467 Currency Pair Deadline Configuration - Add currency pair deadline

  Background: Login to Reference data configuration
    Given user login to Reference data config page
    And Navigate to Currency Pair Deadline Page
    And user open Add panel

  Scenario Outline: AC1 input valid data, add Currency Pair Cutoff successful
    When User Prepare Add Currency Pair Cutoff data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
      | Cutoff          | <Cutoff>          |
    And there is no same Deadline in database
    Then user can get Add result after click Save
      | Result | <Result> |

    @CI @DEV @quick
    Examples: 
      | Pricing_Service | CCY1 | CCY2 | Cutoff | Result                                          |
      | SSH             | MXN  | RUB  | 0      | Currency pair deadline is created successfully. |
      | SSH             | MXN  | RUB  | 101    | Currency pair deadline is created successfully. |

    @QA @UAT
    Examples: 
      | Pricing_Service | CCY1 | CCY2 | Cutoff | Result                                          |
      | SSH             | CHF  | RUB  | 0      | Currency pair deadline is created successfully. |
      | SSH             | MXN  | RUB  | 101    | Currency pair deadline is created successfully. |

  Scenario Outline: AC2 input same Currency Pair Cutoff will get duplicate message
    When User Prepare Add Currency Pair Cutoff data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
      | Cutoff          | <Cutoff>          |
    #And there is same Deadline in database
    Then user can get Add result after click Save
      | Result | <Result> |

    @CI @DEV @quick
    Examples: 
      | Pricing_Service | CCY1 | CCY2 | Cutoff | Result                             |
      | SSH             | MXN  | RUB  | 101    | Error!- The record already exists. |
      | SSH             | RUB  | MXN  | 59     | Error!- The record already exists. |

    @QA @UAT
    Examples: 
      | Pricing_Service | CCY1 | CCY2 | Cutoff | Result                             |
      | SSH             | MXN  | RUB  | 101    | Error!- The record already exists. |
      | SSH             | RUB  | MXN  | 59     | Error!- The record already exists. |

  Scenario Outline: AC3 input an invalid deadline or missing required fields, add cutoff failed
    When User Prepare Add Currency Pair Cutoff data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
      | Cutoff          | <Cutoff>          |
    Then user can get Add result after click Save
      | Result | <Result> |

    @CI @DEV @QA @UAT
    Examples: when missing required field
      | Pricing_Service          | CCY1 | CCY2 | Cutoff | Result                               |
      | SSH                      | MXN  | RUB  |        | Error!- Form field is invalid.       |
      | SSH                      | MXN  |      | 30     | Error!- Currency pair is required.   |
      | SSH                      |      | RUB  | 30     | Error!- Currency pair is required.   |
      | Select a Pricing Service | MXN  | RUB  | 30     | Error!- Pricing service is required. |

    @CI @DEV @QA @UAT
    Examples: when some field is invalid
      | Pricing_Service | CCY1 | CCY2 | Cutoff  | Result                                     |
      | SSH             | MXN  | RUB  | -1      | Error!- Form field is invalid.             |
      | SSH             | MXN  | RUB  | -\'11$% | Error!- Form field is invalid.             |
      | SSH             | MXN  | MXN  | 39      | Error!- Currency should not be duplicated. |
