# Manual Test for Role Authorization
Feature: Instrument Profile Rlease 2.1 It16 Story163615 Role Authorization

  Background: 
    #UI available
    Given Test environment is ready for UI

  Scenario Outline: AC1: GS role Read only (Global Service)
    Given Login to UI with the account which has GS Read role
    When Navigate to INSTRUMENT - INSTRUMENT PROFILE page
    And Select Pricing Service SFX and click the Search button
    Then There is no record be searched out
    And Select Pricing Service SSH and click the Search button
    Then The records should be displayed as properly
    And Navigate to CLIENT PROFILE - HEDGE TRADE page
    And Enter a valid FUND and INSTRUMENT and click the subscribe button
    Then The detail should be read only

  Scenario Outline: AC2: GM role Read only (Sales Trader)
    Given Login to UI with the account which has GM Read role
    When Navigate to INSTRUMENT - INSTRUMENT PROFILE page
    And Select Pricing Service SFX and click the Search button
    Then The records should be displayed as properly
    And Select Pricing Service SSH and click the Search button
    Then The records should be displayed as properly
    And Navigate to CLIENT PROFILE - HEDGE TRADE page
    And Enter a valid FUND and INSTRUMENT and click the subscribe button
    Then The detail should be read only

  Scenario Outline: AC3: GM role Read only (Onboarding)
    Given Login to UI with the account which has GM Read role
    When Navigate to INSTRUMENT - INSTRUMENT PROFILE page
    And Select Pricing Service SFX and click the Search button
    Then The records should be displayed as properly
    And Select Pricing Service SSH and click the Search button
    Then The records should be displayed as properly
    And Navigate to CLIENT PROFILE - HEDGE TRADE page
    And Enter a valid FUND and INSTRUMENT and click the subscribe button
    Then The detail should be read only

  Scenario Outline: AC4: GM role Read only (IT Support)
    Given Login to UI with the account which has GM Read role
    When Navigate to INSTRUMENT - INSTRUMENT PROFILE page
    And Select Pricing Service SFX and click the Search button
    Then The records should be displayed as properly
    And Select Pricing Service SSH and click the Search button
    Then The records should be displayed as properly
    And Navigate to CLIENT PROFILE - HEDGE TRADE page
    And Enter a valid FUND and INSTRUMENT and click the subscribe button
    Then The detail should be read only

  Scenario Outline: AC5: GM role Read only (Risk Manager)
    Given Login to UI with the account which has GM Read role
    When Navigate to INSTRUMENT - INSTRUMENT PROFILE page
    And Select Pricing Service SFX and click the Search button
    Then The records should be displayed as properly
    And Select Pricing Service SSH and click the Search button
    Then The records should be displayed as properly
    And Navigate to CLIENT PROFILE - HEDGE TRADE page
    And Enter a valid FUND and INSTRUMENT and click the subscribe button
    Then The detail should be read only

  Scenario Outline: AC6: GM role Read/Edit only (Middle Office)
    Given Login to UI with the account which has GM Read/Edit role
    When Navigate to INSTRUMENT - INSTRUMENT PROFILE page
    And Select Pricing Service SFX and click the Search button
    Then The records should be displayed ad properly
    And Select Pricing Service SSH and click the Search button
    Then The records should be displayed as properly
    And Navigate to CLIENT PROFILE - HEDGE TRADE page
    And Enter a valid FUND and INSTRUMENT and click the subscribe button
    Then The detail should be editable as properly

  Scenario Outline: AC7: GM and GS role Read only (Sales Trader & Global Service)
    Given Login to UI with the account which has GM and GS Read role
    When Navigate to INSTRUMENT - INSTRUMENT PROFILE page
    And Select Pricing Service SFX and click the Search button
    Then The records should be displayed as properly
    And Select Pricing Service SSH and click the Search button
    Then The records should be displayed as properly
    And Navigate to CLIENT PROFILE - HEDGE TRADE page
    And Enter a valid FUND and INSTRUMENT and click the subscribe button
    Then The detail should be read only

  Scenario Outline: AC8: GM Read/Edit role and GS Read role (Middle Office & Global Service)
    Given Login to UI with the account which has GM Read/Edit role and GS Read role
    When Navigate to INSTRUMENT - INSTRUMENT PROFILE page
    And Select Pricing Service SFX and click the Search button
    Then The records should be displayed ad properly
    And Select Pricing Service SSH and click the Search button
    Then The records should be displayed as properly
    And Navigate to CLIENT PROFILE - HEDGE TRADE page
    And Enter a valid FUND and INSTRUMENT and click the subscribe button
    Then The detail should be editable as properly
