#story 137092
#As a middle office, I want to edit the currency pair deadlines so that I can correct the deadlines.
#AC1> Given a user who has Middle Office Role modifies one currency pair deadline,
#when click save button,
#then new deadline are saved successfully.
#AC2> Given middle office role modifies multiple currency pair deadline,
#when click save button,
#then all of new deadline are saved successfully.
#AC3> Given middle office role enter invalid new deadline(not number),
#when click save button,
#then popped up error message.
#AC4> Given middle office role modifies currency pair deadline,
#when move the cursor,
#then there is a flag indicating the updated currency pair deadline.
#AC5> Given a user IT support/sales trader/Admin role modifies currency pair deadline,
#when click save button,
#then error message popped up indicating no access to modify.
@Regression
Feature: Story_137092 Edit currency pair deadline

  Background: Login to Deadline Page
    Given user login to Reference data config page
    And Navigate to Currency Pair Deadline Page

  Scenario Outline: AC1> Edit one currency pair deadline, save successfully
    Given user add some currency pair
      | Pricing Service | CCY1 | CCY2 | Cutoff |
      | HPS             | CNH  | BHD  | 5000   |
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
      | Pricing_Service | CCY1 | CCY2  | KEY         | DEADLINE |
      | HPS             | CNH  | @Null | HPS_CNH_BHD | 56000    |

  Scenario Outline: AC2> Edit multiple currency pair deadline, save successfully
    Given user add some currency pair
      | Pricing Service | CCY1 | CCY2 | Cutoff |
      | HPS             | EGP  | BHD  | 5000   |
      | HPS             | EGP  | MUR  | 5000   |
    And user prepare currency pair deadline query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    And User click search to query
    When user modify deadline for below data
      | KEY      | <KEY1>      |
      | DEADLINE | <DEADLINE1> |
    And user modify deadline for below data
      | KEY      | <KEY2>      |
      | DEADLINE | <DEADLINE2> |
    And verify new deadline are saved successfully after click save

    @CI @DEV @QA 
    Examples: 
      | Pricing_Service | CCY1 | CCY2  | KEY1        | DEADLINE1 | KEY2        | DEADLINE2 |
      | HPS             | EGP  | @Null | HPS_EGP_BHD | 123400    | HPS_EGP_MUR | 650000    |

  Scenario Outline: AC3> Enter invalid new normal size(not digital)
    Given user add some currency pair
      | Pricing Service   | CCY1   | CCY2   | Cutoff |
      | <Pricing_Service> | <CCY1> | <CCY2> | 500    |
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
    And verify deadline value are not changed
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    
    @CI @DEV @QA 
    Examples: 
      | Pricing_Service | CCY1 | CCY2 | KEY         | DEADLINE | Result                                      |
      | HPS             | ARS  | BDT  | HPS_ARS_BDT | dfsd     | Error!- Form field is invalid.              |
      | HPS             | ARS  | BDT  | HPS_ARS_BDT | -155     | Error!- Invalid value. Please verify again. |
      | HPS             | ARS  | BDT  | HPS_ARS_BDT | 66.78    | Error!- Error parsing the input.            |

  Scenario Outline: AC4> Edit one currency pair deadline, not save
    Given user add some currency pair
      | Pricing Service   | CCY1   | CCY2   | Cutoff |
      | <Pricing_Service> | <CCY1> | <CCY2> | 50000  |
    And user prepare currency pair deadline query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    And User click search to query
    When user modify deadline for below data
      | KEY      | <KEY>      |
      | DEADLINE | <DEADLINE> |
    Then verify deadline value are not changed
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |

    @CI @DEV @QA 
    Examples: 
      | Pricing_Service | CCY1 | CCY2 | KEY         | DEADLINE |
      | HPS             | CLP  | BDT  | HPS_CLP_BDT | 2220     |
