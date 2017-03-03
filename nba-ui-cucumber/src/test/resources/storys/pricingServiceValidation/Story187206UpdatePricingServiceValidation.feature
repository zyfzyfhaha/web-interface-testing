Feature: Release2.1 It13 Story 187206 Update Pricing Service Validation

  Background: Login to Reference Data Management and navigate to OMS VALIDATION VALIDATION page
    #Service available
    Given User login to Reference Data Management
    And User navigates to OMS VALIDATION VALIDATION Page

  @CI @DEV @QA
  Scenario Outline: Update AC1 Update Pricing Service Validation Value
    Given User select select a Pricing service and a Validation
      | VALIDATION   | PRICING SERVICE   |
      | <VALIDATION> | <PRICING SERVICE> |
    And User click Search button
    When User update value
      | VALUE   |
      | <VALUE> |
    And User click Validation Save button
    Then Updated Pricing Service Validation are displayed with updated value and other Pricing Service validation are not updated
      | VALIDATION | PRICING SERVICE | PROPERTY | VALUE | LAST MODIFY BY | LAST MODIFY TIME |

    Examples: 
      | VALIDATION             | PRICING SERVICE | VALUE                                                        |
      | Normal Size Validation | SFX             | Normal Size Validation-SFX-Y                                 |
      | Normal Size Validation |                 | Normal Size Validation-SFX-N, Normal Size Validation-SSH-    |
      |                        | SSH             | Normal Size Validation-SSH-y,Normal Size Default Value-HPS-0 |
      |                        |                 | Normal Size Default Value-HPS-1                              |
