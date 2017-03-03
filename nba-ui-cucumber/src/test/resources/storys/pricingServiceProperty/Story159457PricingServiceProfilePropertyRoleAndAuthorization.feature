Feature: Release 2.0.2 It14 Story 159457 Pricing Service Profile Property Role and Authorization
#AC1 Sales Trader can view Pricing Service Property only
#AC2 IT Support can view Pricing Service Property only
#AC3 Middle Office can view and update Pricing Service Property
#AC4 Onboarding can view and update Pricing Service Property
#AC5 Risk manager can view and update Pricing Service Property

  @ManualTest
  Scenario: AC1 Sales Trader can view Pricing Service Property only
    Given User login to Reference Data Management as Sales Trader
    When User navigates to Prcing Service Page
    Then All Pricing Service and the detailed configuration are displayed order by PRICING_SERVICE_ID and LAST MODIFY TIME is in user time zone
    When User update pricing service property
    Then Error message "Error! - Your ID is not privileged to do this operation." is pop up

   @ManualTest
  Scenario: AC2 IT Support can view Pricing Service Property only
    Given User login to Reference Data Management as IT Support
    When User navigates to Prcing Service Page
    Then All Pricing Service and the detailed configuration are displayed order by PRICING_SERVICE_ID and LAST MODIFY TIME is in user time zone
    When User update pricing service property
    Then Error message "Error! - Your ID is not privileged to do this operation." is pop up

 
    @ManualTest
  Scenario: AC3 Middle Office can view and update Pricing Service Property
    Given User login to Reference Data Management as Middle Office
    When User navigates to Prcing Service Page
    Then All Pricing Service and the detailed configuration are displayed order by PRICING_SERVICE_ID and LAST MODIFY TIME is in user time zone
    When User update pricing service property
    Then Pricing Service Property is updated

    @ManualTest
  Scenario: AC4 Onboarding can view and update Pricing Service Property
    Given User login to Reference Data Management as Onboarding
    When User navigates to Prcing Service Page
    Then All Pricing Service and the detailed configuration are displayed order by PRICING_SERVICE_ID and LAST MODIFY TIME is in user time zone
    When User update pricing service property
    Then Pricing Service Property is updated

    @ManualTest
  Scenario: AC5 Risk manager can view and update Pricing Service Property
    Given User login to Reference Data Management as Risk manager
    When User navigates to Prcing Service Page
    Then All Pricing Service and the detailed configuration are displayed order by PRICING_SERVICE_ID and LAST MODIFY TIME is in user time zone
    When User update pricing service property
    Then Pricing Service Property is updated
      