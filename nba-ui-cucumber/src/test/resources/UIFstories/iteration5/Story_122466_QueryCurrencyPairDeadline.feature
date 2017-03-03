#Story 122466 As a middle office, I want to query the currency pair deadlines so that I can review the deadlines
#AC1 > Given: middle office has navigated to "Currency pair deadline" screen, When: He does not input any criteria and click search button, Then: All currency pairs can be listed.
#AC2 > Given: middle office has navigated to "Currency pair deadline" screen, When: He only inputs pricing service and click search button, Then: All currency pairs in input pricing service can be listed.
#AC3 > Given: middle office has navigated to "Currency pair deadline" screen, When: He inputs individual currency or combined currencies without pricing service input, Then: All currency pairs in input criteria can be listed.
#AC4 > Given: middle office has navigated to "Currency pair deadline" screen, When: He inputs combined criteria, Then: All currency pairs in input criteria can be listed.
#AC5 > Given: middle office has navigated to "Currency pair deadline" screen, When: He clicks the currency dropdown list, Then: All the currencies can be listed in the dropdown list.
#AC6 > Given: middle office has navigated to "Currency pair deadline" screen, When: He chooses the same currency in the criteria and click search, Then: There is the error message indicates the currency pair can not be duplicated.
#AC7 > Given: IT support has navigated to "Currency pair deadline" screen, When: He inputs criteria, Then: All currency pairs in input criteria can be listed.
@Regression
Feature: Story_122466 Query Currency Pair Deadline: Display currency pair deadline when search by pricing service and currency pair in query currency pair deadline screen

  Background: Login to Reference data configuration
    Given user login to Reference data config page
    And Navigate to Currency Pair Deadline Page

  Scenario Outline: AC1> Query by nothing, then list all the deadline
    Given user add some currency pair
      | Pricing Service | CCY1 | CCY2 | Cutoff |
      | HPS             | CNH  | BHD  | 5000   |
    And user prepare currency pair deadline query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    When User click search to query
    Then Verify Currency Pair Deadline Query IDF Service and UI results are equal

    @CI @DEV @QA @UAT
    Examples: 
      | Pricing_Service | CCY1  | CCY2  |
      | @Null           | @Null | @Null |

  Scenario Outline: AC2> Query by pricing service, then list related deadline infomation
    Given user add some currency pair
      | Pricing Service | CCY1 | CCY2 | Cutoff |
      | SSH             | CNH  | BHD  | 5000   |
    And user prepare currency pair deadline query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    When User click search to query
    Then Verify Currency Pair Deadline Query IDF Service and UI results are equal

    @CI @DEV @QA @UAT
    Examples: 
      | Pricing_Service | CCY1  | CCY2  |
      | SSH             | @Null | @Null |

  Scenario Outline: AC3> Query by currency1 and currency2, then list related deadline infomation
    Given user add some currency pair
      | Pricing Service | CCY1 | CCY2 | Cutoff |
      | HPS             | EUR  | KRW  | 5000   |
      | AIR             | PHP  | USD  | 5000   |
    And user prepare currency pair deadline query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    When User click search to query
    Then Verify Currency Pair Deadline Query IDF Service and UI results are equal
    
    @CI @DEV @QA @UAT
    Examples: 
      | Pricing_Service | CCY1  | CCY2  |
      | @Null           | EUR   | @Null |
      | @Null           | @Null | KRW   |
      | @Null           | PHP   | USD   |

  Scenario Outline: AC4> Query by pricing service, currency1 and currency2, then list related deadline infomation
    Given user add some currency pair
      | Pricing Service | CCY1 | CCY2 | Cutoff |
      | SFX             | AUD  | CAD  | 5000   |
    And user prepare currency pair deadline query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    When User click search to query
    Then Verify Currency Pair Deadline Query IDF Service and UI results are equal

    @CI @DEV @QA @UAT
    Examples: 
      | Pricing_Service | CCY1  | CCY2  |
      | SFX             | AUD   | @Null |
      | SFX             | @Null | CAD   |
      
    @CI @DEV @QA @UAT @quick
    Examples: 
      | Pricing_Service | CCY1  | CCY2  |
      | SFX             | AUD   | CAD   |

  Scenario Outline: AC5> Choose same currency1 and currency2, then generate error message indicate the currency is duplicated
    Given user prepare currency pair deadline query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    When User click search to query
    Then user can get result after click search
      | Result | <Result> |

    @CI @DEV @QA @UAT
    Examples: 
      | Pricing_Service | CCY1 | CCY2 | Result                                          |
      | @Null           | AED  | AED  | Error!- Currency should not be duplicated. |

  Scenario Outline: AC6> Query Negative "No Data Found"
    Given user prepare currency pair deadline query data
      | Pricing Service | <Pricing_Service> |
      | CCY1            | <CCY1>            |
      | CCY2            | <CCY2>            |
    When User click search to query
    Then verify Query return error message
      | Result | <Result> |

    @CI @DEV @QA @UAT
    Examples: 
      | Pricing_Service | CCY1 | CCY2 | Result              |
      | AIR             | SEK  | ILS  | No record is found. |
