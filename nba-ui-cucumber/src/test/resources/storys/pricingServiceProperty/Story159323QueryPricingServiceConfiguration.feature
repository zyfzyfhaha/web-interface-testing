Feature: Release 2.0.2 It14 Story 159323 Query Pricing Service Configuration

  @CI @DEV @QA
  Scenario: AC1 Query Pricing Service
    Given User login to Reference Data Management
    When User navigates to Prcing Service Page
    Then All Pricing Service and the detailed configuration are displayed order by PRICING_SERVICE_ID and LAST MODIFY TIME is in user time zone
      | PRICING_SERVICE_ID   | SERVICE_FULL_NAME   | HEDGE_BROKER_CODE   | ETC   |
      | <PRICING_SERVICE_ID> | <SERVICE_FULL_NAME> | <HEDGE_BROKER_CODE> | <ETC> |
