Feature: Release 2.1 It13 Story 187205 Query Pricing Service Validation

  Background: Login to Reference Data Management and navigate to OMS VALIDATION VALIDATION page
    #Service available
    Given User login to Reference Data Management
    And User navigates to OMS VALIDATION VALIDATION Page

  @CI @DEV @QA
  Scenario Outline: Query AC1 Query Pricing Service validation
    Given User select select a Pricing service and a Validation
      | VALIDATION   | PRICING SERVICE   |
      | <VALIDATION> | <PRICING SERVICE> |
    When User click Search button
    Then All matched pricing service validation are displayed and order by Pricing Service and Validation
      | VALIDATION | PRICING SERVICE | PROPERTY | VALUE | LAST MODIFY BY |

    Examples: 
      | VALIDATION                | PRICING SERVICE |
      | Normal Size Validation    | SFX             |
      | Normal Size Validation    |                 |
      | Normal Size Default Value | SSH             |
      |                           |                 |

  @ManualTest
  Scenario Outline: Query AC2 LAST MODIFY TIME is displayed in user's timezone
    Given User in Timezone below
      | TIME ZONE   |
      | <TIME ZONE> |
    When User query pricing service validation
    Then LAST MODIFY TIME is displayed in user's timezone

    Examples: 
      | TIME ZONE |
      | GMT+8     |
      | GMT-5     |
