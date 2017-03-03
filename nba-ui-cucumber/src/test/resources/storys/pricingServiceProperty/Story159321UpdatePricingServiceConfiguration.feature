Feature: Release 2.0.2 It14 Story 159321 Update Pricing Service Configuration

  Scenario Outline: AC1 Update Pricing Service Configuration
    Given User login to Reference Data Management
    And User navigates to Prcing Service Page
    When User update pricing service property value
      | PRICING_SERVICE_ID   | HEDGE_BROKER_CODE   |
      | <PRICING_SERVICE_ID> | <HEDGE_BROKER_CODE> |
    And User click pricing service configuration Save button
    Then Updated Pricing Service Configuration are displayed with updated value with LAST MODIFY TIME in user time zone and other Pricing Service Configuration are not updated
      | PRICING_SERVICE_ID   | HEDGE_BROKER_CODE   |
      | <PRICING_SERVICE_ID> | <HEDGE_BROKER_CODE> |

    @CI @DEV @QA
    Examples: 
      | PRICING_SERVICE_ID | HEDGE_BROKER_CODE                                           |
      | SFX                | SFX-CEH                                                     |
      | SFX,SSH            | SFX-CEHH,SSH-~`!@#$%^&*()-_=+[{]}\|\\;:'"<.>/?TESTBROKERCOD |
      | SFX,SSH            | SFX-CEH,SSH-                                                |
